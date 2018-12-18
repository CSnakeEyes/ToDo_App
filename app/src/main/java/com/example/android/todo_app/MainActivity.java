package com.example.android.todo_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView itemsList_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        itemsList_lv = findViewById(R.id.itemsList_lv);
        itemsList_lv.setAdapter(itemsAdapter);

        // Mock Data
//        items.add("First Item");
//        items.add("Second Item");

        setupListViewListener();
    }


    public void onAddItem(View v) {
        EditText addTodo_et = findViewById(R.id.addTodo_et);
        String itemStr = addTodo_et.getText().toString();

        items.add(itemStr);
        itemsAdapter.notifyDataSetChanged();
        addTodo_et.setText("");
        writeItems();
        Toast.makeText(getApplicationContext(), "Item added successfully", Toast.LENGTH_SHORT).show();
    }

    private void setupListViewListener() {
        itemsList_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                items.remove(i);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                Toast.makeText(getApplicationContext(), "Item removed successfully", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private File getDataFile() {
        return new File(getFilesDir(), "todo.txt");
    }

    private void readItems() {
        try{
            items = new ArrayList<>(FileUtils.readLines(getDataFile(),Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading file", e);
            items = new ArrayList<>();
        }
    }

    private void writeItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading file", e);
        }
    }
}
