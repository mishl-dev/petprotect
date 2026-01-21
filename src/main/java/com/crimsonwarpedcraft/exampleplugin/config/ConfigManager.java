package com.crimsonwarpedcraft.exampleplugin.config;

import com.crimsonwarpedcraft.exampleplugin.PetProtectPlugin;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Objects;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Manages plugin configuration settings.
 */
public class ConfigManager {

  @SuppressFBWarnings(value = "EI_EXPOSE_REP2")
  private final PetProtectPlugin plugin;
  private FileConfiguration config;

  /**
   * Constructs a new ConfigManager.
   *
   * @param plugin the plugin instance
   */
  @SuppressWarnings("EI_EXPOSE_REP")
  public ConfigManager(PetProtectPlugin plugin) {
    this.plugin = Objects.requireNonNull(plugin);
    this.config = plugin.getConfig();
  }

  /**
   * Reloads configuration from file.
   */
  public void reload() {
    plugin.reloadConfig();
    this.config = plugin.getConfig();
  }

  /**
   * Returns whether the plugin is enabled.
   *
   * @return whether the plugin is enabled
   */
  public boolean isEnabled() {
    return config.getBoolean("enabled", true);
  }

  /**
   * Returns whether pets should be protected from entity damage.
   *
   * @return whether pets should be protected from entity damage
   */
  public boolean shouldProtectFromEntities() {
    return config.getBoolean("protect-from-entities", true);
  }

  /**
   * Returns whether pets should be protected from environmental damage.
   *
   * @return whether pets should be protected from environmental damage
   */
  public boolean shouldProtectFromEnvironment() {
    return config.getBoolean("protect-from-environment", true);
  }

  /**
   * Returns whether pets should be protected from projectile damage.
   *
   * @return whether pets should be protected from projectile damage
   */
  public boolean shouldProtectFromProjectiles() {
    return config.getBoolean("protect-from-projectiles", true);
  }

  /**
   * Returns whether pets should be protected from magic damage.
   *
   * @return whether pets should be protected from magic damage
   */
  public boolean shouldProtectFromMagic() {
    return config.getBoolean("protect-from-magic", true);
  }

  /**
   * Returns whether pets should be protected from fire damage.
   *
   * @return whether pets should be protected from fire damage
   */
  public boolean shouldProtectFromFire() {
    return config.getBoolean("protect-from-fire", true);
  }

  /**
   * Returns whether pets should be protected from drowning.
   *
   * @return whether pets should be protected from drowning
   */
  public boolean shouldProtectFromDrowning() {
    return config.getBoolean("protect-from-drowning", true);
  }

  /**
   * Returns whether pets should be protected from fall damage.
   *
   * @return whether pets should be protected from fall damage
   */
  public boolean shouldProtectFromFalling() {
    return config.getBoolean("protect-from-falling", true);
  }

  /**
   * Returns whether pets should be protected from suffocation.
   *
   * @return whether pets should be protected from suffocation
   */
  public boolean shouldProtectFromSuffocation() {
    return config.getBoolean("protect-from-suffocation", true);
  }

  /**
   * Returns whether pets should be protected from lightning.
   *
   * @return whether pets should be protected from lightning
   */
  public boolean shouldProtectFromLightning() {
    return config.getBoolean("protect-from-lightning", true);
  }

  /**
   * Returns whether pets should be protected from starvation.
   *
   * @return whether pets should be protected from starvation
   */
  public boolean shouldProtectFromStarvation() {
    return config.getBoolean("protect-from-starvation", true);
  }

  /**
   * Returns whether pets should be protected from contact damage.
   *
   * @return whether pets should be protected from contact damage
   */
  public boolean shouldProtectFromContact() {
    return config.getBoolean("protect-from-contact", true);
  }

  /**
   * Returns whether pets should be protected from other environmental damage.
   *
   * @return whether pets should be protected from other environmental damage
   */
  public boolean shouldProtectFromOtherEnvironmental() {
    return config.getBoolean("protect-from-other-environmental", true);
  }

  /**
   * Returns whether the owner should be able to directly damage their pet.
   *
   * @return whether the owner should be able to directly damage their pet
   */
  public boolean shouldAllowOwnerDamage() {
    return config.getBoolean("allow-owner-damage", true);
  }

  /**
   * Returns whether the owner's projectiles should be able to damage their pet.
   *
   * @return whether the owner's projectiles should be able to damage their pet
   */
  public boolean shouldAllowOwnerProjectiles() {
    return config.getBoolean("allow-owner-projectiles", true);
  }

  /**
   * Returns whether pets should be teleported to their owner when falling into the void.
   *
   * @return whether pets should be teleported to their owner when falling into the void
   */
  public boolean shouldTeleportOnVoid() {
    return config.getBoolean("teleport-on-void", true);
  }
}
