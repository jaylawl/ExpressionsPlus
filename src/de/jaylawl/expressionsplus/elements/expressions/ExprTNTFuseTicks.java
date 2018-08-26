package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;

public class ExprTNTFuseTicks extends SimplePropertyExpression<Entity, Number> {

    static {
        register(ExprTNTFuseTicks.class, Number.class, "[remaining( |-)]fuse ticks", "entity");
    }

    @Override
    @Nullable
    public Number convert(Entity entity) {
        if (entity.getType() == EntityType.PRIMED_TNT) {
            return ((TNTPrimed) entity).getFuseTicks();
        }
        return null;
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.SET)
            return CollectionUtils.array(Number.class);
        return null;
    }

    @Override
    public void change(Event event, Object[] delta, ChangeMode mode){
        if ((delta != null) && (getExpr().getSingle(event) != null)) {
            Entity entity = getExpr().getSingle(event);
            if (entity.getType() == EntityType.PRIMED_TNT) {
                TNTPrimed tnt = ((TNTPrimed) entity);
                Integer v = ((Number) delta[0]).intValue();
                Integer cur = tnt.getFuseTicks();
                switch (mode) {
                    case ADD:
                        tnt.setFuseTicks(Math.max(0, cur + v));
                        break;
                    case REMOVE:
                        tnt.setFuseTicks(Math.max(0, cur - v));
                        break;
                    case SET:
                        tnt.setFuseTicks(Math.max(0, v));
                        break;
                    default:
                        assert false;
                }
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "fuse ticks";
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }
}
