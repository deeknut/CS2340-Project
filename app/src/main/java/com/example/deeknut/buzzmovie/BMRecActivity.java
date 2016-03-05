package com.example.deeknut.buzzmovie

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.deeknut.buzzmovie.models.Movie;
import com.example.deeknut.buzzmovie.models.Recommendation;


public class BMRecActivity extends AppCompatActivity {

    Recommendation recommendation;
    RatingBar rating;
    TextView title;
    TextView desc;

    //HashMap<String, Recommendation> recommendations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec);

        Movie movie = (Movie) getIntent().getSerializableExtra("DAT_MOVIE_DOE");
        recommendation = BMRecommendationsActivity.prevRecs.get(movie.getTitle());
        title = (TextView)findViewById(R.id.title);
        desc = (TextView)findViewById(R.id.desc);
        rating = (RatingBar)findViewById(R.id.ratingBar);
        title.setText(movie.getTitle());
        if(recommendation != null) {
            Log.d("RecommendationActivity", recommendation.toString());
            desc.setText(recommendation.getDescription());
            rating.setRating((float) recommendation.getRating());
            rating.setOnRatingBarChangeListener(this);
        }



        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(e -> goBackToSearch());
    }

    private void goBackToSearch() {
        //Intent searchScreenIntent = new Intent(this, BMSearchActivity.class);
        //startActivity(searchScreenIntent);
        Recommendation rec = new Recommendation(title.getText().toString(),BMLoginActivity.currentUser,
                desc.getText().toString(), rating.getRating());
        BMRecommendationsActivity.prevRecs.put(rec.getTitle(), rec);
        finish();
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if(fromUser) {
            Log.d("tag", "" + rating);
            recommendation.updateRating(rating);
            BMRecommendationsActivity.prevRecs.put(recommendation.getTitle(), recommendation);
            this.rating.setRating((float) recommendation.getRating());
        }
    }
}
