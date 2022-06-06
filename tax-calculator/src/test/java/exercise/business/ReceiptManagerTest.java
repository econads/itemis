package exercise.business;

import exercise.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class ReceiptManagerEndToEndTest {

    private final TestUtils testUtils = new TestUtils();

    @ParameterizedTest
    @CsvSource({"Input1.txt,Output1.txt", "Input2.txt,Output2.txt", "Input3.txt,Output3.txt"})
    void happyPath(String input, String expectedOutput) {
        ReceiptManager.main(new String[]{"src/test/resources/"+input});
        testUtils.checkFilesMatch(expectedOutput);
    }

    @Test
    void emptyArgs(){
        ReceiptManager.main(new String[]{});
        File actual = new File("Receipt.txt");
        assertThat(actual.exists()).isFalse();
    }

    @Test
    void noFile(){
        ReceiptManager.main(new String[]{"Some-non-existing-file"});
        File actual = new File("Receipt.txt");
        assertThat(actual.exists()).isFalse();
    }

    @AfterEach
    public void cleanup(){
        File actual = new File("Receipt.txt");
        actual.delete();
    }

}