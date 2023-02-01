package gr303.hrynchak.notesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<Note> adp;
    int sel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adp = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1);

        ListView lst = findViewById(R.id.lst_notes);
        lst.setAdapter(adp);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sel = position;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            String title = data.getStringExtra("my-note-title");
            String content = data.getStringExtra("my-note-content");

            Note n = new Note();
            n.title = title;
            n.content = content;

            adp.add(n);
            adp.notifyDataSetChanged();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void on_new_click(View v) {
        Intent i = new Intent(this, Main2Activity.class);
        startActivityForResult(i, 12345);
    }

    public void on_edit_click(View v) {

    }

    public void on_delete_click(View v) {

    }
}