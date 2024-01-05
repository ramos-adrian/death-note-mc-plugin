package ar.com.chasistorcido.deathnote;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathNote extends JavaPlugin {

    private PaperCommandManager commandManager;

    @Override
    public void onEnable() {
        super.onEnable();
        getLogger().info("DeathNote enabled");
        commandManager = new PaperCommandManager(this);
        registerCommands();
    }

    private void registerCommands() {
//        commandManager.registerCommand(new SpawnDeathNote(this));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        getLogger().info("DeathNote disabled");
    }
}
