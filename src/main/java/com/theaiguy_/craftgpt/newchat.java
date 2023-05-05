package com.theaiguy_.craftgpt;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.theaiguy_.craftgpt.CraftGPT.config;

public class newchat implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        gpt.messages.put(sender.getName(), new ArrayList<>());
        sender.sendMessage(ChatColor.GRAY + config.getString("messages.chat-cleared"));
        return true;
    }
}

