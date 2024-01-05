package ar.com.chasistorcido.deathnote.commands;

import ar.com.chasistorcido.deathnote.DeathNote;
import ar.com.chasistorcido.deathnote.DeathNoteItem;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.entity.Player;

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
        player.getInventory().addItem(DeathNoteItem.getInstance());
    }
}
