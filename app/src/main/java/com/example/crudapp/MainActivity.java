package com.example.crudapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String[] daftar;
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
        Cursor cursor = db.rawQuery("SELECT * FROM mahasiswa;",null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for(int i=0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            daftar[i] = cursor.getString(0).toString();
        }
        listView = findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        listView.setSelected(true);

    }
}