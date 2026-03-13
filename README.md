<div align="center">

<img src="common/src/main/resources/assets/dawnoftimebuilder/textures/gui/creative_icons.png" alt="Dawn of Time Logo" width="64"/>

# Dawn of Time

**The ultimate architectural building mod for Minecraft**

[![CurseForge](https://img.shields.io/badge/CurseForge-Download-orange?style=flat-square&logo=curseforge&labelColor=0d1117)](https://www.curseforge.com/minecraft/mc-mods/dawn-of-time)
[![Modrinth](https://img.shields.io/badge/Modrinth-Download-1bd96a?style=flat-square&logo=modrinth&labelColor=0d1117)](https://modrinth.com/mod/dawn-of-time)
[![Discord](https://img.shields.io/discord/303974483591692289?color=informational&label=Discord&logo=discord&style=flat-square&logoColor=white&labelColor=0d1117)](https://discord.gg/MrHu9MJ)
[![Patreon](https://img.shields.io/badge/Support-Patreon-f96854?style=flat-square&logo=patreon&labelColor=0d1117)](https://www.patreon.com/cw/dawnoftimemod)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow?style=flat-square&labelColor=0d1117)](LICENSE.md)

*Inspired by Millénaire, the mod of our childhood.*

</div>

---

## 🏛️ What is Dawn of Time?

**Dawn of Time** is a building mod crafted by Minecraft architects, for Minecraft architects. Whether you're just starting out or a seasoned builder, Dawn of Time enhances your creative experience with a carefully curated collection of nearly **350 new blocks** spanning multiple world cultures and architectural styles.

Every block has been designed with builders in mind: a clean and well-organized creative inventory, multiple skin variants per block type, and seamless connectivity between blocks. Build Roman forums, Japanese dojos, Persian palaces, or medieval French villages — all in one mod.

---

## ✨ Features

### 🌍 Multi-Cultural Architecture
Explore blocks inspired by historical civilizations:

| Culture | Examples |
|---|---|
| 🇫🇷 **French / Medieval** | Limestone gargoyles, reinforced iron fences, shutters |
| 🇩🇪 **German** | Waxed oak furniture, canopy beds, chandeliers |
| 🇯🇵 **Japanese** | Tatami mats, futons, irori fireplaces, paper lanterns |
| 🏛️ **Roman** | Marble columns, birch couches, sandstone statues |
| 🕌 **Persian** | Moraq mosaic columns |
| 🌿 **Pre-columbian** | Plastered stone cressets |

### 🪵 Wood Variants
Most structural blocks come in **10+ wood type variants**: Acacia, Bamboo, Birch, Cherry, Crimson, Dark Oak, Jungle, Mangrove, Oak, Spruce — so everything fits your build's palette.

### 🧱 Block Highlights
- **Structural elements** — Edges, plates, pergolas, beams, support columns, lattices
- **Doors & Shutters** — Centered and standard doors, fancy railing shutters
- **Water features** — Pools, faucets, water jets with 300+ shape configurations
- **Furniture** — Chairs (rideable!), couches, tables, footstools, canopy beds
- **Lighting** — Candlesticks, lanterns, cressets, paper lamps, iron columns
- **Displays** — Displayer blocks with a 9-slot inventory to showcase your items
- **Decorative** — Flower pots, teapots, teacups, statues, gargoyles, chimneys, fireplaces

### 🗂️ Builder-Friendly Inventory
The creative inventory features a **custom tabbing system** with subtabs and icons, keeping your 350+ blocks neatly organized by category and culture — no more scrolling through endless lists.

### 🔗 Connected Textures
Seamless connected textures via [Fusion](https://modrinth.com/mod/fusion-connected-textures), giving walls, columns, and structural elements a polished, continuous look.

---

## 📦 Installation

### Requirements
- Minecraft **1.20.1**
- [**Fusion Connected Textures**](https://modrinth.com/mod/fusion-connected-textures) *(required)*

Available for both **Forge** and **Fabric**.

### Download
| Platform | Link |
|---|---|
| CurseForge | [curseforge.com/minecraft/mc-mods/dawn-of-time](https://www.curseforge.com/minecraft/mc-mods/dawn-of-time) |
| Modrinth | [modrinth.com/mod/dawn-of-time](https://modrinth.com/mod/dawn-of-time) |

---

## 🤝 Compatibility

| Mod | Status |
|---|---|
| [Dramatic Doors](https://modrinth.com/mod/dramatic-doors) | ✅ Perfect for grand entrances |
| [Every Compat](https://modrinth.com/mod/every-compat) | ✅ Extended building possibilities |
| [Fusion](https://modrinth.com/mod/fusion-connected-textures) (1.19.2+) | ✅ Connected textures |
| [CTM](https://www.curseforge.com/minecraft/mc-mods/ctm) (1.16.5 and below) | ✅ Connected textures support |
| Lightspeed optimizations | ⚠️ Not compatible in 1.19.2 |

---

## 🛠️ For Developers

The mod uses a **multiloader architecture** (common + Forge + Fabric) for a clean code-sharing approach.

```
dawnoftimebuilder/
├── common/     # Shared logic — blocks, entities, registries, mixins
├── forge/      # Forge-specific registration and data generators
├── fabric/     # Fabric-specific registration
└── buildSrc/   # Custom Gradle plugins
```

**Tech stack:**
- Java 17
- Minecraft 1.20.1
- Forge 47.2.20 / Fabric 0.92.6
- Parchment mappings
- Mixin 0.8.5

### Building from Source
```bash
git clone https://github.com/DawnOfTimeMC/dawnoftimebuilder
cd dawnoftimebuilder
./gradlew build
```

Outputs are in `forge/build/libs/` and `fabric/build/libs/`.

---

## 🌐 Translations

Dawn of Time is available in **34 languages**, thanks to our amazing community contributors. Want to add or improve a translation? Open a PR with your updated `lang/<code>.json` file!

---

## ❤️ Credits

**Development, models and textures** by [Poulpinou](https://github.com/Poulpinou) & [TheGoldenWorld](https://github.com/TheGoldenWorld).

**Additional models** — thank you to *mr_ch0c0late*, *Botmark*, *Instantnootles*, and *Lucthar* for their wonderful models.

**Code support** — thank you to *Grand_Gibus*, *Aythya*, *Wonyu*, *Zadrac*, *Seynax*, and *Hahdrim* for their invaluable help.

**Showcase builds** — thank you to *Knoxxturre* and *Jackie* for their gorgeous buildings (and their subtle sense of humor).

**Our community** — thank you all for constantly helping us improve the mod. ❤️

**Special thanks** to [Millénaire](https://www.millenaire.org/), the mod of our childhood that inspired this whole project.

**Font** used in assets: [Minecrafter](https://www.dafont.com/minecrafter.font)

---

## 💖 Support the Mod

If you enjoy Dawn of Time and want to help us keep building, consider supporting us on Patreon:

[![Support on Patreon](https://img.shields.io/badge/Support%20us%20on-Patreon-f96854?style=for-the-badge&logo=patreon)](https://www.patreon.com/cw/dawnoftimemod)

---

## 📄 License

Dawn of Time is released under the [MIT License](LICENSE.md).
© Poulpinou & TheGoldenWorld — DawnOfTimeMC team.
