package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Villager;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import javax.annotation.Nullable;

public class ExprTrader extends SimplePropertyExpression<Villager, HumanEntity> {

    static {
        register(ExprTrader.class, HumanEntity.class, "[current ](trader|customer)", "entity");
    }

    @Override
    @Nullable
    public HumanEntity convert(Villager entity) {
        if (entity.isTrading() == true) {
            return entity.getTrader();
        }
        return null;
    }

    @Override
    protected String getPropertyName() {
        return "trader";
    }

    @Override
    public Class<? extends HumanEntity> getReturnType() {
        return HumanEntity.class;
    }
}
