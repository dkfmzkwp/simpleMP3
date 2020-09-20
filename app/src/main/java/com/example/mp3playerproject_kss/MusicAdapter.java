package com.example.mp3playerproject_kss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MusicAdapter extends BaseAdapter {
    private ArrayList<MusicData> arrayList;
    private Context context;

    public MusicAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        //position
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long)i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //만든 화면 객체화
        LayoutInflater layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //리스트뷰에 들어갈 화면 가져옴
        //속도 개선 방법
        if(view == null){
            view=layoutInflater.inflate(R.layout.music_layout,null);
        }
        //만든 화면안에있는 위젯 아이디 찾기
        ImageView ivCover = view.findViewById(R.id.ivCover);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvArtist = view.findViewById(R.id.tvArtist);
        //해당된 데이터 가져온다
        MusicData musicData=arrayList.get(i);

        //해당되는 값 저장
        ivCover.setImageBitmap(musicData.getAlbumArt());
        tvTitle.setText(musicData.getTitle());
        tvArtist.setText(musicData.getArtist());
        return view;
    }

    public ArrayList<MusicData> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<MusicData> arrayList) {
        this.arrayList = arrayList;
    }
}
