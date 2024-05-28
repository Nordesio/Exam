package com.example.exam2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CarListFragment extends Fragment {
    private static final int EXPORT_REQUEST_CODE = 1;
    private static final int IMPORT_REQUEST_CODE = 2;
    private CarRentalManager carRentalManager;
    private CarAdapter carAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_list, container, false);
        carRentalManager = MySingleton.getCarRentalManager(getContext());
        EditText carIdInput = view.findViewById(R.id.carIdInput);
        EditText carStatusInput = view.findViewById(R.id.carStatusInput);
        EditText clientNameInput = view.findViewById(R.id.clientNameInput);
        Button addButton = view.findViewById(R.id.addButton);
        Button rentButton = view.findViewById(R.id.rentButton);
        Button scrapButton = view.findViewById(R.id.scrapButton);
        Button reportButton = view.findViewById(R.id.reportButton);
        Button exportButton = view.findViewById(R.id.exportButton);
        Button importButton = view.findViewById(R.id.importButton);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        carAdapter = new CarAdapter(carRentalManager.getCars());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(carAdapter);

        addButton.setOnClickListener(v -> {
            String carId = carIdInput.getText().toString();
            String carStatus = carStatusInput.getText().toString();
            String clientName = clientNameInput.getText().toString();
            carRentalManager.addCar(new Car(carId, carStatus, clientName));
            carAdapter.updateCars(carRentalManager.getCars());
        });

        rentButton.setOnClickListener(v -> {
            String carId = carIdInput.getText().toString();
            String clientName = clientNameInput.getText().toString();
            carRentalManager.rentCar(carId, clientName);
            carAdapter.updateCars(carRentalManager.getCars());
        });

        scrapButton.setOnClickListener(v -> {
            String carId = carIdInput.getText().toString();
            carRentalManager.scrapCar(carId);
            carAdapter.updateCars(carRentalManager.getCars());
        });

        reportButton.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ReportActivity.class));
        });

        exportButton.setOnClickListener(v -> {
            String json = carRentalManager.exportToJson();
            exportDataToJson();

        });

        importButton.setOnClickListener(v -> {
            importDataFromJson();
        });
        carAdapter.setOnItemClickListener(car -> openEditCarFragment(car));
        return view;
    }
    private void exportDataToJson() {
        String json = carRentalManager.exportToJson();
            saveJsonToFile(json);
    }
    private void openEditCarFragment(Car car) {
        // Создайте новый экземпляр фрагмента для редактирования и передайте в него выбранный автомобиль
        EditCarFragment editCarFragment = new EditCarFragment();
        editCarFragment.setCar(car);

        // Замените текущий фрагмент на фрагмент для редактирования
        FragmentTransaction transaction = requireFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, editCarFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void saveJsonToFile(String json) {
        try {
            File file = new File(getContext().getExternalFilesDir(null), "car_data.json");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(json.getBytes());
            fos.close();
            // Успешно экспортировано
        } catch (IOException e) {
            e.printStackTrace();
            // Обработка ошибки: не удалось записать в файл
        }
    }
    private void importDataFromJson() {
        try {
            File file = new File(getContext().getExternalFilesDir(null), "car_data.json");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            fis.close();
            String json = stringBuilder.toString();
            carRentalManager.importFromJson(json);
            carAdapter.updateCars(carRentalManager.getCars());
            // Успешно импортировано
        } catch (IOException e) {
            e.printStackTrace();
            // Обработка ошибки: не удалось прочитать файл
        }
    }
}
