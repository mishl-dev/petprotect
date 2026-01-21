package com.crimsonwarpedcraft.exampleplugin.listener;

import com.crimsonwarpedcraft.exampleplugin.PetProtectPlugin;
import com.crimsonwarpedcraft.exampleplugin.config.ConfigManager;
import io.papermc.lib.PaperLib;
import org.bukkit.Location;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;

/**
 * Listener for pet damage events to handle protection and void teleportation.
 */
public class PetDamageListener implements Listener {

  private static final double VOID_Y_LEVEL = -67;

  private final PetProtectPlugin plugin;
  private final ConfigManager config;

  /**
   * Constructs a new PetDamageListener.
   *
   * @param plugin the plugin instance
   */
  public PetDamageListener(PetProtectPlugin plugin) {
    this.plugin = plugin;
    this.config = plugin.getConfigManager();
  }

  /**
   * Handles entity damage events to protect pets and teleport them from the void.
   *
   * @param event the damage event
   */
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onEntityDamage(EntityDamageEvent event) {
    if (!config.isEnabled()) {
      return;
    }

    Entity entity = event.getEntity();

    if (!(entity instanceof Tameable tameable)) {
      return;
    }

    if (!tameable.isTamed()) {
      return;
    }

    AnimalTamer owner = tameable.getOwner();
    if (owner == null) {
      return;
    }

    if (event instanceof EntityDamageByEntityEvent) {
      EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) event;
      Entity damager = damageEvent.getDamager();

      if (isDamageFromOwner(damager, owner)) {
        return;
      }

      if (!config.shouldProtectFromEntities()) {
        return;
      }

      if (damager instanceof Projectile projectile) {
        Entity shooter = getProjectileShooter(projectile);
        if (shooter != null && shooter != owner) {
          event.setCancelled(true);
          reflectProjectile(projectile, entity, shooter);
          return;
        }
      }

      event.setCancelled(true);
    } else {
      if (!shouldProtectEnvironmentalDamage(event.getCause())) {
        return;
      }

      if (event.getCause() == DamageCause.VOID) {
        if (config.shouldTeleportOnVoid()) {
          teleportPetToOwner(entity, owner);
        }
        event.setCancelled(true);
        return;
      }

      if (entity.getLocation().getY() <= VOID_Y_LEVEL) {
        if (config.shouldTeleportOnVoid()) {
          teleportPetToOwner(entity, owner);
        }
        event.setCancelled(true);
        return;
      }

      event.setCancelled(true);
      extinguishFire(entity);
    }
  }

  private Entity getProjectileShooter(Projectile projectile) {
    if (projectile.getShooter() instanceof Entity entity) {
      return entity;
    }
    return null;
  }

  private void reflectProjectile(Projectile projectile, Entity pet, Entity shooter) {
    projectile.setShooter(null);

    Vector direction = shooter.getLocation().toVector()
        .subtract(pet.getLocation().toVector()).normalize();

    double speed = projectile.getVelocity().length();
    if (speed < 0.5) {
      speed = 1.5;
    }

    projectile.setVelocity(direction.multiply(speed));
  }

  private boolean isDamageFromOwner(Entity damager, AnimalTamer owner) {
    if (damager == owner) {
      return true;
    }

    if (damager instanceof Projectile projectile) {
      Entity shooter = getProjectileShooter(projectile);
      return shooter == owner && config.shouldAllowOwnerProjectiles();
    }

    return false;
  }

  private boolean shouldProtectEnvironmentalDamage(DamageCause cause) {
    switch (cause) {
      case FIRE:
      case FIRE_TICK:
      case LAVA:
        return config.shouldProtectFromFire();
      case DROWNING:
        return config.shouldProtectFromDrowning();
      case FALL:
        return config.shouldProtectFromFalling();
      case SUFFOCATION:
        return config.shouldProtectFromSuffocation();
      case LIGHTNING:
        return config.shouldProtectFromLightning();
      case MAGIC:
      case POISON:
      case WITHER:
      case THORNS:
        return config.shouldProtectFromMagic();
      case STARVATION:
        return config.shouldProtectFromStarvation();
      case CONTACT:
        return config.shouldProtectFromContact();
      case HOT_FLOOR:
      case CRAMMING:
        return config.shouldProtectFromOtherEnvironmental();
      default:
        return true;
    }
  }

  private void extinguishFire(Entity entity) {
    if (entity.getFireTicks() > 0) {
      entity.setFireTicks(0);
    }
  }

  private void teleportPetToOwner(Entity pet, AnimalTamer owner) {
    if (owner instanceof Player player) {
      Location ownerLoc = player.getLocation();
      Location teleportLoc = new Location(
          ownerLoc.getWorld(),
          ownerLoc.getX(),
          ownerLoc.getY() + 1,
          ownerLoc.getZ(),
          ownerLoc.getYaw(),
          ownerLoc.getPitch()
      );
      PaperLib.teleportAsync(pet, teleportLoc);
    }
  }
}
