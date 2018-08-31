package de.jaylawl.expressionsplus.elements.expressions;

import ch.njol.skript.lang.util.SimpleLiteral;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;

public class LitLineBreak extends SimpleLiteral<String> {

    static {
        Skript.registerExpression(LitLineBreak.class, String.class, ExpressionType.SIMPLE, "(lb|[line( |-)]br[eak])");
    }

    public LitLineBreak() {
        super("\n", true);
    }

    @Override
    public String toString() {
        return "line break";
    }
}
