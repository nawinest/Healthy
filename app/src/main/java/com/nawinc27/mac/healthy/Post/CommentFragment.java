package com.nawinc27.mac.healthy.Post;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.nawinc27.mac.healthy.MenuFragment;
import com.nawinc27.mac.healthy.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommentFragment extends Fragment {
    String result;
    JSONArray jsonArray;
    ArrayList<Comment> comments = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bd = getArguments();
        int id = bd.getInt("key_id");
        beginTaskCallComment(id);

        initBackBtn();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container , false);
    }

    public void beginTaskCallComment(final int id){
        //user async because when i run it on main thread it show some error
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient client = new OkHttpClient();
                String url = "https://jsonplaceholder.typicode.com/posts/"+id+"/comments";//id from bundle
                Request request = new Request.Builder().url(url).build();
                Response response = null; //delare response
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    Log.d("Comment Fragment", "Exception : " + e.getMessage());
                }
                try {
                    jsonArray = new JSONArray(result);
                } catch (JSONException e) {
                    Log.d("Comment Fragment", "Exception : " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Log.d("::::::::::::", ""+obj.getInt("postId"));
                        Comment cm = new Comment(obj.getInt("postId"),obj.getInt("id"),obj.getString("name")
                                ,obj.getString("email"),obj.getString("body"));
                        comments.add(cm);
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
        ListView comment_list = getActivity().findViewById(R.id.comment_list);
        final CommentAdapter adapter = new CommentAdapter(
                getActivity(),
                R.layout.fragment_comment_item,
                comments
        );

        comment_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public void initBackBtn(){
        Button back = getActivity().findViewById(R.id.back_btn_comment);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new PostFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

    }


}
