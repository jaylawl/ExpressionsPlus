package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Creeper;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;

public class ExprMaxFuseTicks extends SimplePropertyExpression<Entity, Number> {

    static {
        register(ExprMaxFuseTicks.class, Number.class, "max[imum] fuse ticks", "entity");
    }

    @Override
    @Nullable
    public Number convert(Entity entity) {
        if (entity.getType() == EntityType.CREEPER) {
            return ((Creeper) entity).getMaxFuseTicks();
        }
        return null;
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.SET || mode == ChangeMode.RESET)
            return CollectionUtils.array(Number.class);
        return null;
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, ChangeMode mode) {
        int value = delta == null ? -1 : ((Number) delta[0]).intValue();

        for (Entity entity : getExpr().getArray(e)) {
            if (entity.getType() == EntityType.CREEPER) {
                Creeper creeper = ((Creeper) entity);
                switch (mode) {
                    case ADD:
                        creeper.setMaxFuseTicks(Math.max(0, creeper.getMaxFuseTicks() + value));
                        break;
                    case REMOVE:
                        creeper.setMaxFuseTicks(Math.max(0, creeper.getMaxFuseTicks() - value));
                        break;
                    case SET:
                        creeper.setMaxFuseTicks(Math.max(0, value));
                        break;
                    case RESET:
                        creeper.setMaxFuseTicks(30);
                        break;
                    default:
                        assert false;
                }
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "maximum fuse ticks";
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

}
