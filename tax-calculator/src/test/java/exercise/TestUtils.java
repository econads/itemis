package exercise;

import org.assertj.core.api.Assertions;

import java.io.File;

public class TestUtils {

    public void checkFilesMatch(String expectedFileName) {
        File expected = new File("src/test/resources/" + expectedFileName);
        File actual = new File("Receipt.txt");
        Assertions.assertThat(expected.exists()).isTrue();
        Assertions.assertThat(actual.exists()).isTrue();
        Assertions.assertThat(actual).hasSameTextualContentAs(expected);
    }
}