package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;

public class ExprItemPickupAbility extends SimplePropertyExpression<LivingEntity, Boolean> {

    static {
        register(ExprItemPickupAbility.class, Boolean.class, "item[stack] pickup[( |-)(state[s]|abilit(y|ies))]", "entities");
    }

    @Override
    @Nullable
    public Boolean convert(LivingEntity entity) {
        return entity.getCanPickupItems();
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

        for (LivingEntity entity : getExpr().getArray(e)) {
            switch (mode) {
                case SET:
                    entity.setCanPickupItems(value);
                    break;
                default:
                    assert false;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "item pickup ability";
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

}
