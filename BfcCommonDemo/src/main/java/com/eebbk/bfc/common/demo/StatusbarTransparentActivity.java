package com.eebbk.bfc.common.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.eebbk.bfc.common.app.StatusBarUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StatusbarTransparentActivity extends AppCompatActivity {
    static final String REQUEST_DATA = "is_transparent";


    @Bind(R.id.sb_change_alpha)
    SeekBar mAlphaSeekBar;

    @Bind(R.id.tv_statusbar_alpha)
    TextView mAlphaTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusbar_transparent);

        setupWindowAnimations();

        if (getIntent().getBooleanExtra(REQUEST_DATA, true)){
            mAlphaSeekBar.setVisibility(View.GONE);
            mAlphaTextView.setVisibility(View.GONE);
            StatusBarUtils.setTransparent( this );
        }else {
            StatusBarUtils.setTranslucent( this, 30 );
            mAlphaTextView.setText(30 + "");
            mAlphaSeekBar.setMax(255);
            mAlphaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mAlphaTextView.setText(progress + "");
                    StatusBarUtils.setTranslucent( StatusbarTransparentActivity.this, progress );
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT < 21) return;

        Explode sildeTransition = new Explode();
        sildeTransition.setDuration(300);
//        sildeTransition.setSlideEdge(Gravity.BOTTOM);

        getWindow().setEnterTransition(sildeTransition );
    }

    public static void startThis(Activity activity, boolean isTransparent){
        Intent intent = new Intent(activity, StatusbarTransparentActivity.class);
        intent.putExtra( REQUEST_DATA, isTransparent );

        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation( activity );
//        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation( activity );

        ActivityCompat.startActivity(activity, intent, transitionActivityOptions.toBundle());

//        activity.startActivity(intent, transitionActivityOptions.toBundle());
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }
}
