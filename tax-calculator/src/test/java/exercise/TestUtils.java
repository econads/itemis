package exercise;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

public class TestUtils {

    public void checkFilesMatch(String expectedFileName) {
        File expected = new File("src/test/resources/" + expectedFileName);
        File actual = new File("Receipt.txt");
        assertThat(expected.exists()).isTrue();
        assertThat(actual.exists()).isTrue();
        assertThat(actual).hasSameTextualContentAs(expected);
    }
}