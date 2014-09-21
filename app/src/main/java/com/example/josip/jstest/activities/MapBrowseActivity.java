package com.example.josip.jstest.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.josip.jstest.R;
import com.example.josip.model.Checkpoint;
import com.example.josip.model.Point;
import com.example.josip.model.Quest;
import com.example.josip.providers.QuestProvider;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Josip on 21/09/2014!
 */
public class MapBrowseActivity extends Activity {

    private static final Logger logger = LoggerFactory.getLogger(MapBrowseActivity.class);

    private GoogleMap map;

    private HashMap<Quest, Marker> questToMarkerMap = new HashMap<Quest, Marker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_browse);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        final ListView listView = (ListView) findViewById(R.id.listView);
        final ArrayList<Quest> quests = QuestProvider.getMockedQuests(10);


        final QuestArrayAdapter questArrayAdapter = new QuestArrayAdapter(this, quests);
        listView.setAdapter(questArrayAdapter);

        listView.setOnScrollListener(new ListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem > 2) {
                    for (Quest quest : quests.subList(0, firstVisibleItem - 1)) {
                        if (questToMarkerMap.containsKey(quest)) {
                            questToMarkerMap.get(quest).remove();
                            questToMarkerMap.remove(quest);
                        }
                    }
                }

                int end = firstVisibleItem + visibleItemCount < quests.size() ? firstVisibleItem + visibleItemCount : quests.size();
                for (Quest quest : quests.subList(firstVisibleItem, end)) {
                    if (!questToMarkerMap.containsKey(quest)) {
                        questToMarkerMap.put(quest, map.addMarker(getMarkerOptionsForQuest(quest)));
                    }
                }

                updateMapZoom(questToMarkerMap.values(), map);


                if (totalItemCount == firstVisibleItem + visibleItemCount) {
                    quests.addAll(QuestProvider.getMockedQuests(10));
                    questArrayAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void updateMapZoom(Collection<Marker> markers, GoogleMap map) {
        if (markers.size() > 0) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : markers) {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();

            int padding = 16;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);

            map.animateCamera(cameraUpdate);
        }
    }

    private MarkerOptions getMarkerOptionsForQuest(Quest quest) {
        Point checkpointCenter = quest.getQuestGraph().getAllCheckpoints().iterator().next().getArea().aproximatingCircle().getCenter();
        LatLng coordinates = new LatLng(checkpointCenter.getLatitude(), checkpointCenter.getLongitude());
        return new MarkerOptions()
                .position(coordinates)
                .title(quest.getName());
    }


    private class QuestArrayAdapter extends ArrayAdapter<Quest> {

        private final int QUEST_LIST_ITEM_ID = R.layout.quest_list_item;
        private Context context;
        private List<Quest> quests;

        public QuestArrayAdapter(Context context, List<Quest> quests) {
            super(context, R.layout.quest_list_item, quests);
            this.context = context;
            this.quests = quests;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView;
            if (convertView == null) {
                rowView = inflater.inflate(QUEST_LIST_ITEM_ID, parent, false);
            } else {
                rowView = convertView;
            }

            Quest rowItem = quests.get(position);
            Checkpoint itemsFirstCheckpoint = rowItem.getQuestGraph().getAllCheckpoints().iterator().next();

            TextView titleView = (TextView) rowView.findViewById(R.id.title);
            titleView.setText(rowItem.getName());
            TextView subtitleView = (TextView) rowView.findViewById(R.id.subtitle);
            StringBuilder coordinatesStringBuilder = new StringBuilder("starting coordinates: ");
            coordinatesStringBuilder.append("latitude-");
            coordinatesStringBuilder.append(itemsFirstCheckpoint.getArea().aproximatingCircle().getCenter().getLatitude());
            coordinatesStringBuilder.append("; longitude-");
            coordinatesStringBuilder.append(itemsFirstCheckpoint.getArea().aproximatingCircle().getCenter().getLongitude());
            coordinatesStringBuilder.append(";");
            subtitleView.setText(coordinatesStringBuilder.toString());

            return rowView;
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

}
