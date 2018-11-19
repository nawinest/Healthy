package com.nawinc27.mac.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.nawinc27.mac.healthy.Post.PostFragment;
import com.nawinc27.mac.healthy.Sleep.SleepFragment;
import com.nawinc27.mac.healthy.Weight.WeightFragment;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    ArrayList<String> menu = new ArrayList<>();
    public MenuFragment(){
        menu.add("BMI");
        menu.add("Weight");
        menu.add("Setup");
        menu.add("Sleep");
        menu.add("Post");
        menu.add("Sign Out");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView menuList = getView().findViewById(R.id.menu_list);
        ArrayAdapter<String> menuAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menu
        );

        menuList.setAdapter(menuAdapter);
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String _menuP = menu.get(position);
                if (_menuP.equals("BMI")) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new BMIFragment()).addToBackStack(null).commit();
                } else if (_menuP.equals("Weight")) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new WeightFragment()).addToBackStack(null).commit();
                } else if (_menuP.equals("Sign Out")) {
                    Log.d("USER","Sign out from firebase auth");
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view,new LoginFragment()).commit();
                } else if (_menuP.equals("Sleep")){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view,new SleepFragment()).commit();

                }else if (_menuP.equals("Post")){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view,new PostFragment()).commit();

                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container , false);
    }
}
