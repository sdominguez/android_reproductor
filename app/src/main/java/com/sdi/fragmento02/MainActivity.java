package com.sdi.fragmento02;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private ImageButton playPauseButton;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playPauseButton = findViewById(R.id.btnPlayPause);
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    pauseAudio();
                } else {
                    playAudio();
                }
            }
        });

        // URL del audio público en Internet
        String audioUrl = "https://files.freemusicarchive.org/storage-freemusicarchive-org/music/ccCommunity/Jorge_Mario_Zuleta/Fauxette/Jorge_Mario_Zuleta_-_12_-_La_noche_fue_roja.mp3";
        //
        https://files.freemusicarchive.org/storage-freemusicarchive-org/music/no_curator/Comme_Jospin/Faire_de_la_musique/Comme_Jospin_-_01_-_Mourir_sur_ses_pieds.mp3
        // Inicializar MediaPlayer
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepareAsync(); // Prepara el MediaPlayer de forma asíncrona
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // El audio está listo para reproducirse
                    playAudio();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playAudio() {
        if (mediaPlayer != null && !isPlaying) {
            mediaPlayer.start(); // Iniciar la reproducción del audio
            isPlaying = true;
            playPauseButton.setImageResource(R.drawable.ic_pausa); // Cambiar la imagen del botón a pausa
        }
    }

    private void pauseAudio() {
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer.pause(); // Pausar la reproducción del audio
            isPlaying = false;
            playPauseButton.setImageResource(R.drawable.ic_play); // Cambiar la imagen del botón a reproducir
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