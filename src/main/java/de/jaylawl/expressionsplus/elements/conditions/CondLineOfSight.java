package de.jaylawl.expressionsplus.elements.conditions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import javax.annotation.Nullable;

public class CondLineOfSight extends Condition {

    static {
        Skript.registerCondition(CondLineOfSight.class,
                "%entity% (0¦(has[ got]|does have)|1¦does(n't| not) have) line of sight of %entity%",
                "%entity% (0¦is|1¦is(n't| not)) in %entity%'s line of sight");
    }

    private Expression<LivingEntity> e1;
    private Expression<LivingEntity> e2;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        this.e1 = (Expression<LivingEntity>) exprs[0];
        this.e2 = (Expression<LivingEntity>) exprs[1];
        setNegated(parser.mark == 1);
        return true;
    }

    @Override
    public boolean check(Event e) {
        return e1.check(e,
                entity1 -> e2.check(e,
                        entity1::hasLineOfSight
                ), isNegated());
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return e1.toString(e, debug) + " has line of sight of " + e2.toString(e, debug);
    }

}