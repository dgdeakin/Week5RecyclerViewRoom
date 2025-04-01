package com.example.week5recyclerviewroom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;


public class NameListFragment extends Fragment {

    private RecyclerView recyclerView;
    private NameAdapter adapter;
    private List<Name> nameList;
    private AppDatabase db;
    private EditText nameInput;
    private Button addButton, updateButton, deleteButton;
    private Name selectedName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_name_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        nameInput = view.findViewById(R.id.nameInput);
        addButton = view.findViewById(R.id.addButton);
        updateButton = view.findViewById(R.id.updateButton);
        deleteButton = view.findViewById(R.id.deleteButton);

        nameList = new ArrayList<>();
        adapter = new NameAdapter(nameList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        db = Room.databaseBuilder(getContext(), AppDatabase.class, "name-database").allowMainThreadQueries().build();
        loadNames();

        addButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            if (!name.isEmpty()) {
                if (selectedName == null) { // Add new name
                    db.nameDao().insert(new Name(name));
                    nameInput.setText("");
                    loadNames();
                } else { // Cancel editing
                    selectedName = null;
                    nameInput.setText("");
                    updateButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                    addButton.setText("Add");
                }
            }
        });

        updateButton.setOnClickListener(v -> {
            if (selectedName != null) {
                String newName = nameInput.getText().toString();
                if (!newName.isEmpty()) {
                    selectedName.setNameText(newName);
                    db.nameDao().update(selectedName);
                    nameInput.setText("");
                    selectedName = null;
                    updateButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                    addButton.setText("Add");
                    loadNames();
                }
            }
        });

        deleteButton.setOnClickListener(v -> {
            if (selectedName != null) {
                db.nameDao().delete(selectedName);
                nameInput.setText("");
                selectedName = null;
                updateButton.setEnabled(false);
                deleteButton.setEnabled(false);
                addButton.setText("Add");
                loadNames();
            }
        });

        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);

        return view;
    }

    private void loadNames() {
        nameList = db.nameDao().getAllNames();
        adapter.updateList(nameList);
    }

    public void onItemClicked(Name name) {
        selectedName = name;
        nameInput.setText(name.getNameText());
        updateButton.setEnabled(true);
        deleteButton.setEnabled(true);
        addButton.setText("Cancel");
    }
}