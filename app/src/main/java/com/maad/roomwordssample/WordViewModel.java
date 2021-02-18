package com.maad.roomwordssample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository repository;

    public WordViewModel(@NonNull Application application) {
        super(application);
        repository = new WordRepository(application.getApplicationContext());
    }


    public void insertWord(Word word) {
        repository.insertWord(word);
    }

    public LiveData<List<Word>> getAllWords() {
        return repository.getWords();
    }

    public void deleteAll() {
        repository.deleteAllWords();
    }

    public void deleteWord(Word word) {
        repository.deleteWord(word);
    }

    public void updateWord(Word word){
        repository.updateWord(word);
    }

}
