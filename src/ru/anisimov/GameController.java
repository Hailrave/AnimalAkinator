package ru.anisimov;

import java.io.*;

public class GameController {
    private Statement root = null;
    private static final String FIlENAME = "rootFile.txt";


    private void initializeRoot() throws IOException, ClassNotFoundException {
        File file = new File(FIlENAME);
        if (file.length() == 0) { //инициализируем дефолтно, если ничего не записано в файл
            //System.out.println("Проинициализирован дефолтно");
            root = new Statement("Это животное живет на суше?");
            root.yesAnswer = new Statement("кот");
            root.noAnswer = new Statement("кит");
        }
        else {
            //System.out.println("Проинициализирован из файла");
            FileInputStream fis = new FileInputStream(FIlENAME);
            ObjectInputStream oin = new ObjectInputStream(fis);
            root = (Statement) oin.readObject();
        }
    }

    public void startGame(){
        try {
            initializeRoot();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка инициализации объекта root");
        }

        System.out.println("Программа понимает только \"да\" и \"нет\"");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("Загадайте животное, а я попробую угадать...");
                guessAnimal(root, reader);
                System.out.println("Хотите сыграть ещё?");
                String continueGame = reader.readLine();

                if (continueGame.equals("нет")) {
                    FileOutputStream fos = new FileOutputStream(FIlENAME);
                    ObjectOutputStream oon = new ObjectOutputStream(fos);
                    oon.writeObject(root);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка в чтении");
            e.printStackTrace();
        }
    }

    private void guessAnimal(Statement statement, BufferedReader reader) throws IOException {
        System.out.println(statement);
        String answer = reader.readLine();
        if (answer.equals("да")) {
            if (statement.yesAnswer.yesAnswer != null) {
                guessAnimal(statement.yesAnswer, reader);
            }
            else {
                statement.yesAnswer = defAnimal(statement.yesAnswer, reader);
            }
        }
        else {
            if (statement.noAnswer.yesAnswer != null) {
                guessAnimal(statement.noAnswer, reader);
            }
            else {
                statement.noAnswer = defAnimal(statement.noAnswer, reader);
            }
        }
    }

    private Statement defAnimal(Statement statement, BufferedReader reader) throws IOException {
        System.out.println("Ваше животное " + statement + "?");
        if (reader.readLine().equals("нет")) {
            System.out.println("Какое животное ты загадал?");
            String hiddenAnimal = reader.readLine();
            System.out.println("Чем \"" + hiddenAnimal + "\" отличается от \"" + statement + "\"?");
            String predicate = reader.readLine();
            String gmf = "Это животное " + predicate + "?";
            Statement temp = statement;
            statement = new Statement(gmf);
            statement.yesAnswer = new Statement(hiddenAnimal);
            statement.noAnswer = temp;
            System.out.println("Данные обновлены.");
        } else {
            System.out.println("Победа!!!");
        }
        return statement;
    }
}
