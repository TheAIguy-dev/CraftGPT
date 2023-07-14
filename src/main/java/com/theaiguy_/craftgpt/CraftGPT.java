package com.theaiguy_.craftgpt;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class CraftGPT extends JavaPlugin
{
    public static CraftGPT main;
    public static FileConfiguration config;
    public static BukkitAudiences adventure;


    @Override
    public void onEnable()
    {
        main = this;
        reloadConfig();

        adventure = BukkitAudiences.create(this);

        registerCommand("gpt", new gpt());
        registerCommand("newchat", new newchat());
        registerCommand("craftgpt", new reload());
    }

    @Override
    public void reloadConfig()
    {
        saveDefaultConfig();
        super.reloadConfig();
        config = getConfig();
    }

    @Override
    public void onDisable()
    {
        if (adventure != null)
        {
            adventure.close();
            adventure = null;
        }
    }

    public static JavaPlugin getPlugin()
    {
        return CraftGPT.getPlugin(CraftGPT.class);
    }

    public static void registerCommand(String command, CommandExecutor executor)
    {
        Objects.requireNonNull(getPlugin().getCommand(command)).setExecutor(executor);
    }

    public static @NotNull Component getFormattedString(String path, TagResolver.Single... tagResolvers)
    {
        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize(config.getString(path), tagResolvers);
    }
}
