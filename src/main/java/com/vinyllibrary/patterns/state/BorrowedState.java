package com.vinyllibrary.patterns.state;

public class BorrowedState implements VinylState {
    private final String borrowedBy;

    public BorrowedState(String borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    @Override
    public void reserve(Vinyl vinyl, String userId) {
        if (vinyl.isMarkedForRemoval()) {
            throw new IllegalStateException("Cannot reserve a vinyl marked for removal");
        }
        vinyl.setState(new BorrowedReservedState(borrowedBy, userId));
        vinyl.setReservedBy(userId);
    }

    @Override
    public void borrow(Vinyl vinyl, String userId) {
        throw new IllegalStateException("Vinyl is already borrowed");
    }

    @Override
    public void returnVinyl(Vinyl vinyl) {
        vinyl.setState(new AvailableState());
        vinyl.setBorrowedBy(null);
    }

    @Override
    public String getStateName() {
        return "Borrowed";
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public boolean isReserved() {
        return false;
    }

    @Override
    public boolean isBorrowed() {
        return true;
    }

    public String getBorrowedBy() {
        return borrowedBy;
    }
}
