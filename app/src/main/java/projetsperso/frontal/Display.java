package projetsperso.frontal;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import projetsperso.commun.graphisme.Ecran;
import projetsperso.commun.graphisme.Jeu;
import projetsperso.commun.graphisme.Render;

// https://www.youtube.com/watch?v=bKbZECPjQs0&list=PL656DADE0DA25ADBB&index=4

public class Display extends Canvas implements Runnable {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private Thread thread;
    private boolean running = false;
    private Render render;
    private Jeu jeu;
    private Ecran ecran;
    private BufferedImage img;
    private int[] pixels;

    public Display() {
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        ecran = new Ecran(WIDTH, HEIGHT);
        jeu = new Jeu();
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
        int frames = 0;
        double unprocessedSeconds = 0;
        long previousTime = System.nanoTime();
        double secondsPerTick = 1 / 60.0;
        int tickCount = 0;
        boolean ticked = false;
        while (running) {
            long currentTime = System.nanoTime();
            long passedTime = currentTime - previousTime;
            previousTime = currentTime;
            unprocessedSeconds += passedTime / 1000000000.0;
            while (unprocessedSeconds > secondsPerTick) {
                tick();
                unprocessedSeconds -= secondsPerTick;
                ticked = true;
                tickCount++;
                if (tickCount % 60 == 0) {
                    System.out.println(frames + "fps");
                    previousTime += 1000;
                    frames = 0;
                }
            }
            if (ticked == true) {
                render();
                frames++;
            }
            render();
            frames++;
        }
    }

    private void tick() {
        jeu.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        ecran.render(jeu);
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            pixels[i] = ecran.pixels[i];
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0, 0, WIDTH + 10, HEIGHT + 10, null);
        g.dispose();
        bs.show();
    }

    public void setupFrontend() {
        Display jeu = new Display();
        JFrame window = new JFrame();
        window.add(jeu);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setResizable(true);
        window.setTitle("Minecraft 3d");
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        System.out.println("Roule");
        jeu.start();

    }
}
