package com.gytxtx.sounder.saibamidori;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int[] AUDIO_RES_IDS = {
            R.raw.gosyuzinsama,
            R.raw.gousyuzinsama
    };

    private static final List<MediaPlayer> ACTIVE_PLAYERS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playRandomAudio();
        finish();
    }

    private void playRandomAudio() {
        int randomIndex = new Random().nextInt(AUDIO_RES_IDS.length);
        MediaPlayer player = MediaPlayer.create(getApplicationContext(), AUDIO_RES_IDS[randomIndex]);
        if (player == null) {
            return;
        }

        ACTIVE_PLAYERS.add(player);
        player.setOnCompletionListener(completedPlayer -> {
            ACTIVE_PLAYERS.remove(completedPlayer);
            completedPlayer.release();
        });
        player.setOnErrorListener((errorPlayer, what, extra) -> {
            ACTIVE_PLAYERS.remove(errorPlayer);
            errorPlayer.release();
            return true;
        });
        player.start();
    }
}
