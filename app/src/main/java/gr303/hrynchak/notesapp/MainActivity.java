package gr303.hrynchak.notesapp;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<Note> adp;
    int current = -1;

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
                lst.requestFocusFromTouch();
                lst.setSelection(position);
                current = position;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            int pos = data.getExtras().getInt("my-note-pos");
            String title = data.getStringExtra("my-note-title");
            String content = data.getStringExtra("my-note-content");

            if (pos < adp.getCount()) {
                adp.getItem(pos).title = title;
                adp.getItem(pos).content = content;
            } else {
                Note n = new Note();
                n.title = title;
                n.content = content;

                adp.add(n);
            }
            adp.notifyDataSetChanged();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void on_new_click(View v) {
        Intent i = new Intent(this, Main2Activity.class);
        i.putExtra("my-note-pos", adp.getCount());
        i.putExtra("my-note-title", "New title");
        i.putExtra("my-note-content", "New content");
        startActivityForResult(i, 54321);
    }

    public void on_edit_click(View v) {
        if(current < 0 && adp.getCount() >= 1) Toast.makeText(getApplicationContext(),
                "Select a note to edit", Toast.LENGTH_SHORT).show();
        if (current >= 0 && current < adp.getCount() ) {
            Intent i = new Intent(this, Main2Activity.class);
            i.putExtra("my-note-pos", current);
            i.putExtra("my-note-title", adp.getItem(current).title);
            i.putExtra("my-note-content", adp.getItem(current).content);
            startActivityForResult(i, 12345);
            current = -1;
        }
        if(adp.getCount() < 1) Toast.makeText(getApplicationContext(),
                "Nothing to edit", Toast.LENGTH_SHORT).show();
    }

    public void on_delete_click(View v) {
        if(current < 0 && adp.getCount() >= 1) Toast.makeText(getApplicationContext(),
                "Select a note to delete", Toast.LENGTH_SHORT).show();
        if (adp.getCount() > 0) {
            if (current >= 0) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                AlertDialog dialog = builder.create();
                dialog.setTitle("Warning");
                dialog.setMessage("Are you sure you want to delete this note?");

                dialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adp.remove(adp.getItem(current));
                        adp.notifyDataSetChanged();
                        current--;
                    }
                });
                dialog.setButton(Dialog.BUTTON_NEGATIVE, "No, return", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        }
        else Toast.makeText(getApplicationContext(),
                "Nothing to delete", Toast.LENGTH_SHORT).show();
    }
}