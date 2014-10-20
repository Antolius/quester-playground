package com.example.josip.providers;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.CheckpointArea;
import com.example.josip.model.Circle;
import com.example.josip.model.Point;
import com.example.josip.model.Quest;
import com.example.josip.model.QuestGraph;
import com.example.josip.model.enums.CircleArea;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestSerializer {

    public String serialize(Quest quest) throws JSONException {

        JSONObject questJsonObject = new JSONObject();
        questJsonObject.put("id", quest.getId());
        questJsonObject.put("name", quest.getName());
        questJsonObject.put("graph", getCheckpointJsonArray(quest));

        return questJsonObject.toString();
    }

    private JSONArray getCheckpointJsonArray(Quest quest) throws JSONException {
        JSONArray checkpointObjectsArray = new JSONArray();
        QuestGraph questGraph = quest.getQuestGraph();
        for (Checkpoint checkpoint : questGraph.getAllCheckpoints()) {
            checkpointObjectsArray.put(getCheckpointJsonObject(questGraph, checkpoint));
        }
        return checkpointObjectsArray;
    }

    private JSONObject getCheckpointJsonObject(QuestGraph questGraph, Checkpoint checkpoint) throws JSONException {
        JSONObject jsonCheckpointObject = new JSONObject();
        jsonCheckpointObject.put("id", checkpoint.getId());
        jsonCheckpointObject.put("name", checkpoint.getName());
        jsonCheckpointObject.put("area", getAreaAsJson(checkpoint));
        //jsonCheckpointObject.put("scripts", checkpoint.getEventsScript().getAbsolutePath());
        //jsonCheckpointObject.put("html", checkpoint.getViewHtml().getAbsolutePath());
        jsonCheckpointObject.put("children", getChildren(questGraph, checkpoint));
        return jsonCheckpointObject;
    }

    private JSONArray getChildren(QuestGraph questGraph, Checkpoint checkpoint) {

        JSONArray jsonArray = new JSONArray();
        for(Checkpoint child : questGraph.getChildren(checkpoint)){
            jsonArray.put(child.getId());
        }

        return jsonArray;
    }

    private JSONObject getAreaAsJson(Checkpoint checkpoint) throws JSONException {
        JSONObject jsonAreaObject = new JSONObject();
        jsonAreaObject.put("type", checkpoint.getArea().getClass().getName());
        jsonAreaObject.put("data", checkpoint.getArea().getJsonData());
        return jsonAreaObject;
    }


    public Quest deserialize(String json) throws JSONException {

        JSONObject questJsonObject = new JSONObject(json);

        JSONArray array = questJsonObject.getJSONArray("graph");

        Map<Long, Checkpoint> checkpointIdMap = new HashMap<Long, Checkpoint>();

        List<Checkpoint> checkpointList = new ArrayList<Checkpoint>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);

            Checkpoint checkpoint = new Checkpoint();
            checkpoint.setId(jsonObject.getLong("id"));
            checkpoint.setName(jsonObject.getString("name"));
            checkpoint.setArea(getAreaFromJson(jsonObject.getJSONObject("area")));
            checkpointList.add(checkpoint);
            checkpointIdMap.put(checkpoint.getId(), checkpoint);
        }

        QuestGraph graph = new QuestGraph(checkpointList);

        for (int checkpointIndex = 0; checkpointIndex < array.length(); checkpointIndex++) {
            JSONObject jsonObject = array.getJSONObject(checkpointIndex);

            JSONArray childrenArray = jsonObject.getJSONArray("children");
            for (int childIndex = 0; childIndex < childrenArray.length(); childIndex++) {
                graph.addEdge(
                        checkpointIdMap.get(jsonObject.getLong("id")),
                        checkpointIdMap.get(childrenArray.getLong(childIndex)));
            }
        }

        Quest quest = new Quest();
        quest.setId(questJsonObject.getLong("id"));
        quest.setName(questJsonObject.getString("name"));
        quest.setQuestGraph(graph);
        return quest;
    }

    private CheckpointArea getAreaFromJson(JSONObject areaObject) throws JSONException {

        String className = areaObject.getString("type");
        if (className.equals(CircleArea.class.getName())) {
            JSONObject circleAreaObject = new JSONObject(areaObject.getString("data"));
            Circle circle = new Circle();
            circle.setRadius(circleAreaObject.getDouble("radius"));
            circle.setCenter(new Point(
                            circleAreaObject.getDouble("latitude"),
                            circleAreaObject.getDouble("longitude"))
            );
            return new CircleArea(circle);
        }

        return null;
    }
}
