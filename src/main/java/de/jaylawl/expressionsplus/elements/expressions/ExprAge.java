package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.Ageable;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;

public class ExprAge extends SimplePropertyExpression<Ageable, Number> {

    static {
        register(ExprAge.class, Number.class, "age", "entity");
    }

    @Override
    @Nullable
    public Number convert(Ageable entity) {
        return entity.getAge();
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.SET)
            return CollectionUtils.array(Number.class);
        return null;
    }

    @Override
    public void change(Event event, Object[] delta, ChangeMode mode){
        if (delta != null) {
            Ageable entity = getExpr().getSingle(event);
            int value = ((Number) delta[0]).intValue();
            switch (mode) {
                case ADD:
                    entity.setAge(entity.getAge() + value);
                case REMOVE:
                    entity.setAge(entity.getAge() - value);
                case SET:
                    entity.setAge(value);
                    break;
                default:
                    assert false;
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
