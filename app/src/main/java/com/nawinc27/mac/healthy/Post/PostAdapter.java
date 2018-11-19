package com.nawinc27.mac.healthy.Post;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nawinc27.mac.healthy.R;
import com.nawinc27.mac.healthy.Sleep.Sleep_info;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends ArrayAdapter {
    ArrayList<Post> posts = new ArrayList<Post>();
    private Context con;

    public PostAdapter(@NonNull Context context, int resource, @NonNull List<Post>  objects) {
        super(context, resource, objects);
        this.con = context;
        this.posts = (ArrayList<Post>) objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,@NonNull ViewGroup parent) {
        View post_item;
        post_item = LayoutInflater.from(con).inflate(R.layout.fragment_postitem, parent,false);
        TextView id = (TextView) post_item.findViewById(R.id.post_id);
        TextView title = (TextView) post_item.findViewById(R.id.post_title);
        TextView content = (TextView) post_item.findViewById(R.id.post_content);
        id.setText(Integer.toString(posts.get(position).getId()));
        title.setText(posts.get(position).getTitle());
        content.setText(posts.get(position).getBody());
        return post_item;
    }


}
