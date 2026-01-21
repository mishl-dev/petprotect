package com.crimsonwarpedcraft.exampleplugin;

import com.crimsonwarpedcraft.exampleplugin.config.ConfigManager;
import com.crimsonwarpedcraft.exampleplugin.listener.PetDamageListener;
import io.papermc.lib.PaperLib;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for PetProtect.
 */
public class PetProtectPlugin extends JavaPlugin {

  private static PetProtectPlugin instance;
  private ConfigManager configManager;

  @Override
  public void onEnable() {
    PaperLib.suggestPaper(this);

    instance = this;
    configManager = new ConfigManager(this);

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
    return instance;
  }

  public ConfigManager getConfigManager() {
    return configManager;
  }
}
