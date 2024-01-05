package ar.com.chasistorcido.deathnote;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathNoteItem {
    private static ItemStack deathNote;

    private DeathNoteItem() {
        // Private constructor to prevent instantiation
    }

    public static ItemStack getInstance() {
        if (deathNote == null) {
            deathNote = new ItemStack(Material.WRITABLE_BOOK);
            ItemMeta im = deathNote.getItemMeta();
            im.setDisplayName(ChatColor.WHITE + "Death Note");
            PersistentDataContainer pdc = im.getPersistentDataContainer();
            JavaPlugin plugin = getMainPluginInstance();
            pdc.set(new NamespacedKey(plugin, "DeathNote"), PersistentDataType.BOOLEAN, true);
            deathNote.setItemMeta(im);
        }
        return deathNote;
    }

    private static JavaPlugin getMainPluginInstance() {
        return JavaPlugin.getPlugin(DeathNote.class);
    }
}