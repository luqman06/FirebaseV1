package com.example.firebasev1;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdapterBarangRecyclerView extends RecyclerView.Adapter<AdapterBarangRecyclerView.ViewHolder> {

    private ArrayList<Barang> daftarBarang;
    private Context context;
    FirebaseDBReadActivity2 listener;

    public AdapterBarangRecyclerView(ArrayList<Barang> barangs, Context ctx) {

        daftarBarang = barangs;
        context = ctx;

        //disini ada masalah
        listener = (FirebaseDBReadActivity2)ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle;

        ViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_namabarang);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final String name = daftarBarang.get(position).getNama();

        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_view);
                dialog.setTitle("Pilih Aksi");
                dialog.show();

                Button editButton = (Button) dialog.findViewById(R.id.bt_edit_data);
                Button delButton = (Button) dialog.findViewById(R.id.bt_delet_data);

                //apabila tombol edit diklik
                editButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                context.startActivity(FireBAseDBCreateActivity2.getActIntent((Activity) context).putExtra("data", daftarBarang.get(position)));
                            }
                        }
                );

                //apabila tombol delete diklik
                delButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                listener.onDeleteData(daftarBarang.get(position), position);

                            }
                        }
                );
                return true;
            }
        });
        holder.tvTitle.setText(name);
    }


    @Override
    public int getItemCount() {

        return daftarBarang.size();
    }

    public interface FirebaseDataListener {
        void onDeleteData(Barang barang, int position);
    }
}