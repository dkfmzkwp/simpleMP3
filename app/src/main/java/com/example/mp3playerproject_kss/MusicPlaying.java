package com.example.mp3playerproject_kss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTabHost;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MusicPlaying extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private Button btnPlay, btnStop;
    private ImageButton btnNext, btnPrev;
    private TextView tvDuration, tvCurrentDuration;
    private String path;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private ArrayList<MusicData> musicDataList = new ArrayList<MusicData>();
    private FragmentTabHost tabHost;
    private TabHost.TabSpec tabSpecOne;
    private TabHost.TabSpec tabSpecTwo;
    private MusicPlaying musicPlaying;

    private int position;
    private SeekBar seekBar1;
    private int flagChangeButton = 0;
    private FrameLayout mainLayout;
    private TotalListFragment totalListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_playing2);

        totalListFragment = new TotalListFragment();

        findViewByIdFnc();

        btnPlay.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);

        requestPermissionsFnc();

        //외장메모리 절대경로 설정
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/";

        //외장메모리 안의 모든 파일을 가져와서 .mp3 파일만 골라낸다
        loadMP3FileFromSDCard();
        MusicAdapter adapter = new MusicAdapter(getApplicationContext());
        adapter.setArrayList(musicDataList);

        //어뎁터 생성
//        final MusicAdapter musicAdapter = new MusicAdapter(getApplicationContext());
//        musicAdapter.setArrayList(musicDataList);

//        // 리스트뷰에 보여줄 화면 설정
//        listViewSelectdFnc(musicAdapter);
//
//        //리스트뷰에서 선택된 파일 이벤트
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                position = i;
//                //select = musicDataList.get(i);
//
//            }
//        });

        btnPlay.setEnabled(true);
        btnStop.setEnabled(false);
        imageView.setImageResource(R.drawable.music);

        startMediaPlayer();
        stopMediaPlayer();

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        tabHost = (FragmentTabHost) findViewById(R.id.tabhost);

        //tabHost 에 fragmentManager 를 tabcontent 가 아닌 다른 framelayout 을 지정
        tabHost.setup(getApplicationContext(),getSupportFragmentManager(),R.id.tabRealContent);

        tabSpecOne = tabHost.newTabSpec("ONE").setIndicator("전체");
//        tabSpecTwo = tabHost.newTabSpec("TWO").setIndicator("MY");

        tabHost.addTab(tabSpecOne, TotalListFragment.class, null);
//        tabHost.addTab(tabSpecTwo, ListFragment.class, null);

        tabHost.setCurrentTab(0);
    }//onCreate

    public ArrayList<MusicData> getMusicDataList() {
        return musicDataList;
    }

    public void setMusicDataList(ArrayList<MusicData> musicDataList) {
        this.musicDataList = musicDataList;
    }

    public void loadMP3FileFromSDCard() {
        // 파일의 메타데이터 구하기위한 MediaMetadataRetriever 인스턴스 생성
        MediaMetadataRetriever media = new MediaMetadataRetriever();

        // 확장자 구하기 위한 MimeTypeMap 인스턴스 생성
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        File[] fileList = new File(path).listFiles();

        for (File file : fileList) {

            // 확장자 가져오기
            String extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());

            // 파일 이름
            String fileName = file.getName();

            // 확장자가 .mp3 인 파일들만 걸러냄
            if (extension.equals("mp3")) {
                // 데이터 소스 지정
                media.setDataSource(path + fileName);
                byte[] data = media.getEmbeddedPicture();
                Bitmap bitmap = null;
                if (data != null) {
                    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                } else {
                    ImageView defaultImg = new ImageView(this);
                    defaultImg.setImageResource(R.drawable.music);
                    BitmapDrawable drawable = (BitmapDrawable) defaultImg.getDrawable();
                    bitmap = drawable.getBitmap();
                }

                String title = media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                String artist = media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

                MusicData musicData = new MusicData(title, artist, bitmap, fileName);
                musicDataList.add(musicData);

            }
        }
    }

    //미디어 플레이어 정지
    private void stopMediaPlayer() {
        mediaPlayer.stop();
        mediaPlayer.reset();

        btnPlay.setText("재생");
        btnStop.setEnabled(false);
        flagChangeButton = 0;

    }

    //미디어 플레이어 플레이
    private void startMediaPlayer() {
        MusicData musicData = musicDataList.get(position);
        mediaPlayer.start();

        imageView.setImageBitmap(musicData.getAlbumArt());
        btnStop.setEnabled(true);
        flagChangeButton = 1;
    }

//    private void listViewSelectdFnc(MusicAdapter musicAdapter) {
//        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        listView.setAdapter(musicAdapter);
//        listView.setItemChecked(0, true);
//    }

    //권한 요청
    public void requestPermissionsFnc() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
    }

    private void findViewByIdFnc() {
        imageView = findViewById(R.id.imageView);
        seekBar1 = findViewById(R.id.seekBar1);
        btnPlay = findViewById(R.id.btnPlay);
        btnStop = findViewById(R.id.btnStop);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);
        mainLayout = findViewById(R.id.mainLayout);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlay:
                try {
                    if (flagChangeButton == 0) {
                        mediaPlayer.setDataSource(path + musicDataList.get(position).getFileName());

                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                while (mediaPlayer.isPlaying() || flagChangeButton == 0 || flagChangeButton == 2) {
                                    if (flagChangeButton != 0) {
                                        seekBar1.setMax(mediaPlayer.getDuration());
                                        seekBar1.setProgress(mediaPlayer.getCurrentPosition());
                                    }
                                    SystemClock.sleep(100);
                                }
                            }
                        };
                        thread.start();

                        mediaPlayer.prepare();

                        startMediaPlayer();
                        btnPlay.setText("일시정지");

                        flagChangeButton = 1;

                    } else if (flagChangeButton == 1) {
                        mediaPlayer.pause();
                        btnPlay.setText("재생");
                        flagChangeButton = 2;
                    } else if (flagChangeButton == 2) {

                        startMediaPlayer();
                        btnPlay.setText("일시정지");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                btnStop.setEnabled(true);

                break;

            case R.id.btnStop:

                stopMediaPlayer();
                seekBar1.setProgress(0);
                break;

            case R.id.btnPrev:
                try {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    flagChangeButton = 0;

                    position--;

                    if (position < 0) {
                        position = musicDataList.size() - 1;
                    }
                    mediaPlayer.setDataSource(path + musicDataList.get(position).getFileName());
                    mediaPlayer.prepare();
                    startMediaPlayer();
                } catch (IOException e) {
                    Log.d("btnPrev", e.getMessage());
                }

                break;
            case R.id.btnNext:
                try {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    flagChangeButton = 0;

                    position++;
                    if (position == musicDataList.size()) {
                        position = 0;
                    }
                    mediaPlayer.setDataSource(path + musicDataList.get(position).getFileName());
                    mediaPlayer.prepare();
                    startMediaPlayer();
                } catch (IOException e) {
                    Log.d("btnNext", e.getMessage());
                }
                break;

            default:
                break;
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }
}

