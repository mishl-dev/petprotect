package com.crimsonwarpedcraft.exampleplugin.listener;

import com.crimsonwarpedcraft.exampleplugin.PetProtectPlugin;
import com.crimsonwarpedcraft.exampleplugin.config.ConfigManager;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;

public class PetDamageListener implements Listener {

  private final PetProtectPlugin plugin;
  private final ConfigManager config;

  public PetDamageListener(PetProtectPlugin plugin) {
    this.plugin = plugin;
    this.config = plugin.getConfigManager();
  }

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

    Vector direction = shooter.getLocation().toVector().subtract(pet.getLocation().toVector()).normalize();

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
}
