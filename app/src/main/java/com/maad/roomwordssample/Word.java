package com.maad.roomwordssample;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class Word {

    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public Word(@NonNull String word) {
        this.mWord = word;
    }

    @Ignore
    public Word(int id, @NonNull String word){
        this.id = id;
        this.mWord = word;
    }

    public String getWord() {
        return this.mWord;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}