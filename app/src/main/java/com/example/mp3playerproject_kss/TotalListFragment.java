package com.example.mp3playerproject_kss;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class TotalListFragment extends Fragment {
    private MusicPlaying musicPlaying;
    private ListView totalListView;
    private String path;
    private ArrayList<MusicData> musicDataList = new ArrayList<MusicData>();
    private int position;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        musicPlaying = (MusicPlaying)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        musicPlaying = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup =(ViewGroup)inflater.inflate(R.layout.totallist,container,false);
        totalListView = viewGroup.findViewById(R.id.totalListView);

        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/";

        musicPlaying.loadMP3FileFromSDCard();

        MusicAdapter adapter = new MusicAdapter(getContext());
        adapter.setArrayList(musicPlaying.getMusicDataList());

        MusicAdapter musicAdapter = new MusicAdapter(getContext());
        musicAdapter.setArrayList(musicPlaying.getMusicDataList());

        listViewSelectdFnc(musicAdapter);

        totalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                musicPlaying.setPosition(i);
            }
        });

        return viewGroup;
    }
    private void listViewSelectdFnc(MusicAdapter musicAdapter) {
        totalListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        totalListView.setAdapter(musicAdapter);
        totalListView.setItemChecked(0, true);
    }
}
