package ar.com.chasistorcido.deathnote;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.function.Consumer;

public enum DeathCause {
    BEES(entity -> {
        Location location = entity.getLocation();
        location.add(0, 4, 0);
        for (int i = 0; i < 5; i++) {
            Bee bee = (Bee) location.getWorld().spawnEntity(location, org.bukkit.entity.EntityType.BEE);
            bee.setAnger(100);
            bee.setTarget(entity);
            new BukkitRunnable() {
                @Override
                public void run() {
                    bee.remove();
                }
            }.runTaskLater(JavaPlugin.getPlugin(DeathNote.class), 20*5);
        }
    }),

    GIANT(entity -> {
        Location location = entity.getLocation();
        location.add(0, 4, 0);
        Giant giant = (Giant) location.getWorld().spawnEntity(location, org.bukkit.entity.EntityType.GIANT);
        giant.attack(entity);
        new BukkitRunnable() {
            @Override
            public void run() {
                giant.remove();
            }
        }.runTaskLater(JavaPlugin.getPlugin(DeathNote.class), 20*5);
    }),

    FIRE(entity -> {
        entity.setFireTicks(100);
    }),

    LAVA(entity -> {
        Location location = entity.getLocation();
        location.add(0, -1, 0);
        for (BlockFace face : BlockFace.values()) {
            if (face == BlockFace.DOWN) continue;
            if (face == BlockFace.UP) continue;
            Block block = location.getBlock().getRelative(face);
            Material originalRelativeMaterial = block.getType();
            block.setType(org.bukkit.Material.LAVA);
            new BukkitRunnable() {
                @Override
                public void run() {
                    block.setType(originalRelativeMaterial);
                }
            }.runTaskLater(JavaPlugin.getPlugin(DeathNote.class), 20*5);
        }
    }),

    LIGHTNING(entity -> {
        entity.setHealth(0.01);
        entity.getWorld().strikeLightning(entity.getLocation());
    }),

    CACTUS(entity -> {
        Location location = entity.getLocation();
        location.add(0, -1, 0);
        for (BlockFace face : BlockFace.values()) {
            if (face == BlockFace.DOWN) continue;
            if (face == BlockFace.UP) continue;
            Block block = location.getBlock().getRelative(face);
            Block sand = block.getRelative(BlockFace.DOWN);
            Material originalRelativeMaterial = block.getType();
            Material originalSandMaterial = sand.getType();
            block.setType(Material.CACTUS);
            block.setMetadata("deathnote_block", new FixedMetadataValue(JavaPlugin.getPlugin(DeathNote.class), true));
            sand.setType(Material.SAND);
            new BukkitRunnable() {
                @Override
                public void run() {
                    block.setType(originalRelativeMaterial);
                    sand.setType(originalSandMaterial);
                }
            }.runTaskLater(JavaPlugin.getPlugin(DeathNote.class), 20*5);
        }
    }),

    SUFFOCATION(entity -> {
        entity.setHealth(0.1);
        entity.setAI(false);
        Location location = entity.getLocation();
        for (BlockFace face : BlockFace.values()) {
            if (face == BlockFace.DOWN) continue;
            Block originalBlock = location.getBlock().getRelative(face);
            for (int i = 0; i < 5; i++) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Block block = originalBlock.getRelative(0, 5, 0);
                        block.setType(Material.SAND);
                    }
                }.runTaskLater(JavaPlugin.getPlugin(DeathNote.class), 5*i);
            }
        }
    }),

    EXPLOSION(entity -> {
        entity.setHealth(0.1);
        entity.getWorld().createExplosion(entity.getLocation(), 5);
    }),

    WOLFS(entity -> {
        Vector vector = entity.getLocation().getDirection();
        vector.multiply(-3);
        Location location = entity.getLocation().add(vector);
        for (int i = 0; i < 3; i++) {
            Wolf wolf = (Wolf) location.getWorld().spawnEntity(location, org.bukkit.entity.EntityType.WOLF);
            wolf.setAngry(true);
            wolf.setTarget(entity);
            new BukkitRunnable() {
                @Override
                public void run() {
                    wolf.remove();
                }
            }.runTaskLater(JavaPlugin.getPlugin(DeathNote.class), 20*5);
        }
    }),

    ARROWS(entity -> {
        Location location = entity.getLocation();
        location.add(-3 , 0, -3);
        for (int i = 0; i < 3; i++) {
            location.add(0, 3, i);
            for (int j = 0; j < 3; j++) {
                location.add(j, 3, 0);
                Arrow arrow = (Arrow) location.getWorld().spawnEntity(location, org.bukkit.entity.EntityType.ARROW);
                arrow.setVelocity(location.toVector().subtract(entity.getLocation().toVector()).normalize().multiply(-2));
            }
            location.add(-0.3*6, 0, 0);
        }
    }),

    HEARTH_ATTACK(entity -> {
        entity.setHealth(0);
    });

    private final Consumer<LivingEntity> executor;

    DeathCause(Consumer<LivingEntity> executor) {
        this.executor = executor;
    }

    public void execute(LivingEntity entity) {
        entity.setHealth(1);
        executor.accept(entity);
    }

}
