package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.ArmorStand;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;

public class ExprMarker extends SimplePropertyExpression<ArmorStand, Boolean> {

    static {
        register(ExprMarker.class, Boolean.class, "marker( |-)(state|value)[s]", "entities");
    }

    @Override
    @Nullable
    public Boolean convert(ArmorStand entity) {
        return entity.isMarker();
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

        for (ArmorStand entity : getExpr().getArray(e)) {
            switch (mode) {
                case SET:
                    entity.setMarker(value);
                    break;
                default:
                    assert false;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "marker state";
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

}
