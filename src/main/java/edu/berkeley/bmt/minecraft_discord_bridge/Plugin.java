package edu.berkeley.bmt.minecraft_discord_bridge;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import club.minnced.discord.webhook.WebhookClient;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Plugin extends JavaPlugin {
    private FileConfiguration config = getConfig();
    private JDA jda;
    private DiscordListener discordListener;
    private MinecraftListener minecraftListener;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        discordListener = new DiscordListener(this, config.getString("channel-id"));
        jda = JDABuilder.createDefault(config.getString("bot-token"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(discordListener)
                .build();
        jda.getRestPing().queue(ping -> getLogger().info("Connected to Discord with ping: " + ping));
        minecraftListener = new MinecraftListener(jda, WebhookClient.withUrl(config.getString("webhook-url")));
        getServer().getPluginManager().registerEvents(minecraftListener, this);
    }

    @Override
    public void onDisable() {
        jda.shutdown();
    }
}
