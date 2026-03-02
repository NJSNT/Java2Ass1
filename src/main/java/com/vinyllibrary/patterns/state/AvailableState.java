package com.vinyllibrary.patterns.state;

public class AvailableState implements VinylState {
    @Override
    public void reserve(Vinyl vinyl, String userId) {
        if (vinyl.isMarkedForRemoval()) {
            throw new IllegalStateException("Cannot reserve a vinyl marked for removal");
        }
        vinyl.setState(new ReservedState(userId));
        vinyl.setReservedBy(userId);
    }

    @Override
    public void borrow(Vinyl vinyl, String userId) {
        vinyl.setState(new BorrowedState(userId));
        vinyl.setBorrowedBy(userId);
    }

    @Override
    public void returnVinyl(Vinyl vinyl) {
        throw new IllegalStateException("Cannot return a vinyl that is not borrowed");
    }

    @Override
    public String getStateName() {
        return "Available";
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean isReserved() {
        return false;
    }

    @Override
    public boolean isBorrowed() {
        return false;
    }
}
