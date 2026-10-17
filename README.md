# Cape Command — NeoForge 1.21.1

Cape Command adds a `/cape` command that lets players choose from a large catalog of Minecraft capes and display the selected cape on player skins.

This repository contains the NeoForge 1.21.1 port of Cape Command.

## Features

- `/cape <cape_name>` — equip a cape from the built-in catalog.
- `/cape reset` — remove your current custom cape.
- Command suggestions for every available cape.
- Server-side cape selection with client synchronization.
- Support for official Minecraft capes, event capes, community capes, and additional catalog entries.
- Optional client-side support for capes that require the Cape Command client mod.

## Commands

```text
/cape <cape_name>
/cape reset
```

Cape names are entered in lowercase; underscores are used between words. For example:

```text
/cape copper
/cape moonlight_trail
/cape zombie_horse
/cape twisted
```

Use command completion in-game to see the capes available on your server. Some capes are only offered when the player has the Cape Command client installed.

## Requirements

- Minecraft 1.21.1
- NeoForge 21.1.x
- Java 21

## Building

```bash
./gradlew build
```

The compiled mod is placed in `build/libs/`.

## License

This project is licensed under the LGPL-3.0-only license. See [LICENSE](LICENSE).
