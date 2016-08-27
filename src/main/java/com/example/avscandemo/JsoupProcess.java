package com.example.avscandemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Administrator on 2016/8/20.
 */
public class JsoupProcess {

    private  ArrayList<Bitmap> bitmapArrayList;

    public JsoupProcess(ArrayList<Bitmap> bitmapArrayList) {
        this.bitmapArrayList = bitmapArrayList;
    }

    public  void urlProcess() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL("https://avmo.pw/cn");
                    Elements elements = Jsoup.parse(url, 1500).body().getElementsByClass("movie-box").select("img[src$=.jpg]");
                    for (Element e : elements) {
                        URL url1 = new URL(e.attr("src"));
                        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url1.openConnection();
                        InputStream inputStream = httpsURLConnection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        bitmapArrayList.add(bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
