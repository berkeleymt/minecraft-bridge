package edu.berkeley.bmt.minecraft_discord_bridge;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.dv8tion.jda.api.JDA;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class MinecraftListener implements Listener {
    private WebhookClient webhookClient;

    public MinecraftListener(JDA jda, WebhookClient webhookClient) {
        this.webhookClient = webhookClient;
    }

    private String componentToString(Component component) {
        PlainTextComponentSerializer plainSerializer = PlainTextComponentSerializer.plainText();
        return plainSerializer.serialize(component);
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        WebhookMessageBuilder builder = new WebhookMessageBuilder();
        builder.setUsername(player.getName());
        builder.setAvatarUrl(String.format("https://crafatar.com/avatars/%s?overlay", player.getUniqueId().toString()));
        builder.setContent(componentToString(event.message()));
        webhookClient.send(builder.build());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        webhookClient.send(componentToString(event.joinMessage()));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        webhookClient.send(componentToString(event.quitMessage()));
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        webhookClient.send(componentToString(event.deathMessage()));
    }
}
