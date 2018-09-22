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
import com.nawinc27.mac.healthy.R;

public class WeightFormFragment extends Fragment{
    FirebaseFirestore _firestore;
    FirebaseAuth _auth;

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


        Button _saveBtn = (Button) getView().findViewById(R.id.savebtn_weightForm);
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _date = getView().findViewById(R.id.date_weightForm);
                EditText _weight = getView().findViewById(R.id.weight_weightForm);

                String _dateString = _date.getText().toString();
                String _weightString = _weight.getText().toString();

                String _uid = _auth.getCurrentUser().getUid();

                Weight _data = new Weight(_dateString,_weightString);

                _firestore.collection("myfitness")
                        .document(_uid).collection("weight")
                        .document(_dateString)
                        .set(_data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {


                            }


                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("WeightForm","onFailExceptionCollection");
                        Toast.makeText(getActivity() , "กรุณาลองใหม่อีกครั้ง" , Toast.LENGTH_SHORT).show();
                    }
                });

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weightform, container , false);
    }
}
