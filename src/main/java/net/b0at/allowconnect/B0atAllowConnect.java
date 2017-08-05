package net.b0at.allowconnect;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Jordin on 7/15/2017.
 * Jordin is still best hacker.
 */
public class B0atAllowConnect extends JavaPlugin implements Listener {
    private static final String B0AT_PREFIX = ChatColor.GRAY + "[" + ChatColor.BLUE + "B0AT" + ChatColor.GRAY + "] " + ChatColor.WHITE;
    private static final String PREVENTED_KICK = ChatColor.RED + "Prevented kick: " + ChatColor.WHITE;

    private static final String PREFIX = B0AT_PREFIX + PREVENTED_KICK;
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLogin(PlayerLoginEvent loginEvent) {
        if (loginEvent.getResult() == PlayerLoginEvent.Result.ALLOWED) {
            return;
        }
        boolean whitelisted = !getServer().hasWhitelist() || loginEvent.getPlayer().isWhitelisted();
        if (!loginEvent.getPlayer().isBanned() && whitelisted) {
            String kickMessage = loginEvent.getKickMessage();
            loginEvent.allow();
            this.getServer().getScheduler().scheduleSyncDelayedTask(this, () ->
                    loginEvent.getPlayer().sendMessage(PREVENTED_KICK + kickMessage), 4);
        }
    }
}