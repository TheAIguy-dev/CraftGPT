package com.theaiguy_.craftgpt;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static com.theaiguy_.craftgpt.CraftGPT.*;

public class reload implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        try
        {
            main.reloadConfig();

            gpt.token = config.getString("chatgpt.token");
            gpt.cooldownMs = config.getLong("cooldown");
            adventure.sender(sender).sendMessage(getFormattedString("messages.reload"));
        }
        catch (Exception e)
        {
            adventure.sender(sender).sendMessage(getFormattedString("messages.error"));
            if (config.getBoolean("show-errors")) throw e;
        }
        return true;
    }
}
