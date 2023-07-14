package com.theaiguy_.craftgpt;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.theaiguy_.craftgpt.CraftGPT.adventure;
import static com.theaiguy_.craftgpt.CraftGPT.getFormattedString;

public class newchat implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        gpt.messages.put(sender.getName(), new ArrayList<>());
        adventure.sender(sender).sendMessage(getFormattedString("messages.chat-cleared"));
        return true;
    }
}

