package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.ArmorStand;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.coll.CollectionUtils;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

public class ExprArmorStandVisibility extends SimplePropertyExpression<ArmorStand, Boolean> {

    static {
        register(ExprArmorStandVisibility.class, Boolean.class, "(0¦visibility|1¦arm( |-)visibility|2¦[base( |-)]plate( |-)visibility)", "entities");
    }

    private int mark;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        mark = parseResult.mark;
        return true;
    }

    @Override
    @Nullable
    public Boolean convert(ArmorStand entity) {
        return mark == 0 ? entity.isVisible() : mark == 1 ? entity.hasArms() : entity.hasBasePlate();
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.SET)
            return CollectionUtils.array(Boolean.class);
        return null;
    }

    @Override
    public void change(Event event, @Nullable Object[] delta, ChangeMode mode) {
        for (ArmorStand entity : getExpr().getArray(event)) {
            switch (mode) {
                case SET:
                    assert delta != null;
                    boolean value = ((Boolean) delta[0]);
                    if (mark == 0) {
                        entity.setVisible(value);
                    } else if (mark == 1) {
                        entity.setArms(value);
                    } else {
                        entity.setBasePlate(value);
                    }
                    break;
                default:
                    assert false;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        switch (mark) {
            case 0:
                return "full visibility";
            case 1:
                return "arm visibility";
            case 2:
                return "visibility";
            default:
                assert false;
                return "visibility";
        }
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

}
