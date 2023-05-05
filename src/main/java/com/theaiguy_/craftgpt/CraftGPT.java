package com.theaiguy_.craftgpt;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CraftGPT extends JavaPlugin
{
    public static CraftGPT main;
    public static FileConfiguration config;

    @Override
    public void onEnable()
    {
        main = this;
        reloadConfig();

        registerCommand("gpt", new gpt());
        registerCommand("newchat", new newchat());
        registerCommand("craftgpt", new reload());
    }

    @Override
    public void reloadConfig()
    {
        super.reloadConfig();

        saveDefaultConfig();
        config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();
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

    public static void registerCommand(String command, CommandExecutor executor)
    {
        Objects.requireNonNull(getPlugin().getCommand(command)).setExecutor(executor);
    }
}
