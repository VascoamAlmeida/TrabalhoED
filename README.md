# Data Structures — Mission Simulator

Academic project developed for the **Data Structures** course during the **2024/2025** academic year.

The project implements a console-based mission simulator inspired by an infiltration scenario. A mission is loaded from JSON and represented as a graph whose vertices are the building rooms and whose edges are the connections between them.

> Originally developed as a group academic project. This repository does not identify the other group member.

## Main objectives

The assignment focused on selecting and implementing suitable data structures instead of relying on the Java Collections Framework for the project data structures.

The project therefore includes custom implementations of structures such as:

- array and linked lists;
- stacks;
- queues;
- trees and heaps;
- graphs and networks.

The mission simulator uses these structures to model the building, movement, inventory and simulation results.

## Features

- Import mission scenarios from JSON.
- Validate the main elements of the imported mission.
- Support mission files containing a single scenario or multiple versions.
- Represent the building as a graph.
- Display the loaded map in the console through its graph representation.
- Manual simulation with selectable entry point and room-by-room movement.
- Enemy movement up to two rooms away.
- Health kits stored in a stack and consumed from the top of the backpack.
- Bulletproof vests that immediately add health points and may raise health above 100.
- Target recovery and mission success/failure conditions.
- At the end of each manual turn, show:
  - the shortest path to the target;
  - the shortest path to the nearest uncollected health kit.
- Export manual simulation routes to JSON.
- Display manual simulation results ordered by remaining health, including the mission version.
- Automatic route planning considering all available entrances and exits and selecting the complete route that preserves the greatest amount of health.

## Project structure

```text
.
├── JSON/
│   └── json-simple-1.1.1.jar
├── src/
│   ├── Collections/
│   │   ├── Exceptions/
│   │   ├── Graphs/
│   │   ├── Lists/
│   │   ├── Queues/
│   │   ├── Stacks/
│   │   └── Trees/
│   ├── elementosJogo/
│   ├── resorces/
│   │   ├── Missoes/
│   │   └── Relatorios/
│   └── Main.java
├── .gitignore
└── README.md
```

The `resorces` directory name is kept from the original academic project structure.

## Running the project

### IntelliJ IDEA

1. Open the repository as a Java project.
2. Add `JSON/json-simple-1.1.1.jar` as a project library.
3. Mark `src` as the Sources Root if the IDE does not do so automatically.
4. Run `Main.java`.

### Command line

The source code is compatible with **Java 17 or newer**.

On Linux/macOS:

```bash
mkdir -p out
javac -encoding UTF-8 -cp "JSON/json-simple-1.1.1.jar" -d out $(find src -name "*.java")
java -cp "out:JSON/json-simple-1.1.1.jar" Main
```

On Windows PowerShell:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
$files = Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -cp "JSON\json-simple-1.1.1.jar" -d out $files
java -cp "out;JSON\json-simple-1.1.1.jar" Main
```

## Sample missions

The repository contains sample mission files in:

```text
src/resorces/Missoes/
```

`Mpatadecoelho.json` contains one version of the mission and `Mpatadecoelho2versoes.json` demonstrates loading multiple versions of the same mission.

## Notes

This repository preserves the original academic project and its custom data-structure implementations. Changes made later were limited to correcting functional issues, removing machine-specific paths/generated IDE files, and preparing the project for public portfolio use.
