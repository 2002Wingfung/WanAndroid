package com.hongyongfeng.wanandroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * @author Wingfung Hung
 */
public class MusicService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class MediaPlayerBinder extends Binder {
        public MusicService get(){
            return MusicService.this;
        }
    }
}
