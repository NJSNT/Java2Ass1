package com.vinyllibrary.patterns.state;

public interface VinylState {
    void reserve(Vinyl vinyl, String userId);
    void borrow(Vinyl vinyl, String userId);
    void returnVinyl(Vinyl vinyl);
    String getStateName();
    boolean isAvailable();
    boolean isReserved();
    boolean isBorrowed();
}
