package projetsperso.frontal;

import java.awt.Canvas;
import javax.swing.JFrame;

import projetsperso.commun.graphisme.Render;

public class Display extends Canvas implements Runnable {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    private Thread thread;
    private boolean running = false;
    private Render render;

    public Display() {
        render = new Render(WIDTH, HEIGHT);
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
