package ar.com.chasistorcido.deathnote;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
            deathNote.setItemMeta(im);
        }
        return deathNote;
    }
}