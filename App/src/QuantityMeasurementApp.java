public class QuantityMeasurementApp {

    // Base Unit = FEET
    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.393701 / 12.0);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double getToFeetFactor() {
            return toFeetFactor;
        }
    }

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

        private double toFeet() {
            return value * unit.getToFeetFactor();
        }

        // UC6 Add (default first operand unit)
        public QuantityLength add(QuantityLength other) {
            return add(other, this.unit);
        }

        // UC7 Add with explicit target unit
        public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {

            if (other == null)
                throw new IllegalArgumentException("Second operand null");

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit null");

            double sumFeet = this.toFeet() + other.toFeet();

            double resultValue = sumFeet / targetUnit.getToFeetFactor();

            return new QuantityLength(resultValue, targetUnit);
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

    // Static helper method
    public static QuantityLength add(QuantityLength q1,
                                     QuantityLength q2,
                                     LengthUnit targetUnit) {

        if (q1 == null || q2 == null)
            throw new IllegalArgumentException("Null operands");

        return q1.add(q2, targetUnit);
    }

    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println("Input: add(" + q1 + ", " + q2 + ", FEET)");
        System.out.println("Output: " + add(q1, q2, LengthUnit.FEET));
        System.out.println();

        System.out.println("Input: add(" + q1 + ", " + q2 + ", INCHES)");
        System.out.println("Output: " + add(q1, q2, LengthUnit.INCHES));
        System.out.println();

        System.out.println("Input: add(" + q1 + ", " + q2 + ", YARDS)");
        System.out.println("Output: " + add(q1, q2, LengthUnit.YARDS));
        System.out.println();

        QuantityLength q3 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q4 = new QuantityLength(3.0, LengthUnit.FEET);

        System.out.println("Input: add(" + q3 + ", " + q4 + ", YARDS)");
        System.out.println("Output: " + add(q3, q4, LengthUnit.YARDS));
        System.out.println();

        QuantityLength q5 = new QuantityLength(36.0, LengthUnit.INCHES);
        QuantityLength q6 = new QuantityLength(1.0, LengthUnit.YARDS);

        System.out.println("Input: add(" + q5 + ", " + q6 + ", FEET)");
        System.out.println("Output: " + add(q5, q6, LengthUnit.FEET));
        System.out.println();

        QuantityLength q7 = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength q8 = new QuantityLength(1.0, LengthUnit.INCHES);

        System.out.println("Input: add(" + q7 + ", " + q8 + ", CENTIMETERS)");
        System.out.println("Output: " + add(q7, q8, LengthUnit.CENTIMETERS));
        System.out.println();

        QuantityLength q9 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q10 = new QuantityLength(0.0, LengthUnit.INCHES);

        System.out.println("Input: add(" + q9 + ", " + q10 + ", YARDS)");
        System.out.println("Output: " + add(q9, q10, LengthUnit.YARDS));
        System.out.println();

        QuantityLength q11 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q12 = new QuantityLength(-2.0, LengthUnit.FEET);

        System.out.println("Input: add(" + q11 + ", " + q12 + ", INCHES)");
        System.out.println("Output: " + add(q11, q12, LengthUnit.INCHES));
    }
}