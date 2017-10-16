package Подготовительный.Test8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Gulshat on 08.10.2017.
 */
public class Map extends JPanel {
    // 36
    public static final int MODE_H_V_A = 0;
    public static final int MODE_H_V_H = 1;

    private Font font = new Font("Verdana", Font.PLAIN, 15);
    char NEED_SIGN = CrossZero5.EMPTY_DOT;
    ArrayList<JButton> playingButtons = new ArrayList<>();

    private static int MODE = -1;

    // 13. создаем конструктор
    Map(int cellsNumber, boolean startGame) {
        // 13.1 задаем цвет
        setBackground(Color.black);
        if (startGame) {
            setLayout(new GridLayout(cellsNumber, cellsNumber));
            for (int i = 0; i < cellsNumber * cellsNumber; i++) {
                JButton btnNewGame = new JButton();
                playingButtons.add(btnNewGame);
                add(btnNewGame);
            }

            for (JButton playingButton : playingButtons) {
                playingButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NEED_SIGN = getNeedSign(NEED_SIGN);
                        JLabel sign = new JLabel(NEED_SIGN + "");
                        sign.setAlignmentX(JLabel.CENTER_ALIGNMENT);
                        sign.setFont(font);
                        playingButton.add(sign, BorderLayout.CENTER);
                        playingButton.setEnabled(false);
                        playingButton.revalidate();
                        printField();
                        boolean nextIf = true;
                        if (NEED_SIGN == CrossZero5.player_DOT || NEED_SIGN == CrossZero5.player2_DOT) {
                            CrossZero5.fieldg[playingButtons.indexOf(playingButton) / cellsNumber][playingButtons.indexOf(playingButton) % cellsNumber]
                                    = NEED_SIGN;
                            printField();
                        }
                        if (CrossZero5.checkWin(NEED_SIGN)) {
                            if (NEED_SIGN == CrossZero5.player_DOT) {
                                GameWindow1.setResultMessage("Победил игрок");
                            } else if (NEED_SIGN == CrossZero5.player2_DOT && MODE == MODE_H_V_H) {
                                GameWindow1.setResultMessage("Победил второй игрок");
                            } else {
                                GameWindow1.setResultMessage("Победил ИИ");
                            }
                            GameWindow1.getResultButton().doClick();
                            nextIf = false;
                        }
                        if (CrossZero5.fullField() && nextIf) {
                            GameWindow1.setResultMessage("Ничья");
                            GameWindow1.getResultButton().doClick();
                            nextIf = false;
                        }
                        if (NEED_SIGN == CrossZero5.player_DOT && nextIf && MODE == MODE_H_V_A) {
                            CrossZero5.AiMove();
                            int buttonNum = CrossZero5.getAiCoords()[0] * cellsNumber + CrossZero5.getAiCoords()[1];
                            playingButtons.get(buttonNum).doClick();
                            printField();
                        }
                    }
                });
            }
        }

    }

    private static char getNeedSign(char sign) {
        if (sign == CrossZero5.EMPTY_DOT || sign == CrossZero5.Ai_DOT || sign == CrossZero5.player2_DOT) {
            return CrossZero5.player_DOT;
        } else {
            return CrossZero5.Ai_DOT;
        }
    }

    private static void printField() {
        System.out.println("----------------------");
        for (int i = 0; i < CrossZero5.SIZE_Y; i++) {
            for (int j = 0; j < CrossZero5.SIZE_X; j++) {
                System.out.print(CrossZero5.fieldg[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("------------------------");

    }

    public static void setMODE(int MODE) {
        Map.MODE = MODE;
    }
}
