package com.justinviasus.tenpercenthappierproject;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

// Populates the view elements in the UI which is visible to the user.
public class TopicViewHolder extends RecyclerView.ViewHolder {

    public TextView topicName;
    public TextView topicMeditationCount;
    public View topicColor;

    // This constructor takes the parent View of the item layout allowing you to setup any views
    // you will need to use when displaying your data.
    public TopicViewHolder(View itemView, final TopicClickListener listener) {
        super(itemView);

        topicName = itemView.findViewById(R.id.topic_name);
        topicMeditationCount = itemView.findViewById(R.id.meditation_count);
        topicColor = itemView.findViewById(R.id.accent_color);

        // itemView click listener (display alert dialogue)
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener  != null) {
                    // get the item in the list that was tapped on
                    int position = getLayoutPosition();

                    // if the user manages to cause a change of the recycler view and then taps on
                    // it within just a few milliseconds, the tap will get lost and have NO_POSITION
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }
}
