import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

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

        new Thread(() -> {
            int result = findMaxNumberOfChar(a, 'a');
            System.out.println("Максимальное кол-во А в тексте: " + result);
        }).start();

        new Thread(() -> {
            int result = findMaxNumberOfChar(b, 'b');
            System.out.println("Максимальное кол-во B в тексте: " + result);
        }).start();

        new Thread(() -> {
            int result = findMaxNumberOfChar(c, 'c');
            System.out.println("Максимальное кол-во C в тексте: " + result);
        }).start();

//        Callable findA = () -> {
//            String maxA = "a";
//            int numberOfAsInMaxA = 0;
//            for (int i = 0; i < numberOfTexts; i++) {
//                int counter = 0;
//                try {
//                    String text = a.take();
//                    char[] lettersInText = text.toCharArray();
//                    for (char value : lettersInText) {
//                        if (value == 'a') {
//                            counter++;
//                        }
//                    }
//                    if (counter >= numberOfAsInMaxA) {
//                        maxA = text;
//                        numberOfAsInMaxA = counter;
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            return numberOfAsInMaxA;
//        };
//
//        Callable findB = () -> {
//            String maxB = "b";
//            int numberOfAsInMaxB = 0;
//            for (int i = 0; i < numberOfTexts; i++) {
//                int counter = 0;
//                try {
//                    String text = b.take();
//                    char[] lettersInText = text.toCharArray();
//                    for (char value : lettersInText) {
//                        if (value == 'b') {
//                            counter++;
//                        }
//                    }
//                    if (counter >= numberOfAsInMaxB) {
//                        maxB = text;
//                        numberOfAsInMaxB = counter;
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            return numberOfAsInMaxB;
//        };
//
//        Callable findC = () -> {
//            String maxC = "c";
//            int numberOfAsInMaxC = 0;
//            for (int i = 0; i < numberOfTexts; i++) {
//                int counter = 0;
//                try {
//                    String text = c.take();
//                    char[] lettersInText = text.toCharArray();
//                    for (char value : lettersInText) {
//                        if (value == 'c') {
//                            counter++;
//                        }
//                    }
//                    if (counter >= numberOfAsInMaxC) {
//                        maxC = text;
//                        numberOfAsInMaxC = counter;
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            return numberOfAsInMaxC;
//        };
//
//        FutureTask<Integer> futureA = new FutureTask<>(findA);
//        FutureTask<Integer> futureB = new FutureTask<>(findB);
//        FutureTask<Integer> futureC = new FutureTask<>(findC);
//        Thread tA = new Thread(futureA);
//        Thread tB = new Thread(futureB);
//        Thread tC = new Thread(futureC);
//
//        tA.start();
//        tB.start();
//        tC.start();
//
//        try {
//            tA.join();
//            tB.join();
//            tC.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Максимальное кол-во А в тексте: " + futureA.get());
//        System.out.println("Максимальное кол-во B в тексте: " + futureB.get());
//        System.out.println("Максимальное кол-во C в тексте: " + futureC.get());
    }

    public static ArrayBlockingQueue<String> a = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> b = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> c = new ArrayBlockingQueue<>(100);

    final static int numberOfTexts = 10_000;
    final static int lengthOfTexts = 100_000;

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static int findMaxNumberOfChar (ArrayBlockingQueue<String> arrayBlockingQueue, char letter) {
        int counter;
        int max = 0;
        for (int i = 0; i < numberOfTexts; i++) {
            counter = 0;
            try {
                String text = arrayBlockingQueue.take();
                char[] lettersInText = text.toCharArray();
                for (char value : lettersInText) {
                    if (value == letter) {
                        counter++;
                    }
                }
                if (counter >= max) {
                    max = counter;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return max;
    }
}
