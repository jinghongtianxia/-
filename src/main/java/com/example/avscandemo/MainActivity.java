package com.example.avscandemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StringLoader;

import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView mainTv;
    private Glide glide;
    private Button mainButton;
    private CardView cardView;
    private RecyclerView recyclerView;
    private ImageView mainImageView;
    private ArrayList<String> arrayListTv;
    private ArrayList<Bitmap> arrayListBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainButton = (Button) findViewById(R.id.main_btn);
        mainButton.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.main_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
//        ViewGroup listItem = ((ViewGroup) View.inflate(MainActivity.this, R.layout.item_listview, null));
//        TextView itemText = (TextView) listItem.findViewById(R.id.list_item);
//        listItem.removeView(itemText);
        arrayListTv = new ArrayList<>();
        arrayListBitmap = new ArrayList<>();
//        mainTv = (TextView) findViewById(R.id.main_tv);
//        mainImageView = (ImageView) findViewById(R.id.main_iv);
    }

    @Override
    public void onClick(View v) {
        new Thread(new Runnable() {
            private String content;
            private Element src;

            @Override
            public void run() {
                try {
                    URL url = new URL("https://avmo.pw/cn");
                    Elements elements = Jsoup.parse(url, 3000).body().getElementsByClass("movie-box").select("img[src$=.jpg]");
                    for (Element e : elements) {
                        e.attr("title");
                        URL url1 = new URL(e.attr("src"));
                        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url1.openConnection();
                        final Bitmap bitmap = BitmapFactory.decodeStream(new BufferedInputStream(httpsURLConnection.getInputStream()));
                        arrayListBitmap.add(bitmap);
                        arrayListTv.add(e.attr("title"));
//                        bitmapHashMap.put(e.attr("title"),bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(new MyAdapter());
                    }
                });
            }
        }).start();
    }


    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        public MyAdapter() {
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            cardView = ((CardView) View.inflate(MainActivity.this, R.layout.my_cardview, null));
            return new MyViewHolder(cardView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.imageCardView.setImageBitmap(arrayListBitmap.get(position));
            holder.textCardView.setText(arrayListTv.get(position));
        }

        @Override
        public int getItemCount() {
            return arrayListBitmap.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageCardView;
        private final TextView textCardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageCardView = (ImageView) itemView.findViewById(R.id.card_iv);
            textCardView = (TextView) itemView.findViewById(R.id.card_tv);
        }
    }

    public class myItemDecoration extends RecyclerView.ItemDecoration{
        public myItemDecoration() {
            super();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
        }
    }
}
