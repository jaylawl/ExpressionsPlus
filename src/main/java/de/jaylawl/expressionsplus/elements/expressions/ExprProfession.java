package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import javax.annotation.Nullable;

public class ExprProfession extends SimplePropertyExpression<Villager, Profession> {

    static {
        register(ExprProfession.class, Profession.class, "profession[s]", "entities");
    }

    @Override
    @Nullable
    public Profession convert(Villager entity) {
        return entity.getProfession();
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.SET)
            return CollectionUtils.array(Profession.class);
        return null;
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, ChangeMode mode) {
        assert delta != null;
        Profession value = ((Profession) delta[0]);

        for (Villager entity : getExpr().getArray(e)) {
            switch (mode) {
                case SET:
                    entity.setProfession(value);
                    break;
                default:
                    assert false;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "profession";
    }

    @Override
    public Class<? extends Profession> getReturnType() {
        return Profession.class;
    }

}
