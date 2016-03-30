package net.kibotu.android.view.animations.exo;

import android.graphics.PointF;

import java.util.List;

public class Spline {

    public static interface Callback {
        void doCallback(float x, float y);
    }

    static PointF catmull_rom_spline_tangent(PointF p0, PointF p1) {
        return new PointF((p0.x - p1.x) / 2, (p0.y - p1.y) / 2);
    }

    public static void doCubicHermiteSpline(List<PointF> knots, float delta, Callback callback) {

        int n = knots.size();
        float px = 0;
        float py = 0;
        for (int i = 0; i < n; i++) {
            // interpolation
            for (float t = 0; t < 1; t += delta) {
                float h00 = (1 + 2 * t) * (1 - t) * (1 - t);
                float h10 = t * (t - 1) * (t - 1);
                float h01 = t * t * (3 - 2 * t);
                float h11 = t * t * (t - 1);

                if (i == 0) {
                    PointF p0 = knots.get(i);
                    PointF p1 = knots.get(i + 1);
                    PointF p2 = null;
                    if (n > 2) {
                        p2 = knots.get(i + 2);
                    } else {
                        p2 = p1;
                    }
                    PointF m0 = catmull_rom_spline_tangent(p1, p0);
                    PointF m1 = catmull_rom_spline_tangent(p2, p0);

                    px = h00 * p0.x + h10 * m0.x + h01 * p1.x + h11
                            * m1.x;
                    py = h00 * p0.y + h10 * m0.y + h01 * p1.y + h11
                            * m1.y;
                    callback.doCallback(px, py);
                } else if (i < n - 2) {
                    PointF p0 = knots.get(i - 1);
                    PointF p1 = knots.get(i);
                    PointF p2 = knots.get(i + 1);
                    PointF p3 = knots.get(i + 2);

                    PointF m0 = catmull_rom_spline_tangent(p2, p0);
                    PointF m1 = catmull_rom_spline_tangent(p3, p1);

                    px = h00 * p1.x + h10 * m0.x + h01 * p2.x + h11
                            * m1.x;
                    py = h00 * p1.y + h10 * m0.y + h01 * p2.y + h11
                            * m1.y;
                    callback.doCallback(px, py);
                } else if (i == n - 1) {
                    if (n < 3) {
                        continue;
                    }
                    PointF p0 = knots.get(i - 2);
                    PointF p1 = knots.get(i - 1);
                    PointF p2 = knots.get(i);

                    PointF m0 = catmull_rom_spline_tangent(p2, p0);
                    PointF m1 = catmull_rom_spline_tangent(p2, p1);

                    px = h00 * p1.x + h10 * m0.x + h01 * p2.x + h11
                            * m1.x;
                    py = h00 * p1.y + h10 * m0.y + h01 * p2.y + h11
                            * m1.y;
                    callback.doCallback(px, py);
                }
            }
        }
    }
}