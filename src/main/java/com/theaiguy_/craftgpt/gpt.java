package com.theaiguy_.craftgpt;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.theaiguy_.craftgpt.CraftGPT.config;
import static com.theaiguy_.craftgpt.CraftGPT.getPlugin;

public class gpt implements CommandExecutor
{
    public static final HashMap<String, List<ChatMessage>> messages = new HashMap<>();
    private static HashMap<String, Long> cooldowns = new HashMap<>();
    static Long cooldownMs = config.getLong("cooldown");
    static String token = config.getString("token");


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        long time = cooldowns.getOrDefault(sender.getName(), -cooldownMs) + cooldownMs;
        if (time > System.currentTimeMillis() && !sender.hasPermission("craftgpt.cooldown"))
        {
            sender.sendMessage(ChatColor.DARK_RED + Objects.requireNonNull(config.getString("messages.cooldown")).replace("%time%", String.valueOf((int) ((time - System.currentTimeMillis()) / 1000))));
            return true;
        }
        cooldowns.put(sender.getName(), System.currentTimeMillis());

        List<ChatMessage> snapshot = messages.get(sender.getName());

        messages.computeIfAbsent(sender.getName(), k -> new ArrayList<>());
        ChatMessage message = new ChatMessage("user", String.join(" ", args));
        messages.get(sender.getName()).add(message);

        sender.sendMessage(ChatColor.GRAY + config.getString("messages.generating"));

        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () ->
        {
            try
            {
                OpenAiService service = new OpenAiService(Objects.requireNonNull(token), Duration.ofMinutes(3));
                ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                        .messages(messages.get(sender.getName()))
                        .model(config.getString("model"))
                        .build();
                ChatMessage result = service.createChatCompletion(completionRequest).getChoices().get(0).getMessage();
                sender.sendMessage(result.getContent());
                messages.get(sender.getName()).add(result);
            }
            catch (Exception e)
            {
                messages.put(sender.getName(), snapshot);
                sender.sendMessage(ChatColor.DARK_RED + config.getString("messages.error"));
                if (config.getBoolean("show-errors")) throw e;
            }
        });
        return true;
    }
}