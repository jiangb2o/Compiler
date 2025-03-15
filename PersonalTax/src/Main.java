import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main
 */
public class Main {
    private static TaxConfig taxConfig;
    private static Scanner scanner;

    /**
     * main method
     * @param args run args
     */
    public static void main(String[] args) {
        taxConfig = new TaxConfig();
        scanner = new Scanner(System.in);

        taxConfig.printTaxConfig();
        // test
        if(args.length != 0) {
            for (String arg : args) {
                System.out.println();
                System.out.println("income:" + arg + ":");
                taxConfig.calculateTax(Float.parseFloat(arg));
            }
        } else {
            // user input
            while(true) {
                printMenu();
                int operation = getInput(Integer.class);
                switch(operation) {
                    case 1:
                        calculateTex();
                        break;
                    case 2:
                        adjustTaxThreshold();
                        break;
                    case 3:
                        modifyTaxRates();
                        break;
                    case 4:
                        System.out.println("Program Exit.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid operation");
                }
            }
        }
    }

    /**
     * Print Menu
     */
    private static void printMenu(){
        System.out.println("----Personal Tax----");
        System.out.println("input to select operation:");
        System.out.println("1. Calculate Tax");
        System.out.println("2. Adjust Tax Threshold");
        System.out.println("3. Modify Tax Rates");
        System.out.println("4. Exit");
    }

    /**
     * Get User income and calculate tex
     */
    private static void calculateTex() {
        System.out.println("Enter the income value:");
        float income = getInput(Float.class);
        if(income < 0) {
            System.out.println("Invalid income value");
            return;
        }
        taxConfig.calculateTax(income);
    }

    /**
     * Get user input to adjust tax threshold
     */
    private static void adjustTaxThreshold() {
        System.out.println("Enter the new tax threshold:");
        int taxThreshold = getInput(Integer.class);
        if(taxThreshold < 0) {
            System.out.println("Invalid tax threshold");
            return;
        }
        taxConfig.adjustTaxThreshold(taxThreshold);
        taxConfig.printTaxConfig();
    }

    /**
     * Get user input to modify tax rates
     */
    private static void modifyTaxRates() {
        List<Pair<Float, Float>> taxRates = new ArrayList<>();

        System.out.print("Set Tax Config\n");
        System.out.print("Input level count:");
        int levelCount = getInput(Integer.class);

        for (int i = 0; i < levelCount; i++) {
            float taxableIncome = Float.MAX_VALUE;
            float rate;
            if(i == levelCount - 1) {
                System.out.print("Input level " + (i + 1) + " tax rate (last level): ");
                rate = getInput(Float.class);
            } else {
                System.out.print("Input level " + (i + 1) + " taxable income and tax rate(split by space): ");
                taxableIncome = getInput(Float.class);
                rate = getInput(Float.class);
            }
            taxRates.add(new Pair<>(taxableIncome, rate));
        }
        taxConfig.setTaxConfig(taxRates);
        taxConfig.printTaxConfig();
    }

    /**
     * get T input with catch exception
     * @return input
     * @param type target Class object
     * @param <T> target type
     */
    private static <T> T getInput(Class<T> type) {
        T input;
        while(true) {
            try {
                if(type.equals(String.class)) {
                    input = type.cast(scanner.next());
                } else if(type.equals(Float.class)) {
                    input = type.cast(scanner.nextFloat());
                } else if(type.equals(Integer.class)) {
                    input = type.cast(scanner.nextInt());
                } else if(type.equals(Double.class)) {
                    input = type.cast(scanner.nextDouble());
                } else {
                    throw new Exception("Invalid input type");
                }
                return input;
            } catch (Exception e) {
                System.out.println("invalid input");
                scanner.nextLine();
            }
        }
    }
}