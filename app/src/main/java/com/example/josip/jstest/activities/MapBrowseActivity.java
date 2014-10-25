package com.example.josip.jstest.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.josip.jstest.R;
import com.example.josip.model.Point;
import com.example.josip.model.Quest;
import com.example.josip.engine.state.archive.QuestProvider;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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

        final MarkerListAdapter<Quest> questMarkerListAdapter = new QuestMarkerListAdapter(map, quests);

        final QuestArrayAdapter questArrayAdapter = new QuestArrayAdapter(this, quests);
        listView.setAdapter(questArrayAdapter);

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                questMarkerListAdapter.clickOn(position);
            }
        });

        listView.setOnScrollListener(new ListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                questMarkerListAdapter.scrollTo(firstVisibleItem, visibleItemCount);

                if (totalItemCount == firstVisibleItem + visibleItemCount) {
                    questMarkerListAdapter.addAll(QuestProvider.getMockedQuests(10));
                    questArrayAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private String getCoordinatesStringFromQuest(Quest quest) {
        Point coordinates = quest.getQuestGraph().getAllCheckpoints().iterator().next().getArea().aproximatingCircle().getCenter();
        return String.format("starting coordinates: latitude %2.2f; longitude %2.2f;", coordinates.getLatitude(), coordinates.getLongitude());
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

            TextView titleView = (TextView) rowView.findViewById(R.id.title);
            titleView.setText(rowItem.getName());
            TextView subtitleView = (TextView) rowView.findViewById(R.id.subtitle);

            subtitleView.setText(getCoordinatesStringFromQuest(rowItem));

            return rowView;
        }
    }

    private class QuestMarkerListAdapter extends MarkerListAdapter<Quest> {
        public QuestMarkerListAdapter(GoogleMap map, ArrayList<Quest> quests) {
            super(map, quests);
        }

        @Override
        public MarkerOptions getMarkerOptionsForItem(Quest quest, int itemPosition) {
            Point checkpointCenter = quest.getQuestGraph().getAllCheckpoints().iterator().next().getArea().aproximatingCircle().getCenter();
            LatLng coordinates = new LatLng(checkpointCenter.getLatitude(), checkpointCenter.getLongitude());
            return new MarkerOptions()
                    .draggable(false)
                    .position(coordinates)
                    .title(quest.getName());
        }
    }
}
