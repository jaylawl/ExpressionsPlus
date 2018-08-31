package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;

public class ExprRiches extends SimplePropertyExpression<Entity, Number> {

    static {
        register(ExprRiches.class, Number.class, "riches", "entities");
    }

    @Override
    @Nullable
    public Number convert(Entity entity) {
        if (entity.getType() == EntityType.VILLAGER) {
            return ((Villager) entity).getRiches();
        }
        return null;
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.SET || mode == ChangeMode.RESET)
            return CollectionUtils.array(Number.class);
        return null;
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, ChangeMode mode) {
        int value = delta == null ? -1 : ((Number) delta[0]).intValue();

        for (Entity entity : getExpr().getArray(e)) {
            if (entity.getType() == EntityType.VILLAGER) {
                Villager villager = (Villager) entity;
                switch (mode) {
                    case ADD:
                        villager.setRiches(villager.getRiches() + value);
                        break;
                    case REMOVE:
                        villager.setRiches(villager.getRiches() - value);
                        break;
                    case SET:
                        villager.setRiches(value);
                        break;
                    case RESET:
                        villager.setRiches(0);
                        break;
                    default:
                        assert false;
                }
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "villager riches";
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

}
