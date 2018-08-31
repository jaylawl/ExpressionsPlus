package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.Ageable;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;

public class ExprAge extends SimplePropertyExpression<Ageable, Number> {

    static {
        register(ExprAge.class, Number.class, "age", "entities");
    }

    @Override
    @Nullable
    public Number convert(Ageable entity) {
        return entity.getAge();
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.SET)
            return CollectionUtils.array(Number.class);
        return null;
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, ChangeMode mode) {
        if (delta != null) {
            int value = ((Number) delta[0]).intValue();
            for (Ageable entity : getExpr().getArray(e)) {
                switch (mode) {
                    case ADD:
                        entity.setAge(entity.getAge() + value);
                        break;
                    case REMOVE:
                        entity.setAge(entity.getAge() - value);
                        break;
                    case SET:
                        entity.setAge(value);
                        break;
                    default:
                        assert false;
                }
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "age";
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

}
