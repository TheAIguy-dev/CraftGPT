package com.theaiguy_.craftgpt;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.*;

import static com.theaiguy_.craftgpt.CraftGPT.*;

public class gpt implements CommandExecutor
{
    public static final HashMap<String, List<ChatMessage>> messages = new HashMap<>();
    private static final HashMap<String, Long> cooldowns = new HashMap<>();
    static Long cooldownMs = config.getLong("cooldown");
    static String token = config.getString("chatgpt.token");


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if (token == null || token.equals(""))
        {
            adventure.sender(sender).sendMessage(getFormattedString("messages.no-token"));
            return true;
        }

        long time = cooldowns.getOrDefault(sender.getName(), -cooldownMs) + cooldownMs;
        if (time > System.currentTimeMillis() && !sender.hasPermission("craftgpt.cooldown"))
        {
            try
            {
                adventure.sender(sender).sendMessage(getFormattedString("messages.cooldown", Placeholder.unparsed("time", String.valueOf((int) ((time - System.currentTimeMillis()) / 1000)))));
            }
            catch (Exception e)
            {
                adventure.sender(sender).sendMessage(getFormattedString("messages.error"));
                if (config.getBoolean("show-errors")) throw e;
            }
            return true;
        }
        cooldowns.put(sender.getName(), System.currentTimeMillis());

        List<ChatMessage> snapshot = messages.get(sender.getName());

        messages.computeIfAbsent(sender.getName(), k -> new ArrayList<>());
        ChatMessage message = new ChatMessage("user", String.join(" ", args));
        messages.get(sender.getName()).add(message);


        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () ->
        {
            try
            {
                adventure.sender(sender).sendMessage(getFormattedString("messages.generating"));

                OpenAiService service = new OpenAiService(Objects.requireNonNull(token), Duration.ofMinutes(3));
                ChatCompletionRequest.ChatCompletionRequestBuilder completionRequestBuilder = ChatCompletionRequest.builder()
                        .messages(messages.get(sender.getName()))
                        .model(config.getString("chatgpt.model"))
                        .temperature(config.getDouble("chatgpt.temperature"))
                        .topP(config.getDouble("chatgpt.top-p"))
                        .stop(config.getStringList("chatgpt.stop").isEmpty() ? null : config.getStringList("chatgpt.stop"))
                        .presencePenalty(config.getDouble("chatgpt.presence-penalty"))
                        .frequencyPenalty(config.getDouble("chatgpt.frequency-penalty"));

                if (!Objects.equals(config.getString("chatgpt.max-tokens"), "inf"))
                {
                    completionRequestBuilder.maxTokens(config.getInt("chatgpt.max-tokens"));
                }

                ChatCompletionRequest completionRequest = completionRequestBuilder.build();

                ChatMessage result = service.createChatCompletion(completionRequest).getChoices().get(0).getMessage();

                adventure.sender(sender).sendMessage(MiniMessage.miniMessage().deserialize(config.getString("messages.prefix") + result.getContent()));
                messages.get(sender.getName()).add(result);
            }
            catch (Exception e)
            {
                messages.put(sender.getName(), snapshot);
                adventure.sender(sender).sendMessage(getFormattedString("messages.error"));
                if (config.getBoolean("show-errors")) throw e;
            }
        });
        return true;
    }
}