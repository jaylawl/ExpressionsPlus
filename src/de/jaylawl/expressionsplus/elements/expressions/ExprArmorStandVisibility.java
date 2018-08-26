package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.ArmorStand;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.coll.CollectionUtils;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

public class ExprArmorStandVisibility extends SimplePropertyExpression<ArmorStand, Boolean> {

    static {
        register(ExprArmorStandVisibility.class, Boolean.class, "(0¦visibility|1¦arm( |-)visibility|2¦[base( |-)]plate( |-)visibility)", "entity");
    }

    private int mark;

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
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
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if (mode == ChangeMode.SET)
            return CollectionUtils.array(Boolean.class);
        return null;
    }

    @Override
    public void change(Event event, Object[] delta, ChangeMode mode){
        if (delta != null) {
            ArmorStand entity = getExpr().getSingle(event);
            Boolean value = ((Boolean) delta[0]);
            switch (mode) {
                case SET:
                    if (mark == 0) {
                        entity.setVisible(value);
                    }
                    else if (mark == 1) {
                        entity.setArms(value);
                    }
                    else {
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
        return "full/arm/baseplate visibility";
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }
}
