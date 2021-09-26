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

public class AllCustomerCentersAdapter extends FirebaseRecyclerAdapter<AddCenters, AllCustomerCentersAdapter.myviewholder> {
    public AllCustomerCentersAdapter(@NonNull FirebaseRecyclerOptions<AddCenters> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position, @NonNull AddCenters model) {
        holder.centerId.setText(model.getCenterId());
        holder.centerName.setText(model.getCenterName());
        holder.centerAddress.setText(model.getCenterAddress());
        holder.centerPhone.setText(model.getCenterPhoneNo());


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowcustomer, parent, false);
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
