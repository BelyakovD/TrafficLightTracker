package com.belyakov.trafficlighttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
        dbHelper.create_db();
    }

    public DatabaseAdapter open(){
        database = dbHelper.open();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_GREENSTART, DatabaseHelper.COLUMN_GREENEND, DatabaseHelper.COLUMN_REDEND, DatabaseHelper.COLUMN_ROUTEID};
        return  database.query(DatabaseHelper.TABLE, columns, null, null, null, null, null);
    }

    public List<TrafficLight> getTrafficLights(){
        ArrayList<TrafficLight> trafficLights = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if(cursor.moveToFirst()){
            do{
                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                long greenStart = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_GREENSTART));
                long greenEnd = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_GREENEND));
                long redEnd = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_REDEND));
                long routeId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ROUTEID));
                trafficLights.add(new TrafficLight(id, name, greenStart, greenEnd, redEnd, routeId));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  trafficLights;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE);
    }

    public TrafficLight getTrafficLight(long id){
        TrafficLight trafficLight = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            long greenStart = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_GREENSTART));
            long greenEnd = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_GREENEND));
            long redEnd = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_REDEND));
            long routeId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ROUTEID));
            trafficLight = new TrafficLight(id, name, greenStart, greenEnd, redEnd, routeId);
        }
        cursor.close();
        return  trafficLight;
    }

    public long insert(TrafficLight trafficLight){

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, trafficLight.getName());
        cv.put(DatabaseHelper.COLUMN_GREENSTART, trafficLight.getGreenStart());
        cv.put(DatabaseHelper.COLUMN_GREENEND, trafficLight.getGreenEnd());
        cv.put(DatabaseHelper.COLUMN_REDEND, trafficLight.getRedEnd());
        cv.put(DatabaseHelper.COLUMN_ROUTEID, trafficLight.getRouteId());
        return database.insert(DatabaseHelper.TABLE, null, cv);
    }

    public long delete(long trafficLightId){

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(trafficLightId)};
        return database.delete(DatabaseHelper.TABLE, whereClause, whereArgs);
    }

    public long update(TrafficLight trafficLight){

        String whereClause = DatabaseHelper.COLUMN_ID + "=" + String.valueOf(trafficLight.getId());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, trafficLight.getName());
        cv.put(DatabaseHelper.COLUMN_GREENSTART, trafficLight.getGreenStart());
        cv.put(DatabaseHelper.COLUMN_GREENEND, trafficLight.getGreenEnd());
        cv.put(DatabaseHelper.COLUMN_REDEND, trafficLight.getRedEnd());
        cv.put(DatabaseHelper.COLUMN_ROUTEID, trafficLight.getRouteId());
        return database.update(DatabaseHelper.TABLE, cv, whereClause, null);
    }
}
