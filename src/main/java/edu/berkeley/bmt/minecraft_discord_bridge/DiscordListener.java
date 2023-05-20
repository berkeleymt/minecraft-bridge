package edu.berkeley.bmt.minecraft_discord_bridge;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.Component;

public class DiscordListener extends ListenerAdapter {
    private Plugin plugin;
    private String channelId;

    public DiscordListener(Plugin plugin, String channelId) {
        this.plugin = plugin;
        this.channelId = channelId;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        plugin.getLogger().info("Received message: " + event.getMessage().getContentDisplay());

        if (event.getAuthor().isBot()) {
            return;
        }
        if (!event.getGuildChannel().getId().equals(channelId)) {
            return;
        }

        String msg = String.format("«%s» %s", event.getAuthor().getAsTag(), event.getMessage().getContentDisplay());
        plugin.getServer().broadcast(Component.text(msg));
    }
}
