package Подготовительный.Test8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Gulshat on 08.10.2017.
 */
public class GameWindow1 extends JFrame {

    // 2. обозначаем размеры
    private static final int WIN_HEIGHT = 550;
    private static final int WIN_WIDTH = 500;
    private static final int WIN_POS_X = 800;
    private static final int WIN_POS_Y = 300;

    // 16 создаем константы
    private static StartNewGameWindow gameWindow;
    private static Map field;
    private static ResultWindow resultWindow;

    private Font font = new Font("Verdana", Font.PLAIN, 30);
    private static String resultMessage;
    private static JButton resultButton;

    // 3. создаем конструктор
    GameWindow1() {
        // 5. добавляем операцию close, при закрытии окна, программа завершается.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 6. задаем размеры
        setBounds(WIN_POS_X, WIN_POS_Y, WIN_WIDTH, WIN_HEIGHT);

        //12. Добавляем заголовок и запрещаем изменение размера окна
        setTitle("Tic Tac Toe");
        setResizable(false);


        // 9 Создаем панель, (только сначала рассказываем по север юг запад восток),
        // далее рассказываем про new GridLayout(1, 2) и что можно еще что-нибудь добавить
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

        // 7. создаем кнопку
        JButton btnNewGame = new JButton("Start new game");
        // 7.1 добавляем, потом перепишем
        // add(btnNewGame);
        JLabel statusLabel = new JLabel();

        // 22 обрабатываем стар новая игра
        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 19 - 22 делаем наше окно настроек видимое // при крестике окно просто становить не видимым
                gameWindow.setVisible(true);
            }
        });


        // 8. создаем кнопку
        JButton btnExit = new JButton("Exit game");
        // 8.1 добавляем, потом перепишем
        //  add(btnExit);
        // 21 Вешаем слушателя на кнопку
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ноль для остановки программы
                System.exit(0);
            }
        });

        // 10 далее в панель кнопки
        bottomPanel.add(btnNewGame);
        bottomPanel.add(btnExit);

        // 17 добавляем Map (игровое поле)
        field = new Map(0, false);
        add(field, BorderLayout.CENTER);

        // 11 добавляем в окно панель и распологаем ее на юге
        add(bottomPanel, BorderLayout.SOUTH);

        // 18 передаем возможность определение координатов следующему окну
        gameWindow = new StartNewGameWindow(this);
        // 19 делаем наше окно настроек видимое
        // gameWindow.setVisible(true);

        gameWindow.getBtnStartGame().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(field);
                field = new Map(gameWindow.getSlFieldSize().getValue(), true);
                add(field, BorderLayout.CENTER);
                revalidate();
            }
        });

        JPanel finishPanel = new JPanel(new GridLayout(1, 1));

        resultButton = new JButton();
        finishPanel.add(resultButton);
        add(finishPanel, BorderLayout.NORTH);
        finishPanel.setVisible(false);

        resultWindow = new ResultWindow(this);
        resultWindow.setVisible(false);


        resultButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ResultWindow.getResultLabel().setText(resultMessage);
                ResultWindow.getResultLabel().setVerticalAlignment(JLabel.CENTER);
                ResultWindow.getResultLabel().setHorizontalAlignment(JLabel.CENTER);
                ResultWindow.getResultLabel().setFont(font);
                resultWindow.add(ResultWindow.getResultLabel(),BorderLayout.CENTER);
                resultWindow.revalidate();
                resultWindow.setVisible(true);
            }
        });

        // 4. создаем наше первое окно (УРА!)
        setVisible(true);
    }

    public static JButton getResultButton() {
        return resultButton;
    }

    public static void setResultMessage(String resultMessage) {
        GameWindow1.resultMessage = resultMessage;
    }
}