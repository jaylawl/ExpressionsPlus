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
import java.util.UUID;

public class ExprUUIDFrom extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprUUIDFrom.class, String.class, ExpressionType.SIMPLE,
                "uuid (from|with) %number% (and [with]|to) %number%");
    }

    private Expression<Number> a;
    private Expression<Number> b;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        this.a = (Expression<Number>) exprs[0];
        this.b = (Expression<Number>) exprs[1];
        return true;
    }

    @Override
    @Nullable
    protected String[] get(Event e) {
        long most = a.getSingle(e).longValue();
        long least = b.getSingle(e).longValue();
        return CollectionUtils.array(new UUID(most, least).toString());
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
        return "UUID from";
    }

}
