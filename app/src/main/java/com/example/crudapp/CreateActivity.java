package com.example.crudapp;

import androidx.appcompat.app.AppCompatActivity;

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
    EditText nama,univ;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        database = new Database(this);
        nama = findViewById(R.id.et_nama);
        univ = findViewById(R.id.et_univ);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit:
                SQLiteDatabase db = database.getWritableDatabase();
                String query = "INSERT INTO mahasiswa VALUES('" +
                        nama.getText().toString() + "','" + univ.getText().toString() + "');";
                db.execSQL(query);
                Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                MainActivity.ma.RefreshList();
                finish();
                break;
        }
    }
}