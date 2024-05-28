package com.example.exam2;
import android.content.Context;
public class MySingleton {
    private static CarRentalManager carRentalManager;
    public static CarRentalManager getCarRentalManager(Context context) {
        if (carRentalManager == null) {
            carRentalManager = new CarRentalManager(context);
        }
        return carRentalManager;
    }

    public static CarRentalManager getCarRentalManager() {
        return carRentalManager;
    }
}
