package com.vinyllibrary.model;

import com.vinyllibrary.patterns.observer.Observable;
import com.vinyllibrary.patterns.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class VinylLibrary implements Observable {
    private final List<Vinyl> vinyls;
    private final List<Observer> observers;

    public VinylLibrary() {
        this.vinyls = new ArrayList<>();
        this.observers = new ArrayList<>();
        initializeSampleData();
    }

    private void initializeSampleData() {
        addVinyl(new Vinyl("V001", "Dark Side of the Moon", "Pink Floyd", 1973));
        addVinyl(new Vinyl("V002", "Abbey Road", "The Beatles", 1969));
        addVinyl(new Vinyl("V003", "Thriller", "Michael Jackson", 1982));
        addVinyl(new Vinyl("V004", "Back in Black", "AC/DC", 1980));
        addVinyl(new Vinyl("V005", "Rumours", "Fleetwood Mac", 1977));
        addVinyl(new Vinyl("V006", "Led Zeppelin IV", "Led Zeppelin", 1971));
        addVinyl(new Vinyl("V007", "The Wall", "Pink Floyd", 1979));
        addVinyl(new Vinyl("V008", "Hotel California", "Eagles", 1976));
    }

    public List<Vinyl> getVinyls() {
        return new ArrayList<>(vinyls);
    }

    public Vinyl getVinylById(String id) {
        return vinyls.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addVinyl(Vinyl vinyl) {
        vinyls.add(vinyl);
        vinyl.addObserver(this);
        notifyObservers();
    }

    public void removeVinyl(String id) {
        Vinyl vinyl = getVinylById(id);
        if (vinyl != null && vinyl.canBeRemoved()) {
            vinyl.removeObserver(this);
            vinyls.remove(vinyl);
            notifyObservers();
        }
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
