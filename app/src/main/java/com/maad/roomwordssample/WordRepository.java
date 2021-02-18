package com.maad.roomwordssample;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Update;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WordRepository {

    private WordDao wordDao;

    //gets a handle to the database and initializes the member variables.
    public WordRepository(Context context) {
        wordDao = WordRoomDatabase.getInstance(context).wordDao();
    }

    public LiveData<List<Word>> getWords() {
        LiveData<List<Word>> words = wordDao.getAllWords();
        return words;
    }

    public void insertWord(Word word) {
        WordRoomDatabase.executorService.execute(() -> wordDao.insert(word));
    }

    public void deleteAllWords() {
        WordRoomDatabase.executorService.execute(() -> wordDao.deleteAll());
    }

    public void deleteWord(Word word) {
        WordRoomDatabase.executorService.execute(() -> wordDao.deleteWord(word));
    }

    public void updateWord(Word word) {
        WordRoomDatabase.executorService.execute(() -> wordDao.updateWord(word));
    }

}