# Itemis coding challenge

## Problem 1: SALES TAXES
Basic sales tax is applicable at a rate of 10% on all goods, except books, food, and medical products that are exempt.
Import duty is an additional sales tax applicable on all imported goods at a rate of 5%, with no exemptions. When I
purchase items I receive a receipt which lists the name of all the items and their price (including tax), finishing with
the total cost of the items, and the total amounts of sales taxes paid. The rounding rules for sales tax are that for a
tax rate of n%, a shelf price of p contains (np/100 rounded up to the nearest 0.05) amount of sales tax. Write an
application that prints out the receipt details for these shopping baskets...

## Assumptions/Limitations
- This code will only be used for a single unspecified currency with 100 smaller units to a big unit (100c,p = 1EUR,GBP etc.)
- The only items on the receipts will be those listed in the inputs to the exercise
- The code will be run locally for 1 person, no remote calls necessary
- Each line of the receipt will be only 1 item, e.g. '2 bottles of perfume at 12.00 each' is excluded

## Run instructions
- Build the jar with ```mvn clean package```
- The jar is built as ```tax-calculator-1.0-SNAPSHOT-jar-with-dependencies.jar```  in the target folder
- The jar can be run as usual with ```java -jar <path to jar> <input file name>```.  Possible input files are in
src/test/resources as Input[n].txt
- Running the jar provides a receipt called Receipt.txt in the same directory the jar is run from, which is generated
from the input file supplied as the argument.