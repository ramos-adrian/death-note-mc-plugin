package ar.com.chasistorcido.deathnote.commands;

import ar.com.chasistorcido.deathnote.DeathNote;
import ar.com.chasistorcido.deathnote.DeathNoteItem;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

@CommandAlias("spawndeathnote|sdn")
public class SpawnDeathNote extends BaseCommand {

    private final DeathNote plugin;

    public SpawnDeathNote(DeathNote plugin) {
        this.plugin = plugin;
    }
    @Default
    @Description("Spawns a Death Note")
    public void spawnDeathNote(Player player) {
        plugin.getLogger().info("Spawned a Death Note");
        Location location = getSpawnLocation(player);
        Item deathNote = player.getWorld().dropItem(location, DeathNoteItem.getInstance());
        LivingEntity entity = getLivingEntity(location);
        entity.addPassenger(deathNote);
        removeWhenOnGround(entity);
    }

    private LivingEntity getLivingEntity(Location spawnLocation) {
        LivingEntity entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.SILVERFISH);
        entity.setInvisible(true);
        entity.setInvulnerable(true);
        entity.setSilent(true);
        PotionEffect slowFallingEffect = new PotionEffect(org.bukkit.potion.PotionEffectType.SLOW_FALLING, PotionEffect.INFINITE_DURATION, 2, false, false);
        entity.addPotionEffect(slowFallingEffect);
        return entity;
    }

    private void removeWhenOnGround(Entity entity) {
        new org.bukkit.scheduler.BukkitRunnable() {
            @Override
            public void run() {
                if (entity.isOnGround()) {
                    entity.remove();
                }
            }
        }.runTaskTimer(plugin, 0, 5);
    }

    private Location getSpawnLocation(Player player) {
        Vector vector = player.getLocation().getDirection();
        vector.multiply(10);
        vector.setY(player.getLocation().getY() + 20);
        return player.getLocation().add(vector);
    }


}
