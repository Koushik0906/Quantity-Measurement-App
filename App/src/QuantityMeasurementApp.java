public class QuantityMeasurementApp // Standalone Enum Class
enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(0.393701 / 12.0);

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    // Convert current unit value to base unit (FEET)
    public double convertToBaseUnit(double value) {
        return value * toFeetFactor;
    }

    // Convert FEET to current unit
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toFeetFactor;
    }
}


// Main Application Class
public class QuantityMeasurementApp {

    static class QuantityLength {

        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {

            if (!Double.isFinite(value))
                throw new IllegalArgumentException("Invalid value");

            if (unit == null)
                throw new IllegalArgumentException("Unit cannot be null");

            this.value = value;
            this.unit = unit;
        }

        // Convert current object to base unit FEET
        private double toFeet() {
            return unit.convertToBaseUnit(value);
        }

        // Convert to another unit
        public QuantityLength convertTo(LengthUnit targetUnit) {

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit null");

            double feetValue = this.toFeet();
            double result = targetUnit.convertFromBaseUnit(feetValue);

            return new QuantityLength(result, targetUnit);
        }

        // Add with explicit target unit
        public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {

            if (other == null)
                throw new IllegalArgumentException("Second operand null");

            double totalFeet = this.toFeet() + other.toFeet();

            double result = targetUnit.convertFromBaseUnit(totalFeet);

            return new QuantityLength(result, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            QuantityLength other = (QuantityLength) obj;

            return Math.abs(this.toFeet() - other.toFeet()) < 0.000001;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }


    // Static Conversion API
    public static double convert(double value,
                                 LengthUnit source,
                                 LengthUnit target) {

        if (source == null || target == null)
            throw new IllegalArgumentException("Unit null");

        double feet = source.convertToBaseUnit(value);

        return target.convertFromBaseUnit(feet);
    }

    // Main Method
    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println("Equality Check:");
        System.out.println(q1 + " == " + q2 + " -> " + q1.equals(q2));
        System.out.println();

        System.out.println("Conversion:");
        System.out.println("1 FEET to INCHES = "
                + convert(1.0, LengthUnit.FEET, LengthUnit.INCHES));
        System.out.println();

        System.out.println("Addition:");
        System.out.println(q1 + " + " + q2 + " in YARDS = "
                + q1.add(q2, LengthUnit.YARDS));
    }
}