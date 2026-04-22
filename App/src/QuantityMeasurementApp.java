// Standalone WeightUnit Enum
enum WeightUnit {

    KILOGRAM(1.0),          // Base unit
    GRAM(0.001),           // 1000 g = 1 kg
    POUND(0.453592);       // 1 lb = 0.453592 kg

    private final double toKgFactor;

    WeightUnit(double toKgFactor) {
        this.toKgFactor = toKgFactor;
    }

    // Convert unit value to KG
    public double convertToBaseUnit(double value) {
        return value * toKgFactor;
    }

    // Convert KG to target unit
    public double convertFromBaseUnit(double kgValue) {
        return kgValue / toKgFactor;
    }
}


// Main Application
public class QuantityMeasurementApp {

    static class QuantityWeight {

        private final double value;
        private final WeightUnit unit;

        public QuantityWeight(double value, WeightUnit unit) {

            if (!Double.isFinite(value))
                throw new IllegalArgumentException("Invalid value");

            if (unit == null)
                throw new IllegalArgumentException("Unit cannot be null");

            this.value = value;
            this.unit = unit;
        }

        // Convert to KG
        private double toKg() {
            return unit.convertToBaseUnit(value);
        }

        // Convert to target unit
        public QuantityWeight convertTo(WeightUnit targetUnit) {

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit null");

            double kg = this.toKg();
            double result = targetUnit.convertFromBaseUnit(kg);

            return new QuantityWeight(result, targetUnit);
        }

        // Add (default first operand unit)
        public QuantityWeight add(QuantityWeight other) {
            return add(other, this.unit);
        }

        // Add with explicit target unit
        public QuantityWeight add(QuantityWeight other,
                                  WeightUnit targetUnit) {

            if (other == null)
                throw new IllegalArgumentException("Second operand null");

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit null");

            double totalKg = this.toKg() + other.toKg();

            double result = targetUnit.convertFromBaseUnit(totalKg);

            return new QuantityWeight(result, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            QuantityWeight other = (QuantityWeight) obj;

            return Math.abs(this.toKg() - other.toKg()) < 0.000001;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }


    // Static convert API
    public static double convert(double value,
                                 WeightUnit source,
                                 WeightUnit target) {

        if (source == null || target == null)
            throw new IllegalArgumentException("Unit null");

        double kg = source.convertToBaseUnit(value);

        return target.convertFromBaseUnit(kg);
    }


    public static void main(String[] args) {

        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        System.out.println("Equality:");
        System.out.println(w1 + " == " + w2 + " -> " + w1.equals(w2));
        System.out.println();

        System.out.println("Conversion:");
        System.out.println("2 KG to GRAM = "
                + convert(2.0, WeightUnit.KILOGRAM, WeightUnit.GRAM));

        System.out.println("5 POUND to KG = "
                + convert(5.0, WeightUnit.POUND, WeightUnit.KILOGRAM));
        System.out.println();

        System.out.println("Addition:");
        QuantityWeight w3 = new QuantityWeight(2.0, WeightUnit.KILOGRAM);
        QuantityWeight w4 = new QuantityWeight(500.0, WeightUnit.GRAM);

        System.out.println(w3 + " + " + w4 + " = "
                + w3.add(w4));

        System.out.println(w3 + " + " + w4 + " in POUND = "
                + w3.add(w4, WeightUnit.POUND));
    }
}