import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.min;

/**
 * TaxConfig class manages tax configuration and calculations.
 */
public class TaxConfig {
    private int levelCount;
    private int taxThreshold;
    private List<Pair<Float, Float>> taxRates;

    /**
     * Constructor
     * Set default TaxConfig
     */
    public TaxConfig() {
        this.levelCount = 5;
        this.taxThreshold = 1600;
        this.taxRates = new ArrayList<>();

        this.taxRates.add(new Pair<>(500f, 0.05f));
        this.taxRates.add(new Pair<>(2000f, 0.1f));
        this.taxRates.add(new Pair<>(5000f, 0.15f));
        this.taxRates.add(new Pair<>(20000f, 0.2f));
        this.taxRates.add(new Pair<>(Float.MAX_VALUE, 0.25f));
    }

    /**
     * Set new tax threshold
     * @param taxThreshold new tax threshold
     */
    public void adjustTaxThreshold(int taxThreshold) {
        this.taxThreshold = taxThreshold;
    }

    /**
     * Set new TaxConfig
     * @param taxRates new tax rates
     */
    public void setTaxConfig(List<Pair<Float, Float>> taxRates) {
        this.taxRates = taxRates;
        levelCount = taxRates.size();
    }

    /**
     * Calculates the tax based on the given income.
     *
     * @param income The income to calculate tax for.
     */
    public void calculateTax(float income) {
        float totalTax = 0;
        float taxableIncome = income - taxThreshold;
        if(taxableIncome <= 0) {
            System.out.println("Tax threshold not reached.");
            System.out.format("Total Tax : %.2f\n", 0.0f);
            return ;
        }

        System.out.format("Total Taxable is: %.2f\n", taxableIncome);
        for(int i = 0; i < levelCount; ++i) {
            float levelTaxable;
            float levelTax;
            if(i == 0) {
                levelTaxable = min(taxableIncome, taxRates.get(i).first);
            } else {
                levelTaxable = min(taxableIncome, taxRates.get(i).first) - taxRates.get(i-1).first;
            }
            if(levelTaxable <= 0) {
                levelTaxable = 0;
            }

            levelTax = levelTaxable * taxRates.get(i).second;
            totalTax += levelTax;
            System.out.format("Level %2d : taxable is %8.2f tax is %8.2f * %.2f = %8.2f\n",
                    i + 1, levelTaxable, levelTaxable, taxRates.get(i).second, levelTax);
        }
        System.out.format("Total Tax : %.2f\n", totalTax);
    }

    /**
     * Prints the current tax configuration.
     */
    public void printTaxConfig() {
        System.out.print( "Current Tax Config: \n");
        System.out.println("\tTax Threshold: " + taxThreshold);
        System.out.println("\tTax Level Count: " + levelCount);
        for(int i = 0; i < levelCount; ++i) {
            System.out.print("\tLevel " + (i + 1) + ": ");
            String ceil = i == levelCount - 1 ? "  inf" : String.format("%5.0f",taxRates.get(i).first);
            String floor = i == 0 ? "    0" : String.format("%5.0f", taxRates.get(i-1).first);

            System.out.println("[" + floor + ',' + ceil + "]\t" + taxRates.get(i).second);
        }
    }

}
