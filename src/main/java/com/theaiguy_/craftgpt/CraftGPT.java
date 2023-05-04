package com.theaiguy_.craftgpt;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CraftGPT extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        registerCommand("gpt", new gpt());
        registerCommand("newchat", new newchat());
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
    }

    public static JavaPlugin getPlugin()
    {
        return CraftGPT.getPlugin(CraftGPT.class);
    }

    public static FileConfiguration config()
    {
        return getPlugin().getConfig();
    }

    public static void registerCommand(String command, CommandExecutor executor)
    {
        Objects.requireNonNull(getPlugin().getCommand(command)).setExecutor(executor);
    }
}
