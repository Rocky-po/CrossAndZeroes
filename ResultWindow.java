package Подготовительный.Test8;

import javax.swing.*;
import java.awt.*;

/**
 * Created by rocky-po on 13.10.17.
 */
public class ResultWindow extends JFrame{

    private static final int WIN_HEIGHT = 230;
    private static final int WIN_WIDTH = 350;
    private final GameWindow1 gameWindow1;
    private static JLabel resultLabel = new JLabel();

    ResultWindow(GameWindow1 gameWindow1){
        this.gameWindow1 = gameWindow1;

        setSize(WIN_WIDTH, WIN_HEIGHT);

        Rectangle gameWindowsBounds = gameWindow1.getBounds();
        // здесь мы получаем координаты левого верхнего угла маленького окна
        int posX = (int) (gameWindowsBounds.getCenterX() - WIN_WIDTH/2);
        int posY = (int) (gameWindowsBounds.getCenterY() - WIN_HEIGHT/2);
        setLocation(posX, posY);

        setTitle("Result of Battle");
        setLayout(new GridLayout(1,1));
    }

    public static JLabel getResultLabel() {
        return resultLabel;
    }
}
