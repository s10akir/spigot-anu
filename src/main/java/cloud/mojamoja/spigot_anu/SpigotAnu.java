package cloud.mojamoja.spigot_anu;

import org.bukkit.plugin.java.JavaPlugin;

public class SpigotAnu extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Activate.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Deactivate.");
    }
}