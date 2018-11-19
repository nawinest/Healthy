package com.nawinc27.mac.healthy.Post;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nawinc27.mac.healthy.MenuFragment;
import com.nawinc27.mac.healthy.R;
import com.nawinc27.mac.healthy.Sleep.SleepAdapter;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class PostFragment extends Fragment {

    private String url,result;
    private ArrayList<Post> posts;
    JSONArray jsonArray ;
    OkHttpClient client = new OkHttpClient();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        posts = new ArrayList<Post>();
        getJson();
        initBackBtn();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container , false);
    }


    void getJson() {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("https://jsonplaceholder.typicode.com/posts").build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    jsonArray = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
//                        Log.d("Test data is comming ? " , "onpost excute : " + obj.getInt("userId"));
                        Post p = new Post(obj.getInt("userId"),obj.getInt("id"),obj.getString("title"),obj.getString("body"));
                        posts.add(p);
                    }
                    initSetList();
                }
                catch (JSONException e)
                {
                    Log.d("Comment Fragment", "Exception : " + e.getMessage());
                }
            }
        };
        task.execute();
    }

    public void initSetList(){
        ListView listView = getActivity().findViewById(R.id.post_list);
        final PostAdapter adapter = new PostAdapter(
                getActivity(),
                R.layout.fragment_postitem,
                posts
        );
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bd = new Bundle();
                bd.putInt("key_id", posts.get(position).getId());
                Fragment fragment = new CommentFragment();
                fragment.setArguments(bd);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }


    public void initBackBtn(){
        Button back = getActivity().findViewById(R.id.backbtn_post);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }




}
