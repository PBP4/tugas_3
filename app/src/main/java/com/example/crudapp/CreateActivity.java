package com.example.crudapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {
    protected Cursor cursor;
    Database database;
    EditText gedung,ruang, kapasitas;
    Button btn_submit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        database = new Database(this);
        gedung = findViewById(R.id.et_gedung);
        ruang = findViewById(R.id.et_ruang);
        kapasitas = findViewById(R.id.et_kapasitas);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit:
                SQLiteDatabase db = database.getWritableDatabase();
                String query = "INSERT INTO inventory (gedung, ruang, kapasitas) VALUES ('"+gedung.getText().toString()+"','"+ruang.getText().toString()+"','"+kapasitas.getText().toString()+"')";
                db.execSQL(query);
                Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                MainActivity.ma.RefreshList();
                finish();
                break;
        }
    }
}