package cloud.mojamoja.spigot_anu;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.Location;

class SpigotAnuListener implements Listener {
  Logger logger;
  String discordWebhookUrl;
  HttpClient httpClient;

  SpigotAnuListener(Logger logger) {
    this.logger = logger;
    this.discordWebhookUrl = Bukkit.getPluginManager().getPlugin("spigot-anu").getConfig().getString("discordWebhookUrl");
    this.httpClient = HttpClient.newHttpClient();
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    var player = event.getPlayer();
    var location = player.getLocation();
    var locationString = locationToString(location);

    var message = player.getName() + " joined the game: " + locationString;
    logger.info(message);
    postMessage(message);
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    var player = event.getPlayer();
    var location = player.getLocation();
    var locationString = locationToString(location);

    var message = player.getName() + " left the game: " + locationString;
    logger.info(message);
    postMessage(message);
  }

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    var player = event.getEntity();
    var location = player.getLocation();
    var deathMessage = event.getDeathMessage();
    var locationString = locationToString(location);

    var message = deathMessage + ": " + locationString;
    logger.info(message);
    postMessage(message);
  }

  private String locationToString(Location location) {
    return "[" + location.getWorld().getName() + "] " + (int)location.getX() + ", " + (int)location.getY() + ", " + (int)location.getZ();
  }

  private void postMessage(String message) {
    var request = HttpRequest.newBuilder(
        URI.create(this.discordWebhookUrl)
    ).header("Content-Type", "application/json").POST(
        HttpRequest.BodyPublishers.ofString("{\"content\": \"" + message + "\"}")
    ).build();


    try {
      httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    } catch (Exception e) {
      logger.warning(e.toString());
    }
  }
}
