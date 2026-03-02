package com.vinyllibrary.model;

import com.vinyllibrary.patterns.observer.Observable;
import com.vinyllibrary.patterns.observer.Observer;
import com.vinyllibrary.patterns.state.AvailableState;
import com.vinyllibrary.patterns.state.VinylState;
import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;

public class Vinyl implements Observable {
    private final StringProperty id;
    private final StringProperty title;
    private final StringProperty artist;
    private final IntegerProperty releaseYear;
    private final StringProperty borrowedBy;
    private final StringProperty reservedBy;
    private final BooleanProperty markedForRemoval;
    private VinylState state;
    private final List<Observer> observers;

    public Vinyl(String id, String title, String artist, int releaseYear) {
        this.id = new SimpleStringProperty(id);
        this.title = new SimpleStringProperty(title);
        this.artist = new SimpleStringProperty(artist);
        this.releaseYear = new SimpleIntegerProperty(releaseYear);
        this.borrowedBy = new SimpleStringProperty(null);
        this.reservedBy = new SimpleStringProperty(null);
        this.markedForRemoval = new SimpleBooleanProperty(false);
        this.state = new AvailableState();
        this.observers = new ArrayList<>();
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getArtist() {
        return artist.get();
    }

    public StringProperty artistProperty() {
        return artist;
    }

    public int getReleaseYear() {
        return releaseYear.get();
    }

    public IntegerProperty releaseYearProperty() {
        return releaseYear;
    }

    public String getBorrowedBy() {
        return borrowedBy.get();
    }

    public StringProperty borrowedByProperty() {
        return borrowedBy;
    }

    public void setBorrowedBy(String borrowedBy) {
        this.borrowedBy.set(borrowedBy);
    }

    public String getReservedBy() {
        return reservedBy.get();
    }

    public StringProperty reservedByProperty() {
        return reservedBy;
    }

    public void setReservedBy(String reservedBy) {
        this.reservedBy.set(reservedBy);
    }

    public boolean isMarkedForRemoval() {
        return markedForRemoval.get();
    }

    public BooleanProperty markedForRemovalProperty() {
        return markedForRemoval;
    }

    public void setMarkedForRemoval(boolean markedForRemoval) {
        this.markedForRemoval.set(markedForRemoval);
    }

    public VinylState getState() {
        return state;
    }

    public void setState(VinylState state) {
        this.state = state;
    }

    public String getStateName() {
        return state.getStateName();
    }

    public void reserve(String userId) {
        state.reserve(this, userId);
        notifyObservers();
    }

    public void borrow(String userId) {
        state.borrow(this, userId);
        notifyObservers();
    }

    public void returnVinyl() {
        state.returnVinyl(this);
        notifyObservers();
    }

    public boolean canBeRemoved() {
        return state.isAvailable() && !isMarkedForRemoval();
    }

    public void markForRemoval() {
        if (state.isAvailable() && getReservedBy() == null) {
            markedForRemoval.set(true);
        } else if (!state.isAvailable() || getReservedBy() != null) {
            markedForRemoval.set(true);
        }
        notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
