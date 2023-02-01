package gr303.hrynchak.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    EditText txt_title;
    EditText txt_content;

    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txt_title = findViewById(R.id.txt_title);
        txt_content = findViewById(R.id.txt_content);

    }

    public void on_cancel_click(View v) {
        finish();
    }

    public void on_save_click(View v) {
        Intent i = new Intent();
        i.putExtra("my-note-title", txt_title.getText().toString());
        i.putExtra("my-note-content", txt_content.getText().toString());

        setResult(RESULT_OK, i);
        finish();
    }
}