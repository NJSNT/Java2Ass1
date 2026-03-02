# Vinyl Library Assignment - Submission Summary

## Project Overview
This is a complete JavaFX application for managing a vinyl library using MVVM, Observer, and State design patterns.

## Design Patterns Implementation

### 1. MVVM (Model-View-ViewModel) Pattern
- **Model Layer**:
  - `Vinyl.java` - Represents a vinyl record with properties and state management
  - `VinylLibrary.java` - Manages the collection of vinyls
  
- **ViewModel Layer**:
  - `MainViewModel.java` - Bridges the model and view, handles business logic
  
- **View Layer**:
  - `MainController.java` - Handles UI interactions
  - `main-view.fxml` - UI layout definition

### 2. Observer Pattern
- **Observable Interface**: `com.vinyllibrary.patterns.observer.Observable`
- **Observer Interface**: `com.vinyllibrary.patterns.observer.Observer`
- **Implementation**:
  - `Vinyl` class implements Observable - notifies observers when state changes
  - `VinylLibrary` class implements Observable - notifies observers when collection changes
  - `MainViewModel` implements Observer - receives updates and refreshes the UI
- **Benefit**: Automatic GUI updates when data changes in the model

### 3. State Pattern
- **State Interface**: `com.vinyllibrary.patterns.state.VinylState`
- **Concrete States**:
  - `AvailableState` - Vinyl is available for reservation/borrowing
  - `ReservedState` - Vinyl is reserved by one user
  - `BorrowedState` - Vinyl is currently borrowed
  - `BorrowedReservedState` - Vinyl is both borrowed and reserved
- **Context**: `Vinyl` class uses the state pattern to manage its state transitions
- **Benefit**: Clean separation of state-specific behavior and easy state transitions

### 4. Singleton Pattern
- **SessionManager** - Singleton class for managing session data (current user ID, selected vinyl ID)
- Shared across the application to maintain state between views

### 5. Runnable/Thread Pattern
- `UserSimulation.java` - Implements Runnable to simulate concurrent users
- Runs in a separate daemon thread to perform random actions at random intervals
- Demonstrates thread-safe GUI updates using the Observer pattern

## Key Features

1. **View Vinyls**: Display all vinyls in a table with their information (ID, Title, Artist, Release Year, State, Borrowed By, Reserved By, Marked for Removal)

2. **Reserve Vinyl**:
   - Can reserve if vinyl is Available
   - Can reserve if vinyl is Borrowed and not already Reserved
   - Cannot reserve if vinyl is marked for removal

3. **Borrow Vinyl**:
   - Can borrow if vinyl is Available
   - Can borrow if vinyl is Reserved by the current user

4. **Return Vinyl**:
   - Return borrowed vinyl back to Available state
   - If vinyl had a reservation, it goes to Reserved state

5. **Mark for Removal**:
   - Mark vinyl for removal
   - If Available and no reservation: can be removed immediately
   - If Borrowed/Reserved: marked for removal, will be removed when Available

6. **Add Vinyl**:
   - Dialog to add new vinyls to the library

7. **Automatic Updates**:
   - GUI automatically updates via Observer pattern
   - Works with concurrent user simulation

## Project Structure

```
vinyl-library/
├── pom.xml                                          # Maven configuration
├── README.md                                        # Project documentation
├── vinyl-state-machine.md                           # UML State Machine Diagram
├── vinyl-class-diagram.md                           # UML Class Diagram
└── src/main/
    ├── java/com/vinyllibrary/
    │   ├── Main.java                                # Application entry point
    │   ├── model/
    │   │   ├── Vinyl.java                           # Vinyl model with state pattern
    │   │   └── VinylLibrary.java                    # Library model with observer pattern
    │   ├── viewmodel/
    │   │   └── MainViewModel.java                   # MVVM ViewModel layer
    │   ├── view/
    │   │   └── MainController.java                  # MVVM View controller
    │   ├── patterns/
    │   │   ├── observer/
    │   │   │   ├── Observable.java                  # Observer pattern interface
    │   │   │   └── Observer.java                    # Observer pattern interface
    │   │   ├── state/
    │   │   │   ├── VinylState.java                  # State pattern interface
    │   │   │   ├── AvailableState.java             # Concrete state: available
    │   │   │   ├── ReservedState.java               # Concrete state: reserved
    │   │   │   ├── BorrowedState.java               # Concrete state: borrowed
    │   │   │   └── BorrowedReservedState.java       # Concrete state: borrowed & reserved
    │   │   └── session/
    │   │       └── SessionManager.java              # Singleton for session management
    │   └── util/
    │       └── UserSimulation.java                  # Runnable for simulating other users
    └── resources/
        └── com/vinyllibrary/view/
            └── main-view.fxml                       # FXML view definition
```

## UML Diagrams

### State Machine Diagram (`vinyl-state-machine.md`)
Shows all possible state transitions for a Vinyl:
- Available → Reserved → Borrowed
- Available → Borrowed → BorrowedReserved
- BorrowedReserved → Reserved
- All states return to Available (except Marked for Removal)

### Class Diagram (`vinyl-class-diagram.md`)
Shows all classes and their relationships:
- MVVM architecture components
- Observer pattern implementation
- State pattern implementation
- Session management
- User simulation

## Requirements Compliance

- MVVM Design Pattern: Implemented with Model, ViewModel, and View layers
- Observer Design Pattern: Implemented with Observable and Observer interfaces
- State Design Pattern: Implemented with VinylState interface and 4 concrete states
- Threading: UserSimulation runs in a separate daemon thread
- Automatic GUI Updates: Via Observer pattern
- UML State Machine Diagram: Created in vinyl-state-machine.md
- UML Class Diagram: Created in vinyl-class-diagram.md

## Building and Running

Requirements:
- Java 17 or higher
- Maven 3.6 or higher

Build:
```bash
mvn clean install
```

Run:
```bash
mvn javafx:run
```

## Assignment Submission

This project includes all required materials for submission:
1. Source code for all classes in `src/main/java/com/vinyllibrary/`
2. UML State Machine Diagram: `vinyl-state-machine.md`
3. UML Class Diagram: `vinyl-class-diagram.md`
4. FXML resources: `src/main/resources/com/vinyllibrary/view/main-view.fxml`
5. Maven configuration: `pom.xml`

The project demonstrates clean code practices with proper separation of concerns, design pattern implementation, and thread safety.
