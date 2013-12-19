package sandbox.android.animation;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;

public class DisplayHelper {

    public int mScreenWidth;
    public int mScreenHeight;
    public int mScreenDpi;
    public boolean mHasSoftKeys;
    private Activity context;

    public int absScreenWidth;
    public int absScreenHeight;

    public DisplayHelper(final Activity context) {
        mScreenWidth = -1;
        mScreenHeight = -1;
        mScreenDpi = -1;
        mHasSoftKeys = false;
        this.context = context;
        init();
    }

    private void init() {

        DisplayMetrics dm = new DisplayMetrics();
        Display display = context.getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);

        mScreenDpi = (int) dm.densityDpi;

        if (dm.heightPixels <= dm.widthPixels) {
            mScreenWidth = dm.heightPixels;
            mScreenHeight = dm.widthPixels;
        } else {
            mScreenWidth = dm.widthPixels;
            mScreenHeight = dm.heightPixels;
        }

        // to determine if we have softkeys: try to read absolute display size and compare to useable screen size
        absScreenWidth = mScreenWidth;
        absScreenHeight = mScreenHeight;
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
            try {
                absScreenWidth = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                absScreenHeight = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (Exception ignored) {
            }
        }
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
                absScreenWidth = realSize.x;
                absScreenHeight = realSize.y;
            } catch (Exception ignored) {
            }
        }
    }

    public boolean hasSoftKeys() {
        return mScreenWidth < absScreenWidth || mScreenHeight < absScreenHeight;
    }

    public boolean isTabled() {
        return context.getResources().getBoolean(R.bool.IsTablet);
    }

    public int getOrientation() {
        return isTabled() ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    public static native String getAssetPathImages();
}