package de.jaylawl.expressionsplus.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;

public class ExprSkullTexture extends SimplePropertyExpression<ItemStack, String> {

    static {
        if (Skript.classExists("com.destroystokyo.paper.profile.PlayerProfile")) {
            register(ExprSkullTexture.class, String.class, "[(skin|player)( |-)]texture( |-)(value|url)", "itemstack");
        }
    }

    @Override
    @Nullable
    public String convert(ItemStack i) {
        if (i.getType() != Material.SKULL_ITEM) {
            return null;
        }
        SkullMeta meta = ((SkullMeta) i.getItemMeta());
        PlayerProfile pp = meta.getPlayerProfile();
        Set<ProfileProperty> prop = pp.getProperties();
        for (int x = 0; x < prop.size(); x++) {
            if (prop.iterator().next().getName().contains("textures")) {
                return prop.iterator().next().getValue();
            }
        }
        return null;
    }

    @Override
    public Class<?>[] acceptChange(final ChangeMode mode) {
        if (mode == ChangeMode.SET)
            return CollectionUtils.array(String.class);
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, ChangeMode mode) {
        ItemStack i = getExpr().getSingle(e);
        if (i != null && i.getType() == Material.SKULL_ITEM) {
            switch (mode) {
                case SET:
                    String name = "Head";
                    SkullMeta meta = ((SkullMeta) i.getItemMeta());
                    if (meta.getDisplayName() != null) {
                        name = meta.getDisplayName();
                    }
                    UUID uuid = UUID.randomUUID();
                    OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
                    meta.setOwningPlayer(p);
                    PlayerProfile pp = meta.getPlayerProfile();
                    pp.setId(uuid);
                    pp.setName(uuid.toString());
                    pp.setProperty(new ProfileProperty("textures", ((String) delta[0])));
                    meta.setPlayerProfile(pp);
                    meta.setDisplayName(name);
                    i.setItemMeta(meta);
                    break;
                default:
                    assert false;
            }
        }
    }

    @Override
    protected String getPropertyName() {
        return "skin texture url";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
