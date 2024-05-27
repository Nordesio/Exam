package com.example.exam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private EditText airplaneInput;
    private Button addButton;
    private Button exportButton;
    private Button importButton;
    private ListView airplaneListView;
    private ArrayList<Airplane> airplaneList;
    private ArrayAdapter<Airplane> adapter;
    private Gson gson;
    private static final String FILE_NAME = "airplanes.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson = new Gson();

        airplaneInput = findViewById(R.id.editTextAirplane);
        addButton = findViewById(R.id.buttonAdd);
        airplaneListView = findViewById(R.id.listViewAirplanes);
        exportButton = findViewById(R.id.buttonExport);
        importButton = findViewById(R.id.buttonImport);
        airplaneList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, airplaneList);
        airplaneListView.setAdapter(adapter);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAirplane();
            }
        });

        airplaneListView.setOnItemClickListener((parent, view, position, id) -> {
            Airplane selectedAirplane = airplaneList.get(position);
            EditAirplaneFragment fragment = EditAirplaneFragment.newInstance(selectedAirplane.getName());
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportData();
            }
        });

        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importData();
            }
        });
        Button buttonViewList = findViewById(R.id.buttonViewList);
        buttonViewList.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AirplaneListActivity.class);
            startActivity(intent);
        });
    }

    private void addAirplane() {
        String inputText = airplaneInput.getText().toString().trim();
        if (!inputText.isEmpty()) {
            String[] airplaneInfo = inputText.split(",");
            if (airplaneInfo.length == 2) {
                String name = airplaneInfo[0].trim();
                String model = airplaneInfo[1].trim();
                Airplane airplane = new Airplane(name, model);
                airplaneList.add(airplane);
                adapter.notifyDataSetChanged();
                airplaneInput.setText("");
            } else {
                Toast.makeText(this, "Введите данные в формате: Название, Модель", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Введите данные о самолете", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateAirplane(String oldName, String newName) {
        for (Airplane airplane : airplaneList) {
            if (airplane.getName().equals(oldName)) {
                airplane.setName(newName);
                adapter.notifyDataSetChanged();
                DatabaseHelper dbHelper = new DatabaseHelper(this);
                dbHelper.updateAirplane(oldName, newName);
                break;
            }
        }
    }
    private void exportData() {
        // Удаляем существующий файл JSON, если он существует
        File file = new File(getFilesDir(), FILE_NAME);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (!deleted) {
                Log.e("ExportData", "Не удалось удалить файл: " + FILE_NAME);
                Toast.makeText(this, "Ошибка удаления старых данных", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        // Сначала сохраняем данные в JSON
        String jsonString = gson.toJson(airplaneList);
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(jsonString.getBytes());
            Toast.makeText(this, "Данные успешно экспортированы", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ExportData", "Ошибка экспорта данных: " + e.getMessage());
            Toast.makeText(this, "Ошибка экспорта данных", Toast.LENGTH_SHORT).show();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("ExportData", "Ошибка закрытия файла: " + e.getMessage());
                }
            }
        }
        // Удаляем все данные из базы данных перед добавлением новых
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.clearDatabase();
        // Затем сохраняем данные в базу данных
        for (Airplane data : airplaneList) {

            dbHelper.addAirplane(String.valueOf(data));
        }
    }

    private void importData() {
        // Загружаем данные из JSON
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            String json = stringBuilder.toString();
            Type listType = new TypeToken<List<Airplane>>(){}.getType();
            List<Airplane> importedAirplanes = gson.fromJson(json, listType);
            airplaneList.addAll(importedAirplanes); // Добавляем импортированные самолеты в список
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Данные успешно импортированы", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ImportData", "Ошибка импорта данных: " + e.getMessage());
            Toast.makeText(this, "Ошибка импорта данных", Toast.LENGTH_SHORT).show();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("ImportData", "Ошибка закрытия файла: " + e.getMessage());
                }
            }
        }


    }


}
