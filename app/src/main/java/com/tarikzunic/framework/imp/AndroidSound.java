package com.tarikzunic.framework.imp;

import android.media.SoundPool;

import com.tarikzunic.framework.Sound;

/**
 * Created by Tarik on 17.4.2015.
 */
public class AndroidSound implements Sound {
    SoundPool soundPool;
    int soundId;

    public AndroidSound(SoundPool soundPool, int soundId) {
        this.soundPool = soundPool;
        this.soundId = soundId;
    }


    @Override
    public void play(float volume) {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    @Override
    public void dispose() {
        soundPool.unload(soundId);
    }
}
