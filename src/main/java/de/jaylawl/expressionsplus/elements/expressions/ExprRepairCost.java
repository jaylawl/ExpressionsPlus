package de.jaylawl.expressionsplus.elements.expressions;

import ch.njol.skript.Skript;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.inventory.InventoryView;

import javax.annotation.Nullable;

public class ExprRepairCost extends SimplePropertyExpression<Player, Number> {

    static {
        register(ExprRepairCost.class, Number.class, "[current] [item( |-)](repair|enchant) cost[s]", "players");
    }

    @Override
    @Nullable
    public Number convert(Player player) {
        Skript.error("The current repair cost of a player can only be set");
        return null;
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.SET || mode == ChangeMode.RESET)
            return CollectionUtils.array(Number.class);
        return null;
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, ChangeMode mode) {
        assert delta != null;
        int value = ((Number) delta[0]).intValue();

        for (Player player : getExpr().getArray(e)) {
            switch (mode) {
                case SET:
                    player.setWindowProperty(InventoryView.Property.REPAIR_COST, value);
                    break;
                default:
                    assert false;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "repair cost";
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

}
