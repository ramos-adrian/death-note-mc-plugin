package ar.com.chasistorcido.deathnote.events;

import ar.com.chasistorcido.deathnote.DeathNote;
import ar.com.chasistorcido.deathnote.ShinigamiEyesScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Random;

public class EntitySpawnEvent implements Listener {
    private final DeathNote plugin;

    public EntitySpawnEvent(DeathNote plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntitySpawn(org.bukkit.event.entity.EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof LivingEntity livingEntity))return;
        if (livingEntity.isInvisible()) return;
        setRandomCustomName(livingEntity);
        ShinigamiEyesScoreBoard.getTeam().addEntity(event.getEntity());
    }

    private void setRandomCustomName(LivingEntity livingEntity) {
        List<String> names = plugin.getConfig().getStringList("names");
        Random random = new Random();
        String randomName = names.get(random.nextInt(names.size()));
        livingEntity.setCustomName(randomName);
        if (!livingEntity.isInvisible())livingEntity.setCustomNameVisible(true);
    }

    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        event.getPlayer().setScoreboard(ShinigamiEyesScoreBoard.getScoreBoard());
        ShinigamiEyesScoreBoard.getNoTeam().addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onChunkLoad(org.bukkit.event.world.ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if (!(entity instanceof LivingEntity livingEntity))continue;
            if(entity instanceof Player)continue;
            if(livingEntity.getCustomName() == null)setRandomCustomName(livingEntity);
            ShinigamiEyesScoreBoard.getTeam().addEntity(livingEntity);
        }
    }

    @EventHandler
    public void onPlayerDeath(org.bukkit.event.entity.PlayerDeathEvent event) {
        Player player = event.getEntity();
        ShinigamiEyesScoreBoard.getNoTeam().addPlayer(player);
    }

}
