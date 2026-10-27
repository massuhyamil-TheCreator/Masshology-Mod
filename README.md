Masshbestiary (Masshology)

A solo-developed creature and dimension mod for Minecraft (NeoForge 1.21.11), set in an original lore universe called Masshology. The mod is built around the Farfrost, a hostile frozen dimension with its own creatures, bosses, progression and survival rules.

Status: work in progress. The first creature is complete and playable; the second is mid-implementation. See Project status for an honest breakdown of what exists today and what is only designed.

<!-- TODO: add 1-2 screenshots or a short GIF of the Draugr Warrior in-game here --> <!-- ![Draugr Warrior](docs/draugr.png) -->
What this project is

Everything in the mod is built end to end by one person:

3D modeling and texturing in Blockbench
Keyframe animation (GeckoLib format)
Java gameplay code: entities, AI, renderers, registries
Data-driven assets: blockstates, models, language files
Game and world design: lore, creature roster, progression and mechanics
Project status
Component	State
Draugr Warrior (hostile mob)	Complete: model, animations, custom renderer, tested in-game
Cabrunu (goat-type mob)	In progress: model and full animation set done, entity class and Brain AI partly implemented (see below)
Glacial Moss (custom block)	Implemented in code and assets (registry, blockstate, models, lang); texture is a placeholder
Frostvelgr (boss)	Model geometry complete, texturing partial
Bullgar (cargo animal) and the rest of the roster	Designed only, not started
Farfrost dimension and world generation	Designed only, not started
Cabrunu in detail
Piece	State
Model and animation set (idle, walk, run, graze, shake_head, warning, ram, attack x2, death, jump x3)	Done
CabrunuEntity (attributes, breeding food, Brain wiring)	Done
CabrunuAi: memories, Core activity, Idle activity	Done
CabrunuAi: Ram activity, Long Jump activity	Pending
Panic only below ~30% health	Pending
Renderer and in-game testing	Pending (not yet runnable in-game)
<!-- TODO: update these tables to match the real state of the repo before sending -->
Technical highlights

Brain-based creature AI. Instead of the simple Goal system, the Cabrunu uses Minecraft's Brain architecture (the same one used by vanilla goats, villagers and piglins): Sensors write to typed Memories, Behaviors read them, and Behaviors are grouped into Activities that compete based on memory conditions. The Idle activity already composes vanilla behaviors (BabyFollowAdult, RandomStroll, AnimalMakeLove, FollowTemptation); the Ram and Long Jump activities are planned to reuse vanilla's RamTarget / PrepareRamNearestTarget rather than reimplement them. The AI logic lives in its own class (CabrunuAi), separate from the entity, mirroring the structure of vanilla.

Animation pipeline. Models and animations are authored in Blockbench and exported to GeckoLib, with a custom renderer per entity. Animation states are designed around the AI: a warning pose before a looping ram charge, and a three-part jump (jump_start, jump_air held on its last frame, jump_land) because flight time varies at runtime.

Registry and data layout. Entities, blocks and items are registered through NeoForge DeferredRegisters, with attributes registered through a single event hook. The Glacial Moss block extends vanilla GlowLichenBlock and is described by a multipart blockstate, a block model, an item model and a lang entry.

Working with a moving target. NeoForge 1.21.11 is a transitional version with many renamed classes, and most online tutorials are wrong for it. A recurring part of the workflow was reading decompiled vanilla source (Goat, GoatAi, PanicGoal, GlowLichenBlock) to learn the real API instead of copying outdated examples.

Version control across two machines. The project is developed from two computers, synchronized through Git (pull before work, commit and push after).

Design (not yet implemented)

These systems are fully designed but not coded yet. They are listed to show the direction of the project, not as existing features.

Farfrost threat model. Ambient cold and freezing handled through the Cold Sweat mod; a custom "Corruption" effect (a 0-100% counter with stacking debuffs over 40 seconds) handled by a custom MobEffect. Armor protection against Corruption uses a consumable purification mechanic.
Ring-based world generation. A custom BiomeSource that picks the biome by distance from the dimension portal, reusing vanilla terrain definitions rather than writing new density functions.
Ecosystem AI. Passive creatures flee, neutral ones react only when attacked, and hostile species compete with each other.
Progression. A three-fragment progression chain tied to the dimension's bosses.
Tech stack

Language	Java
Platform	Minecraft 1.21.11, NeoForge 21.11.45
Animation / rendering	GeckoLib 5.4.3
Art	Blockbench
Build	Gradle
IDE	IntelliJ IDEA
Planned dependencies	Cold Sweat, Citadel
Project structure
src/main/java/com/massh/masshology/
├── MasshologyMasshbestiary.java        # Mod entry point, registry wiring
├── MasshologyMasshbestiaryClient.java  # Client-side setup
├── ModEntities.java                    # Entity types, spawn eggs, attributes
├── ModBlocks.java                      # Blocks and block items
├── Config.java
├── client/renderer/                    # Entity renderers
└── entity/
├── draugr/DraugrEntity.java
└── cabrunu/
├── CabrunuEntity.java          # Entity definition, Brain wiring
└── CabrunuAi.java              # Memories, Activities, Behaviors

src/main/resources/assets/masshbestiary/
├── geckolib/{animations,models,textures}/entity/
├── blockstates/
├── models/{block,item}/
├── textures/block/
└── lang/en_us.json
Building and running

Requirements: JDK 25 and an internet connection for the first Gradle sync.

bash
git clone https://github.com/massuhyamil-TheCreator/Masshology-Mod.git
cd Masshology-Mod
./gradlew runClient      # Windows: gradlew.bat runClient

This launches a development Minecraft client with the mod loaded. In-game, spawn the Draugr Warrior with its spawn egg from the creative inventory.

<!-- TODO: confirm the exact steps above work from a fresh clone -->
Roadmap
Finish the Cabrunu (Ram, Long Jump, panic threshold, renderer) and test it in-game
Bullgar (cargo animal) design and modeling
Remaining creature roster, then Frostvelgr
Farfrost dimension, world generation and survival systems
Polish, balance and a 1.0 release
Author

Yamil Massuh, Licenciatura en Sistemas (Information Systems) student at UADE, Buenos Aires, Argentina. Self-taught in Python; learned Java largely by building this project.

GitHub: massuhyamil-TheCreator
Contact: <!-- TODO: email: massuhyamil@gmail.com / LinkedIn: www.linkedin.com/in/yamil-massuh-sistemas
License
<!-- TODO: choose a license (e.g. MIT for the code) and state whether the art assets are all rights reserved -->
Resumen en español

Masshbestiary es un mod para Minecraft (NeoForge 1.21.11) desarrollado por una sola persona, ambientado en un universo original llamado Masshology. El eje es el Farfrost, una dimensión helada y hostil con criaturas, jefes, progresión y reglas de supervivencia propias.

Qué hay hecho hoy:

Draugr Warrior: completo (modelo, animaciones, renderer, probado en el juego).
Cabrunu: en desarrollo. Modelo y set de animaciones terminados; la entidad y parte de su IA basada en Brain implementadas. Faltan las actividades de embestida y salto, el umbral de pánico y el renderer.
Musgo Glacial: bloque implementado en código y assets (textura provisoria).
Frostvelgr: geometría del modelo completa, texturizado parcial.
El resto del bestiario y la dimensión están diseñados pero no empezados.

Qué demuestra el proyecto: arquitectura de IA con Brain (Sensors, Memories, Behaviors, Activities), pipeline de animación con GeckoLib, registros de NeoForge, assets orientados a datos, lectura de código fuente decompilado para trabajar sobre una versión con APIs renombradas, y control de versiones con Git entre dos máquinas.

Autor: Yamil Massuh, estudiante de Licenciatura en Sistemas en UADE. Autodidacta en Python; aprendió Java en buena parte construyendo este proyecto.    