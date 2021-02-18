package com.maad.roomwordssample;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Update;

import java.util.List;

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
        new InsertAsyncTask().execute(word);
    }

    class InsertAsyncTask extends AsyncTask<Word, Void, Void> {

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insert(words[0]);
            return null;
        }
    }

    public void deleteAllWords() {
        new DeleteAllWords().execute();
    }

    class DeleteAllWords extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAll();
            return null;
        }
    }

    public void deleteWord(Word word) {
        new DeleteWord().execute(word);
    }

    class DeleteWord extends AsyncTask<Word, Void, Void> {

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.deleteWord(words[0]);
            return null;
        }
    }

    public void updateWord(Word word){
        new UpdateWord().execute(word);
    }

    class UpdateWord extends AsyncTask<Word, Void, Void>{
        @Override
        protected Void doInBackground(Word... words) {
            wordDao.updateWord(words[0]);
            return null;
        }
    }

}
