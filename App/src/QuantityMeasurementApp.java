
public class QuantityMeasurementApp {

    // Enum for all supported units (base unit = FEET)
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

    // Generic QuantityLength class
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        // Convert any unit to feet
        private double convertToFeet() {
            return value * unit.getToFeetFactor();
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(this.convertToFeet(),
                    other.convertToFeet()) == 0;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // Main Method
    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(3.0, LengthUnit.FEET);

        QuantityLength q3 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q4 = new QuantityLength(36.0, LengthUnit.INCHES);

        QuantityLength q5 = new QuantityLength(2.0, LengthUnit.YARDS);
        QuantityLength q6 = new QuantityLength(2.0, LengthUnit.YARDS);

        QuantityLength q7 = new QuantityLength(2.0, LengthUnit.CENTIMETERS);
        QuantityLength q8 = new QuantityLength(2.0, LengthUnit.CENTIMETERS);

        QuantityLength q9 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        QuantityLength q10 = new QuantityLength(0.393701, LengthUnit.INCHES);

        System.out.println("Input: " + q1 + " and " + q2);
        System.out.println("Output: Equal (" + q1.equals(q2) + ")\n");

        System.out.println("Input: " + q3 + " and " + q4);
        System.out.println("Output: Equal (" + q3.equals(q4) + ")\n");

        System.out.println("Input: " + q5 + " and " + q6);
        System.out.println("Output: Equal (" + q5.equals(q6) + ")\n");

        System.out.println("Input: " + q7 + " and " + q8);
        System.out.println("Output: Equal (" + q7.equals(q8) + ")\n");

        System.out.println("Input: " + q9 + " and " + q10);
        System.out.println("Output: Equal (" + q9.equals(q10) + ")");
    }
}