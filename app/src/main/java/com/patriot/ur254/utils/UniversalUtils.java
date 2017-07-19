package com.patriot.ur254.utils;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.patriot.ur254.R;
import com.patriot.ur254.views.Btn;
import com.patriot.ur254.views.Txt;

import net.bohush.geometricprogressview.GeometricProgressView;
import net.bohush.geometricprogressview.TYPE;

import java.util.ArrayList;

/**
 * Created by dennis on 15/07/17.
 */

public class UniversalUtils {
    private Activity activity;
    private AlertDialog dialogBuilder, dialogError;

    public UniversalUtils(Activity activity) {
        this.activity = activity;
    }

    public void centerToolbarTitle(@NonNull final Toolbar toolbar) {
        final CharSequence title = toolbar.getTitle();
        final ArrayList<View> outViews = new ArrayList<>(1);
        toolbar.findViewsWithText(outViews, title, View.FIND_VIEWS_WITH_TEXT);
        if (!outViews.isEmpty()) {
            final TextView titleView = (TextView) outViews.get(0);
            titleView.setGravity(Gravity.CENTER);
            titleView.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/SourceSansPro-Regular.ttf"));
            final Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) titleView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            toolbar.requestLayout();
        }
    }

    public void ShowProgressDialog(String message) {
        dialogBuilder = new AlertDialog.Builder(activity).create();
        final View dialogView = LayoutInflater.from(activity).inflate(R.layout.layout_progress_view, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);

        Txt txtMessage = (Txt) dialogView.findViewById(R.id.textViewProgressText);
        txtMessage.setText(message);

        GeometricProgressView progressView = (GeometricProgressView) dialogView.findViewById(R.id.progressView);
        progressView.setType(TYPE.KITE);
        progressView.setNumberOfAngles(6);
        progressView.setColor(activity.getResources().getColor(R.color.colorPrimary));
        progressView.setDuration(1000);
        progressView.setFigurePadding(16);

        dialogBuilder.show();

    }

    public void DismissProgressDialog() {
        if (dialogBuilder.isShowing()) {
            dialogBuilder.dismiss();
        }
    }

    public void ShowErrorDialog(String message) {
        dialogError = new AlertDialog.Builder(activity).create();
        final View dialogView = LayoutInflater.from(activity).inflate(R.layout.layout_error_dialog, null);
        dialogError.setView(dialogView);
        dialogError.setCancelable(false);

        Txt txtMessage = (Txt) dialogView.findViewById(R.id.textViewErrorMessage);
        txtMessage.setText(message);

        Btn btnTryAgain = (Btn) dialogView.findViewById(R.id.buttonErrorTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogError.isShowing()) {
                    dialogError.dismiss();
                }
            }
        });

        dialogError.show();
    }
}
