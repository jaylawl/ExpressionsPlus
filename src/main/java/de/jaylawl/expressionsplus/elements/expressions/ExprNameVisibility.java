package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;

public class ExprNameVisibility extends SimplePropertyExpression<Entity, Boolean> {

    static {
        register(ExprNameVisibility.class, Boolean.class, "[custom] name[[ |-](plate|tag)] visibilit(y|ies)", "entities");
    }

    @Override
    @Nullable
    public Boolean convert(Entity entity) {
        return entity.isCustomNameVisible();
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
