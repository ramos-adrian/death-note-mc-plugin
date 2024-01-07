package ar.com.chasistorcido.deathnote.commands;

import ar.com.chasistorcido.deathnote.DeathNote;
import ar.com.chasistorcido.deathnote.ShinigamiEyesScoreBoard;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Particle;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

@CommandAlias("shinigamieyes|se")
public class ToggleShinigamiEyes extends BaseCommand {
    private final DeathNote plugin;

    public ToggleShinigamiEyes(DeathNote plugin) {
        this.plugin = plugin;
    }

    @Default
    public void toggleShinigamiEyes(Player player) {
        plugin.getLogger().info("Toggled Shinigami Eyes");
        if (!player.hasMetadata("death_note_shinigami_eyes")) {
            enableEyes(player);
        } else disableEyes(player);
    }

    private void disableEyes(Player player) {
        removeTint(player);
        ShinigamiEyesScoreBoard.getNoTeam().addPlayer(player);
        player.resetMaxHealth();
    }

    private void removeTint(Player player) {
        player.removeMetadata("death_note_shinigami_eyes", plugin);
        WorldBorder wb = player.getWorld().getWorldBorder();
        player.setWorldBorder(wb);
    }

    private void enableEyes(Player player) {
        showTint(player);
        ShinigamiEyesScoreBoard.getTeam().addPlayer(player);
        player.setMaxHealth(10);
        player.playEffect(EntityEffect.TOTEM_RESURRECT);
    }

    private void showTint(Player player) {
        player.setMetadata("death_note_shinigami_eyes", new FixedMetadataValue(plugin, true));
        WorldBorder wb = Bukkit.createWorldBorder();
        wb.setCenter(player.getLocation());
        wb.setWarningDistance(Integer.MAX_VALUE);
        player.setWorldBorder(wb);
    }

}
