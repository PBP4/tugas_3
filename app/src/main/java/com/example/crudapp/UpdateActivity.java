package com.example.crudapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {
    protected Cursor cursor;
    Database database;
    EditText gedung,ruang, kapasitas;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        database = new Database(this);
        gedung = findViewById(R.id.et_gedung);
        ruang = findViewById(R.id.et_ruang);
        kapasitas = findViewById(R.id.et_kapasitas);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

        SQLiteDatabase db = database.getReadableDatabase();
        String query =  "";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit:

                break;
        }
    }
}