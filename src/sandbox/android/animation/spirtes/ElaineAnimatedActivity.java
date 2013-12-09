package sandbox.android.animation.spirtes;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import sandbox.android.animation.R;

public class ElaineAnimatedActivity extends Activity implements Runnable {

    private ElaineAnimated elaine;
    public GLSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        surfaceView = new GLSurfaceView(this);
        setContentView(surfaceView);

        // create Elaine and load bitmap
        elaine = new ElaineAnimated(BitmapFactory.decodeResource(getResources(), R.drawable.walk_elaine)
                , 10, 50    // initial position
                , 30, 47    // width and height of sprite
                , 5, 5);    // FPS and number of frames in the animation

        runOnUiThread(this);
    }

    // desired fps
    private final static int MAX_FPS = 50;
    // maximum number of frames to be skipped
    private final static int MAX_FRAME_SKIPS = 5;
    // the frame period
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;

    boolean running = true;

    public static final String TAG = ElaineAnimatedActivity.class.getSimpleName();

    @Override
    public void run() {
        Canvas canvas;
        Log.d(TAG, "Starting game loop");

        long beginTime;        // the time when the cycle begun
        long timeDiff;        // the time it took for the cycle to execute
        int sleepTime;        // ms to sleep (<0 if we're behind)
        int framesSkipped;    // number of frames being skipped

        sleepTime = 0;

        while (running) {
            canvas = null;
            // try locking the canvas for exclusive pixel editing
            // in the surface
            try {
                canvas = surfaceView.getHolder().lockCanvas();
                synchronized (surfaceView.getHolder()) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;    // resetting the frames skipped
                    // update game state
                    this.update();
                    // render state to the screen
                    // draws the canvas on the panel
                    //this.render(canvas);
                    elaine.draw(canvas);
                    // calculate how long did the cycle take
                    timeDiff = System.currentTimeMillis() - beginTime;
                    // calculate sleep time
                    sleepTime = (int) (FRAME_PERIOD - timeDiff);

                    if (sleepTime > 0) {
                        // if sleepTime > 0 we're OK
                        try {
                            // send the thread to sleep for a short period
                            // very useful for battery saving
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                        }
                    }

                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                        // we need to catch up
                        // update without rendering
                        this.update();
                        // add frame period to check if in next frame
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                }
            } finally {
                // in case of an exception the surface is not left in
                // an inconsistent state
                if (canvas != null) {
                    surfaceView.getHolder().unlockCanvasAndPost(canvas);
                }
            }    // end finally
        }
    }

    public void update() {
        elaine.update(System.currentTimeMillis());
    }
}
