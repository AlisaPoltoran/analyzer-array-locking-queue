import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        final int numberOfTexts = 10_000;
        final int lengthOfTexts = 100_000;

        Random random = new Random();

        new Thread(() -> {
            for (int i = 0; i < numberOfTexts; i++) {
                String text = generateText("abc", lengthOfTexts + random.nextInt(3));
                try {
                    a.put(text);
                    b.put(text);
                    c.put(text);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Callable findA = () -> {
            String maxA = "a";
            int numberOfAsInMaxA = 0;
            for (int i = 0; i < numberOfTexts; i++) {
                int counter = 0;
                try {
                    String text = a.take();
                    char[] lettersInText = text.toCharArray();
                    for (char value : lettersInText) {
                        if (value == 'a') {
                            counter++;
                        }
                    }
                    if (counter >= numberOfAsInMaxA) {
                        maxA = text;
                        numberOfAsInMaxA = counter;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return maxA;
        };

        Callable findB = () -> {
            String maxB = "b";
            int numberOfAsInMaxB = 0;
            for (int i = 0; i < numberOfTexts; i++) {
                int counter = 0;
                try {
                    String text = b.take();
                    char[] lettersInText = text.toCharArray();
                    for (char value : lettersInText) {
                        if (value == 'b') {
                            counter++;
                        }
                    }
                    if (counter >= numberOfAsInMaxB) {
                        maxB = text;
                        numberOfAsInMaxB = counter;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return maxB;
        };

        Callable findC = () -> {
            String maxC = "c";
            int numberOfAsInMaxC = 0;
            for (int i = 0; i < numberOfTexts; i++) {
                int counter = 0;
                try {
                    String text = c.take();
                    char[] lettersInText = text.toCharArray();
                    for (char value : lettersInText) {
                        if (value == 'c') {
                            counter++;
                        }
                    }
                    if (counter >= numberOfAsInMaxC) {
                        maxC = text;
                        numberOfAsInMaxC = counter;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return maxC;
        };

        FutureTask<String> futureA = new FutureTask<>(findA);
        FutureTask<String> futureB = new FutureTask<>(findB);
        FutureTask<String> futureC = new FutureTask<>(findC);
        new Thread(futureA).start();
        new Thread(futureB).start();
        new Thread(futureC).start();
    }

    public static ArrayBlockingQueue<String> a = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> b = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> c = new ArrayBlockingQueue<>(100);


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
