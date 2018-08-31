package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

public class ExprBreak extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprBreak.class, String.class, ExpressionType.SIMPLE, "(lb|[line( |-)]br[eak])");
    }

    @Override
    @Nullable
    protected String[] get(Event event) {
        return new String[] {"\n"};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "line break";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

}
