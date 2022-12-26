package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    //Заведите в статических полях три счётчика - по одному для длин: 3, 4 и 5
    public static AtomicInteger counterThreeLetters = new AtomicInteger(0);
    public static AtomicInteger counterFourLetters = new AtomicInteger(0);
    public static AtomicInteger counterFiveLetters = new AtomicInteger(0);


    //Создайте генератор текстов и сгенерируйте набор из 100'000 текстов, используя код из описания задачи
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        //Заведите три потока - по одному на каждый критерий "красоты" слова
        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (methodPalindrom(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (methodTheSame(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        thread2.start();

        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (methodInOrder(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
        System.out.println("Красивых слов с длиной 3: " + counterThreeLetters + " шт");
        System.out.println("Красивых слов с длиной 4: " + counterFourLetters + " шт");
        System.out.println("Красивых слов с длиной 5: " + counterFiveLetters + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    // Каждый поток проверяет все тексты на "красоту" и увеличивает счётчик нужной длины, если текст соответствует критериям.
    public static boolean methodPalindrom(String text) {
        for (int i = 0; i < text.length() / 2; i++) {
            if (text.charAt(i) != text.charAt(text.length() - 1 - i))
                return false;
        }
        return true;
    }

    public static boolean methodTheSame(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) != text.charAt(i + 1))
                return false;
        }
        return true;
    }

    public static boolean methodInOrder(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) > text.charAt(i + 1))
                return false;
        }
        return true;
    }

    private static void incrementCounter(int textLength) {
        switch (textLength) {
            case 3 -> counterThreeLetters.getAndIncrement();
            case 4 -> counterFourLetters.getAndIncrement();
            case 5 -> counterFiveLetters.getAndIncrement();

        }
    }
}


