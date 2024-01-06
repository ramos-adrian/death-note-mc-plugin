package ar.com.chasistorcido.deathnote;

import ar.com.chasistorcido.deathnote.commands.SpawnDeathNote;
import ar.com.chasistorcido.deathnote.events.PlayerEditDeathNote;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathNote extends JavaPlugin {

    private PaperCommandManager commandManager;

    @Override
    public void onEnable() {
        super.onEnable();
        getLogger().info("DeathNote enabled");
        commandManager = new PaperCommandManager(this);
        registerCommands();
        registerEvents();
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerEditDeathNote(this), this);
    }

    private void registerCommands() {
        commandManager.registerCommand(new SpawnDeathNote(this));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        getLogger().info("DeathNote disabled");
    }
}
