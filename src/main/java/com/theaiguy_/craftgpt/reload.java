package com.theaiguy_.craftgpt;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static com.theaiguy_.craftgpt.CraftGPT.config;

public class reload implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        try
        {
            gpt.token = config().getString("token");
            gpt.cooldownMs = config().getLong("cooldown");
            sender.sendMessage(ChatColor.GRAY + config().getString("messages.reload"));
        }
        catch (Exception e)
        {
            sender.sendMessage(ChatColor.DARK_RED + config().getString("messages.error"));
            if (config().getBoolean("show-errors")) throw e;
        }
        return true;
    }
}
