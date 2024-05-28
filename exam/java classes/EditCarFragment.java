package com.example.exam2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class EditCarFragment extends Fragment {

    private Car car;
    private EditText carIdInput;
    private EditText carStatusInput;
    private EditText clientNameInput;
    private Button saveButton;

    public EditCarFragment() {
        // Пустой конструктор обязателен
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_car, container, false);

        carIdInput = view.findViewById(R.id.editCarIdInput);
        carStatusInput = view.findViewById(R.id.editCarStatusInput);
        clientNameInput = view.findViewById(R.id.editClientNameInput);
        saveButton = view.findViewById(R.id.saveButton);

        // Заполняем поля данными выбранного автомобиля
        if (car != null) {
            carIdInput.setText(car.getId());
            carStatusInput.setText(car.getStatus());
            clientNameInput.setText(car.getClientName());
        }

        saveButton.setOnClickListener(v -> saveChanges());

        return view;
    }

    private void saveChanges() {
        // Получаем новые значения из полей ввода
        String newCarId = carIdInput.getText().toString();
        String newCarStatus = carStatusInput.getText().toString();
        String newClientName = clientNameInput.getText().toString();

        // Обновляем данные выбранного автомобиля
        if (car != null) {
            car.setId(newCarId);
            car.setStatus(newCarStatus);
            car.setClientName(newClientName);

            // Обновляем данные в базе данных
            CarDatabaseHelper dbHelper = new CarDatabaseHelper(requireContext());
            dbHelper.updateCar(car);

            // Возвращаемся назад в список автомобилей
            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
