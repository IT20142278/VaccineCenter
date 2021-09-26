package com.example.vaccinecenter.AdapterClasses;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.vaccinecenter.R;
import com.example.vaccinecenter.model.Symptom;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;


import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SymptomAdapter extends FirebaseRecyclerAdapter<Symptom, SymptomAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SymptomAdapter(@NonNull FirebaseRecyclerOptions<Symptom> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Symptom model) {

        holder.sympName.setText(model.getSymptomName());
        holder.sympId.setText(model.getSymptomId());
        holder.sympRarity.setText(model.getsRarity());

        // Glide

        Glide.with(holder.img.getContext())
                .load(model.getsUrl())
                    .placeholder(R.drawable.symptom_vec)
                    .circleCrop()
                    .error(R.drawable.error_toast)
                    .into(holder.img);

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_symptom))
                        .setExpanded(true, 1280)
                        .create();

                // Update

                View view = dialogPlus.getHolderView();

                EditText sympName = view.findViewById(R.id.update_symptom_nameEdit);
                EditText sympDescription = view.findViewById(R.id.update_descriptionEdit);
                EditText sympRarity = view.findViewById(R.id.update_rarityEdit);
                EditText sympEffectedHours = view.findViewById(R.id.update_effected_hoursEdit);
                EditText sympPercentage = view.findViewById(R.id.update_percentageEdit);
                EditText sympUrl = view.findViewById(R.id.update_urlEdit);

                Button updateBtn = view.findViewById(R.id.symp_updateBtn);

                sympName.setText(model.getSymptomName());
                sympDescription.setText(model.getsDescription());
                sympRarity.setText(model.getsRarity());
                sympEffectedHours.setText(model.getsEffectedHours());
                sympPercentage.setText(model.getsPercentage());
                sympUrl.setText(model.getsUrl());

                dialogPlus.show();


                updateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("symptomName", sympName.getText().toString());
                        map.put("sDescription", sympDescription.getText().toString());
                        map.put("sRarity", sympRarity.getText().toString());
                        map.put("sEffectedHours", sympEffectedHours.getText().toString());
                        map.put("sPercentage", sympPercentage.getText().toString());
                        map.put("sUrl", sympUrl.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("symptomDetails")
                                .child(getRef(holder.getAdapterPosition()).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.sympName.getContext(), "Data has been updated successfully", Toast.LENGTH_LONG).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.sympName.getContext(), "Error while updating", Toast.LENGTH_SHORT).show();
                                    }
                                });



                    }
                });




            }
        });

        // Delete
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.sympName.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleting data is permanent!");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseDatabase.getInstance().getReference().child("symptomDetails")
                                .child(getRef(holder.getAdapterPosition()).getKey())
                                .removeValue();


                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(holder.sympName.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();

            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.symptom_card,parent, false);

        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView sympName, sympId, sympRarity;

        Button editBtn, deleteBtn;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView) itemView.findViewById(R.id.symp_cardImage);
            sympName = (TextView) itemView.findViewById(R.id.card_title);
            sympId = (TextView) itemView.findViewById(R.id.card_id);
            sympRarity = (TextView) itemView.findViewById(R.id.card_info);

            editBtn = (Button) itemView.findViewById(R.id.symp_editBtn);
            deleteBtn = (Button) itemView.findViewById(R.id.symp_deleteBtn);



        }
    }
}
