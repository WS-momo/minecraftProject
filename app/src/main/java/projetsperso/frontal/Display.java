package projetsperso.frontal;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import projetsperso.commun.graphisme.Ecran;
import projetsperso.commun.graphisme.Render;

// https://www.youtube.com/watch?v=bKbZECPjQs0&list=PL656DADE0DA25ADBB&index=4

public class Display extends Canvas implements Runnable {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    private Thread thread;
    private boolean running = false;
    private Render render;
    private Ecran ecran;
    private BufferedImage img;
    private int[] pixels;

    public Display() {
        ecran = new Ecran(WIDTH, HEIGHT);
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
    }

    private void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
        System.out.println("En route..");
    }

    public void stop() {
        if (!running) {
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void run() {
        while (running) {
            tick();
            render();
        }
    }

    private void tick() {

    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        ecran.render();
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            pixels[i] = ecran.pixels[i];
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0, 0, WIDTH * 20, HEIGHT * 20, null);
        g.dispose();
        bs.show();
    }

    public void setupFrontend() {
        Display jeu = new Display();
        JFrame window = new JFrame();
        window.add(jeu);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(WIDTH, HEIGHT);
        window.setResizable(true);
        window.setTitle("Minecraft 3d");
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        System.out.println("Roule");
        jeu.start();

    }
}
