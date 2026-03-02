package com.vinyllibrary.viewmodel;

import com.vinyllibrary.model.Vinyl;
import com.vinyllibrary.model.VinylLibrary;
import com.vinyllibrary.patterns.observer.Observer;
import com.vinyllibrary.patterns.session.SessionManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainViewModel implements Observer {
    private final VinylLibrary library;
    private final ObservableList<Vinyl> vinylList;
    private final StringProperty currentUserId;
    private final StringProperty selectedVinylId;
    private final SessionManager sessionManager;

    public MainViewModel() {
        this.library = new VinylLibrary();
        this.vinylList = FXCollections.observableArrayList();
        this.currentUserId = new SimpleStringProperty("user1");
        this.selectedVinylId = new SimpleStringProperty();
        this.sessionManager = SessionManager.getInstance();
        this.sessionManager.setCurrentUserId("user1");

        library.addObserver(this);
        loadVinyls();
    }

    private void loadVinyls() {
        vinylList.clear();
        vinylList.addAll(library.getVinyls());
    }

    public ObservableList<Vinyl> getVinylList() {
        return vinylList;
    }

    public StringProperty currentUserIdProperty() {
        return currentUserId;
    }

    public StringProperty selectedVinylIdProperty() {
        return selectedVinylId;
    }

    public void setCurrentUserId(String userId) {
        currentUserId.set(userId);
        sessionManager.setCurrentUserId(userId);
    }

    public void setSelectedVinylId(String vinylId) {
        selectedVinylId.set(vinylId);
        sessionManager.setSelectedVinylId(vinylId);
    }

    public Vinyl getSelectedVinyl() {
        String id = selectedVinylId.get();
        if (id == null) {
            return null;
        }
        return library.getVinylById(id);
    }

    public void reserveVinyl() {
        Vinyl vinyl = getSelectedVinyl();
        if (vinyl != null) {
            vinyl.reserve(currentUserId.get());
        }
    }

    public void borrowVinyl() {
        Vinyl vinyl = getSelectedVinyl();
        if (vinyl != null) {
            vinyl.borrow(currentUserId.get());
        }
    }

    public void returnVinyl() {
        Vinyl vinyl = getSelectedVinyl();
        if (vinyl != null) {
            vinyl.returnVinyl();
        }
    }

    public void markForRemoval() {
        Vinyl vinyl = getSelectedVinyl();
        if (vinyl != null) {
            vinyl.markForRemoval();
        }
    }

    public void addVinyl(String title, String artist, int releaseYear) {
        String id = "V" + String.format("%03d", vinylList.size() + 1);
        Vinyl vinyl = new Vinyl(id, title, artist, releaseYear);
        library.addVinyl(vinyl);
    }

    public VinylLibrary getLibrary() {
        return library;
    }

    @Override
    public void update() {
        loadVinyls();
    }
}
