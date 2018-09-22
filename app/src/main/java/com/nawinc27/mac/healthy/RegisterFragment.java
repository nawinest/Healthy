package com.nawinc27.mac.healthy;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRegisterBtn();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    void initRegisterBtn(){
        Button _regisBtn = (Button) getView().findViewById(R.id.register_btn);
        _regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _userId = (EditText) getView().findViewById(R.id.user_id_signup);
                EditText _password = (EditText) getView().findViewById(R.id.password_signup);
                EditText _password_again = (EditText) getView().findViewById(R.id.password_signup_again);

                String _userIdStr = ((EditText) getView().findViewById(R.id.user_id_signup)).getText().toString();
                String _passwordStr = ((EditText) getView().findViewById(R.id.password_signup)).getText().toString();
                String _password_againStr = ((EditText) getView().findViewById(R.id.password_signup_again)).getText().toString();

                if (_userIdStr.equals("") || _passwordStr.equals("") || _password_againStr.equals("")){
                    Toast.makeText(getActivity(), "กรุณาระบุให้ครบทุกส่วน", Toast.LENGTH_SHORT).show();
                    Log.i("REGISTER","EMPTY PASSWORD OR USERNAME OR RE-PASSWORD");
                } else if (_passwordStr.length() < 6){
                    Toast.makeText(getActivity(), "รหัสผ่านต้องมากกว่า 6 ตัวอักษร", Toast.LENGTH_SHORT).show();
                    Log.i("REGISTER","PASSWORD LENGHT LESS THAN 6 CHARACTER");
                }else if (!_passwordStr.equals(_password_againStr)){

                    Toast.makeText(getActivity(), "กรุณาใส่รหัสผ่านให้ตรงกัน", Toast.LENGTH_SHORT).show();
                    Log.i("REGISTER","PASSWORD NOT MATCH WITH RE-PASSWORD");
                    Log.i("ERROR _ LOG FIND ",_password_againStr + " and " + _passwordStr);
                }
                else{
                    Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();

                    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(_userIdStr, _passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Log.i("Register", "Complete");
                            sendVerifiedEmail(authResult.getUser());
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).addToBackStack(null).commit();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "ERROR" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }


            }
        });
    }

    private void sendVerifiedEmail(FirebaseUser _user){
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}
