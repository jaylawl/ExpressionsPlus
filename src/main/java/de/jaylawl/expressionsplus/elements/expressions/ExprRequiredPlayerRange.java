package de.jaylawl.expressionsplus.elements.expressions;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprRequiredPlayerRange extends SimplePropertyExpression<Block, Number> {

    static {
        register(ExprRequiredPlayerRange.class, Number.class, "required [player( |-)]range[s]", "blocks");
    }

    @Override
    @Nullable
    public Number convert(Block block) {
        if (block.getType() != Material.SPAWNER)
            return null;
        BlockState state = block.getState();
        CreatureSpawner spawner = (CreatureSpawner) state;
        return spawner.getRequiredPlayerRange();
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.SET || mode == ChangeMode.RESET)
            return CollectionUtils.array(Number.class);
        return null;
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, ChangeMode mode) {
        int value = delta == null ? -1 : ((Number) delta[0]).intValue();

        for (Block block : getExpr().getArray(e)) {
            if (block.getType() == Material.SPAWNER) {
                BlockState state = block.getState();
                CreatureSpawner spawner = (CreatureSpawner) state;
                switch (mode) {
                    case ADD:
                        spawner.setRequiredPlayerRange(spawner.getRequiredPlayerRange() + value);
                        break;
                    case REMOVE:
                        spawner.setRequiredPlayerRange(spawner.getRequiredPlayerRange() - value);
                        break;
                    case SET:
                        spawner.setRequiredPlayerRange(value);
                        break;
                    case RESET:
                        spawner.setRequiredPlayerRange(16);
                        break;
                    default:
                        assert false;
                }
                spawner.update();
            }
        }

    }

    @Override
    protected String getPropertyName() {
        return "required player range";
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

}