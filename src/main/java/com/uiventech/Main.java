package com.uiventech;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Arrays;

public class Main {
    private static final int NUMBER_OF_lINES_LOW_BOUNDARY = 1;
    private static final int NUMBER_OF_lINES_HIGH_BOUNDARY = 230 - 1;
    private static final long MEGABYTE = 1024L * 1024L;

    private static final String PATH = "src/main/java/com/uiventech/test.text";

    public static void main(String[] args) throws IOException, InterruptedException {
        Thread.sleep(2000);
        Main instance = new Main();
        instance.generateUniqueStrings(10, PATH, 100);
    }

    public void generateUniqueStrings(int numberOfLines, String path, int characterLength) throws IOException, InterruptedException {
        if (numberOfLines < NUMBER_OF_lINES_LOW_BOUNDARY ||
                numberOfLines > NUMBER_OF_lINES_HIGH_BOUNDARY) {
            throw new InvalidParameterException("invalid numberOfLines");
        }

        byte[] bytes = new byte[characterLength];
        Arrays.fill(bytes, (byte) 'a');

        byte[] crlf = new byte[]{'\r', '\n'};

        FileOutputStream fileOutputStream = new FileOutputStream(path);

        for (int i = 0; i < numberOfLines; i++) {
            fileOutputStream.write(bytes);
            fileOutputStream.write(crlf);
            fileOutputStream.flush();

            for (int j = 0; j < characterLength; j++) {
                bytes[j]++;
                if (bytes[j] <= 'z') {
                    break;
                } else {
                    bytes[j] = 'a';
                }
            }
        }

        fileOutputStream.close();

        Runtime runtime = Runtime.getRuntime();
        runtime.gc();

        long memory = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("Used memory in bytes: " + memory + " Used memory in megabytes: " + bytesToMegabytes(memory));
    }

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }

}
