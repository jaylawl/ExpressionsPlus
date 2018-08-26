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

    Expression<LivingEntity> e1;
    Expression<LivingEntity> e2;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        this.e1 = (Expression<LivingEntity>) expressions[0];
        this.e2 = (Expression<LivingEntity>) expressions[1];
        setNegated(parser.mark == 1);
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "has line of sight";
    }

    @Override
    public boolean check(Event event) {
        LivingEntity a = e1.getSingle(event);
        LivingEntity b = e2.getSingle(event);
        if (a == null || b == null) {
            return isNegated();
        }
        return a.hasLineOfSight(b) ? isNegated() : !isNegated();
    }

}