package com.example.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends Activity {
	EditText etEditTodo;
	int editPosition;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        
        etEditTodo = (EditText) findViewById(R.id.editTodo);
        String itemText = getIntent().getStringExtra(getString(R.string.intentItemText));
        editPosition = getIntent().getIntExtra(getString(R.string.intentEditPosition), 0);
        
        etEditTodo.setText(itemText);
    }
	
	public void onSaveClick(View v) {
		EditText editTodo = (EditText) findViewById(R.id.editTodo);
    	//don't add blanks
    	if (editTodo.getText().toString().trim().isEmpty() == false) {
    		Intent data = new Intent();
    		data.putExtra(getString(R.string.intentEditedTodo), editTodo.getText().toString());
    		data.putExtra(getString(R.string.intentEditPosition), editPosition);
    		editTodo.setText("");
    		setResult(RESULT_OK, data);
    		finish();
    	}
	}
}
