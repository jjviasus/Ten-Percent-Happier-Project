package com.justinviasus.tenpercenthappierproject;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Binds data to view holder
public class TopicViewAdapter extends RecyclerView.Adapter<TopicViewHolder> {
    private final ArrayList<TopicItemCard> topicList;
    private TopicClickListener listener;

    public TopicViewAdapter(ArrayList<TopicItemCard> topicList) { this.topicList = topicList; }

    public void setOnItemClickListener(TopicClickListener listener) { this.listener = listener; }

    // Methods below are required to be overridden and added to the adapter for it to work.

    // Used to initialize ViewHolder(s)
    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_card, parent, false);
        return new TopicViewHolder(view, listener);
    }

    // Called for each ViewHolder to bind it to the adapter. We pass our data to our ViewHolder here.
    @Override
    public void onBindViewHolder(TopicViewHolder holder, int position) {
        TopicItemCard currentItem = topicList.get(position);
        holder.topicName.setText(currentItem.getTitle());
        int size = currentItem.getMeditations().size();
        holder.topicMeditationCount.setText(size + " Meditations");
        String color = currentItem.getColor();
        holder.topicColor.setBackgroundColor(Color.parseColor(color));
    }

    // Returns the size of the collection that contains the items we want to display.
    @Override
    public int getItemCount() {
        return topicList.size();
    }

}
