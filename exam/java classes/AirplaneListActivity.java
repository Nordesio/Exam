package com.example.exam;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AirplaneListActivity extends AppCompatActivity {

    private List<Airplane> airplaneList = new ArrayList<>();
    private List<Airplane> filteredList = new ArrayList<>();
    private ArrayAdapter<Airplane> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airplane_list);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        airplaneList = dbHelper.getAllAirplanes();
        filteredList.addAll(airplaneList);

        ListView listView = findViewById(R.id.listViewAirplanes);
        EditText editTextFilter = findViewById(R.id.editTextFilter);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredList);
        listView.setAdapter(adapter);

        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });
    }

    private void filterList(String query) {
        filteredList.clear();
        for (Airplane airplane : airplaneList) {
            if (airplane.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(airplane);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
