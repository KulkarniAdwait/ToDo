package com.example.todo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.todo.EditActivity;

public class MainActivity extends Activity {

	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	
	final int REQUEST_CODE = 20;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);
        
        setupItemClickListener();
        setupItemLongClickListener();
        
    }
    
    public void onAddClick(View v) {
    	EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    	//don't add blanks
    	if (etNewItem.getText().toString().trim().isEmpty() == false) {
    		itemsAdapter.add(etNewItem.getText().toString().trim());
    		saveItems();
    		etNewItem.setText("");
    	}
    }
    
    private void setupItemLongClickListener() {
    	lvItems.setOnItemLongClickListener( new OnItemLongClickListener() {
    		@Override
    		public boolean onItemLongClick(AdapterView<?> parent
    				, View view, int position, long rowId) {
    			items.remove(position);
    			itemsAdapter.notifyDataSetChanged();
    			saveItems();
    			return true;
    		}
    	});
    }
    
    private void setupItemClickListener() {
    	lvItems.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent
					, View view, int position, long rowId) {
				
				Intent editActivity = new Intent(MainActivity.this, EditActivity.class);
				editActivity.putExtra(getString(R.string.intentItemText), items.get(position).toString());
				editActivity.putExtra(getString(R.string.intentEditPosition), position);
				startActivityForResult(editActivity, REQUEST_CODE);
			}
    	});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if( requestCode == REQUEST_CODE && resultCode == RESULT_OK ) {
    		String editedTodo = data.getExtras().getString(getString(R.string.intentEditedTodo));
    		if (editedTodo.trim().isEmpty() == false) {
    			int position = data.getExtras().getInt(getString(R.string.intentEditPosition));
        		//remove existing entry
    			items.remove(position);
    			itemsAdapter.notifyDataSetChanged();
    			//insert edited entry at position
    			itemsAdapter.insert(editedTodo.trim(), position);
        		
        		saveItems();
        	}
    	}
    }
    
    private void getItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		items = new ArrayList<String>(FileUtils.readLines(todoFile));
    	} catch(IOException e) {
    		items = new ArrayList<String>();
    	}
    }
    
    private void saveItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		FileUtils.writeLines(todoFile, items);
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    	
    }
}
