package com.justinviasus.tenpercenthappierproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TopicItemCard> topicList = new ArrayList<>();

    private RecyclerView recyclerView;
    private TopicViewAdapter viewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timber.plant(new Timber.DebugTree());

        builder = new AlertDialog.Builder(this);

        viewAdapter = new TopicViewAdapter(topicList);
        recyclerView = findViewById(R.id.recycler_view);
        // Since we want to display our data as a linear vertical list, we will use LinearLayoutManager.
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        // Helps the Android framework optimize the RecyclerView by letting it know in advance
        // the RecyclerView size will not be affected by the Adapter contents
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(viewAdapter);

        // Display alert dialogue with topic's description_short
        TopicClickListener linkClickListener = new TopicClickListener() {
            @Override
            public void onItemClick(int position) {
                TopicItemCard item = topicList.get(position);
                String itemDescShort = item.getDescriptionShort();
                String itemTopicName = item.getTitle();
                builder.setMessage(itemDescShort)
                        .setPositiveButton("BACK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.setTitle(itemTopicName);
                alert.show();
            }
        };
        viewAdapter.setOnItemClickListener(linkClickListener);

        showFeaturedTopics();
    }

    // Returns the JSON data from the topics.json file as a String
    private String jsonDataFromAssets() {
        String json = null;
        try {
            InputStream inputStream = getAssets().open("topics.json");
            int sizeOfFile = inputStream.available();
            byte[] bufferData = new byte[sizeOfFile];
            inputStream.read(bufferData);
            inputStream.close();
            json = new String(bufferData, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    // Converts the JSON Array to an Array List of Strings
    private ArrayList<String> convert(JSONArray jArr) {
        ArrayList<String> list = new ArrayList<>();

        if (jArr.length() != 0) { // Can the length be 0?
            for (int i=0;i<jArr.length();i++){
                try {
                    list.add(jArr.getString(i));
                } catch (JSONException e) {
                    // A more meaningful exception could be caught here
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    // Adds the featured topics to the Recycler View
    private void showFeaturedTopics() {
        try {
            JSONObject jsonObject = new JSONObject(jsonDataFromAssets());
            JSONArray jsonArray = jsonObject.getJSONArray("topics");

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject topicData = jsonArray.getJSONObject(i);
                boolean isFeatured = topicData.getBoolean("featured");

                // Only show the featured topics in the list
                if (isFeatured) {
                    String uuid = topicData.getString("uuid");
                    String title = topicData.getString("title");
                    int position = topicData.getInt("position");
                    JSONArray meditations = topicData.getJSONArray("meditations");
                    String color = topicData.getString("color");
                    String description_short = topicData.getString("description_short");

                    // Convert the json list to array list
                    ArrayList<String> meditationsList = convert(meditations);

                    // Display the data on a new TopicItemCard in the Recycler View
                    TopicItemCard newItem = new TopicItemCard(uuid, title, position, meditationsList, true, color, description_short);
                    topicList.add(0, newItem);
                    viewAdapter.notifyItemInserted(0);

                    Timber.i("Item added. Title: %s, Meditations: %s, Color: %s", newItem.getTitle(), newItem.getMeditations(), newItem.getColor());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}