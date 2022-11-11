package com.example.crudapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String[] id, ruang, gedung, kapasitas, getAll;
    ListView listView;
    Menu menu;
    protected Cursor cursor;
    Database database;
    public static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fabtn = findViewById(R.id.fabtn_add);
        fabtn.setOnClickListener(this);
        ma = this;
        database = new Database(this);
        RefreshList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabtn_add:
                Intent moveIntent = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(moveIntent);
                break;
        }
    }

    public void RefreshList(){
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ruang;",null);
        id = new String[cursor.getCount()];
        gedung = new String[cursor.getCount()];
        ruang = new String[cursor.getCount()];
        kapasitas = new String[cursor.getCount()];
        getAll = new String[cursor.getCount()];
        cursor.moveToFirst();
        for(int i=0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            id[i] = cursor.getString(0).toString();
            gedung[i] = cursor.getString(1).toString();
            ruang[i] = cursor.getString(2).toString();
            kapasitas[i] = cursor.getString(3).toString();
            getAll[i] = i+1 + ". " + gedung[i] + ruang[i];
        }
        listView = findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, getAll));
        listView.setSelected(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String selection = id[i];
                final CharSequence[] dialogitem = {"View Data", "Update Data", "Delete Data"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Menu");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        switch (item) {
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                                intent.putExtra("ID", selection);
                                startActivity(intent);
                                break;
                            case 1:
                                Intent intent2 = new Intent(getApplicationContext(), UpdateActivity.class);
                                intent2.putExtra("ID", selection);
                                startActivity(intent2);
                                break;
                            case 2:
                                SQLiteDatabase db = database.getWritableDatabase();
                                //delete data
                                db.execSQL("DELETE FROM ruang WHERE id = '" + selection + "'");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
    }
}