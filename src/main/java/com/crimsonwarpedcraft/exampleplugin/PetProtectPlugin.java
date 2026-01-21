package com.crimsonwarpedcraft.exampleplugin;

import com.crimsonwarpedcraft.exampleplugin.config.ConfigManager;
import com.crimsonwarpedcraft.exampleplugin.listener.PetDamageListener;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.papermc.lib.PaperLib;
import java.util.Objects;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for PetProtect.
 */
public class PetProtectPlugin extends JavaPlugin {

  @SuppressFBWarnings(value = "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
  private static PetProtectPlugin instance;
  private final ConfigManager configManager;

  /**
   * Constructs a new PetProtectPlugin.
   */
  public PetProtectPlugin() {
    instance = this;
    this.configManager = new ConfigManager(this);
  }

  @Override
  public void onEnable() {
    PaperLib.suggestPaper(this);

    saveDefaultConfig();

    registerListeners();

    getLogger().info("PetProtect has been enabled!");
  }

  @Override
  public void onDisable() {
    getLogger().info("PetProtect has been disabled!");
  }

  private void registerListeners() {
    getServer().getPluginManager().registerEvents(new PetDamageListener(this), this);
  }

  public static PetProtectPlugin getInstance() {
    return Objects.requireNonNull(instance);
  }

  public ConfigManager getConfigManager() {
    return configManager;
  }
}
