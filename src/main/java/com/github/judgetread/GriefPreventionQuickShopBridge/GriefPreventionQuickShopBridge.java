package com.github.judgetread.GriefPreventionQuickShopBridge;

import com.github.judgetread.GriefPreventionQuickShopBridge.listeners.GriefPreventionListener;
import com.github.judgetread.GriefPreventionQuickShopBridge.listeners.QuickShopListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;

@Getter(AccessLevel.PUBLIC)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GriefPreventionQuickShopBridge extends JavaPlugin {

    /**
     * The active instance of GriefPreventionQuickShopBridge
     */
    private static GriefPreventionQuickShopBridge instance;

    /**
     * The plugin GriefPrevention(null if not present)
     */
    @Getter(AccessLevel.PUBLIC)
    @Nullable
    @NonFinal
    private Plugin griefPrevention;

    /**
     * The plugin QuickShop(null if not present)
     */
    @Getter(AccessLevel.PUBLIC)
    @Nullable
    @NonFinal
    private Plugin quickShop;


    /**
     * onLoad
     */
    @Override
    public void onLoad() {
        instance = this;
    }

    /**
     * onEnable
     */
    @Override
    public void onEnable() {
        Instant start = Instant.now();
        saveDefaultConfig();
        reloadConfig();
        startup();
        Bukkit.getConsoleSender().sendMessage(" Loaded! " + Duration.between(Instant.now(), start).toMillis() + "ms");
    }

    /**
     * Reload this plugin.
     */
    private void reload() {
        Bukkit.getConsoleSender().sendMessage(" Reloading " + getName());
        unregisterListeners();
        unloadHooks();
        reloadConfig();
        startup();
    }

    /**
     * Startup procedures.
     */
    private void startup() {
        loadHooks();
        if(griefPrevention == null || quickShop == null){
            Bukkit.getConsoleSender().sendMessage(" Missing dependency of either GriefPrevention or QuickShop");
            Bukkit.getConsoleSender().sendMessage(" Unable to ");
            this.getPluginLoader().disablePlugin(this);
        }
        registerListeners();
    }

    /**
     * Register Listeners.
     */
    private void registerListeners() {
        Bukkit.getConsoleSender().sendMessage(" Registering Listeners...");
        Bukkit.getPluginManager().registerEvents(new GriefPreventionListener(this), this);
        Bukkit.getPluginManager().registerEvents(new QuickShopListener(this), this);
    }

    /**
     * Unregister Listeners.
     */
    private void unregisterListeners() {
        Bukkit.getConsoleSender().sendMessage(" Unregistering Listeners...");
        HandlerList.unregisterAll(this);
    }

    /**
     * Reload config
     */
    @SuppressWarnings("EmptyMethod")
    @Override
    public void reloadConfig() {
        super.reloadConfig();
    }

    /**
     * Load hooks.
     */
    private void loadHooks() {
        Bukkit.getConsoleSender().sendMessage(" Loading Hooks...");
        griefPrevention = hookPlugin("GriefPrevention");
        quickShop = hookPlugin("QuickShop");
    }

    /**
     * Unload hooks
     */
    private void unloadHooks() {
        Bukkit.getConsoleSender().sendMessage(" Unloading Hooks...");
        this.griefPrevention = null;
        this.quickShop = null;
    }

    /**
     * Get the plugin from plugin manager with the
     * given name.
     * <p>
     * Returns null, if none found.
     *
     * @param pluginName String name of the plugin.
     * @return Plugin
     */
    private @Nullable Plugin hookPlugin(@NotNull String pluginName) {
        @Nullable Plugin thirdPlugin = Bukkit.getPluginManager().getPlugin(pluginName);
        if (thirdPlugin != null) {
            Bukkit.getConsoleSender().sendMessage(" Hooked: " + pluginName);
        }
        return thirdPlugin;
    }
}
