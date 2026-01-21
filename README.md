# PetProtect
A PaperMC/Spigot Minecraft server plugin that protects tamed pets from damage while allowing owners to interact with them.

## Features

- **Pet Protection**: Tamed pets (wolves, cats, horses, parrots, etc.) are protected from environmental damage and entity attacks
- **Owner Permissions**: Pet owners can still damage their pets directly or with projectiles
- **Void Teleportation**: Pets that fall into the void are automatically teleported back to their owner
- **Configurable**: All protection settings can be enabled/disabled in config.yml

### Protected Damage Types
- Entity attacks
- Environmental damage (fire, lava, drowning, falling, suffocation, lightning, starvation, contact)
- Projectiles
- Magic/poison/wither damage

## Configuration

All settings are managed in `config.yml`:

```yaml
# Enable/disable the plugin
enabled: true

# Protection settings
protect-from-entities: true
protect-from-environment: true
protect-from-projectiles: true
protect-from-magic: true
protect-from-fire: true
protect-from-drowning: true
protect-from-falling: true
protect-from-suffocation: true
protect-from-lightning: true
protect-from-starvation: true
protect-from-contact: true
protect-from-other-environmental: true

# Owner damage settings
allow-owner-damage: true
allow-owner-projectiles: true

# Void teleportation
teleport-on-void: true
```

## Building

```bash
./gradlew build
```

JARs will be in `build/libs/`.

## Compatibility

- PaperMC/Spigot 1.21+
- Java 21
