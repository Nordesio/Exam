// EditAirplaneFragment.java
package com.example.exam;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditAirplaneFragment extends Fragment {

    private static final String ARG_AIRPLANE_NAME = "airplane_name";
    private EditText editTextAirplane;
    private Button buttonSave;

    private String airplaneName;

    public EditAirplaneFragment() {
        // Required empty public constructor
    }

    public static EditAirplaneFragment newInstance(String airplaneName) {
        EditAirplaneFragment fragment = new EditAirplaneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_AIRPLANE_NAME, airplaneName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            airplaneName = getArguments().getString(ARG_AIRPLANE_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_airplane, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextAirplane = view.findViewById(R.id.editTextAirplane);
        buttonSave = view.findViewById(R.id.buttonSave);

        editTextAirplane.setText(airplaneName);

        buttonSave.setOnClickListener(v -> {
            String newName = editTextAirplane.getText().toString();
            if (!newName.isEmpty()) {
                // Обновление имени самолета
                ((MainActivity) getActivity()).updateAirplane(airplaneName, newName);
                Toast.makeText(getActivity(), "Самолет обновлен", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(getActivity(), "Имя самолета не может быть пустым", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
