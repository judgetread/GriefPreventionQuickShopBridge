package com.github.judgetread.GriefPreventionQuickShopBridge.listeners;

import com.github.judgetread.GriefPreventionQuickShopBridge.GriefPreventionQuickShopBridge;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.events.ClaimDeletedEvent;
import me.ryanhamshire.GriefPrevention.events.ClaimExpirationEvent;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.maxgamer.quickshop.Event.ShopPreCreateEvent;
import org.maxgamer.quickshop.QuickShop;
import org.maxgamer.quickshop.Shop.ContainerShop;
import org.maxgamer.quickshop.Shop.Shop;

import java.util.Iterator;

@AllArgsConstructor
public class GriefPreventionListener implements Listener {

    /**
     * The instance of GriefPreventionQuickShopBridge
     */
    @NonNull
    private final GriefPreventionQuickShopBridge plugin;

    /**
     * GriefPrevention Claim Delete Event
     *
     * @param event ClaimDeletedEvent
     */
    @EventHandler
    public void onClaimDeleteEvent(ClaimDeletedEvent event) {
        if(plugin.getConfig().getBoolean("delete-shops-when-claims-deleted", true)) {
            deleteAllShopsInClaim(event.getClaim());
        }
    }

    /**
     * GriefPrevention Claim Expire Event
     *
     * @param event ClaimExpirationEvent
     */
    @EventHandler
    public void onClaimExpireEvent(ClaimExpirationEvent event) {
        if(plugin.getConfig().getBoolean("delete-shops-when-claims-expires", true)) {
            deleteAllShopsInClaim(event.getClaim());
        }
    }


    /**
     * Delete any shop found inside the claim.
     *
     * @param claim A GriefPrevention claim
     */
    private void deleteAllShopsInClaim(Claim claim) {
        if(claim == null || claim.equals(null)){
            return;
        }

        Iterator<Shop> bIt = ((QuickShop) plugin.getQuickShop()).getShopManager().getShopIterator();

        if (bIt == null) {
            return;
        }

        while (bIt.hasNext()) {
            final Location loc = bIt.next().getLocation();

            if (loc == null) {
                continue;
            }

            if (claim.contains(loc, false, false)) {
                final ContainerShop shop = (ContainerShop) ((QuickShop) plugin.getQuickShop()).getShopManager().getShop(loc);

                if (shop == null) {
                    continue;
                }

                // Try force saving of chunk ???
                //loc.getChunk().addPluginChunkTicket(plugin);
                shop.delete(false);
                //loc.getChunk().removePluginChunkTicket(plugin);
            }

        }

    }

}
