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
            register(ExprSkullTexture.class, String.class, "[(skin|player)( |-)]texture( |-)(value|url)[s]", "itemstacks");
        }
    }

    @Override
    @Nullable
    public String convert(ItemStack i) {
        if (i.getType() != Material.PLAYER_HEAD) {
            return null;
        }
        SkullMeta meta = ((SkullMeta) i.getItemMeta());
        PlayerProfile pp = meta.getPlayerProfile();
        if (pp == null) {
            return null;
        }
        Set<ProfileProperty> prop = pp.getProperties();
        for (int x = 0; x < prop.size(); x++) {
            if (prop.iterator().next().getName().contains("textures")) {
                return prop.iterator().next().getValue();
            }
        }
        return null;
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.SET)
            return CollectionUtils.array(String.class);
        return null;
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, ChangeMode mode) {
        for (ItemStack itemStack : getExpr().getArray(e)) {
            if (itemStack != null && itemStack.getType() == Material.SPAWNER) {
                switch (mode) {
                    case SET:
                        String name = "Head";
                        SkullMeta meta = ((SkullMeta) itemStack.getItemMeta());
                        if (meta.getDisplayName() != null) {
                            name = meta.getDisplayName();
                        }
                        UUID uuid = UUID.randomUUID();
                        OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
                        meta.setOwningPlayer(p);
                        PlayerProfile pp = meta.getPlayerProfile();
                        if (pp == null) {
                            pp = Bukkit.createProfile(uuid);
                        }
                        pp.setId(uuid);
                        pp.setName(uuid.toString());
                        assert delta != null;
                        pp.setProperty(new ProfileProperty("textures", ((String) delta[0])));
                        meta.setPlayerProfile(pp);
                        meta.setDisplayName(name);
                        itemStack.setItemMeta(meta);
                        break;
                    default:
                        assert false;
                }
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
