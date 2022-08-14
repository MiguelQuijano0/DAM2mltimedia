package com.example.multimedia2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    //varibles mediaplayer
    private Button buttonPlay;
    private Button buttonPause;
    private Button buttonStop;
    private MediaPlayer mediaPlayer;

    //variables video player
    private Button buttonvPlay;
    private Button buttonvPause;
    private Button buttonvStop;
    VideoView simpleVideoView;
    MediaController mediaControls;

    Button btnCamara;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find id in XML mediaplayer
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonPause = findViewById(R.id.buttonPause);
        buttonStop = findViewById(R.id.buttonStop);
        mediaPlayer = MediaPlayer.create(this, R.raw.audio1);

        //find id in XML Videoplayer
        buttonvPlay = findViewById(R.id.buttonvPlay);
        buttonvPause = findViewById(R.id.buttonvPause);
        buttonvStop = findViewById(R.id.buttonvStop);
        simpleVideoView = (VideoView) findViewById(R.id.simpleVideoView);

        btnCamara = findViewById(R.id.btnCamara);
        imgView = findViewById(R.id.imageView);

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camaralauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            }
        });

        //methods Videoplayer
        if (mediaControls == null) {
            mediaControls = new MediaController(MainActivity.this);
            mediaControls.setAnchorView(simpleVideoView);
        }
        simpleVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.groot));

        //buttons videoplayer
        buttonvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleVideoView.start();
                Toast.makeText(MainActivity.this, "Reproduciendo", Toast.LENGTH_SHORT).show();
            }
        });

        buttonvPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleVideoView.pause();
                Toast.makeText(MainActivity.this, "Pausando", Toast.LENGTH_SHORT).show();
            }
        });
        buttonvStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleVideoView.stopPlayback();
                Toast.makeText(MainActivity.this, "deteniendo", Toast.LENGTH_SHORT).show();
            }

        });
        //toaste videoplayer
        simpleVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(getApplicationContext(), "gracias por ver...!!!", Toast.LENGTH_LONG).show();
            }
        });
        simpleVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                Toast.makeText(getApplicationContext(), "Ooops ocurrio un problema la reproducir el video", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        //end of videoplayer
        //--------------------------------------------------------------------------------------------

        //methods mediaplayer
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                Toast.makeText(MainActivity.this, "Reproduciendo", Toast.LENGTH_SHORT).show();
            }
        });
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                Toast.makeText(MainActivity.this, "Pausando", Toast.LENGTH_SHORT).show();
            }
        });
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.audio1);
                Toast.makeText(MainActivity.this, "deteniendo", Toast.LENGTH_SHORT).show();
            }
        });

    }

    ActivityResultLauncher<Intent> camaralauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode()==RESULT_OK){
                Bundle extras = result.getData().getExtras();
                Bitmap imgBitmap = (Bitmap) extras.get("data");
                imgView.setImageBitmap(imgBitmap);
            }
        }
    });
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            imgView.setImageBitmap(imgBitmap);
        }
    }
}