package com.example.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class Faq extends AppCompatActivity {
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        pdfView=(PDFView)findViewById(R.id.pdf);
        pdfView.fromAsset("FAQ.pdf")



                .defaultPage(0)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(2)
                //.pageFitPolicy(FitPolicy.WIDTH)
                .load();
    }

    public void back(View view) {
        onBackPressed();
    }
}