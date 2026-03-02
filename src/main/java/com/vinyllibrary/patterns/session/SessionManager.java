package com.vinyllibrary.patterns.session;

public class SessionManager {
    private static SessionManager instance;
    private String currentUserId;
    private String selectedVinylId;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getSelectedVinylId() {
        return selectedVinylId;
    }

    public void setSelectedVinylId(String selectedVinylId) {
        this.selectedVinylId = selectedVinylId;
    }
}
