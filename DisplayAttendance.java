package com.example.firaqr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class DisplayAttendance extends AppCompatActivity {
        private ListView listViewNames;
        private SearchView searchViewNames;
        private ScanDatabase databaseHelper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_display_attendance);

            listViewNames = findViewById(R.id.list_view_names);
            searchViewNames = findViewById(R.id.search_view_names);
            databaseHelper = new ScanDatabase(this);

            List<NameModel> names = databaseHelper.getAllNames();
            List<String> nameStrings = new ArrayList<>();

            for (NameModel name : names) {
                nameStrings.add(name.getId() + ": " + name.getName() + " [" + name.getTime() + "]");
            }



            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, nameStrings);
            listViewNames.setAdapter(adapter);

            searchViewNames.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchNames(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    searchNames(newText);
                    return true;
                }
            });
        }

        private void searchNames(String query) {
            List<NameModel> names = databaseHelper.searchNames(query);
            List<String> nameStrings = new ArrayList<>();

            for (NameModel name : names) {
                nameStrings.add(name.getId() + ": " + name.getName() + " [" + name.getTime() + "]");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, nameStrings);
            listViewNames.setAdapter(adapter);
        }
    }