# HumilityVAL

## Humility - Version Agnostic Library

Small library focused on performance, bridging some differences between older and newer MC versions, 
to make supporting multiple MC versions easier.

HumilityVAL ecosystem consists of:
- ***this*** Minecraft mod library
- separate optional IntelliJ IDEA plugin found [here](https://github.com/MikiP98/HumilityVAL-Plugin/releases)
- custom optional preconfigured project template generator found ~~[here](TODO)~~ ***WIP***
- custom semi-optional version of *BSL* to *AGPL* licence

[//]: # (- optional Python package for mod building)

Full development documentation can be found ~~[here](TODO)~~ ***WIP***

## Current features

Almost no overhead by making the abstraction logic handled in compile time as much as possible 
and designing everything else to have as little overhead as possible.

**Supported Minecraft versions:**
- 26.2
- 26.1.2
- 1.21.11
- 1.21.8
- 1.21.6
- 1.21.4
- 1.21.1
- 1.20.6
- 1.20.4
- 1.20.1

**Minecraft version agnostic features:**

[//]: # (| common                                                                                                                                                                                           | client                                                                                                                      | gametest                                                   |)
[//]: # (|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------|:-----------------------------------------------------------|)
[//]: # (| <ul><li>Block Registration</li><li>Item Registration</li><li>Level's build limit getter</li><li>Player chat message sending</li><li>Block Entity NBT/Data Component saving and loading</li><ul/> | <ul><li>Block Entity Renderer Registration</li><li>Safe way of assigning block render layers in older MC versions</li><ul/> | <ul><li>GameTestHelper True and False assertions</li><ul/> |)

- **common**
  - Block Registration
  - Item Registration
  - Level's build limit getter
  - Player chat message sending
  - Block Entity NBT/Data Component saving and loading
- **client**
  - Block Entity Renderer Registration
  - Safe way of assigning block render layers in older MC versions
- **gametest**
  - GameTestHelper True and False assertions

Plus `Math.clamp(...)` port to JAVA 17

## Roadmap

- documentation