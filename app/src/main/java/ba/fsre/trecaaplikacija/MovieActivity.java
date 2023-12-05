package ba.fsre.trecaaplikacija;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import ba.fsre.trecaaplikacija.adapter.MovieAdapter;
import ba.fsre.trecaaplikacija.fragments.AddMovieDialogFragment;
import ba.fsre.trecaaplikacija.model.Movie;


public class MovieActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        FloatingActionButton openMovieDialogBtn = findViewById(R.id.addMovieBtn);
        openMovieDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMovieDialogFragment fragment = new AddMovieDialogFragment();
                fragment.show(getSupportFragmentManager(),"movieDialogFragment");
            }
        });

        this.recyclerView = findViewById(R.id.movieListView);
        this.recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );
        FirebaseRecyclerOptions<Movie> options = new FirebaseRecyclerOptions.Builder<Movie>().setQuery(
                this.mDatabase.getReference("movies"),
                Movie.class
        ).build();

        this.movieAdapter = new MovieAdapter(options);
        this.recyclerView.setAdapter(this.movieAdapter);

    }

    @Override
    protected void onStart(){
        super.onStart();
        this.movieAdapter.startListening();
    }


    @Override
    protected void onStop(){
        super.onStop();
        this.movieAdapter.stopListening();
    }

}