package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;

public class ExprWolfAnger extends SimplePropertyExpression<Entity, Boolean> {

    static {
        register(ExprWolfAnger.class, Boolean.class, "ang(er|ry) (state|value)", "entity");
    }

    @Override
    @Nullable
    public Boolean convert(Entity entity) {
        if (entity.getType() == EntityType.WOLF) {
            return ((Wolf) entity).isAngry();
        }
        return null;
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if (mode == ChangeMode.SET)
            return CollectionUtils.array(Boolean.class);
        return null;
    }

    @Override
    public void change(Event event, Object[] delta, ChangeMode mode){
        if ((delta != null) && (getExpr().getSingle(event) != null)) {
            Entity entity = getExpr().getSingle(event);
            if (entity.getType() == EntityType.WOLF) {
                Boolean value = ((Boolean) delta[0]);
                switch (mode) {
                    case SET:
                        ((Wolf) entity).setAngry(value);
                        break;
                    default:
                        assert false;
                }
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "anger state";
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }
}
