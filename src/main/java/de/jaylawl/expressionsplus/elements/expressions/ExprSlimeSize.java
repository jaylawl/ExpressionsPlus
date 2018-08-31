package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;

public class ExprSlimeSize extends SimplePropertyExpression<Entity, Number> {

    static {
        register(ExprSlimeSize.class, Number.class, "[slime( |-)]size[s]", "entities");
    }

    @Override
    @Nullable
    public Number convert(Entity entity) {
        if (entity.getType() == EntityType.SLIME) {
            return ((Slime) entity).getSize();
        }
        return null;
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.SET)
            return CollectionUtils.array(Number.class);
        return null;
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, ChangeMode mode) {
        assert delta != null;
        int value = ((Number) delta[0]).intValue();

        for (Entity entity : getExpr().getArray(e)) {
            if (entity.getType() == EntityType.SLIME) {
                Slime slime = ((Slime) entity);
                switch (mode) {
                    case ADD:
                        slime.setSize(Math.max(0, slime.getSize() + value));
                        break;
                    case REMOVE:
                        slime.setSize(Math.max(0, slime.getSize() - value));
                        break;
                    case SET:
                        slime.setSize(Math.max(0, value));
                        break;
                    default:
                        assert false;
                }
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "size";
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

}
