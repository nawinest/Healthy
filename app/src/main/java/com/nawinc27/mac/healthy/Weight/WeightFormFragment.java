package com.nawinc27.mac.healthy.Weight;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nawinc27.mac.healthy.R;

public class WeightFormFragment extends Fragment{
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String uid;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button _backBtn = (Button) getView().findViewById(R.id.backBtn_weightForm);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new WeightFragment()).addToBackStack(null).commit();
            }
        });

        uid = currentFirebaseUser.getUid().toString();
        Button _saveBtn = (Button) getView().findViewById(R.id.savebtn_weightForm);
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _date = getView().findViewById(R.id.date_weightForm);
                EditText _weight = getView().findViewById(R.id.weight_weightForm);

                String _dateString = _date.getText().toString();
                String _weightString = _weight.getText().toString();

                //Create Fire Store
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                //Get Weight Obj
                final Weight weightStore = new Weight(_date.getText().toString(),Float.parseFloat(_weightString), "-");
                //Send Data To Database
                db.collection("myfitness")
                        .document(uid)
                        .collection("weight")
                        .document(_dateString)
                        .set(weightStore)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "เพิ่มข้อมูลเรียบร้อยแล้ว", Toast.LENGTH_LONG).show();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new WeightFragment()).addToBackStack(null).commit();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weightform, container , false);
    }
}
