package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private MediaPlayer.OnCompletionListener mCompletetionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> family_name = new ArrayList<Word>();
        family_name.add(new Word("father", "әpә", R.drawable.family_father,R.raw.family_father));
        family_name.add(new Word("mother", "әṭa", R.drawable.family_mother,R.raw.family_mother));
        family_name.add(new Word("son", "angsi", R.drawable.family_son,R.raw.family_son));
        family_name.add(new Word("daughter", "tune", R.drawable.family_daughter,R.raw.family_daughter));
        family_name.add(new Word("older brother", "taachi", R.drawable.family_older_brother,R.raw.family_older_brother));
        family_name.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother,R.raw.family_younger_brother));
        family_name.add(new Word("older sister", "teṭe", R.drawable.family_older_sister,R.raw.family_older_sister));
        family_name.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister,R.raw.family_younger_sister));
        family_name.add(new Word("grandfather", "paapa", R.drawable.family_grandfather,R.raw.family_grandfather));
        family_name.add(new Word("grandmother", "ama",R.drawable.family_grandmother,R.raw.family_grandmother));

        WordAdapter adapter = new WordAdapter(this, family_name,R.color.category_family);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                Word word = family_name.get(position);
                int result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getmAudioId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mCompletetionListener);
                }
                audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
            }
        });
    }
    private void releaseMediaPlayer(){
        if (mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            (new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT){
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                        releaseMediaPlayer();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                        mediaPlayer.start();
                    }
                }
            });

    @Override
    protected void onStop() {
        super.onStop();
       releaseMediaPlayer();
    }
}
