package com.maad.roomwordssample;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Word.class}, version = 2, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    private static WordRoomDatabase instance;

    private static RoomDatabase.Callback callback =
            new RoomDatabase.Callback(){
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(instance).execute();
                }
            };

    public static WordRoomDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (WordRoomDatabase.class) {
                if (instance == null)
                    instance = Room
                            .databaseBuilder(context, WordRoomDatabase.class, "MyDB")
                            // Wipes and rebuilds instead of migrating
                            .fallbackToDestructiveMigration()
                            //Adding data every time DB is opened
                            .addCallback(callback)
                            .build();
            }
        }
        return instance;
    }


    public abstract WordDao wordDao();


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;
        String[] words = {"dolphin", "crocodile", "cobra"};

        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // If we have no words, then create the initial list of words
            if (mDao.getAnyWord().size() < 1) {
                for (int i = 0; i <= words.length - 1; i++) {
                    Word word = new Word(words[i]);
                    mDao.insert(word);
                }
            }
            return null;
        }
    }

}
