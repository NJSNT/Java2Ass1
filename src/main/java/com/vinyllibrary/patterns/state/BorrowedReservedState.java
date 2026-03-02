package com.vinyllibrary.patterns.state;

public class BorrowedReservedState implements VinylState {
    private final String borrowedBy;
    private final String reservedBy;

    public BorrowedReservedState(String borrowedBy, String reservedBy) {
        this.borrowedBy = borrowedBy;
        this.reservedBy = reservedBy;
    }

    @Override
    public void reserve(Vinyl vinyl, String userId) {
        throw new IllegalStateException("Vinyl is already reserved");
    }

    @Override
    public void borrow(Vinyl vinyl, String userId) {
        throw new IllegalStateException("Vinyl is already borrowed");
    }

    @Override
    public void returnVinyl(Vinyl vinyl) {
        vinyl.setState(new ReservedState(reservedBy));
        vinyl.setBorrowedBy(null);
    }

    @Override
    public String getStateName() {
        return "Borrowed & Reserved";
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
        return true;
    }

    public String getBorrowedBy() {
        return borrowedBy;
    }

    public String getReservedBy() {
        return reservedBy;
    }
}
