# Vinyl Library System

A JavaFX application implementing a vinyl library management system using MVVM, Observer, and State design patterns.

## Project Structure

```
vinyl-library/
├── pom.xml
├── src/main/java/com/vinyllibrary/
│   ├── Main.java                          # Application entry point
│   ├── model/
│   │   ├── Vinyl.java                     # Vinyl model with state pattern
│   │   └── VinylLibrary.java              # Library model with observer pattern
│   ├── viewmodel/
│   │   └── MainViewModel.java             # MVVM ViewModel layer
│   ├── view/
│   │   └── MainController.java            # MVVM View controller
│   ├── patterns/
│   │   ├── observer/
│   │   │   ├── Observable.java            # Observer pattern interface
│   │   │   └── Observer.java              # Observer pattern interface
│   │   ├── state/
│   │   │   ├── VinylState.java            # State pattern interface
│   │   │   ├── AvailableState.java       # Concrete state: available
│   │   │   ├── ReservedState.java         # Concrete state: reserved
│   │   │   ├── BorrowedState.java         # Concrete state: borrowed
│   │   │   └── BorrowedReservedState.java # Concrete state: borrowed & reserved
│   │   └── session/
│   │       └── SessionManager.java        # Singleton for session management
│   └── util/
│       └── UserSimulation.java            # Runnable for simulating other users
└── src/main/resources/
    └── com/vinyllibrary/view/
        └── main-view.fxml                 # FXML view definition
```

## Design Patterns

### MVVM (Model-View-ViewModel)
- **Model**: `Vinyl`, `VinylLibrary` - Data models
- **ViewModel**: `MainViewModel` - Business logic and state management
- **View**: `MainController`, `main-view.fxml` - UI presentation

### Observer Pattern
- `Observable` interface implemented by `Vinyl` and `VinylLibrary`
- `Observer` interface implemented by `MainViewModel`
- Automatic UI updates when data changes

### State Pattern
- `VinylState` interface defines state behavior
- Four concrete states: `AvailableState`, `ReservedState`, `BorrowedState`, `BorrowedReservedState`
- State transitions handled by state objects

### Singleton Pattern
- `SessionManager` provides global access to session data

## Features

- View list of vinyls with their information and states
- Reserve vinyls (if available or borrowed without reservation)
- Borrow vinyls (if available or reserved by you)
- Return vinyls
- Mark vinyls for removal
- Add new vinyls
- Automatic UI updates via Observer pattern
- Simulated concurrent users via threads

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build
```bash
mvn clean install
```

### Run
```bash
mvn javafx:run
```

## User Guide

1. **Set User ID**: Enter your user ID in the text field at the top
2. **Select a Vinyl**: Click on a vinyl in the table
3. **Perform Actions**:
   - **Reserve**: Reserve the selected vinyl
   - **Borrow**: Borrow the selected vinyl
   - **Return**: Return the selected vinyl
   - **Mark for Removal**: Mark the vinyl for removal
4. **Add Vinyl**: Click "Add Vinyl" to add a new vinyl to the library

## Vinyl States

- **Available**: Not borrowed, no reservation
- **Reserved**: Reserved by one user
- **Borrowed**: Currently borrowed by one user
- **Borrowed & Reserved**: Borrowed and reserved simultaneously

## Thread Safety

The application uses a background thread to simulate other users randomly performing actions. The GUI remains responsive and updates automatically when changes occur.

## UML Diagrams

- State Machine Diagram: `vinyl-state-machine.md`
- Class Diagram: `vinyl-class-diagram.md`

## Requirements Met

- MVVM Design Pattern
- Observer Design Pattern
- State Design Pattern
- Threading for user simulation
- Automatic GUI updates
- UML State Machine Diagram
- UML Class Diagram
