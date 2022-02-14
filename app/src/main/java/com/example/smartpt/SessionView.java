package com.example.smartpt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class SessionView extends AppCompatActivity {

    VideoView v;
    String url="https://im2.ezgif.com/tmp/ezgif-2-42f806e6e4.mp4";
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_view);
//
//        v= (VideoView)findViewById(R.id.video);
////        pd=new ProgressDialog(SessionView.this);
////        pd.setMessage("loading");
////        pd.show();
//        Uri uri = Uri.parse(url);
//        v.setVideoURI(uri);
//        v.start();
//        v.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
////                pd.dismiss();
//            }
//        });
////        v.setVideoURI(uri);
////        v.setMediaController(new MediaController(this));


    }
}