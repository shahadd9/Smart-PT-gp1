package com.example.smartpt;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.firestore.core.View;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Locale;

public class Audio extends AppCompatActivity {
    private TextToSpeech mTTS;
    private TextView textEnt;
    private Button mButtonSpeak;

    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        VideoView vid =(VideoView) findViewById(R.id.videoView);

        vid.setVideoURI(Uri.parse("https://drive.google.com/file/d/1Jw7foqn7JRVSf-5CV0Nio2FUSTi7ZAij/view"));
        vid.setMediaController(new MediaController(this));
        vid.requestFocus();
        vid.start();
        /*
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource("https://vimeo.com/538582670");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();*/

        mButtonSpeak =findViewById(R.id.buttonspeech);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        mButtonSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        textEnt=findViewById(R.id.textspeech);
        mButtonSpeak.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                speak();
            }
        });




    }


    private void speak() {
        String text = textEnt.getText().toString();
        float pitch = 5;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = 0.9f;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }


    public void play(android.view.View view) {
       /* if (player == null) {
            player = MediaPlayer.create(this, R.raw.test);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }*/

        String audioUrl = "https://od.lk/s/NzVfMzI4MzI4MDhf/test.mp3";

        // initializing media player
        player = new MediaPlayer();

        // below line is use to set the audio
        // stream type for our media player.
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // below line is use to set our
        // url to our media player.
        try {
            player.setDataSource(audioUrl);
            // below line is use to prepare
            // and start our media player.
            player.prepare();
            player.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // below line is use to display a toast message.
        Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();


    }

    public void pause(android.view.View view) {
        if (player != null) {
            player.pause();
        }
    }

    public void stop(android.view.View view) {
        stopPlayer();
    }

    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }


}