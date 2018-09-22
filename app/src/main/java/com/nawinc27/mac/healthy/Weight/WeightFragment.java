package com.nawinc27.mac.healthy.Weight;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.nawinc27.mac.healthy.R;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.WeakHashMap;

public class WeightFragment extends Fragment {
    private String uid;
    private FirebaseUser _user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<Weight> weightStore= new ArrayList<Weight>();
    private DocumentSnapshot doc;

    @Nullable
    @Override



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button _addBtn = (Button) getView().findViewById(R.id.add_weightBtn);
        _addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFormFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        // this part is hard code.
//        weights.add((new Weight("01 Jan 2018",63,"UP")));
//        weights.add((new Weight("02 Jan 2018",67,"UP")));
//        weights.add((new Weight("03 Jan 2018",69,"UP")));
//        weights.add((new Weight("04 Jan 2018",79,"UP")));
        ListView weightList = (ListView) getView().findViewById(R.id.weight_list); //id
        final WeightAdapter weightAdapter = new WeightAdapter(getActivity(), android.R.layout.list_content ,weightStore);
        weightList.setAdapter(weightAdapter);
        weightAdapter.clear();
//        getObject From firestore
        this.uid = _user.getUid();

        db.collection("myfitness").document(uid).collection("weight").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        weightStore.add(document.toObject(Weight.class));
                    }
                }

                weightStore = checkStatus(weightStore);
                weightAdapter.sort(new Comparator<Weight>() {
                    @Override
                    public int compare(Weight o1, Weight o2) {
                        return o2.getDate().compareTo(o1.getDate());
                    }
                });

                updateStatusToFirestore(weightStore);
                weightAdapter.notifyDataSetChanged();
            }
        });


    }

    private ArrayList<Weight> checkStatus(ArrayList<Weight> weightStore) {
        this.uid = _user.getUid();

        for (int i = 1; i < weightStore.size(); i++) {
            if (weightStore.get(i - 1).getWeight() > weightStore.get(i).getWeight()) {
                weightStore.get(i).setStatus("Down");
            }else if(weightStore.get(i - 1).getWeight() == weightStore.get(i).getWeight()){
                weightStore.get(i).setStatus("-");
            } else {
                weightStore.get(i).setStatus("Up");
            }
        }
        return weightStore;
    }

    private void  updateStatusToFirestore(ArrayList<Weight> weightStore){
        uid = _user.getUid().toString();
        this.weightStore = weightStore;
        int index = 0;
        for(Weight items: this.weightStore ){
            db.collection("myfitness").document(uid).collection("weight").document(items.getDate()).set(this.weightStore.get(index++));
        }

    }
}
