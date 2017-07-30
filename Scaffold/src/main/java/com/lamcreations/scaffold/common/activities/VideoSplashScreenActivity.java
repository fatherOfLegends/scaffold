/*
 * Copyright (C) 2015 LAM Creations
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lamcreations.scaffold.common.activities;

import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.widget.FrameLayout;

import com.lamcreations.scaffold.R;
import com.lamcreations.scaffold.common.views.AutoFitTextureView;

import static android.support.v4.view.ViewCompat.animate;


public abstract class VideoSplashScreenActivity extends BaseActivity
        implements
        TextureView.SurfaceTextureListener, MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnSeekCompleteListener {

    private static final String TAG = "VideoSplashScreen";

    protected MediaPlayer mMediaPlayer;
    protected FrameLayout mSplashContainer;
    protected AutoFitTextureView mTextureView;

    private boolean mAnimating = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scaffold_activity_video_splash_screen);
        mSplashContainer = findViewById(R.id.splash_container);
        mTextureView = findViewById(R.id.auto_fit_texture_view);
        assert mTextureView != null;
        mTextureView.setSurfaceTextureListener(this);
        mTextureView.setAlpha(0f);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Surface s = new Surface(surface);

        try {
            mMediaPlayer = MediaPlayer.create(this, getVideoRawResId());
            mMediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
            mMediaPlayer.setSurface(s);
            mMediaPlayer.setOnBufferingUpdateListener(this);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnVideoSizeChangedListener(this);
            mMediaPlayer.setOnSeekCompleteListener(this);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mTextureView.setAspectRatio(mMediaPlayer.getVideoWidth(), mMediaPlayer.getVideoHeight());
        } catch (IllegalArgumentException | SecurityException | IllegalStateException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    protected abstract int getVideoRawResId();

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        if (!mAnimating) {
            mAnimating = true;

        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer.start();
        mMediaPlayer.seekTo(500);
        mMediaPlayer.pause();
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        animate(mTextureView)
                .setStartDelay(750)
                .alpha(1f)
                .setDuration(250)
                .start();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMediaPlayer.setOnCompletionListener(VideoSplashScreenActivity.this);
                mMediaPlayer.start();
            }
        }, 1000);
    }
}
