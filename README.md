# Sudoku App

A Sudoku game built in Java. Play classic 9×9 puzzles with a clean, keyboard-friendly interface.

## Features

- **Classic Sudoku** — Standard 9×9 grid with 3×3 boxes
- **Multiple difficulties** — Easy, medium, and hard puzzles
- **Input validation** — Highlights conflicts in rows, columns, and boxes
- **Hints and undo** — Get help when stuck and step back through moves
- **Timer and stats** — Track solve time and personal bests

> **Note:** This project is in early development. Game logic and the user interface are being built out incrementally.

## Requirements

- Java 17 or later
- Maven 3.8+ (for building and running tests)

## Getting Started

Clone the repository:

```bash
git clone https://github.com/stoichy/Sudoku-App.git
cd Sudoku-App
```

Build the project (once source is added):

```bash
mvn clean package
```

Run the game:

```bash
mvn exec:java
```

Or run the packaged JAR:

```bash
java -jar target/sudoku-app.jar
```

## Project Structure

```
Sudoku-App/
├── src/
│   ├── main/java/     # Game logic, puzzle generation, and UI
│   └── test/java/     # Unit and integration tests
├── pom.xml            # Maven build configuration
└── README.md
```

## Development

Run tests:

```bash
mvn test
```

Format and lint (when configured):

```bash
mvn verify
```

## Contributing

Contributions are welcome. Please open an issue to discuss larger changes, or submit a pull request with a clear description of your changes.

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/my-feature`)
3. Commit your changes (`git commit -m 'Add my feature'`)
4. Push to your branch (`git push origin feature/my-feature`)
5. Open a pull request

## License

This project is licensed under the Apache License 2.0. See [LICENSE](LICENSE) for details.
