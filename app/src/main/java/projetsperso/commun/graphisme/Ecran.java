package projetsperso.commun.graphisme;

import java.util.Random;

import projetsperso.commun.monde3d.Render3D;

public class Ecran extends Render {

    private Render test;
    private Render3D monde3d;

    public Ecran(int width, int height) {
        super(width, height);
        Random random = new Random();
        monde3d = new Render3D(width, height);
        test = new Render(256, 256);
        for (int i = 0; i < 256 * 256; i++) {
            test.pixels[i] = random.nextInt() * (random.nextInt(5) / 4);
        }
    }

    public void render(Jeu jeu) {
        for (int i = 0; i < width * height; i++) {
            pixels[i] = 0;
        }
        for (int i = 0; i < 50; i++) {
            int anim = (int) (Math.sin((jeu.time + i * 2) % 1000.0 / 100) * 100);
            int anim2 = (int) (Math.cos((jeu.time + i * 2) % 1000.0 / 100) * 100);
            // draw(test, (width - 256) / 2 + anim, (height - 256) / 2 + anim2);
        }
        monde3d.floor();
        draw(monde3d, 0, 0);
    }
}
