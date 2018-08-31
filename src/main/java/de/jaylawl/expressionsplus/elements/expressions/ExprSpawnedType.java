package de.jaylawl.expressionsplus.elements.expressions;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.entity.EntityData;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import ch.njol.skript.entity.EntityType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprSpawnedType extends SimplePropertyExpression<Block, EntityType> {

    static {
        register(ExprSpawnedType.class, EntityType.class, "spawned ([entity] type[s]|type[s] [of entit(y|ies)])", "blocks");
    }

    @Override
    @Nullable
    public EntityType convert(Block block) {
        if (block.getType() != Material.SPAWNER)
            return null;
        BlockState state = block.getState();
        CreatureSpawner spawner = (CreatureSpawner) state;
        return EntityType.parse(spawner.getSpawnedType().toString().replace("_", " "));
    }

//    @Override
//    public Class<?>[] acceptChange(ChangeMode mode) {
//        if (mode == ChangeMode.SET)
//            return CollectionUtils.array(EntityType.class);
//        return null;
//    }
//
//    @Override
//    public void change(Event e, @Nullable Object[] delta, ChangeMode mode) {
//        assert delta != null;
//        EntityType value = (EntityType) delta[0];
//
//        for (Block block : getExpr().getArray(e)) {
//            if (block.getType() == Material.SPAWNER) {
//                BlockState state = block.getState();
//                CreatureSpawner spawner = (CreatureSpawner) state;
//                switch (mode) {
//                    case SET:
//                        spawner.setSpawnedType(value);
//                        break;
//                    default:
//                        assert false;
//                }
//            }
//        }
//    }

    @Override
    protected String getPropertyName() {
        return "spawned entity type";
    }

    @Override
    public Class<? extends EntityType> getReturnType() {
        return EntityType.class;
    }

}