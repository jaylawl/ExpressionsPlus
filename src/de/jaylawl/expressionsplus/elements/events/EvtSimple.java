package de.jaylawl.expressionsplus.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

@SuppressWarnings("unchecked")
public class EvtSimple {
    static {
        Skript.registerEvent("Swap Hand Items", SimpleEvent.class, PlayerSwapHandItemsEvent.class, "swap [hand] item[s]");
    }
}