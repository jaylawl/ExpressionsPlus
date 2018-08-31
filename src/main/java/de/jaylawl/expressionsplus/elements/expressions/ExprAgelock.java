package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.Ageable;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;

public class ExprAgelock extends SimplePropertyExpression<Ageable, Boolean> {

    static {
        register(ExprAgelock.class, Boolean.class, "age[ ]lock[s]", "entities");
    }

    @Override
    @Nullable
    public Boolean convert(Ageable entity) {
        return entity.getAgeLock();
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
            for (Ageable entity : getExpr().getArray(e)) {
                switch (mode) {
                    case SET:
                        entity.setAgeLock(value);
                        break;
                    default:
                        assert false;
                }
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "agelock";
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

}
