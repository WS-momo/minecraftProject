package projetsperso.frontal;

import javax.swing.JFrame;

public class FrontalApp {

    public void setupFrontend() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1000,800);
        window.setResizable(true);
        window.setTitle("Minecraft 3d");
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
    }
}
