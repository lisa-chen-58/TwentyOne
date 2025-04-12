# Twenty-One Mini Game

This is a simple terminal-based Java game inspired by Blackjack. Players take turns rolling a 6-sided die to get as close as possible to 21 without going over. The goal is to be the last player standing — or to hit exactly 21.

This project was created as a refresher in Java fundamentals, object-oriented programming, and clean code practices. It focuses on:
- Class design and separation of concerns
- Input validation
- Game loop logic
- Beginner-level algorithmic reasoning
- Writing testable, maintainable Java code

---

## 🎯 How to Play

- Add 1 or more players to begin.
- On each turn, press enter to roll the dice.
- A player wins by reaching **exactly 21**.
- A player **busts** if they exceed 21.
- The game ends when:
    - A player wins
    - All other players bust
    - The user manually exits the game

---

## 🧠 Concepts Practiced

- Java object modeling (`Player`, `Game`)
- Input handling with `Scanner`
- Flow control (loops, conditions, method structure)
- Encapsulation and private fields
- Defensive programming (null checks, validation)
- Basic game mechanics (state transitions, turn-taking)

---

## 🗂️ Project Structure

```
project-root/
├── Main.java         // Entry point
├── Game.java         // Core game loop and logic
└── Player.java       // Player state and roll logic
```

---

## ✅ Requirements

- Java 8 or higher
- Terminal / command line access

---

## 🏁 How to Run

Compile all files:
```bash
javac Main.java Game.java Player.java
```

Run the program:
```bash
java Main
```

---

## 🧪 Future Improvements (Optional)

This project is intentionally small, but if extended, ideas include:
- Unit tests with JUnit
- Dice roll mocking for testability
- GUI or web front-end
- Logging system or scoreboard
- Replay or multi-round support

---

## 🔖 About This Project

This mini-project was created to re-establish hands-on familiarity with Java after using other tech stacks. It reflects software engineering principles even in a beginner-sized scope. While simple, the structure and decisions reflect readiness for larger-scale systems and collaborative development environments.

