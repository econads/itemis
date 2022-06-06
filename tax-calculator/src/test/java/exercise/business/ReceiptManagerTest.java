package exercise.business;

import exercise.TestUtils;
import exercise.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

class ReceiptManagerTest {

    private final TestUtils testUtils = new TestUtils();

    @Test
    void main() throws ValidationException {
        ReceiptManager.main(new String[]{});
        testUtils.checkFilesMatch("Output1.txt");
    }
}