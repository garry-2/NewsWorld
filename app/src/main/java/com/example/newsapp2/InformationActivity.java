package com.example.newsapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InformationActivity extends AppCompatActivity {
    TextView content_textView,content_titleView;
    ImageView content_imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        content_textView = findViewById(R.id.content_textView);
        content_titleView = findViewById(R.id.content_title);
        content_imageView = findViewById(R.id.content_image);

        Log.d("Gaurav","This is second Activity");

        Intent intent2 = getIntent();

        String ContentView = intent2.getStringExtra("ContentResponse");
        String ContentTitleView  = intent2.getStringExtra("ContentTitle1");
        String ContentImageUrl = intent2.getStringExtra("ImageUrlContent");



        Log.d("Gaurav","Content View Response : "+ContentView);
        if(!ContentView.equals("null")){
            content_textView.setText(ContentView);
        }
        else{
            content_textView.setVisibility(View.GONE);
        }
        content_titleView.setText(ContentTitleView);

        if(!ContentImageUrl.equals("null")){
            Picasso.get().load(ContentImageUrl).into(content_imageView);
        }
        else{
            content_imageView.setVisibility(View.GONE);
        }
    }
}