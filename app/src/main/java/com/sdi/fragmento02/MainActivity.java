package com.sdi.fragmento02;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

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

        // URL del audio
        String audioUrl = "https://files.freemusicarchive.org/storage-freemusicarchive-org/music/ccCommunity/Jorge_Mario_Zuleta/Fauxette/Jorge_Mario_Zuleta_-_12_-_La_noche_fue_roja.mp3";

        // Inicializar MediaPlayer
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepareAsync(); // Prepara el MediaPlayer de forma asíncrona
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
            mediaPlayer.release(); // Liberar recursos del MediaPlayer cuando la actividad se destruye
        }
    }
}