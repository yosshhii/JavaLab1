public class Complex {
    private final double re, im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public Complex add(Complex other) {
        return new Complex(this.re + other.re, this.im + other.im);
    }

    public Complex subtract(Complex other) {
        return new Complex(this.re - other.re, this.im - other.im);
    }

    public Complex multiply(Complex other) {
        return new Complex(this.re * other.re - this.im * other.im, this.re * other.im + this.im * other.re);
    }

    public Complex divide(Complex other) {
        if (other.isZero()) {
            throw new ArithmeticException("Нельзя делить на ноль");
        }

        double denominator = other.re * other.re + other.im * other.im;
        return new Complex((this.re * other.re + this.im * other.im) / denominator,
                (this.im * other.re - this.re * other.im) / denominator);
    }

    public Complex conjugate() {
        return new Complex(this.re, -this.im);
    }

    public double abs() {
        return Math.sqrt(Math.pow(this.re, 2) + Math.pow(this.im, 2));
    }

    public boolean isZero() {
        double epsilon = 1e-9;
        return Math.abs(this.re) < epsilon && Math.abs(this.im) < epsilon;
    }

    @Override
    public String toString() {
        return "(" + re + (im >= 0 ? " + " : " - ") + Math.abs(im) + "i)";
    }
}