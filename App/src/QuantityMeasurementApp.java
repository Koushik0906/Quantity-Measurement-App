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

        // Convert to base unit FEET
        private double toFeet() {
            return value * unit.getToFeetFactor();
        }

        // Convert to target unit
        public QuantityLength convertTo(LengthUnit targetUnit) {
            double converted =
                    QuantityMeasurementApp.convert(this.value, this.unit, targetUnit);

            return new QuantityLength(converted, targetUnit);
        }

        // Add another quantity (result in first operand unit)
        public QuantityLength add(QuantityLength other) {

            if (other == null)
                throw new IllegalArgumentException("Second operand cannot be null");

            double sumFeet = this.toFeet() + other.toFeet();

            double resultValue = sumFeet / this.unit.getToFeetFactor();

            return new QuantityLength(resultValue, this.unit);
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

    // Static convert method
    public static double convert(double value,
                                 LengthUnit source,
                                 LengthUnit target) {

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");

        if (source == null || target == null)
            throw new IllegalArgumentException("Unit cannot be null");

        double feet = value * source.getToFeetFactor();

        return feet / target.getToFeetFactor();
    }

    // Static add method
    public static QuantityLength add(QuantityLength q1,
                                     QuantityLength q2) {

        if (q1 == null || q2 == null)
            throw new IllegalArgumentException("Operands cannot be null");

        return q1.add(q2);
    }

    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(2.0, LengthUnit.FEET);

        QuantityLength q3 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q4 = new QuantityLength(12.0, LengthUnit.INCHES);

        QuantityLength q5 = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength q6 = new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength q7 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q8 = new QuantityLength(3.0, LengthUnit.FEET);

        QuantityLength q9 = new QuantityLength(36.0, LengthUnit.INCHES);
        QuantityLength q10 = new QuantityLength(1.0, LengthUnit.YARDS);

        QuantityLength q11 = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength q12 = new QuantityLength(1.0, LengthUnit.INCHES);

        System.out.println("Input: add(" + q1 + ", " + q2 + ")");
        System.out.println("Output: " + add(q1, q2));
        System.out.println();

        System.out.println("Input: add(" + q3 + ", " + q4 + ")");
        System.out.println("Output: " + add(q3, q4));
        System.out.println();

        System.out.println("Input: add(" + q5 + ", " + q6 + ")");
        System.out.println("Output: " + add(q5, q6));
        System.out.println();

        System.out.println("Input: add(" + q7 + ", " + q8 + ")");
        System.out.println("Output: " + add(q7, q8));
        System.out.println();

        System.out.println("Input: add(" + q9 + ", " + q10 + ")");
        System.out.println("Output: " + add(q9, q10));
        System.out.println();

        System.out.println("Input: add(" + q11 + ", " + q12 + ")");
        System.out.println("Output: " + add(q11, q12));
    }
}