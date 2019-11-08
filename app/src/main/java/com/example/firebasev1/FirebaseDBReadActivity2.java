package com.example.firebasev1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FirebaseDBReadActivity2 extends AppCompatActivity{


    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Barang> daftarBarang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ctivity_db_read);


        rvView = (RecyclerView) findViewById(R.id.rv_main);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);


        database = FirebaseDatabase.getInstance().getReference();

        database.child("barang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                daftarBarang = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {

                    Barang barang = noteDataSnapshot.getValue(Barang.class);
                    barang.setKey(noteDataSnapshot.getKey());


                    daftarBarang.add(barang);
                }


                adapter = new AdapterBarangRecyclerView(daftarBarang, FirebaseDBReadActivity2.this);
                rvView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });
    }

    public static Intent getActIntent(Activity activity){
        return new Intent(activity, FirebaseDBReadActivity2.class);
    }


    public void onDeleteData(Barang barang,final  int position){
        if (database!=null){
            database.child("barang").child(barang.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(FirebaseDBReadActivity2.this,"succes delet data",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}