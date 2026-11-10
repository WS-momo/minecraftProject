package projetsperso.commun.monde3d;

import projetsperso.commun.graphisme.Render;

public class Render3D extends Render {

    public Render3D(int width, int height) {
        super(width, height);

    }

    public void floor() {
        for (int y = 0; y < height; y++) {
            double yDepth = y - height / 2.4;
            double z = 100.0 / yDepth;
            for (int x = 0; x < width; x++) {
                double xDepth = x - width / 2;
                xDepth *= z;
                int xx = (int) (xDepth) & 5;
                pixels[x + y * width] = xx * 128;
            }
        }
    }
}
