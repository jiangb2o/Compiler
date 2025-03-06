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

        System.out.print("Personal Tex");
        while(true) {
            taxConfig.printTaxConfig();
            System.out.println("input personal income(input 0 if you want to modify tax config):");
            income = scanner.nextFloat();
            if(income != 0) break;
            taxConfig.setTaxConfig();
        }

        taxConfig.calculateTax(income);
    }
}