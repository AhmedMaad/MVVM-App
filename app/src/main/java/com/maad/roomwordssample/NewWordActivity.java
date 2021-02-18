package com.maad.roomwordssample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        String receivedWord = getIntent().getStringExtra("word");
        if (receivedWord != null) {
            EditText editText = findViewById(R.id.edit_word);
            editText.setText(receivedWord);
            Button button = findViewById(R.id.button_update);
            button.setVisibility(View.VISIBLE);
        }


    }

    public void saveWord(View view) {
        EditText editText = findViewById(R.id.edit_word);
        String writtenText = editText.getText().toString();

        Intent replyIntent = new Intent();

        if (writtenText.isEmpty())
            setResult(RESULT_CANCELED, replyIntent);
        else {
            replyIntent.putExtra("data", writtenText);
            setResult(RESULT_OK, replyIntent);
        }
        finish();

    }


    public void updateWord(View view) {
        EditText editText = findViewById(R.id.edit_word);
        String writtenText = editText.getText().toString();

        Intent i = new Intent();
        i.putExtra("data", writtenText);
        i.putExtra("idData", getIntent().getIntExtra("id", -1));
        setResult(RESULT_OK, i);
        finish();
    }
}