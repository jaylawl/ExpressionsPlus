package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.event.Event;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import java.util.UUID;

public class ExprUUIDFrom extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprUUIDFrom.class, String.class, ExpressionType.SIMPLE,"uuid (from|with) %number% (and[ with]|to) %number%");
    }

    Expression<Number> a;
    Expression<Number> b;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        this.a = (Expression<Number>) expressions[0];
        this.b = (Expression<Number>) expressions[1];
        return true;
    }

    @Override
    @Nullable
    protected String[] get(Event event) {
        long most = a.getSingle(event).longValue();
        long least = b.getSingle(event).longValue();
        return new String[] {new UUID(most, least).toString()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "UUID from";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

}
