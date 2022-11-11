package com.example.crudapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {
    protected Cursor cursor;
    Database database;
    EditText gedung, ruang, kapasitas;
    Button btn_submit, btn_back;

    @SuppressLint("MissingInflatedId")
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
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(view -> finish());

        SQLiteDatabase db = database.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM ruang WHERE id = '" + getIntent().getStringExtra("ID") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            gedung.setText(cursor.getString(1).toString());
            ruang.setText(cursor.getString(2).toString());
            kapasitas.setText(cursor.getString(3).toString());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit:
                SQLiteDatabase db = database.getWritableDatabase();

                // Ambil nilai yang diberikan pengguna pada seluruh EditText
                String inputGedung = gedung.getText().toString();
                String inputRuang = ruang.getText().toString();
                String inputKapasitas = kapasitas.getText().toString();

                // Validasi
                boolean isEmptyFields = false;

                // Mengecek apakah inputan kosong
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

                // Gedung dan ruang tidak boleh sama
                cursor = db.rawQuery("SELECT * FROM ruang WHERE gedung = '" + inputGedung + "' AND ruang = '" + inputRuang + "' AND id != '" + getIntent().getStringExtra("ID") + "'",null);
                cursor.moveToFirst();
                if (cursor.getCount()>0){
                    isEmptyFields = true;
                    Toast.makeText(this, "Gedung dan Ruang sudah ada", Toast.LENGTH_SHORT).show();
                }

                if (!isEmptyFields) {
                    db.execSQL("update ruang set gedung='" +
                            inputGedung + "', ruang='" +
                            inputRuang + "', kapasitas='" +
                            inputKapasitas + "' " +
                            "where id='" + getIntent().getStringExtra("ID") + "'");
                    MainActivity.ma.RefreshList();
                    finish();
                }

        }
    }

}