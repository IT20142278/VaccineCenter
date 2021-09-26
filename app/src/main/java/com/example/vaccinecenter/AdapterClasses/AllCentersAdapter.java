package com.example.vaccinecenter.AdapterClasses;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vaccinecenter.R;
import com.example.vaccinecenter.model.AddCenters;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.ViewHolder;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.HashMap;
import java.util.Map;

public class AllCentersAdapter extends FirebaseRecyclerAdapter<AddCenters, AllCentersAdapter.myviewholder> {
    public AllCentersAdapter(@NonNull FirebaseRecyclerOptions<AddCenters> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position, @NonNull AddCenters model) {
        holder.centerId.setText(model.getCenterId());
        holder.centerName.setText(model.getCenterName());
        holder.centerAddress.setText(model.getCenterAddress());
        holder.centerPhone.setText(model.getCenterPhoneNo());


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.centerId.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true, 1100)
                        .create();

                View myview = dialogPlus.getHolderView();
                final EditText name = myview.findViewById(R.id.uname);
                final EditText doctor = myview.findViewById(R.id.vc1);
                final EditText hours = myview.findViewById(R.id.vc2);
                final EditText address = myview.findViewById(R.id.vc3);
                final EditText number = myview.findViewById(R.id.vc4);
                Button submit = myview.findViewById(R.id.usubmit);

                name.setText(model.getCenterName());
                doctor.setText(model.getCenterDoctor());
                hours.setText(model.getCenterHours());
                address.setText(model.getCenterAddress());
                number.setText(model.getCenterPhoneNo());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("centerName", name.getText().toString());
                        map.put("centerDoctor", doctor.getText().toString());
                        map.put("centerHours", hours.getText().toString());
                        map.put("centerAddress", address.getText().toString());
                        map.put("centerPhoneNo", number.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("centerDetails")
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
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.centerId.getContext());
                builder.setTitle("Delete Vaccine Center");
                builder.setMessage("Do you want to delete this center permanently?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("centerDetails")
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder {

        CircleImageView edit, delete;
        TextView centerId, centerName, centerAddress, centerPhone;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            centerId = (TextView) itemView.findViewById(R.id.idtext);
            centerName = (TextView) itemView.findViewById(R.id.nametext);
            centerAddress = (TextView) itemView.findViewById(R.id.addresstext);
            centerPhone = (TextView) itemView.findViewById(R.id.phonetext);

            edit = (CircleImageView) itemView.findViewById(R.id.editicon);
            delete = (CircleImageView) itemView.findViewById(R.id.deleteicon);
        }
    }
}
