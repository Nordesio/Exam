package com.example.exam2;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class CarRentalManager {
    private CarDatabaseHelper dbHelper;
    private Gson gson;

    public CarRentalManager(Context context) {
        dbHelper = new CarDatabaseHelper(context);
        gson = new Gson();
    }

    public void addCar(Car car) {
        dbHelper.addCar(car);
    }

    public void rentCar(String carId, String clientName) {
        Car car = dbHelper.getCarById(carId);
        if (car != null) {
            car.setStatus("rented");
            car.setClientName(clientName);
            dbHelper.updateCar(car);
        }
    }

    public void scrapCar(String carId) {
        dbHelper.deleteCar(carId);
    }

    public List<Car> getCars() {
        return dbHelper.getAllCars();
    }

    public String exportToJson() {
        List<Car> carList = dbHelper.getAllCars();
        return gson.toJson(carList);
    }

    public void importFromJson(String json) {
        Type listType = new TypeToken<List<Car>>() {}.getType();
        List<Car> importedCars = gson.fromJson(json, listType);
        if (importedCars != null) {
            dbHelper.deleteAllCars();
            for (Car car : importedCars) {
                dbHelper.addCar(car);
            }
        }
    }

    public int getCountByStatus(String status) {
        return dbHelper.getCarsCountByStatus(status);
    }
}
