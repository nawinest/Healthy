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

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends ArrayAdapter<Comment> {
    private Context con;
    private ArrayList<Comment> comments;

    public CommentAdapter(@NonNull Context context, int resource,@NonNull List<Comment> objects) {
        super(context, resource, objects);
        this.con= context;
        this.comments = (ArrayList<Comment>) objects;
    }


    @NonNull
    @Override
    public View getView(int position,  @Nullable View convertView, @NonNull ViewGroup parent) {
        View comment_item;
        comment_item = LayoutInflater.from(con).inflate(R.layout.fragment_comment_item, parent,false);
        TextView post_id_incomment = comment_item.findViewById(R.id.post_id_incomment);
        TextView id_comment = comment_item.findViewById(R.id.id_comment);
        TextView body_incomment3 = comment_item.findViewById(R.id.body_incomment3);
        TextView name_incomment = comment_item.findViewById(R.id.name_incomment);
        TextView email_incomment = comment_item.findViewById(R.id.email_incomment);

        post_id_incomment.setText(Integer.toString(comments.get(position).getPostId()));
        id_comment.setText(Integer.toString(comments.get(position).getId()));
        body_incomment3.setText(comments.get(position).getBody());
        name_incomment.setText(comments.get(position).getName());
        email_incomment.setText("( "+comments.get(position).getEmail()+" )");

        return  comment_item;

    }
}
