package com.chromsicle.mediaplayersampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find the buttons from the layout activity_main.xml
        Button playButton = findViewById(R.id.play_button);
        Button randomButton = findViewById(R.id.random_button);
        Button pauseButton = findViewById(R.id.pause_button);
        Button stopButton = findViewById(R.id.stop_button);

        //create the media player by using the song file in the raw directory
        mediaPlayer = MediaPlayer.create(this, R.raw.tranquility2);

        //set the onClickListeners to do stuff when the buttons are pressed

        //start the song when the play button is pressed
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
            }
        });

        //seek to a random position in the song
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int randomTime = (int) (Math.random() * (mediaPlayer.getDuration() / (3 / 4)));
                mediaPlayer.seekTo(randomTime);
            }
        });

        //pause the song
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                //would probably be helpful to make this so pressing it a second time resumes play
            }
        });

        //stop the song
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.reset();
            }
        });
    }
}
