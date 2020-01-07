package com.github.judgetread.GriefPreventionQuickShopBridge.listeners;

import com.github.judgetread.GriefPreventionQuickShopBridge.GriefPreventionQuickShopBridge;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author Judgetread
 */
@AllArgsConstructor
public class MainCommandExecutor implements org.bukkit.command.CommandExecutor {

    /** Plugin instance */
    private GriefPreventionQuickShopBridge plugin;

    /**
     * @param sender CommandSender
     * @param command Command
     * @param label String
     * @param args String[]
     * @return True if handled, false if not handled
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // Return if there is no sub command
        if (args.length <= 0) {
            return false;
        }

        switch (args[0].toUpperCase()) {

            /**Help/Version Sub command */
            case "VERSION":
            case "HELP":
                if (!sender.hasPermission("GriefPreventionQuickShopBridge.help")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getCommand("GriefPreventionQuickShopBridge").getPermissionMessage()));
                    return true;
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[===== &aGriefPreventionQuickShopBridge &e=====]"));
                String authors = "";
                for (String str : plugin.getDescription().getAuthors()) {
                    String tmp = "";
                    if (!authors.isEmpty()) {
                        tmp = ", ";
                    }
                    authors = authors + tmp + str;
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&f*] &aAuthors: &b" + authors));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&f*] &aVersion: &b" + plugin.getDescription().getVersion().toString()));
                return true;

            /** Reload Sub-command */
            case "RELOAD":
                if (!sender.hasPermission("GriefPreventionQuickShopBridge.reload")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getCommand("GriefPreventionQuickShopBridge").getPermissionMessage()));
                    return true;
                }
                plugin.reload();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&f*] &aGriefPreventionQuickShopBridge Reload Complete"));
                return true;
            default:
                return false;
        }

    }
}
