package com.example.crudapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    protected Cursor cursor;
    Database database;
    TextView gedung, ruang, kapasitas;
    Button btn_back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //show data from database
        database = new Database(this);
        gedung = findViewById(R.id.tv_gedung);
        ruang = findViewById(R.id.tv_ruang);
        kapasitas = findViewById(R.id.tv_kapasitas);
        SQLiteDatabase db = database.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM ruang WHERE id = '" + getIntent().getStringExtra("ID") + "'",null);

        // get data from database
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            gedung.setText(cursor.getString(1).toString());
            ruang.setText(cursor.getString(2).toString());
            kapasitas.setText(cursor.getString(3).toString());
        }

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(view -> finish());
    }
}