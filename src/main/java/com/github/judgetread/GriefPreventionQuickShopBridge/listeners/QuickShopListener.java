package com.github.judgetread.GriefPreventionQuickShopBridge.listeners;

import com.github.judgetread.GriefPreventionQuickShopBridge.GriefPreventionQuickShopBridge;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.maxgamer.quickshop.Event.ShopPreCreateEvent;

import java.util.Optional;

@AllArgsConstructor
public final class QuickShopListener implements Listener {

    /**
     * The instance of GriefPreventionQuickShopBridge
     */
    @NonNull
    private final GriefPreventionQuickShopBridge plugin;

    /**
     * QuickShop Remake ShopPreCreateEvent.
     *
     * <P>
     *     Check the location of when the shop is goinf to be placed,
     *     and if the shops location is not inside a claim cancel the shop creation.
     *
     *     If the shops location is inside a claim, check that the player making the
     *     shop is the same player that owns the claim, if not cancel the shop creation.
     * </P>
     *
     * @param event QuickShop ShopPreCreateEvent
     */
    @EventHandler(priority = EventPriority.HIGH)
    public final void onPreShopEvent(@NonNull ShopPreCreateEvent event) {

        if (event.isCancelled()) {
            return;
        }

        final Location location = event.getLocation();
        final Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, false, null);
        final Player player = event.getPlayer();

        if(plugin.getConfig().getBoolean("op-bypass-claim-checks", true) && player.isOp()){
            return;
        }

        if(claim == null){
            event.setCancelled(true);
            return;
        }

        if(plugin.getConfig().getBoolean("only-claim-owner-can-create-shops", true) && !claim.ownerID.toString().equals(player.getUniqueId().toString())){
            event.setCancelled(true);
            return;
        }



    }

}
