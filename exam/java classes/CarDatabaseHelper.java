package com.example.exam2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class CarDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "car_rental.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CARS = "cars";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_CLIENT_NAME = "client_name";

    private static final String CREATE_TABLE_CARS = "CREATE TABLE " + TABLE_CARS + "("
            + COLUMN_ID + " TEXT PRIMARY KEY,"
            + COLUMN_STATUS + " TEXT,"
            + COLUMN_CLIENT_NAME + " TEXT"
            + ")";

    public CarDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CARS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARS);
        onCreate(db);
    }

    public void addCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, car.getId());
        values.put(COLUMN_STATUS, car.getStatus());
        values.put(COLUMN_CLIENT_NAME, car.getClientName());
        db.insert(TABLE_CARS, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public List<Car> getAllCars() {
        List<Car> carList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CARS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Car car = new Car();
                car.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                car.setStatus(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));
                car.setClientName(cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_NAME)));
                carList.add(car);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return carList;
    }

    public void deleteAllCars() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CARS, null, null);
        db.close();
    }

    public Car getCarById(String carId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CARS, new String[] {
                        COLUMN_ID, COLUMN_STATUS, COLUMN_CLIENT_NAME },
                COLUMN_ID + "=?", new String[] { carId },
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Car car = new Car(cursor.getString(0), cursor.getString(1), cursor.getString(2));

        cursor.close();
        db.close();

        return car;
    }

    public void updateCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, car.getStatus());
        values.put(COLUMN_CLIENT_NAME, car.getClientName());

        db.update(TABLE_CARS, values, COLUMN_ID + " = ?",
                new String[] { car.getId() });
        db.close();
    }

    public void deleteCar(String carId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CARS, COLUMN_ID + " = ?",
                new String[] { carId });
        db.close();
    }

    public int getCarsCountByStatus(String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT COUNT(*) FROM " + TABLE_CARS + " WHERE " + COLUMN_STATUS + " = ?";
        Cursor cursor = db.rawQuery(countQuery, new String[]{status});
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return count;
    }

}
