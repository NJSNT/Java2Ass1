package com.vinyllibrary.util;

import com.vinyllibrary.model.Vinyl;
import com.vinyllibrary.patterns.session.SessionManager;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class UserSimulation implements Runnable {
    private static final String[] USER_IDS = {"user2", "user3", "user4", "user5"};
    private static final Random random = new Random();
    private final com.vinyllibrary.model.VinylLibrary library;

    public UserSimulation(com.vinyllibrary.model.VinylLibrary library) {
        this.library = library;
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(5) + 2);

                Vinyl[] vinyls = library.getVinyls().toArray(new Vinyl[0]);
                if (vinyls.length == 0) {
                    continue;
                }

                Vinyl randomVinyl = vinyls[random.nextInt(vinyls.length)];
                String randomUser = USER_IDS[random.nextInt(USER_IDS.length)];

                int action = random.nextInt(4);

                switch (action) {
                    case 0:
                        if (randomVinyl.isMarkedForRemoval()) {
                            System.out.println(randomUser + ": Cannot reserve vinyl " + randomVinyl.getId() + " - marked for removal");
                        } else {
                            try {
                                randomVinyl.reserve(randomUser);
                                System.out.println(randomUser + ": Reserved vinyl " + randomVinyl.getId());
                            } catch (IllegalStateException e) {
                                System.out.println(randomUser + ": Cannot reserve vinyl " + randomVinyl.getId() + " - " + e.getMessage());
                            }
                        }
                        break;
                    case 1:
                        try {
                            randomVinyl.borrow(randomUser);
                            System.out.println(randomUser + ": Borrowed vinyl " + randomVinyl.getId());
                        } catch (IllegalStateException e) {
                            System.out.println(randomUser + ": Cannot borrow vinyl " + randomVinyl.getId() + " - " + e.getMessage());
                        }
                        break;
                    case 2:
                        try {
                            randomVinyl.returnVinyl();
                            System.out.println(randomUser + ": Returned vinyl " + randomVinyl.getId());
                        } catch (IllegalStateException e) {
                            System.out.println(randomUser + ": Cannot return vinyl " + randomVinyl.getId() + " - " + e.getMessage());
                        }
                        break;
                    case 3:
                        if (randomVinyl.getState().isAvailable() && randomVinyl.getReservedBy() == null) {
                            try {
                                library.removeVinyl(randomVinyl.getId());
                                System.out.println(randomUser + ": Removed vinyl " + randomVinyl.getId());
                            } catch (Exception e) {
                                System.out.println(randomUser + ": Cannot remove vinyl " + randomVinyl.getId() + " - " + e.getMessage());
                            }
                        } else {
                            try {
                                randomVinyl.markForRemoval();
                                System.out.println(randomUser + ": Marked vinyl " + randomVinyl.getId() + " for removal");
                            } catch (Exception e) {
                                System.out.println(randomUser + ": Cannot mark vinyl " + randomVinyl.getId() + " for removal - " + e.getMessage());
                            }
                        }
                        break;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
