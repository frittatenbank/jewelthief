package net.bplaced.therefactory.jewelthief;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import net.bplaced.therefactory.jewelthief.misc.AndroidInterface;

public class AndroidLauncher extends AndroidApplication implements AndroidInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useGyroscope = false;
        config.useWakelock = true;
        config.hideStatusBar = true;
        config.useImmersiveMode = true;
        initialize(new JewelThief(this), config);
    }

    @Override
    public void toast(final String message, final boolean longDuration) {
        Handler h = new Handler(Looper.getMainLooper());
        h.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message,
                        longDuration ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
            }

        });
    }

}