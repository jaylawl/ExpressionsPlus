package de.jaylawl.expressionsplus.elements.expressions;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprSpawnerDelay extends SimplePropertyExpression<Block, Number> {

    static {
        register(ExprSpawnerDelay.class, Number.class, "(0¦current|1¦min[imum]|2¦max[imum]) [spawn( |-)](delay|cooldown)", "block");
    }

    private int mark;

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        mark = parseResult.mark;
        return true;
    }

    @Override
    @Nullable
    public Number convert(Block block) {
        if (block.getType() != Material.SPAWNER)
            return null;
        BlockState state = block.getState();
        CreatureSpawner spawner = (CreatureSpawner) state;
        return mark == 0 ? spawner.getDelay() : mark == 1 ? spawner.getMinSpawnDelay() : spawner.getMaxSpawnDelay();
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.SET || mode == ChangeMode.RESET)
            return CollectionUtils.array(Number.class);
        return null;
    }

    @Override
    public void change(Event event, Object[] delta, ChangeMode mode){
        if (delta != null) {
            Block block = getExpr().getSingle(event);
            if (block.getType() == Material.SPAWNER) {
                BlockState state = block.getState();
                CreatureSpawner spawner = (CreatureSpawner) state;
                Integer value = ((Number) delta[0]).intValue();
                Integer cur = spawner.getDelay();
                Integer min = spawner.getMinSpawnDelay();
                Integer max = spawner.getMaxSpawnDelay();
                switch (mode) {
                    case ADD:
                        if (mark == 0) {
                            spawner.setDelay(cur + value);
                        }
                        else if (mark == 1) {
                            if (min + value > max)
                                spawner.setMaxSpawnDelay(min + value);
                            spawner.setMinSpawnDelay(min + value);
                        }
                        else {
                            if (max + value < min)
                                spawner.setMinSpawnDelay(max + value);
                            spawner.setMaxSpawnDelay(max + value);
                        }
                        spawner.update();
                        break;
                    case REMOVE:
                        if (mark == 0) {
                            spawner.setDelay(cur - value);
                        }
                        else if (mark == 1) {
                            spawner.setMinSpawnDelay(min - value);
                        }
                        else {
                            if (max - value < min)
                                spawner.setMinSpawnDelay(max - value);
                            spawner.setMaxSpawnDelay(max - value);
                        }
                        spawner.update();
                        break;
                    case SET:
                        if (mark == 0) {
                            spawner.setDelay(value);
                        }
                        else if (mark == 1) {
                            if (value > max)
                                spawner.setMaxSpawnDelay(value);
                            spawner.setMinSpawnDelay(value);
                        }
                        else {
                            if (value < min)
                                spawner.setMinSpawnDelay(value);
                            spawner.setMaxSpawnDelay(value);
                        }
                        spawner.update();
                        break;
                    case RESET:
                        if (mark == 0) {
                            spawner.setDelay(max);
                        }
                        else if (mark == 1) {
                            spawner.setMinSpawnDelay(200);
                        }
                        else {
                            spawner.setMaxSpawnDelay(800);
                        }
                        spawner.update();
                        break;
                    default:
                        assert false;
                }
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "spawner delay";
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }
}
