package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.event.Event;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import static java.util.UUID.randomUUID;

public class ExprUUIDRandom extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprUUIDRandom.class, String.class, ExpressionType.SIMPLE,"[a ][new ]random uuid");
    }

    @Override
    @Nullable
    protected String[] get(Event event) {
        return new String[] {randomUUID().toString()};
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
        return "random UUID";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

}
