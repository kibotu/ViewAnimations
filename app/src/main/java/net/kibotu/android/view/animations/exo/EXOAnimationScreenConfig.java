package net.kibotu.android.view.animations.exo;


import net.kibotu.android.deviceinfo.library.display.Dimension;

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
    x1440(1, 1), // todo
    x1920(1, 1), // todo
    x2560(1, 1), // todo
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

    public static EXOAnimationScreenConfig getResolution() {


        EXOAnimationScreenConfig config = EXOAnimationScreenConfig.x320;

        EXOAnimationScreenConfig.globalImageViewScaleX *= 2.0; // images are scaled to 50% but based on 720
        EXOAnimationScreenConfig.globalImageViewScaleY *= 2.0; // images are scaled to 50% but based on 720
        EXOAnimationScreenConfig.globalImageViewPositionScaleY *= isTablet() ? 1.2 : 1.0;

        // Device
        Dimension screenDimensions = getScreenDimensions();
        int width = Math.max(screenDimensions.width, screenDimensions.height);
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
        } else if (width >= 1200 && width < 1920) {
            config = EXOAnimationScreenConfig.x1440;
        } else if (width >= 1920 && width < 2560) {
            config = EXOAnimationScreenConfig.x1920;
        } else if (width >= 2560) {
            config = EXOAnimationScreenConfig.x2560;
        }
        return config;
    }
}
