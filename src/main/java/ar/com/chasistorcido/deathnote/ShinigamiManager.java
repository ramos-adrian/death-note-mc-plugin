package ar.com.chasistorcido.deathnote;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.CommandTrait;
import net.citizensnpcs.trait.LookClose;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

public class ShinigamiManager implements Listener {

    private DeathNote plugin;

    public ShinigamiManager(DeathNote plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeathNoteRightClick(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (!DeathNoteItem.isDeathNote(event.getItem())) return;
        if (!event.getPlayer().isSneaking()) return;
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;
        event.setCancelled(true);
        Location spawnLocation = getSpawnLocation(event.getPlayer());
        spawnShinigami(spawnLocation, event.getPlayer());
    }

    private void spawnShinigami(Location location, Player player) {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Ryuk");

        SkinTrait skinTrait = npc.getOrAddTrait(SkinTrait.class);
        String texture = plugin.getConfig().getString("shinigami_skin.texture");
        String signature = plugin.getConfig().getString("shinigami_skin.textureSignature");
        skinTrait.setSkinPersistent("ryukSkin", signature, texture);

        LookClose lookClose = npc.getOrAddTrait(LookClose.class);
        lookClose.lookClose(true);
        lookClose.setPerPlayer(true);

        CommandTrait commandTrait = npc.getOrAddTrait(CommandTrait.class);
        CommandTrait.NPCCommandBuilder commandBuilder = new CommandTrait.NPCCommandBuilder(String.format("dm open shinigami_deal %s", player.getName()), CommandTrait.Hand.RIGHT);
        commandTrait.addCommand(commandBuilder);

        npc.spawn(location);

    }

    private Location getSpawnLocation(Player player) {
        Vector vector = player.getLocation().getDirection();
        vector.multiply(2);
        vector.setY(player.getLocation().getY());
        Location loc = player.getLocation().add(vector);
        loc.setYaw(player.getLocation().getYaw() + 180);
        return loc;
    }
}
