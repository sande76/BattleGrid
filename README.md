# BattleGrid

BattleGrid is a turn-based robot arena game developed using Java SE and JavaFX.

The project focuses on demonstrating object-oriented programming principles through robot classes, reusable abilities, combat strategies, and modular game architecture.

## Status

🚧 Under Development

## Overview

BattleGrid pits robots against each other on a grid-based arena. Players take turns moving, attacking, and using special abilities, while enemy robots respond with strategy-based behavior rather than random actions. The project was built to demonstrate object-oriented design in a real, interactive application.

## Technologies

- Java 21
- Java SE
- JavaFX
- Maven
- JUnit 5

## Core Concepts

- Object-Oriented Programming
- Encapsulation
- Abstraction
- Inheritance
- Polymorphism
- Strategy Pattern
- Java Collections
- Event-Driven Programming

## Planned Features

- Grid-based robot movement
- Turn-based combat
- Multiple robot classes
- Robot abilities
- Enemy AI strategies
- Obstacles and pickups
- Victory and game-over states

## Design & Architecture

BattleGrid is built around the core concepts listed above:

- **Encapsulation** - robot state (health, position, abilities) is managed internally and exposed through controlled interfaces
- **Inheritance** - a shared `Robot` base class is extended by specific robot types to reuse common behavior
- **Polymorphism** - combat and ability logic is dispatched through overridden methods, letting each robot type behave differently under a common interface
- **Abstraction** - abstract classes/interfaces define contracts (e.g., movement, attack, ability) that concrete robot types implement
- **Strategy Pattern** - enemy AI behavior is encapsulated into interchangeable strategy implementations rather than hardcoded logic
- **Java Collections** - used to manage robots, grid state, and turn queues
- **Event-Driven Programming** — game and UI actions (moves, attacks, ability triggers) are handled via events
- **Modular class design** - game logic, grid/board management, rendering, and AI strategy are separated into distinct, loosely-coupled modules for maintainability and testability

## Getting Started

### Prerequisites

- JDK 21
- Maven 3.8+
- JavaFX SDK (if not resolved automatically via Maven)

### Build

```bash
mvn clean install
```

### Run

```bash
mvn javafx:run
```

### Test

```bash
mvn test
```

## Planned Project Structure

```
BattleGrid/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── model/       # Robot classes, grid, game state
│   │   │   ├── controller/  # Game logic, turn management
│   │   │   ├── view/        # JavaFX UI components
│   │   │   └── ai/          # Enemy strategy/behavior logic
│   │   └── resources/       # FXML, images, styles
│   └── test/
│       └── java/            # JUnit 5 test suites
├── pom.xml
└── README.md
```

## License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

## Author

**sande76**