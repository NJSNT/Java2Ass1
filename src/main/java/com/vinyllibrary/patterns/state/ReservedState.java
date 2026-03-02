package com.vinyllibrary.patterns.state;

public class ReservedState implements VinylState {
    private final String reservedBy;

    public ReservedState(String reservedBy) {
        this.reservedBy = reservedBy;
    }

    @Override
    public void reserve(Vinyl vinyl, String userId) {
        throw new IllegalStateException("Vinyl is already reserved");
    }

    @Override
    public void borrow(Vinyl vinyl, String userId) {
        if (userId.equals(reservedBy)) {
            vinyl.setState(new BorrowedState(userId));
            vinyl.setBorrowedBy(userId);
            vinyl.setReservedBy(null);
        } else {
            throw new IllegalStateException("Vinyl is reserved by another user");
        }
    }

    @Override
    public void returnVinyl(Vinyl vinyl) {
        throw new IllegalStateException("Cannot return a vinyl that is not borrowed");
    }

    @Override
    public String getStateName() {
        return "Reserved";
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public boolean isReserved() {
        return true;
    }

    @Override
    public boolean isBorrowed() {
        return false;
    }

    public String getReservedBy() {
        return reservedBy;
    }
}
