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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment{
    private FirebaseUser _user = FirebaseAuth.getInstance().getCurrentUser();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Button _loginBtn = (Button) getView().findViewById(R.id.login_btn);
        if(_user != null){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view,new MenuFragment()).addToBackStack(null).commit();
        }

        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLogin();
            }

        });

        TextView _registerBtn = (TextView) getView().findViewById(R.id.sign_up_button);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("USER", "GOTO REGIS");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new RegisterFragment()).addToBackStack(null).commit();
            }
        });
    }

    void initLogin(){
        TextView userTextField = (TextView) getView().findViewById(R.id.user_id);
        TextView passwordTextField = (TextView) getView().findViewById(R.id.user_password);

        String userTextFieldStr = userTextField.getText().toString();
        String passwordTextFieldStr = passwordTextField.getText().toString();

        if(userTextFieldStr.equals("") || passwordTextFieldStr.equals("")){
            Log.i("LOGIN","USER OR PASSWORD IS EMPTY");
            Toast.makeText(getActivity(), "กรุณาระบุ Username หรือ Password",Toast.LENGTH_SHORT).show();
        } else {
            Log.i("LOGIN","SUCCESS");
            final FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(userTextFieldStr, passwordTextFieldStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    _user = FirebaseAuth.getInstance().getCurrentUser();
                    if(task.isSuccessful()){
                        if(_user.isEmailVerified()){
                            Log.i("LOGIN", "Complete");
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).addToBackStack(null).commit();
                        }else{
                            Toast.makeText(getActivity(), "กรุณายืนยัน E-Mail", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "Loading", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



}
