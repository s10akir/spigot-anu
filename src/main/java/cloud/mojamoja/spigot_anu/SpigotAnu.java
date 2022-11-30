package cloud.mojamoja.spigot_anu;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class SpigotAnu extends JavaPlugin {
    Logger logger;

    public SpigotAnu() {
        super();
        this.logger = getLogger();
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new SpigotAnuListener(logger), this);

        logger.info("Activate.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Deactivate.");
    }
}