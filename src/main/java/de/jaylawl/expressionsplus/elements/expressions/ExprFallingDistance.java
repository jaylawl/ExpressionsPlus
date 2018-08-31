package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;

public class ExprFallingDistance extends SimplePropertyExpression<Entity, Number> {

    static {
        register(ExprFallingDistance.class, Number.class, "fall[ing] distance[s]", "entities");
    }

    @Override
    @Nullable
    public Number convert(Entity entity) {
        return entity.getFallDistance();
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.SET || mode == ChangeMode.RESET)
            return CollectionUtils.array(Number.class);
        return null;
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, ChangeMode mode) {
        float value = delta == null ? -1 : ((Number) delta[0]).floatValue();

        for (Entity entity : getExpr().getArray(e)) {
            switch (mode) {
                case ADD:
                    entity.setFallDistance(entity.getFallDistance() + value);
                    break;
                case REMOVE:
                    entity.setFallDistance(entity.getFallDistance() - value);
                    break;
                case SET:
                    entity.setFallDistance(value);
                    break;
                case RESET:
                    entity.setFallDistance(0);
                    break;
                default:
                    assert false;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "falling distance";
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

}
