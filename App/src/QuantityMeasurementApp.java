public class QuantityMeasurementApp {

    // Enum with conversion factor relative to FEET
    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),              // 12 inches = 1 foot
        YARDS(3.0),                     // 1 yard = 3 feet
        CENTIMETERS(0.393701 / 12.0);   // 1 cm = 0.393701 inch

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double getToFeetFactor() {
            return toFeetFactor;
        }
    }

    // QuantityLength Class
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid numeric value");
            }

            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }

            this.value = value;
            this.unit = unit;
        }

        // Convert current object to feet
        private double toFeet() {
            return value * unit.getToFeetFactor();
        }

        // Convert current object to target unit
        public QuantityLength convertTo(LengthUnit targetUnit) {
            double convertedValue =
                    QuantityMeasurementApp.convert(this.value, this.unit, targetUnit);

            return new QuantityLength(convertedValue, targetUnit);
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

    // Static API Conversion Method
    public static double convert(double value,
                                 LengthUnit source,
                                 LengthUnit target) {

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }

        if (source == null || target == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }

        // Convert source to feet
        double feetValue = value * source.getToFeetFactor();

        // Convert feet to target
        return feetValue / target.getToFeetFactor();
    }

    // Overloaded method 1
    public static void demonstrateLengthConversion(double value,
                                                   LengthUnit from,
                                                   LengthUnit to) {

        double result = convert(value, from, to);

        System.out.println("Input: convert(" + value + ", " + from + ", " + to + ")");
        System.out.println("Output: " + result);
        System.out.println();
    }

    // Overloaded method 2
    public static void demonstrateLengthConversion(QuantityLength q,
                                                   LengthUnit to) {

        QuantityLength result = q.convertTo(to);

        System.out.println("Input: " + q + " convertTo " + to);
        System.out.println("Output: " + result);
        System.out.println();
    }

    // Main Method
    public static void main(String[] args) {

        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES);
        demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET);
        demonstrateLengthConversion(36.0, LengthUnit.INCHES, LengthUnit.YARDS);
        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES);
        demonstrateLengthConversion(0.0, LengthUnit.FEET, LengthUnit.INCHES);

        QuantityLength q1 = new QuantityLength(2.0, LengthUnit.YARDS);
        demonstrateLengthConversion(q1, LengthUnit.INCHES);
    }
}