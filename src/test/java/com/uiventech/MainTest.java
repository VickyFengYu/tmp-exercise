package com.uiventech;

import org.apache.commons.io.FileUtils;
import org.junit.gen5.api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainTest {
    Main instance;

    private static final String MOCK_PATH = "src/test/java/com/uiventech/test.text";
    private static final int MOCK_CHARACTER_LENGTH = 100;

    @BeforeAll()
    void createInstance() {
        instance = new Main();
    }

    @Test
    @ParameterizedTest(name = "number of lines {0} is an invalid number.")
    @ValueSource(ints = {0, 230})
    public void test_line_number_boundaries(int numberOfLines) {

        Assertions.assertThrows(InvalidParameterException.class, () -> {
            instance.generateUniqueStrings(numberOfLines, MOCK_PATH, MOCK_CHARACTER_LENGTH);
        });

    }

    @Test
    @ParameterizedTest(name = "test generated file of specified lines")
    @ValueSource(ints = {5})
    public void test_file_generated(int numberOfLines) throws IOException, InterruptedException {
        instance.generateUniqueStrings(numberOfLines, MOCK_PATH, MOCK_CHARACTER_LENGTH);

        File tmpFileDir = new File(MOCK_PATH);
        boolean exists = tmpFileDir.exists();
        Assertions.assertTrue(exists);

        String content = FileUtils.readFileToString(tmpFileDir, StandardCharsets.UTF_8);
        Assertions.assertEquals(numberOfLines * MOCK_CHARACTER_LENGTH, content.replaceAll("(\r|\n)", "").length());

        Assertions.assertTrue(tmpFileDir.delete());

        Assertions.assertFalse(new File(MOCK_PATH).exists());
    }
}
