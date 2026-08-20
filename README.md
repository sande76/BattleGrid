# BattleGrid

BattleGrid is a single-player, turn-based robot arena game developed with Java 21 and JavaFX.

The project demonstrates object-oriented design through robot state, grid movement, combat rules, and basic enemy behavior.

## Status

Under development. 
The current milestone is a playable 5x5 arena with one player robot, one enemy robot, movement, basic combat, and an automatic enemy turn.

## Game Mode

BattleGrid is currently a single-player game. The player controls one robot against an enemy robot controlled by simple rule-based behavior.

## Current Features

- 5x5 grid-based arena
- Player and enemy robot displayed as colored circles
- Player movement to adjacent cells
- Collision prevention between robots
- Robot health and attack damage
- Adjacent player attacks
- Enemy turn after each successful player action
- Enemy movement toward the player
- Enemy attack when adjacent
- Victory and game-over state
- JUnit 5 tests for board, movement, enemy, and combat rules

## Planned Features

- Multiple robot types
- Special abilities and cooldowns
- Interchangeable AI strategies using the Strategy pattern
- Obstacles and pickups
- Improved victory conditions
- Animations, sound, and richer visuals

## Technologies

- Java 21
- JavaFX
- Maven Wrapper
- JUnit 5

## Design & Architecture

- **Encapsulation** - `Robot` owns its position, health, and damage state.
- **Abstraction** - `GameBoard` owns board rules such as movement, combat, and turns.
- **Records** - `Position` represents an immutable row and column coordinate.
- **Event-driven UI** - JavaFX cell clicks trigger movement or attacks.
- **Planned Strategy pattern** - future enemy behaviors will be moved into interchangeable strategy classes.

## Getting Started

### Prerequisites

- JDK 21
- Internet access for the first Maven Wrapper run

JavaFX dependencies are downloaded automatically by Maven; a separate JavaFX SDK installation is not required.

### Build on Windows

```bat
mvnw.cmd clean install
```

### Run on Windows

```bat
mvnw.cmd javafx:run
```

### Test on Windows

```bat
mvnw.cmd test
```

On macOS or Linux, use `./mvnw` instead of `mvnw.cmd`.

## Current Project Structure

```text
BattleGrid/
├── src/
│   ├── main/java/com/sande76/battlegrid/
│   │   ├── BattleGridApplication.java
│   │   └── model/
│   │       ├── GameBoard.java
│   │       ├── Position.java
│   │       └── Robot.java
│   └── test/java/com/sande76/battlegrid/model/
│       └── GameBoardTest.java
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

