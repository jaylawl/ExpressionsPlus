package de.jaylawl.expressionsplus.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import com.destroystokyo.paper.event.entity.EntityPathfindEvent;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;

@SuppressWarnings("unchecked")
public class EvtSimple {
    static {
        Skript.registerEvent("Armor Stand Manipulate", SimpleEvent.class, PlayerArmorStandManipulateEvent.class, "armor( |-)stand manipulat(e|ing|ion)");
        Skript.registerEvent("Swap Hand Items", SimpleEvent.class, PlayerSwapHandItemsEvent.class, "swap[ping of] [hand( |-)]item[s]");
        if (Skript.classExists("com.destroystokyo.paper.event.entity.EntityPathfindEvent")) {
            Skript.registerEvent("Entity Pathfind", SimpleEvent.class, EntityPathfindEvent.class, "entity( |-)pathfind[ing]");
        }
        if (Skript.classExists("com.destroystokyo.paper.event.player.PlayerJumpEvent")) {
            Skript.registerEvent("Jump", SimpleEvent.class, PlayerJumpEvent.class, "jump[ing]");
        }
    }
}