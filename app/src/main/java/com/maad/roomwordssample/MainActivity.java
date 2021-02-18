package com.maad.roomwordssample;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NewWordActivity.class);
                startActivityForResult(i, 99);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(WordViewModel.class);
        viewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                adapter.setWords(words);
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        //get word as string
                        List<Word> words = adapter.getWords();
                        int pos = viewHolder.getAdapterPosition();
                        Word itemToDelete = words.get(pos);
                        viewModel.deleteWord(itemToDelete);
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new WordListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent i = new Intent(MainActivity.this, NewWordActivity.class);
                i.putExtra("word", adapter.getWords().get(position).getWord());
                i.putExtra("id", adapter.getWords().get(position).getId());
                startActivityForResult(i, 1);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_clear) {
            viewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //handle code for insert
        if (resultCode == RESULT_OK && requestCode == 99) {
            String text = (String) data.getExtras().get("data");
            viewModel.insertWord(new Word(text));
            Toast.makeText(this, "Word Saved", Toast.LENGTH_SHORT).show();
        } else if (resultCode == RESULT_CANCELED && requestCode == 99)
            Toast.makeText(this, "Cannot save empty text", Toast.LENGTH_SHORT).show();

        else if (resultCode == RESULT_OK && requestCode == 1) {
            //handle code for update
            //test to update word to the same word and solve it by using merge strategy
            String text = data.getStringExtra("data");
            int id = data.getIntExtra("idData", -2);
            viewModel.updateWord(new Word(id, text));
        }

    }
}