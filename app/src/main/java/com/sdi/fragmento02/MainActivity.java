package com.sdi.fragmento02;

import static com.sdi.fragmento02.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    //private String URLBase = "http://10.0.2.2:8084";
    private String URLBase = "http://192.168.1.103:8084/static";
    private MediaPlayer mediaPlayer;
    private ImageButton playPauseButton;
    private boolean isPlaying = false;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playPauseButton = findViewById(R.id.btnPlayPause);
        seekBar = findViewById(R.id.seekBar);
        playPauseButton.setOnClickListener(v-> {
                if (isPlaying) {
                    pauseAudio();
                } else {
                    playAudio();
                }
        });

        String imageUrl = URLBase+"/tiesto/Tiestokaleidoscope.jpg";
        String audioUrl = URLBase+"/music/tiesto_short.mp3";

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(v-> {
                seekBar.setMax(mediaPlayer.getDuration());
                playAudio();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void playAudio() {
        if (mediaPlayer != null && !isPlaying) {
            mediaPlayer.start();
            isPlaying = true;
            playPauseButton.setImageResource(R.drawable.ic_pausa);
            updateSeekBar();
        }
    }

    private void pauseAudio() {
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer.pause();
            isPlaying = false;
            playPauseButton.setImageResource(R.drawable.ic_play);
        }
    }

    private void updateSeekBar() {
        if (mediaPlayer != null && isPlaying) {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    updateSeekBar();
                }
            };
            seekBar.postDelayed(runnable, 1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}