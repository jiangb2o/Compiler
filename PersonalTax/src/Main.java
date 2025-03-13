import java.util.Scanner;

/**
 * Main
 */
public class Main {
    /**
     * main method
     * @param args run args
     */
    public static void main(String[] args) {
        TaxConfig taxConfig = new TaxConfig();
        float income;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Personal Tex");
        taxConfig.printTaxConfig();
        // test
        if(args.length != 0) {
            for (String arg : args) {
                System.out.println();
                System.out.println("income:" + arg + ":");
                taxConfig.calculateTax(Float.parseFloat(arg));
            }
        } else {
            while(true) {
                System.out.println("input personal income(input 0 if you want to modify tax config):");
                try {
                    income = scanner.nextFloat();
                    if(income != 0) break;
                    taxConfig.setTaxConfig();
                    taxConfig.printTaxConfig();
                } catch (Exception e) {
                    System.out.println("invalid input");
                    scanner.nextLine();
                }
            }

            taxConfig.calculateTax(income);
        }
    }
}