package net.kibotu.android.view.animations.legacy;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import net.kibotu.android.view.animations.views.CustomView;

public class CustomViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        RelativeLayout layout = new RelativeLayout(this);
        setContentView(layout);

        CustomView view = new CustomView(this);
        layout.addView(view);
        view.invalidate();
    }
}
