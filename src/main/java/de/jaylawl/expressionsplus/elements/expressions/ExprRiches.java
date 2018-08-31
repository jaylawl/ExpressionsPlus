package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.Villager;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;

public class ExprRiches extends SimplePropertyExpression<Villager, Number> {

    static {
        register(ExprRiches.class, Number.class, "riches", "entity");
    }

    @Override
    @Nullable
    public Number convert(Villager entity) {
        return entity.getRiches();
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.SET || mode == ChangeMode.RESET)
            return CollectionUtils.array(Number.class);
        return null;
    }

    @Override
    public void change(Event event, Object[] delta, ChangeMode mode){
        if (delta != null) {
            Villager entity = getExpr().getSingle(event);
            int value = ((Number) delta[0]).intValue();
            switch (mode) {
                case ADD:
                    entity.setRiches(entity.getRiches() + value);
                case REMOVE:
                    entity.setRiches(entity.getRiches() - value);
                case SET:
                    entity.setRiches(value);
                    break;
                case RESET:
                    entity.setRiches(0);
                    break;
                default:
                    assert false;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "villager riches";
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }
}
