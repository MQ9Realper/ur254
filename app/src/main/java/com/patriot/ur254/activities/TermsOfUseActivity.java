package com.patriot.ur254.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.patriot.ur254.R;
import com.patriot.ur254.utils.UniversalUtils;

public class TermsOfUseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_use);
        PDFView pdfView = (PDFView) findViewById(R.id.pdfview);
        initToolBar();
        LoadPdfFile(pdfView);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_terms_use);
        toolbar.setTitle("Terms Of Use");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_white_24dp));
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsOfUseActivity.this, TimelineActivity.class);
                startActivity(intent);
                finish();
            }
        });
        setSupportActionBar(toolbar);
    }

    private void LoadPdfFile(PDFView pdfView) {
        final UniversalUtils universalUtils = new UniversalUtils(this);
        universalUtils.ShowProgressDialog("Loading...");

        pdfView.fromAsset("terms_of_use.pdf")
                .defaultPage(1)
                .showMinimap(false)
                .enableSwipe(true)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        universalUtils.DismissProgressDialog();
                    }
                })
                .load();
    }
}
