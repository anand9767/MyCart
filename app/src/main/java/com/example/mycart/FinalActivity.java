package com.example.mycart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mycart.Fragments.CartFragment;
import com.example.mycart.SqlDB.QueryClass;
import com.example.mycart.Utils.CommonUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.example.mycart.BuildConfig.DEBUG;

public class FinalActivity extends AppCompatActivity {

    ImageView ballImage,finalImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        ballImage = findViewById(R.id.ball_image);
        finalImage = findViewById(R.id.finalImage);

        final MediaPlayer mp = MediaPlayer.create(this, Settings.System.DEFAULT_NOTIFICATION_URI);

        ballImage.clearAnimation();

        TranslateAnimation translateAnimation = new TranslateAnimation(0,0,0,getDisplayHeight()/2 - 150);
        translateAnimation.setStartOffset(500);
        translateAnimation.setDuration(3000);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new BounceInterpolator());
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                ballImage.clearAnimation();
                ballImage.setVisibility(View.GONE);
                finalImage.setVisibility(View.VISIBLE);
                mp.start();

//
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ballImage.startAnimation(translateAnimation);

    }

    public void HomeBack(View view) {

        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        CommonUtils.deleteCartTable(getApplicationContext(), QueryClass.TABLE_ITEMSCART);
        finish();
    }

    @Override
    public void onBackPressed() {

    }

    private int getDisplayHeight(){

        return this.getResources().getDisplayMetrics().heightPixels;
    }
}
