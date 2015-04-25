package com.tarikzunic.mrnom;

import com.tarikzunic.framework.Screen;
import com.tarikzunic.framework.imp.AndroidGame;

/**
 * Created by Tarik on 24.4.2015.
 */
public class MrNomGame extends AndroidGame {
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}
