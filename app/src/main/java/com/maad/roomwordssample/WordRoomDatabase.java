package com.maad.roomwordssample;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Word.class}, version = 2, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    private static WordRoomDatabase instance;

    //To handle executing  DB operations in a background then we wll use the ExecutorService
    public static final ExecutorService executorService =
            Executors.newFixedThreadPool(4);

    private static RoomDatabase.Callback callback =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    executorService.execute(() -> createInitialData(instance));
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

    // If we have no words, then create the initial list of words
    private static void createInitialData(WordRoomDatabase db) {
        String[] words = {"dolphin", "crocodile", "cobra"};
        if (db.wordDao().getAnyWord().size() < 1) {
            for (int i = 0; i <= words.length - 1; i++) {
                Word word = new Word(words[i]);
                db.wordDao().insert(word);
            }
        }
    }

}
