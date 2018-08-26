package de.jaylawl.expressionsplus.elements.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.Event;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;

import javax.annotation.Nullable;

public class ExprDisplayItem extends SimplePropertyExpression<Entity, ItemStack> {

    static {
        register(ExprDisplayItem.class, ItemStack.class, "(display[ed]|show[n])( |-)item", "entity");
    }

    @Override
    @Nullable
    public ItemStack convert(Entity entity) {
        if (entity.getType() == EntityType.ITEM_FRAME) {
            return ((ItemFrame) entity).getItem();
        }
        return null;
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if (mode == ChangeMode.SET)
            return CollectionUtils.array(ItemStack.class);
        return null;
    }

    @Override
    public void change(Event event, Object[] delta, ChangeMode mode) {
        if ((delta != null) && (getExpr().getSingle(event) != null)) {
            Entity entity = getExpr().getSingle(event);
            if (entity.getType() == EntityType.ITEM_FRAME) {
                ItemStack value = ((ItemStack) delta[0]);
                switch (mode) {
                    case SET:
                        ((ItemFrame) entity).setItem(value);
                        break;
                    default:
                        assert false;
                }
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "display item";
    }

    @Override
    public Class<? extends ItemStack> getReturnType() {
        return ItemStack.class;
    }
}
