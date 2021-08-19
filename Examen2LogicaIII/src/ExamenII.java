import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ExamenII {
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            JFrame frame =  new Ventana();
            frame.setSize(500,300);
            frame.setVisible(true);
            }
        });

    }
}

