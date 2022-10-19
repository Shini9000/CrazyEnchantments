package com.badbones69.crazyenchantments.api.events;

import com.badbones69.crazyenchantments.api.enums.CEnchantments;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AuraActiveEvent extends Event {
    
    private static final HandlerList handlers = new HandlerList();
    private final Player other;
    private final Player player;
    private final int level;
    private final CEnchantments enchantment;
    
    /**
     * This event fires when a player walks near another player with an aura enchantment on.
     * @param player player that has the aura enchantment on.
     * @param other The player that will be effected by it.
     * @param enchantment The aura enchantment that is activated.
     * @param level The power of the aura enchantment.
     */
    public AuraActiveEvent(Player player, Player other, CEnchantments enchantment, int level) {
        this.player = player;
        this.other = other;
        this.enchantment = enchantment;
        this.level = level;
    }
    
    /**
     * Gets the player that is being effected by the aura enchantment.
     * @return The player with the enchantment.
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * The player being effected by the aura enchantment.
     * @return The player that is being targeted.
     */
    public Player getOther() {
        return other;
    }
    
    /**
     * The aura enchantment that is on the player.
     * @return The aura enchantment being used.
     */
    public CEnchantments getEnchantment() {
        return enchantment;
    }
    
    /**
     * The power of the aura enchantment.
     * @return The power of the aura enchantment that is being used.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets a list of handlers handling this event.
     *
     * @return A list of handlers handling this event.
     */
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}