package de.jaylawl.expressionsplus.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.destroystokyo.paper.event.block.BeaconEffectEvent;

@SuppressWarnings("unchecked")
public class EvtSimple {
    static {
        Skript.registerEvent("Swap Hand Items", SimpleEvent.class, PlayerSwapHandItemsEvent.class, "swap [hand] item[s]");
        if (Skript.classExists("com.destroystokyo.paper.event.player.PlayerJumpEvent")) {
            Skript.registerEvent("Jump", SimpleEvent.class, PlayerJumpEvent.class, "jump");
        }
        /*if (Skript.classExists("com.destroystokyo.paper.event.block.BeaconEffectEvent")) {
            Skript.registerEvent("Beacon Tick", SimpleEvent.class, BeaconEffectEvent.class, "beacon [effect ](tick|pulse)");
        }*/
    }
}