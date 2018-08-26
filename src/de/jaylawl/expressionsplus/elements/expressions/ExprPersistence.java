package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;

public class ExprPersistence extends SimplePropertyExpression<LivingEntity, Boolean> {

    static {
        register(ExprPersistence.class, Boolean.class, "persistence( |-)(state|value|ability)", "entity");
    }

    @Override
    @Nullable
    public Boolean convert(LivingEntity entity) {
        return entity.getRemoveWhenFarAway();
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
            LivingEntity entity = getExpr().getSingle(event);
            Boolean value = ((Boolean) delta[0]);
            switch (mode) {
                case SET:
                    entity.setRemoveWhenFarAway(value);
                    break;
                default:
                    assert false;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "persistence";
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }
}
