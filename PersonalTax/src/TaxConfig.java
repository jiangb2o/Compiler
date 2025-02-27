import java.util.Scanner;

import static java.lang.Math.min;

public class TaxConfig {
    private int levelCount;
    private int taxThreshold;
    private Pair<Float, Float>[] taxRates;

    public TaxConfig() {
        this.levelCount = 5;
        this.taxThreshold = 1600;
        this.taxRates = new Pair[this.levelCount];

        this.taxRates[0] = new Pair(500f, 0.05f);
        this.taxRates[1] = new Pair(2000f, 0.1f);
        this.taxRates[2] = new Pair(5000f, 0.15f);
        this.taxRates[3] = new Pair(20000f, 0.2f);
        this.taxRates[4] = new Pair(Float.MAX_VALUE, 0.25f);
    }

    public void setTaxConfig() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Set Tax Config\n");
        System.out.print("input tax threshold:");
        taxThreshold = scanner.nextInt();
        System.out.print("input level count:");
        levelCount = scanner.nextInt();

        taxRates = new Pair[levelCount];
        for (int i = 0; i < levelCount; i++) {
            float taxableIncome = Float.MAX_VALUE;
            float rate;
            if(i == levelCount - 1) {
                System.out.print("Input level " + (i + 1) + " tax rate (last level): ");
                rate = scanner.nextFloat();
            } else {
                System.out.print("Input level " + (i + 1) + " taxable income and tax rate(split by space): ");
                taxableIncome = scanner.nextFloat();
                rate = scanner.nextFloat();
            }
            taxRates[i] = new Pair(taxableIncome, rate);
        }
    }

    public float CalculateTax(float income) {
        float totalTax = 0;
        float taxableIncome = income - taxThreshold;
        if(taxableIncome <= 0) return 0;

        for(int i = 0; i < levelCount; ++i) {
            float levelTaxable = 0;
            float levelTax = 0;
            if(i == 0) {
                levelTaxable = min(taxableIncome, taxRates[i].first);
            } else {
                levelTaxable = min(taxableIncome, taxRates[i].first) - taxRates[i-1].first;
            }
            if(levelTaxable <= 0) break;

            levelTax = levelTaxable * taxRates[i].second;
            totalTax += levelTax;
            System.out.format("Level %d : taxable is %.2f tax is %.2f * %.2f = %.2f\n",
                    i + 1, levelTaxable, levelTaxable, taxRates[i].second, levelTax);
        }
        System.out.format("Total Tax : %.2f\n", totalTax);

        return totalTax;
    }

    public void printTaxConfig() {
        System.out.print( "Current Tax Config: \n");
        System.out.println("\tTax Threshold: " + taxThreshold);
        System.out.println("\tTax Level Count: " + levelCount);
        for(int i = 0; i < levelCount; ++i) {
            System.out.print("\tLevel " + (i + 1) + ": ");
            String ceil = i == levelCount - 1 ? "  inf" : String.format("%5.0f",taxRates[i].first);
            String floor = i == 0 ? "    0" : String.format("%5.0f", taxRates[i-1].first);

            System.out.println("[" + floor + ',' + ceil + "]\t" + taxRates[i].second);
        }
    }

}
