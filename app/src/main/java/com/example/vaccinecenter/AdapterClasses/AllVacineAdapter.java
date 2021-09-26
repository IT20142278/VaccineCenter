package com.example.vaccinecenter.AdapterClasses;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vaccinecenter.R;
import com.example.vaccinecenter.model.AddCenters;
import com.example.vaccinecenter.model.ApplyVaccine;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllVacineAdapter extends FirebaseRecyclerAdapter<ApplyVaccine, AllVacineAdapter.myviewholder> {
    public AllVacineAdapter(@NonNull FirebaseRecyclerOptions<ApplyVaccine> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position, @NonNull ApplyVaccine model) {
        holder.applyId.setText(model.getApplyID());
        holder.does.setText(model.getDoes());
        holder.address.setText(model.getAddress());
        holder.district.setText(model.getDistrict());


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.applyId.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontentvaccine))
                        .setExpanded(true, 1100)
                        .create();

                View myview = dialogPlus.getHolderView();
                final EditText does = myview.findViewById(R.id.uname);
                final EditText age = myview.findViewById(R.id.vc1);
                final EditText nic = myview.findViewById(R.id.vc2);
                final EditText gender = myview.findViewById(R.id.vc3);
                final EditText occu = myview.findViewById(R.id.vc4);
                final EditText address = myview.findViewById(R.id.vc5);
                final EditText gd = myview.findViewById(R.id.vc6);
                final EditText district = myview.findViewById(R.id.vc7);
                Button submit = myview.findViewById(R.id.usubmit);

                does.setText(model.getDoes());
                age.setText(model.getAge());
                nic.setText(model.getNic());
                gender.setText(model.getGender());
                occu.setText(model.getOccupation());
                address.setText(model.getAddress());
                gd.setText(model.getGramaDivision());
                district.setText(model.getDistrict());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("does", does.getText().toString());
                        map.put("age", age.getText().toString());
                        map.put("nic", nic.getText().toString());
                        map.put("gender", gender.getText().toString());
                        map.put("occupation", occu.getText().toString());
                        map.put("address", address.getText().toString());
                        map.put("gramaDivision", gd.getText().toString());
                        map.put("district", district.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("ApplyDetails")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                        TastyToast.makeText(myview.getContext(), "Update Successfully.", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.applyId.getContext());
                builder.setTitle("Delete Apply Vaccine");
                builder.setMessage("Do you want to delete this apply permanently?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("ApplyDetails")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowvaccine, parent, false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder {

        CircleImageView edit, delete;
        TextView applyId, does, district, address;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            applyId = (TextView) itemView.findViewById(R.id.idtext);
            does = (TextView) itemView.findViewById(R.id.nametext);
            address = (TextView) itemView.findViewById(R.id.addresstext);
            district = (TextView) itemView.findViewById(R.id.phonetext);

            edit = (CircleImageView) itemView.findViewById(R.id.editicon);
            delete = (CircleImageView) itemView.findViewById(R.id.deleteicon);
        }
    }
}
