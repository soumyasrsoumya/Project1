package com.example.integratyaudivideo;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener  {
   // Ui components
    private VideoView myVideoView;
    private Button btnPlayVideo;
    private MediaController mediaController;
    private Button btnPlayMusic;
    private Button btnPauseMusic;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBarVolume;
    private AudioManager audioManager;
    private SeekBar moveBackAndForthSeekBar;
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myVideoView = findViewById(R.id.myVideoView);
        btnPlayVideo = findViewById(R.id.btnPlayVideo);

        btnPlayMusic = findViewById(R.id.btnPlayMusic);
        btnPauseMusic = findViewById(R.id.btnPauseMusic);
        seekBarVolume = findViewById(R.id.seekBarVolume);
        moveBackAndForthSeekBar = findViewById(R.id.seekBarMove);

        btnPlayVideo.setOnClickListener(MainActivity.this);
        btnPlayMusic.setOnClickListener(MainActivity.this);
        btnPauseMusic.setOnClickListener(MainActivity.this);


        mediaController = new MediaController(MainActivity.this);

        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        audioManager = ( AudioManager ) getSystemService(AUDIO_SERVICE);

        int maximumVolumeOfUserDevice = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolumeOfUserDevice = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        seekBarVolume.setMax(maximumVolumeOfUserDevice);
        seekBarVolume.setProgress(currentVolumeOfUserDevice);

        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser){

                   // Toast.makeText(MainActivity.this, Integer.toString(progress),Toast.LENGTH_SHORT).show();
                    mediaPlayer.seekTo(progress);

            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });

        moveBackAndForthSeekBar.setOnSeekBarChangeListener(this);
        moveBackAndForthSeekBar.setMax(mediaPlayer.getDuration());

        mediaPlayer.setOnCompletionListener(this);

    }

    @Override
    public void onClick(View buttonView) {

        switch (buttonView.getId()) {

            case R.id.btnPlayVideo:

                Uri videoUri = Uri.parse("android.resource://" + getPackageName()
                        + "/" + R.raw.videothree);
                myVideoView.setVideoURI(videoUri);

                myVideoView.setMediaController(mediaController);
                mediaController.setAnchorView(myVideoView);

                myVideoView.start();

                break;

            case R.id.btnPlayMusic:

                mediaPlayer.start();
                timer= new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        moveBackAndForthSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                }, 0,1000);

            break;



            case  R.id.btnPauseMusic:

                mediaPlayer.pause();
                timer.cancel();

            break;


        }


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    if (fromUser){

        Toast.makeText(this, Integer.toString(progress), Toast.LENGTH_SHORT).show();
    }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        timer.cancel();

       Toast.makeText(this,"Music is ended", Toast.LENGTH_LONG).show();

    }
}
