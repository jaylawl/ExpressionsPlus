package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;

public class ExprNameVisibility extends SimplePropertyExpression<Entity, Boolean> {

    static {
        register(ExprNameVisibility.class, Boolean.class, "[custom] name[[ |-](plate|tag)] visibility", "entity");
    }

    @Override
    @Nullable
    public Boolean convert(Entity entity) {
        return entity.isCustomNameVisible();
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if (mode == ChangeMode.SET)
            return CollectionUtils.array(Boolean.class);
        return null;
    }

    @Override
    public void change(Event event, Object[] delta, ChangeMode mode){
        if (delta != null) {
            Entity entity = getExpr().getSingle(event);
            Boolean value = ((Boolean) delta[0]);
            switch (mode) {
                case SET:
                    entity.setCustomNameVisible(value);
                    break;
                default:
                    assert false;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "name visibility";
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }
}
