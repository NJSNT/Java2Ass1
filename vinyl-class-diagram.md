# Vinyl Library - Class Diagram

## UML Class Diagram

```mermaid
classDiagram
    class Main {
        +void start(Stage)
        +void main(String[])
    }
    
    class VinylLibrary {
        -List~Vinyl~ vinyls
        -List~Observer~ observers
        +VinylLibrary()
        +List~Vinyl~ getVinyls()
        +Vinyl getVinylById(String)
        +void addVinyl(Vinyl)
        +void removeVinyl(String)
        +void addObserver(Observer)
        +void removeObserver(Observer)
        +void notifyObservers()
    }
    
    class Vinyl {
        -StringProperty id
        -StringProperty title
        -StringProperty artist
        -IntegerProperty releaseYear
        -StringProperty borrowedBy
        -StringProperty reservedBy
        -BooleanProperty markedForRemoval
        -VinylState state
        -List~Observer~ observers
        +Vinyl(String, String, String, int)
        +String getId()
        +String getTitle()
        +String getArtist()
        +int getReleaseYear()
        +String getBorrowedBy()
        +String getReservedBy()
        +boolean isMarkedForRemoval()
        +VinylState getState()
        +String getStateName()
        +void reserve(String)
        +void borrow(String)
        +void returnVinyl()
        +boolean canBeRemoved()
        +void markForRemoval()
        +void addObserver(Observer)
        +void removeObserver(Observer)
        +void notifyObservers()
    }
    
    class VinylState {
        <<interface>>
        +void reserve(Vinyl, String)
        +void borrow(Vinyl, String)
        +void returnVinyl(Vinyl)
        +String getStateName()
        +boolean isAvailable()
        +boolean isReserved()
        +boolean isBorrowed()
    }
    
    class AvailableState {
        +void reserve(Vinyl, String)
        +void borrow(Vinyl, String)
        +void returnVinyl(Vinyl)
        +String getStateName()
        +boolean isAvailable()
        +boolean isReserved()
        +boolean isBorrowed()
    }
    
    class ReservedState {
        -String reservedBy
        +ReservedState(String)
        +void reserve(Vinyl, String)
        +void borrow(Vinyl, String)
        +void returnVinyl(Vinyl)
        +String getStateName()
        +boolean isAvailable()
        +boolean isReserved()
        +boolean isBorrowed()
    }
    
    class BorrowedState {
        -String borrowedBy
        +BorrowedState(String)
        +void reserve(Vinyl, String)
        +void borrow(Vinyl, String)
        +void returnVinyl(Vinyl)
        +String getStateName()
        +boolean isAvailable()
        +boolean isReserved()
        +boolean isBorrowed()
    }
    
    class BorrowedReservedState {
        -String borrowedBy
        -String reservedBy
        +BorrowedReservedState(String, String)
        +void reserve(Vinyl, String)
        +void borrow(Vinyl, String)
        +void returnVinyl(Vinyl)
        +String getStateName()
        +boolean isAvailable()
        +boolean isReserved()
        +boolean isBorrowed()
    }
    
    class Observable {
        <<interface>>
        +void addObserver(Observer)
        +void removeObserver(Observer)
        +void notifyObservers()
    }
    
    class Observer {
        <<interface>>
        +void update()
    }
    
    class SessionManager {
        -static SessionManager instance
        -String currentUserId
        -String selectedVinylId
        -SessionManager()
        +static SessionManager getInstance()
        +String getCurrentUserId()
        +void setCurrentUserId(String)
        +String getSelectedVinylId()
        +void setSelectedVinylId(String)
    }
    
    class MainViewModel {
        -VinylLibrary library
        -ObservableList~Vinyl~ vinylList
        -StringProperty currentUserId
        -StringProperty selectedVinylId
        -SessionManager sessionManager
        +MainViewModel()
        +ObservableList~Vinyl~ getVinylList()
        +StringProperty currentUserIdProperty()
        +StringProperty selectedVinylIdProperty()
        +void setCurrentUserId(String)
        +void setSelectedVinylId(String)
        +Vinyl getSelectedVinyl()
        +void reserveVinyl()
        +void borrowVinyl()
        +void returnVinyl()
        +void markForRemoval()
        +void addVinyl(String, String, int)
        +VinylLibrary getLibrary()
        +void update()
    }
    
    class MainController {
        -TableView~Vinyl~ vinylTableView
        -TextField userIdField
        -Button reserveButton
        -Button borrowButton
        -Button returnButton
        -Button markForRemovalButton
        -Button addVinylButton
        -MainViewModel viewModel
        +void initialize(URL, ResourceBundle)
        +void handleReserve()
        +void handleBorrow()
        +void handleReturn()
        +void handleMarkForRemoval()
        +void handleAddVinyl()
    }
    
    class UserSimulation {
        -static String[] USER_IDS
        -Random random
        -VinylLibrary library
        +UserSimulation(VinylLibrary)
        +void run()
    }
    
    Main --> VinylLibrary
    Main --> MainViewModel
    VinylLibrary ..|> Observable
    VinylLibrary --> Vinyl
    Vinyl ..|> Observable
    Vinyl --> VinylState
    AvailableState ..|> VinylState
    ReservedState ..|> VinylState
    BorrowedState ..|> VinylState
    BorrowedReservedState ..|> VinylState
    MainViewModel ..|> Observer
    MainViewModel --> VinylLibrary
    MainViewModel --> SessionManager
    MainController --> MainViewModel
    UserSimulation --> VinylLibrary
    SessionManager ..> Observable
```

## Pattern Implementations

### MVVM Pattern
- **Model**: Vinyl, VinylLibrary
- **ViewModel**: MainViewModel
- **View**: MainController + main-view.fxml

### Observer Pattern
- **Observable Interface**: Vinyl, VinylLibrary implement this
- **Observer Interface**: MainViewModel implements this
- VinylLibrary observes changes in Vinyl objects
- MainViewModel observes changes in VinylLibrary

### State Pattern
- **State Interface**: VinylState
- **Concrete States**: AvailableState, ReservedState, BorrowedState, BorrowedReservedState
- **Context**: Vinyl class
- State transitions are handled by the state objects themselves

### Session Management
- **Singleton**: SessionManager
- Holds current user ID and selected vinyl ID
- Shared between ViewModel and other components
