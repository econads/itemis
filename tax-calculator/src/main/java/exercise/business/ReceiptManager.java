package exercise.business;

import exercise.domain.Receipt;
import exercise.exceptions.BlockingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReceiptManager {

    private static final Logger logger = LogManager.getLogger(ReceiptManager.class);

    public static void main(String[] args) {

        try {
            validateArgs(args);

            String inputFileName = args[0];

            logger.debug("Creating receipt from {}", inputFileName);

            Receipt receipt = new Receipt();

            receipt.readInReceiptFromFile(inputFileName);
            receipt.printReceipt();

        } catch (BlockingException e){
            logger.error("Something went wrong, sorry.", e);
        }
    }

    private static void validateArgs(String[] args) throws BlockingException {
        if (args == null || args.length != 1){
            logger.error("Please supply a filepath to an input file consisting only of lines with the following pattern:");
            logger.error("1 <item name> at <item price>");
            logger.error("The following items are recognised:");
            logger.error("book, music CD, chocolate bar, box of chocolates, bottle of perfume, packet of headache pills");
            logger.error("if import tax should be applied please include the word 'imported' in the name, e.g:");
            logger.error("1 imported box of chocolates at 10.00");
            throw new BlockingException("Wrong arguments");
        }
    }
}
