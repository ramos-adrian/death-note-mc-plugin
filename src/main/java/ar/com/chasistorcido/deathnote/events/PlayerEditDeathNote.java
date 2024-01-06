package ar.com.chasistorcido.deathnote.events;

import ar.com.chasistorcido.deathnote.DeathNote;
import ar.com.chasistorcido.deathnote.DeathNoteItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

public class PlayerEditDeathNote implements Listener {

    DeathNote plugin;

    public PlayerEditDeathNote(DeathNote plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerEditDeathNote(PlayerEditBookEvent event) {
        if(!isDeathNote(event.getNewBookMeta())) return;

        plugin.getLogger().info("Player edited Death Note");
        String entityName = getLastName(event.getNewBookMeta());
        killEntityNamed(entityName);
    }

    private void killEntityNamed(String entityName) {
        plugin.getServer().getWorlds().forEach(world -> world.getLivingEntities().forEach(entity -> {
            if (entity.getCustomName() != null && entity.getCustomName().equals(entityName)) {
                entity.setHealth(0);
            }
        }));
    }

    private String getLastName(BookMeta bookMeta) {
        String content = bookMeta.getPage(bookMeta.getPageCount());
        return content.substring(content.lastIndexOf("\n") + 1);
    }

    private boolean isDeathNote(ItemMeta im) {
        PersistentDataContainer pdc = im.getPersistentDataContainer();
        return pdc.has(DeathNoteItem.getNamespacedKey());
    }

}
