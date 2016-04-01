package com.example.apple.tic_tac_toe;


import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "HighscoreData.db";
    private static final String TABLE_NAME = "HighscoreData";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_SCORE = "Score";
    private static final String COLUMN_ID = "id";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_NAME + " TEXT ," +
                COLUMN_SCORE + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void addRow(Highscores highscores)
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + ";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        boolean flag = false;
        while(!c.isAfterLast())
        {
            if(c.getString(c.getColumnIndex(COLUMN_NAME)).equals(highscores.get_name()))
            {//if name is already present just increase its score by 1
                int score = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_SCORE)));
                String query2 = "UPDATE " + TABLE_NAME + " SET " + COLUMN_SCORE + "=" + "\"" + (score+highscores.get_score()) + "\"" + " WHERE " + COLUMN_NAME + "=\"" + highscores.get_name() + "\";";
                db.execSQL(query2);
                flag = true;
                break;
            }
            c.moveToNext();
        }
        if(flag == false)
        db.execSQL("INSERT INTO " + TABLE_NAME + "(" + COLUMN_NAME + ", " + COLUMN_SCORE + ")" + " VALUES(" + "\"" + highscores.get_name() + "\"" + ", " + "\"" + highscores.get_score() + "\"" + ");");
    }

    public int getScore(String name)
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + "=" + "\"" + name + "\"" + ";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int score = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_SCORE)));
        return score;
    }

    public String[] names()
    {
        SQLiteDatabase db = getWritableDatabase();

        int numRows = (int)DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        String dbString[] = new String[numRows];

        int i=0;
        if(db.isOpen())
        {
            String query = "SELECT * FROM " + TABLE_NAME + ";";
            Cursor c = db.rawQuery(query, null);
            c.moveToFirst();
            while (!c.isAfterLast())
            {
                if (c.getString(c.getColumnIndex(COLUMN_NAME)) != null)
                {
                    dbString[i] = c.getString(c.getColumnIndex(COLUMN_NAME));
                    c.moveToNext();
                    i++;
                }
            }
        }
        return dbString;
    }

    public String[] databaseToString()
    {

        SQLiteDatabase db = getWritableDatabase();

        int numRows = (int)DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        String dbString[] = new String[numRows];
        for(int i=0;i<numRows;i++)
        {
            dbString[i]="";
        }
        int i=0;
        if(db.isOpen())
        {
            String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_SCORE + " DESC;";
            Cursor c = db.rawQuery(query, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                if (c.getString(c.getColumnIndex(COLUMN_NAME)) != null) {
                    dbString[i] += c.getString(c.getColumnIndex(COLUMN_NAME)) + "  ";
                    dbString[i] += c.getString(c.getColumnIndex(COLUMN_SCORE));
                    c.moveToNext();
                    i++;
                }
            }
        }
        return dbString;
    }

    public void reset()
    {
        String query = "DELETE FROM " + TABLE_NAME + ";";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
    }
}
