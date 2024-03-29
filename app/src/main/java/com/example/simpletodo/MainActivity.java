package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List <String> items;

    // Member variables.
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;

    itemsAdapter ItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // References to the ID's which acts as handlers.
        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        loadItems();

        itemsAdapter.OnLongClickListener LongClickListener = new itemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                // Delete the item from the model
                items.remove(position);
                // Notify the adapter
                ItemsAdapter.notifyItemRemoved(position);
                // Add a toast to let the users know the item has been removed.
                Toast.makeText(getApplicationContext(),"Item was removed",Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };
        // Use the adapter.
        ItemsAdapter = new itemsAdapter(items,LongClickListener);
        // Set it to the recycler view.
        rvItems.setAdapter(ItemsAdapter);
        // Set the layout manager on the recycler view.
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the item from the editText field convert it to string.
                String todoItem = etItem.getText().toString();
                // Add item to the model.
                items.add(todoItem);
                // Notify adapter that an item is inserted.
                ItemsAdapter.notifyItemInserted(items.size()-1);
                // set the editText to empty string again.
                etItem.setText("");
                // Add a toast to let the users know the item has been added.
                Toast.makeText(getApplicationContext(),"Item was added",Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }
    private File getDataFile(){
        return new File(getFilesDir(),"data.txt");
    }

    // This function will load items by reading every line of the data file.
    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity","Error reading items",e);
            items = new ArrayList<>();
        }
    }

    // This function saves items by writing them into the data file.
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("MainActivity","Error writing items",e);
        }
    }
}
