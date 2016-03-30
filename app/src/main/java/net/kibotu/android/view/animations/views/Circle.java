package net.kibotu.android.view.animations.views;

import android.graphics.PointF;

public class Circle implements Runnable {

    public PointF center;
    public float radius;
    public int color;

    public Circle(final PointF center, final float radius, final int color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            center.y += 9.81;
        }
    }
}
