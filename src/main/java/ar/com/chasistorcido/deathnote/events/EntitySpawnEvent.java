package ar.com.chasistorcido.deathnote.events;

import ar.com.chasistorcido.deathnote.DeathNote;
import ar.com.chasistorcido.deathnote.ShinigamiEyesScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Random;

public class EntitySpawnEvent implements Listener {
    private final DeathNote plugin;

    public EntitySpawnEvent(DeathNote plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntitySpawn(org.bukkit.event.entity.EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof LivingEntity))return;
        List<String> names = plugin.getConfig().getStringList("names");
        Random random = new Random();
        String randomName = names.get(random.nextInt(names.size()));
        ShinigamiEyesScoreBoard.getTeam().addEntity(event.getEntity());
        event.getEntity().setCustomName(randomName);
    }

    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        event.getPlayer().setScoreboard(ShinigamiEyesScoreBoard.getScoreBoard());
        ShinigamiEyesScoreBoard.getNoTeam().addPlayer(event.getPlayer());
    }
}
