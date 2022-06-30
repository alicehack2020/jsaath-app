package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.model.Todo;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    List<Todo> getTodos;
    Context context;

    public NewsAdapter(List<Todo> getTodos, Context context){
        this.getTodos=getTodos;
        this.context=context;

    }

    @NonNull
    @Override
    public NewsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_news,parent,false);
        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapterViewHolder holder, int i) {



           Glide.with(context).load(getTodos.get(i).getImageLink()).placeholder(R.drawable.collapse).into(holder.imageView);
            holder.title.setText(getTodos.get(i).getTitle());
            holder.status.setText(Integer.toString(getTodos.get(i).getId()));
            holder.playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(context, VideoPlayer.class);
                    intent.putExtra("videoLink",getTodos.get(i).getVideoLink());
                    context.startActivity(intent);
                }
            });



     }

    @Override
    public int getItemCount() {
        return getTodos.size();
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title,status;
        Button playButton;

        public NewsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.news_image);
            title=itemView.findViewById(R.id.title);
            status=itemView.findViewById(R.id.status);
            playButton=itemView.findViewById(R.id.playButton);

        }
    }
}
