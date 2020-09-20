package com.example.mp3playerproject_kss;

import android.graphics.Bitmap;

public class MusicData {
    private String title;
    private String artist;
    private Bitmap albumArt;
    private String fileName;
    private String duration;

    public MusicData(String title, String artist, Bitmap albumArt, String fileName) {
        this.title = title;
        this.artist = artist;
        this.albumArt = albumArt;
        this.fileName = fileName;
    }

    public MusicData(String title, String artist, Bitmap albumArt, String fileName, String duration) {
        this.title = title;
        this.artist = artist;
        this.albumArt = albumArt;
        this.fileName = fileName;
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Bitmap getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(Bitmap albumArt) {
        this.albumArt = albumArt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
