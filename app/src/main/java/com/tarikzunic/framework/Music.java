package com.tarikzunic.framework;

/**
 * Created by Tarik on 1.4.2015.
 */
public interface Music {
    public void play();
    public void pause();
    public void stop();

    public void setLooping(boolean looping);
    public void setVolume(float volume);

    public boolean isPlaying();
    public boolean isStopped();
    public boolean isLooping();

    public void dispose();
}
