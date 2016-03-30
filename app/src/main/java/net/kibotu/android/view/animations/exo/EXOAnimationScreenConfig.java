package net.kibotu.android.view.animations.exo;

import android.app.Activity;

import static net.kibotu.android.deviceinfo.library.display.Display.getScreenDimensions;
import static net.kibotu.android.deviceinfo.library.display.Display.isTablet;

public enum EXOAnimationScreenConfig {

    x320(0.2083, 0.208333),
    x480(0.3125, 0.3125),
    x540(0.3515625, 0.35156),
    x600(0.390625, 0.390625),
    x720(0.46875, 0.46875),
    x768(0.5, 0.5),
    x800(0.52083, 0.520833),
    x1080(0.703125, 0.703125),
    NO_SCALING(1, 1);

    static double globalImageViewScaleX = 1.0, globalImageViewScaleY = 1.0;
    static double globalImageViewPositionScaleX = globalImageViewScaleX * 1.0, globalImageViewPositionScaleY = globalImageViewScaleY * 1.0;
    static double globalImageViewPositionAddX = 0.0, globalImageViewPositionAddY = 0.0;

    private final double scaleX, scaleY;
    private final double yAspectFix = 1.0;

    private EXOAnimationScreenConfig(final double scaleX, final double scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public double imageViewScaleX(final double x) {
        return x * scaleX / x768.scaleX * globalImageViewScaleX;
    }

    public double imageViewScaleY(final double y) {
        return y * scaleY / x768.scaleY * yAspectFix * globalImageViewScaleY;
    }

    public double imageViewPositionX(final double x) {
        return x * scaleX / x768.scaleX * globalImageViewPositionScaleX + globalImageViewPositionAddX;
    }

    public double imageViewPositionY(final double y) {
        return y * scaleY / x768.scaleY * yAspectFix * globalImageViewPositionScaleY + globalImageViewPositionAddY;
    }

    // these function get applyed
    public double animationScaleX(final double x) {
        // i think the final scalings shouldn't be scaled, instead scale the imageviews
        return x;
    }

    public double animationScaleY(final double y) {
        // i think the final scalings shouldn't be scaled, instead scale the imageviews
        return y;
    }

    public double animationPositionX(final double x) {
        // we dont have to add the stuff here since its already done for the imageviews
        return x * scaleX / x768.scaleX * globalImageViewPositionScaleX;
    }

    public double animationPositionY(final double y) {
        // we dont have to add the stuff here since its already done for the imageviews
        return y * scaleY / x768.scaleY * yAspectFix * globalImageViewPositionScaleY;
    }

    public static EXOAnimationScreenConfig getResolution(final Activity context) {

        EXOAnimationScreenConfig config = EXOAnimationScreenConfig.x320;

        //Logger.v("jniresolution", "width = " + helper.mScreenWidth + " height = " + helper.mScreenHeight);
        EXOAnimationScreenConfig.globalImageViewScaleX *= 2.0; // images are scaled to 50% but based on 720
        EXOAnimationScreenConfig.globalImageViewScaleY *= 2.0; // images are scaled to 50% but based on 720
        EXOAnimationScreenConfig.globalImageViewPositionScaleY *= isTablet() ? 1.2 : 1.0;

        // Device
        int width = getScreenDimensions().width;
        if (width >= 480 && width < 540) {
            config = EXOAnimationScreenConfig.x480;
        } else if (width >= 540 && width < 600) {
            config = EXOAnimationScreenConfig.x540;
        } else if (width >= 600 && width < 720) {
            config = EXOAnimationScreenConfig.x600;
        } else if (width >= 720 && width < 768) {
            config = EXOAnimationScreenConfig.x720;
        } else if (width >= 768 && width < 800) {
            config = EXOAnimationScreenConfig.x768;
        } else if (width >= 800 && width < 1080) {
            config = EXOAnimationScreenConfig.x800;
        } else if (width >= 1080 && width < 1200) {
            config = EXOAnimationScreenConfig.x1080;
        } else if (width >= 1200 && width < 1600) {
            config = EXOAnimationScreenConfig.x1080;
        } else if (width >= 1600) {
            config = EXOAnimationScreenConfig.x1080;
        }
        return config;
    }
}
