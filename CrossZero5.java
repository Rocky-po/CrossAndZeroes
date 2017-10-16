package Подготовительный.Test8;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CrossZero5 {

    //1. параметры игрового поля
    static int SIZE_Y; //размер поля по вертикале
    static int SIZE_X; //расчет поля по горизонтале
    static int SIZE_WIN; //кол-во заполненных подряж полей для победы
    static char[][] fieldg;
    // игровые элемент
    static final char player_DOT = 'X';
    static final char player2_DOT = 'O';
    static final char Ai_DOT = 'O';
    static final char EMPTY_DOT = '.';

    private static int[] AI_COORDS = new int[2];

    // обявляется классов ввода и случайного числа для игры
    static Scanner scr = new Scanner(System.in);
    static Random rnd = new Random();

    //поле в начале игры
    static void emtpyField() {

        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                fieldg[i][j] = EMPTY_DOT;
            }
        }
    }

    //печать поля на экран
    private static void printField() {
        printFieldLine();
        for (int i = 0; i < SIZE_Y; i++) {
            System.out.print("|");
            for (int j = 0; j < SIZE_X; j++) {
                System.out.print(fieldg[i][j] + "|");
            }
            System.out.println("");
        }
        printFieldLine();
    }

    //чертим линию для поля
    private static void printFieldLine() {
        for (int i = 0; i < fieldg.length * 2 + 1; i++) {
            System.out.print("-");
        }
        System.out.println("");
    }

    //запись хода игрока на поле
    private static void dotField(int y, int x, char dot) {
        fieldg[y][x] = dot;
    }

    //Ход человева
    private static void playerMove() {
        int x, y;
        do {
            System.out.println("Введите координаты вашего хода в диапозоне от 1 до " + SIZE_Y);
            System.out.print("Координат по строке ");
            y = scr.nextInt() - 1;
            System.out.print("Координат по столбцу ");
            x = scr.nextInt() - 1;

        } while (!checkMove(y, x));
        dotField(y, x, player_DOT);
    }

    //Ход компьютера
    public static void AiMove() {
        int x, y;
        //блокировка ходов человека
        for (int v = 0; v < SIZE_Y; v++) {
            for (int h = 0; h < SIZE_X; h++) {
                //анализ наличие поля для проверки
                if (h + SIZE_WIN <= SIZE_X) {                           //по горизонтале
                    if (checkLineHorisont(v, h, player_DOT) == SIZE_WIN - 1) {
                        if (MoveAiLineHorisont(v, h, Ai_DOT)) return;
                    }

                    if (v - SIZE_WIN > -2) {                            //вверх по диагонале
                        if (checkDiaUp(v, h, player_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaUp(v, h, Ai_DOT)) return;
                        }
                    }
                    if (v + SIZE_WIN <= SIZE_Y) {                       //вниз по диагонале
                        if (checkDiaDown(v, h, player_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaDown(v, h, Ai_DOT)) return;
                        }
                    }
                }
                if (v + SIZE_WIN <= SIZE_Y) {                       //по вертикале
                    if (checkLineVertical(v, h, player_DOT) == SIZE_WIN - 1) {
                        if (MoveAiLineVertical(v, h, Ai_DOT)) return;
                    }
                }
            }
        }
        //игра на победу
        for (int v = 0; v < SIZE_Y; v++) {
            for (int h = 0; h < SIZE_X; h++) {
                //анализ наличие поля для проверки
                if (h + SIZE_WIN <= SIZE_X) {                           //по горизонтале
                    if (checkLineHorisont(v, h, Ai_DOT) == SIZE_WIN - 1) {
                        if (MoveAiLineHorisont(v, h, Ai_DOT)) return;
                    }

                    if (v - SIZE_WIN > -2) {                            //вверх по диагонале
                        if (checkDiaUp(v, h, Ai_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaUp(v, h, Ai_DOT)) return;
                        }
                    }
                    if (v + SIZE_WIN <= SIZE_Y) {                       //вниз по диагонале
                        if (checkDiaDown(v, h, Ai_DOT) == SIZE_WIN - 1) {
                            if (MoveAiDiaDown(v, h, Ai_DOT)) return;
                        }
                    }

                }
                if (v + SIZE_WIN <= SIZE_Y) {                       //по вертикале
                    if (checkLineVertical(v, h, Ai_DOT) == SIZE_WIN - 1) {
                        if (MoveAiLineVertical(v, h, Ai_DOT)) return;
                    }
                }
            }
        }

        //случайный ход
        do {
            y = rnd.nextInt(SIZE_Y);
            x = rnd.nextInt(SIZE_X);
        } while (!checkMove(y, x));
        AI_COORDS[0] = y;
        AI_COORDS[1] = x;
        dotField(y, x, Ai_DOT);
    }

    //ход компьютера по горизонтале
    private static boolean MoveAiLineHorisont(int v, int h, char dot) {
        for (int j = h; j < SIZE_WIN; j++) {
            if ((fieldg[v][j] == EMPTY_DOT)) {
                AI_COORDS[0] = v;
                AI_COORDS[1] = j;
                fieldg[v][j] = dot;
                return true;
            }
        }
        return false;
    }

    //ход компьютера по вертикале
    private static boolean MoveAiLineVertical(int v, int h, char dot) {
        for (int i = v; i < SIZE_WIN; i++) {
            if ((fieldg[i][h] == EMPTY_DOT)) {
                AI_COORDS[0] = i;
                AI_COORDS[1] = h;
                fieldg[i][h] = dot;
                return true;
            }
        }
        return false;
    }
    //проверка заполнения всей линии по диагонале вверх

    private static boolean MoveAiDiaUp(int v, int h, char dot) {
        for (int i = 0, j = 0; j < SIZE_WIN; i--, j++) {
            if ((fieldg[v + i][h + j] == EMPTY_DOT)) {
                AI_COORDS[0] = v + i;
                AI_COORDS[1] = h + j;
                fieldg[v + i][h + j] = dot;
                return true;
            }
        }
        return false;
    }
    //проверка заполнения всей линии по диагонале вниз

    private static boolean MoveAiDiaDown(int v, int h, char dot) {

        for (int i = 0; i < SIZE_WIN; i++) {
            if ((fieldg[i + v][i + h] == EMPTY_DOT)) {
                AI_COORDS[0] = i + v;
                AI_COORDS[1] = i + h;
                fieldg[i + v][i + h] = dot;
                return true;
            }
        }
        return false;
    }

    //проверка заполнения выбранного для хода игроком
    private static boolean checkMove(int y, int x) {
        if (x < 0 || x >= SIZE_X || y < 0 || y >= SIZE_Y) return false;
        else if (!(fieldg[y][x] == EMPTY_DOT)) return false;

        return true;
    }

    //проверка на ничью (все  ячейки поля заполнены ходами)
    public static boolean fullField() {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                if (fieldg[i][j] == EMPTY_DOT) return false;
            }
        }
        System.out.println("Игра закончилась в ничью");
        return true;
    }

    //проверка победы
    public static boolean checkWin(char dot) {
        for (int v = 0; v < SIZE_Y; v++) {
            for (int h = 0; h < SIZE_X; h++) {
                //анализ наличие поля для проверки
                if (h + SIZE_WIN <= SIZE_X) {                           //по горизонтале
                    if (checkLineHorisont(v, h, dot) >= SIZE_WIN) return true;

                    if (v - SIZE_WIN > -2) {                            //вверх по диагонале
                        if (checkDiaUp(v, h, dot) >= SIZE_WIN) return true;
                    }
                    if (v + SIZE_WIN <= SIZE_Y) {                       //вниз по диагонале
                        if (checkDiaDown(v, h, dot) >= SIZE_WIN) return true;
                    }
                }
                if (v + SIZE_WIN <= SIZE_Y) {                       //по вертикале
                    if (checkLineVertical(v, h, dot) >= SIZE_WIN) return true;
                }
            }
        }
        return false;
    }

    //проверка заполнения всей линии по диагонале вверх

    private static int checkDiaUp(int v, int h, char dot) {
        int count = 0;
        for (int i = 0, j = 0; j < SIZE_WIN; i--, j++) {
            if ((fieldg[v + i][h + j] == dot)) count++;
        }
        return count;
    }
    //проверка заполнения всей линии по диагонале вниз

    private static int checkDiaDown(int v, int h, char dot) {
        int count = 0;
        for (int i = 0; i < SIZE_WIN; i++) {
            if ((fieldg[i + v][i + h] == dot)) count++;
        }
        return count;
    }

    //провека заполнения всей линии горизонтале
    private static int checkLineHorisont(int v, int h, char dot) {
        int count = 0;
        for (int j = h; j < SIZE_WIN; j++) {
            if ((fieldg[v][j] == dot)) count++;
        }
        return count;
    }

    //проверка заполнения всей линии по вертикале
    private static int checkLineVertical(int v, int h, char dot) {
        int count = 0;
        for (int i = v; i < SIZE_WIN; i++) {
            if ((fieldg[i][h] == dot)) count++;
        }
        return count;
    }

    public static void main(String[] args) {
        emtpyField();
        printField();
        do {
            playerMove();
            System.out.println("Ваш ход на поле");
            printField();
            if (checkWin(player_DOT)) {
                System.out.println("Вы выиграли");
                break;
            } else if (fullField()) break;
            AiMove();
            System.out.println("Ход Компа на поле");
            printField();
            if (checkWin(Ai_DOT)) {
                System.out.println("Выиграли Комп");
                break;
            } else if (fullField()) break;
        } while (true);
        System.out.println("!Конец игры!");
    }

    public static int[] getAiCoords() {
        return AI_COORDS;
    }

    @Test
    public void test() {
        int t = 3;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < t * t; i++) {
            list.add(i);
        }
        System.out.println(list);
        System.out.println("-----------------");
        int[][] arr = new int[t][t];
        for (int num : list) {
            arr[list.indexOf(num)/t][list.indexOf(num)%t] = num;
        }
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < t; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }
}
