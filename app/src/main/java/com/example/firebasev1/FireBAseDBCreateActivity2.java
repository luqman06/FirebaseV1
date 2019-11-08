package com.example.firebasev1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBAseDBCreateActivity2 extends AppCompatActivity {
    // variable yang merefers ke Firebase Realtime Database
    private DatabaseReference database;

    // variable fields EditText dan Button
    private Button btSubmit;
    private EditText etNama;
    private EditText etMerk;
    private EditText etHarga;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_create);


        etNama = (EditText) findViewById(R.id.et_namabarang);
        etMerk = (EditText) findViewById(R.id.et_merkbarang);
        etHarga = (EditText) findViewById(R.id.et_hargabarang);
        btSubmit = (Button) findViewById(R.id.bt_submit);


        database = FirebaseDatabase.getInstance().getReference();

        final Barang barang = (Barang) getIntent().getSerializableExtra("data");

        if (barang != null) {
            etNama.setText(barang.getNama());
            etMerk.setText(barang.getMerk());
            etHarga.setText(barang.getHarga());
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    barang.setNama(etNama.getText().toString());
                    barang.setMerk(etMerk.getText().toString());
                    barang.setHarga(etHarga.getText().toString());

                    updateBarang(barang);
                }
            });
        } else {
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isEmpty(etNama.getText().toString()) && !isEmpty(etMerk.getText().toString()) && !isEmpty(etHarga.getText().toString()))
                        submitBarang(new Barang(etNama.getText().toString(), etMerk.getText().toString(), etHarga.getText().toString()));
                    else
                        Snackbar.make(findViewById(R.id.bt_submit), "Data barang tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            etNama.getWindowToken(), 0);
                }
            });
        }
    }

    private boolean isEmpty(String s){

        return TextUtils.isEmpty(s);
    }

    private void updateBarang(Barang barang) {

        database.child("barang") //akses parent index, ibaratnya seperti nama tabel
                .child(barang.getKey()) //select barang berdasarkan key
                .setValue(barang) //set value barang yang baru
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        Snackbar.make(findViewById(R.id.bt_submit), "Data berhasil diupdatekan", Snackbar.LENGTH_LONG).setAction("Oke", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        }).show();
                    }
                });
    }

    private void submitBarang(Barang barang) {

        database.child("barang").push().setValue(barang).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                etNama.setText("");
                etMerk.setText("");
                etHarga.setText("");
                Snackbar.make(findViewById(R.id.bt_submit), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public static Intent getActIntent(Activity activity) {

        return new Intent(activity, FireBAseDBCreateActivity2.class);
    }
}
