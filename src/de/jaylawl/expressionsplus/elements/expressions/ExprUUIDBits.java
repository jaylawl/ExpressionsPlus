package de.jaylawl.expressionsplus.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import java.util.UUID;

import static java.util.UUID.fromString;

public class ExprUUIDBits extends SimplePropertyExpression<String, Number> {

    static {
        register(ExprUUIDBits.class, Number.class, "(0¦most|1¦least)[ significant][ bits]", "string");
    }

    private int mark;

    @Override
    public boolean init(final Expression<?>[] expressions, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        super.init(expressions, matchedPattern, isDelayed, parseResult);
        mark = parseResult.mark;
        return true;
    }

    @Override
    @Nullable
    public Number convert(String s) {
        if (s.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
            UUID uuid = fromString(s);
            return mark == 0 ? uuid.getMostSignificantBits() : uuid.getLeastSignificantBits();
        }
        return null;
    }

    @Override
    protected String getPropertyName() {
        return "most/least bits of UUID";
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }
}
