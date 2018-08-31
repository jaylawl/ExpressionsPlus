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
        register(ExprWolfAnger.class, Boolean.class, "ang(er|ry) (state|value)[s]", "entities");
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
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.SET)
            return CollectionUtils.array(Boolean.class);
        return null;
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, ChangeMode mode) {
        assert delta != null;
        boolean value = ((Boolean) delta[0]);

        for (Entity entity : getExpr().getArray(e)) {
            if (entity.getType() == EntityType.WOLF) {
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
