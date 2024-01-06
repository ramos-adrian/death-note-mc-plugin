package ar.com.chasistorcido.deathnote.events;

import ar.com.chasistorcido.deathnote.DeathCause;
import ar.com.chasistorcido.deathnote.DeathNote;
import ar.com.chasistorcido.deathnote.DeathNoteItem;
import com.google.common.collect.Sets;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

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
        DeathCause deathCause = getDeathCause(event.getNewBookMeta());
        plugin.getLogger().info("Entidad: " + entityName + " Causa: " + deathCause.name());
        killEntityNamed(entityName, deathCause);
    }


    private void killEntityNamed(String entityName, DeathCause deathCause) {
        plugin.getServer().getWorlds().forEach(world -> world.getLivingEntities().forEach(entity -> {
            if (entity.getCustomName() != null && entity.getCustomName().equals(entityName)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        deathCause.execute(entity);
                    }
                }.runTaskLater(plugin, 20*5);
            }
        }));
    }

    private String getLastName(BookMeta bookMeta) {
        String content = bookMeta.getPage(bookMeta.getPageCount());
        String[] Lines = content.split("\n");
        String lastLine = Lines[Lines.length - 1];
        return lastLine.split(" ")[0];
    }

    private DeathCause getDeathCause(BookMeta newBookMeta) {
        ConfigurationSection deathCausesSection = plugin.getConfig().getConfigurationSection("deathCauses");
        if (deathCausesSection == null) {
            plugin.getLogger().warning("deathCauses section is null. Please revise configurations!");
            return DeathCause.HEARTH_ATTACK;
        }
        for (String deathCauseName : deathCausesSection.getKeys(true)) {
            List<String> alias = plugin.getConfig().getStringList("deathCauses." + deathCauseName);
            Sets.SetView<String> view = Sets.intersection(Sets.newHashSet(alias), Sets.newHashSet(newBookMeta.getPage(newBookMeta.getPageCount()).split(" ")));
            if(!view.isEmpty()) {
                return DeathCause.valueOf(deathCauseName);
            }
        }
        return DeathCause.HEARTH_ATTACK;
    }

    private boolean isDeathNote(ItemMeta im) {
        PersistentDataContainer pdc = im.getPersistentDataContainer();
        return pdc.has(DeathNoteItem.getNamespacedKey());
    }

}
