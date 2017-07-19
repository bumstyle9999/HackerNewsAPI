package com.dmspro.trieungo.hackernewapi.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmspro.trieungo.hackernewapi.DTO.PostDTO;
import com.dmspro.trieungo.hackernewapi.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Trieu Ngo on 7/18/2017.
 */

public class AdapterPost extends  RecyclerView.Adapter<AdapterPost.ViewHolder> {

    private Context mContext;
    private List<PostDTO> mListPost;

    public AdapterPost(final List<PostDTO> postDTOs) {
        this.mListPost = postDTOs;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text_number, text_score, text_title, text_post_by_url, text_datetime,
                text_author, text_number_comment;

        public ViewHolder(View itemView) {
            super(itemView);

            text_number = (TextView) itemView.findViewById(R.id.text_number);
            text_score = (TextView) itemView.findViewById(R.id.text_score);
            text_title = (TextView) itemView.findViewById(R.id.text_title);
            text_post_by_url = (TextView) itemView.findViewById(R.id.text_post_by_url);
            text_datetime = (TextView) itemView.findViewById(R.id.text_datetime);
            text_author = (TextView) itemView.findViewById(R.id.text_author);
            text_number_comment = (TextView) itemView.findViewById(R.id.text_number_comment);
        }
    }

    public List<PostDTO> getItems() {
        return mListPost;
    }


    public AdapterPost(Context mContext, List<PostDTO> listPost) {
        this.mContext = mContext;
        this.mListPost = listPost;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_post, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PostDTO postDTO = mListPost.get(position);

        holder.text_number.setText(String.valueOf(position + 1));
        holder.text_score.setText(postDTO.getmScore());
        holder.text_title.setText(postDTO.getmTitle());
        holder.text_post_by_url.setText(postDTO.getmURL());

        Date date = new Date(Long.parseLong(postDTO.getmTime())*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        holder.text_datetime.setText(formattedDate);

        holder.text_author.setText(postDTO.getmAuthor());
        holder.text_number_comment.setText(postDTO.getmDescendants());


    }

    @Override
    public int getItemCount() {
        return mListPost.size();
    }


}
