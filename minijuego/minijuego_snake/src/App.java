import controller.GameController;
import model.GameModel;
import view.GameFrame;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameModel model = new GameModel();
                GameFrame view = new GameFrame(model);
                GameController controller = new GameController(model, view);
                controller.start();
            }
        });
    }
}