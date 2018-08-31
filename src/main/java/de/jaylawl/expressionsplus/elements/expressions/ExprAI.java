package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;

public class ExprAI extends SimplePropertyExpression<LivingEntity, Boolean> {

    static {
        register(ExprAI.class, Boolean.class, "ai", "entities");
    }

    @Override
    @Nullable
    public Boolean convert(LivingEntity entity) {
        return entity.hasAI();
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.SET)
            return CollectionUtils.array(Boolean.class);
        return null;
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, ChangeMode mode) {
        if (delta != null) {
            boolean value = ((Boolean) delta[0]);
            for (LivingEntity entity : getExpr().getArray(e)) {
                switch (mode) {
                    case SET:
                        entity.setAI(value);
                        break;
                    default:
                        assert false;
                }
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "ai";
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

}
