package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.Rotation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;

import javax.annotation.Nullable;

public class ExprItemFrameRotation extends SimplePropertyExpression<Entity, Number> {

    static {
        register(ExprItemFrameRotation.class, Number.class, "[display( |-)]item rotation[s]", "entities");
    }

    @Override
    @Nullable
    public Number convert(Entity entity) {
        if (entity.getType() == EntityType.ITEM_FRAME) {
            Rotation rot = ((ItemFrame) entity).getRotation();
            return rot.ordinal();
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
            if (entity.getType() == EntityType.ITEM_FRAME) {
                ItemFrame frame = ((ItemFrame) entity);
                int current = frame.getRotation().ordinal();
                switch (mode) {
                    case ADD:
                        frame.setRotation(Rotation.values()[((current + value) % 8)]);
                        break;
                    case REMOVE:
                        frame.setRotation(Rotation.values()[((current + (8 - (value % 8))) % 8)]);
                        break;
                    case SET:
                        frame.setRotation(Rotation.values()[Math.max(0, Math.min(7, value))]);
                        break;
                    case RESET:
                        frame.setRotation(Rotation.NONE);
                        break;
                    default:
                        assert false;
                }
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "display item rotation";
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

}
