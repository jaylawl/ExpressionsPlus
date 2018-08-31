package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;

public class ExprLeashHolder extends SimplePropertyExpression<LivingEntity, Entity> {

    static {
        register(ExprLeashHolder.class, Entity.class, "(leash|lead) holder[s]", "entities");
    }

    @Override
    @Nullable
    public Entity convert(LivingEntity entity) {
        if (entity.isLeashed()) {
            return entity.getLeashHolder();
        }
        return null;
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.SET)
            return CollectionUtils.array(Entity.class);
        return null;
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, ChangeMode mode) {
        assert delta != null;
        Entity value = ((Entity) delta[0]);

        for (LivingEntity entity : getExpr().getArray(e)) {
            switch (mode) {
                case SET:
                    entity.setLeashHolder(value);
                    break;
                default:
                    assert false;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "leash holder";
    }

    @Override
    public Class<? extends Entity> getReturnType() {
        return Entity.class;
    }

}
