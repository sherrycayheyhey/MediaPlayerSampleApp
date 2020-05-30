package com.chromsicle.mediaplayersampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//cool state machine diagram for the MediaPlayer https://developer.android.com/reference/android/media/MediaPlayer.html

public class MainActivity extends AppCompatActivity {

    //handles playback of all the sound files
    private MediaPlayer mediaPlayer;

    //this listener is triggered when the MediaPlayer completes playing the audio file
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find the buttons from the layout activity_main.xml
        Button playButton = findViewById(R.id.play_button);
        Button randomButton = findViewById(R.id.random_button);
        Button pauseButton = findViewById(R.id.pause_button);
        Button stopButton = findViewById(R.id.stop_button);

        // release any previous playing audio before making a new MediaPlayer
        // this is added because a user might be quickly playing the sounds and triggering new
        // ones before the previous one actually completed
        releaseMediaPlayer();

        //create the media player by using the song file in the raw directory
        mediaPlayer = MediaPlayer.create(this, R.raw.number_one);


        //set the onClickListeners to do stuff when the buttons are pressed

        //start the song when the play button is pressed
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                //when the song is done, show a toast
                //this uses an asynchronous callback to notify us when the MediaPlayer is done playing the song
                //in the meantime, we can go about our merry way while waiting to get called back and
                // do stuff like respond to other user button clicks such as the ones below like pause or seek
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Toast.makeText(getApplicationContext(), "Song done!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //you don't have to call prepare() here because it's already called automatically when the player was created

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
                //once the mediaPlayer is stopped it can't be started again so it would have to be
                //prepared again to play it again
            }
        });
    }

    /**
     * onStop method that will release the media player resources because they activity is navigated away from
     */
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Helper method for Cleaning up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
        }
    }
}
