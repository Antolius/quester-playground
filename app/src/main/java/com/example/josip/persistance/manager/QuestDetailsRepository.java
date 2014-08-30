package com.example.josip.persistance.manager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.josip.model.Point;
import com.example.josip.model.QPoint;
import com.example.josip.persistance.QQuestDetails;
import com.example.josip.persistance.QuestDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by tdubravcevic on 13.8.2014!
 */
public class QuestDetailsRepository extends SQLiteRepository {

    private static final Logger logger = LoggerFactory.getLogger(QuestDetailsRepository.class);

    public QuestDetailsRepository(SQLiteDatabase database) {
        super(database);
    }

    public QuestDetails save(QuestDetails details) {

        ContentValues values = new ContentValues();
        values.put("id", details.getId());
        values.put("name", details.getName());
        values.put("latitude", details.getPoint().getLatitude());
        values.put("longitude", details.getPoint().getLongitude());
        values.put("description", details.getDescription());

        long rowId = database.insertWithOnConflict("quest_details", null, values, SQLiteDatabase.CONFLICT_IGNORE);
        details.setId(rowId);
        values.remove("id");
        database.update("quest_details", values, " id = " + details.getId(), null);

        return details;
    }

    public List<QuestDetails> getAllDetailsSortedByDistance(Point point) {

        QQuestDetails qDetails = new QQuestDetails("quest_details");
        QPoint qpoint = new QPoint("quest_details");

        return query.from(qDetails)
                .orderBy(
                        qpoint.latitude.subtract(point.getLatitude())
                                .multiply(qpoint.latitude.subtract(point.getLatitude()))
                                .add(qpoint.longitude.subtract(point.getLongitude())
                                        .multiply(qpoint.longitude.subtract(point.getLongitude())))
                                .asc()
                ).list(QQuestDetails.create(qDetails.id, qDetails.name, qpoint.latitude, qpoint.longitude, qDetails.description));
    }
}
