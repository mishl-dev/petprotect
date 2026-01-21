package com.crimsonwarpedcraft.exampleplugin.config;

import com.crimsonwarpedcraft.exampleplugin.PetProtectPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

  private final PetProtectPlugin plugin;
  private FileConfiguration config;

  public ConfigManager(PetProtectPlugin plugin) {
    this.plugin = plugin;
    this.config = plugin.getConfig();
  }

  public void reload() {
    plugin.reloadConfig();
    this.config = plugin.getConfig();
  }

  public boolean isEnabled() {
    return config.getBoolean("enabled", true);
  }

  public boolean shouldProtectFromEntities() {
    return config.getBoolean("protect-from-entities", true);
  }

  public boolean shouldProtectFromEnvironment() {
    return config.getBoolean("protect-from-environment", true);
  }

  public boolean shouldProtectFromProjectiles() {
    return config.getBoolean("protect-from-projectiles", true);
  }

  public boolean shouldProtectFromMagic() {
    return config.getBoolean("protect-from-magic", true);
  }

  public boolean shouldProtectFromFire() {
    return config.getBoolean("protect-from-fire", true);
  }

  public boolean shouldProtectFromDrowning() {
    return config.getBoolean("protect-from-drowning", true);
  }

  public boolean shouldProtectFromFalling() {
    return config.getBoolean("protect-from-falling", true);
  }

  public boolean shouldProtectFromSuffocation() {
    return config.getBoolean("protect-from-suffocation", true);
  }

  public boolean shouldProtectFromLightning() {
    return config.getBoolean("protect-from-lightning", true);
  }

  public boolean shouldProtectFromStarvation() {
    return config.getBoolean("protect-from-starvation", true);
  }

  public boolean shouldProtectFromContact() {
    return config.getBoolean("protect-from-contact", true);
  }

  public boolean shouldProtectFromOtherEnvironmental() {
    return config.getBoolean("protect-from-other-environmental", true);
  }

  public boolean shouldAllowOwnerDamage() {
    return config.getBoolean("allow-owner-damage", true);
  }

  public boolean shouldAllowOwnerProjectiles() {
    return config.getBoolean("allow-owner-projectiles", true);
  }
}
