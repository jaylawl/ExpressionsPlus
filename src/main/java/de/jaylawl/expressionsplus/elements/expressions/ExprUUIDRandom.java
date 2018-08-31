package de.jaylawl.expressionsplus.elements.expressions;

import ch.njol.util.coll.CollectionUtils;
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
        Skript.registerExpression(ExprUUIDRandom.class, String.class, ExpressionType.SIMPLE, "[a] [new] random uuid");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        return true;
    }

    @Override
    @Nullable
    protected String[] get(Event e) {
        return CollectionUtils.array(randomUUID().toString());
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "random UUID";
    }

}
