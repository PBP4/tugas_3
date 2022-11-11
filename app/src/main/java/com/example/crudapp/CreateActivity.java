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
    EditText gedung, ruang, kapasitas;
    Button btn_submit, btn_back;

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
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit:
                // Ambil nilai yang diberikan pengguna pada seluruh EditText
                String inputGedung = gedung.getText().toString();
                String inputRuang = ruang.getText().toString();
                String inputKapasitas = kapasitas.getText().toString();

                // Validasi
                boolean isEmptyFields = false;

                // Mengecek apakah inputLength kosong
                if (inputGedung.isEmpty()) {
                    isEmptyFields = true;
                    gedung.setError("Gedung tidak boleh kosong");
                }
                if (inputRuang.isEmpty()) {
                    isEmptyFields = true;
                    ruang.setError("Ruang tidak boleh kosong");
                }
                if (inputKapasitas.isEmpty()) {
                    isEmptyFields = true;
                    kapasitas.setError("Kapasitas tidak boleh kosong");
                }

                if (!isEmptyFields) {
                    // Jika tidak ada field yang kosong, maka masukkan data ke database
                    SQLiteDatabase db = database.getWritableDatabase();
                    db.execSQL("INSERT INTO inventory (gedung, ruang, kapasitas) VALUES ('" + inputGedung + "','" + inputRuang + "','" + inputKapasitas + "')");
                    Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    MainActivity.ma.RefreshList();
                    finish();
                }
                break;
        }
    }
}