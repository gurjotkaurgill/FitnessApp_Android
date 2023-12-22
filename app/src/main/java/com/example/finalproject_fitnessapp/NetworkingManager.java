package com.example.finalproject_fitnessapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NetworkingManager {

    interface GotResultFromAPI {
        void gotListResultFromAPI(String jsonResponse);
        //void gotGIF(Bitmap demoGIF);
    }
    GotResultFromAPI listener;

    String apiKey = "ace899a702msh423906df3dc0feep124266jsna5314aec7bb4";
    void getMuscleGroups(){
        String urlString = "https://exercisedb.p.rapidapi.com/exercises/targetList";
        connect(urlString);
    }

    void getExercisesForMuscleGroup(String muscleGroupName){
        String urlString = "https://exercisedb.p.rapidapi.com/exercises/target/" + muscleGroupName;
        connect(urlString);
    }

    private void connect(String urlString){
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpsURLConnection httpsURLConnection = null;
                try {
                    URL urlObject = new URL(urlString);
                    httpsURLConnection = (HttpsURLConnection) urlObject.openConnection();
                    httpsURLConnection.setRequestProperty("X-RapidAPI-Key",apiKey);
                    httpsURLConnection.setRequestProperty("X-RapidAPI-Host","exercisedb.p.rapidapi.com");
                    httpsURLConnection.setRequestProperty("limit","50");
                    InputStream inputStream = httpsURLConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    int v;
                    while((v = inputStream.read()) != -1) {
                        buffer.append((char)v);
                    }
                    String jsonResponse = buffer.toString();
                    MyApp.mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.gotListResultFromAPI(jsonResponse);
                        }
                    });
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    httpsURLConnection.disconnect();
                }
            }
        });
    }
//    void getDemoGIF(String url){
//        MyApp.executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                Bitmap demoGif;
//                try {
//                    URL urlObject = new URL(url);
//                    demoGif = BitmapFactory.decodeStream(urlObject.openStream());
//                    MyApp.mainHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            listener.gotGIF(demoGif.extractAlpha());
//                        }
//                    });
//                } catch (MalformedURLException e) {
//                    throw new RuntimeException(e);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//    }
}