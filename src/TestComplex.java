public class TestComplex {
    public static void main(String[] args) {
        Complex a = new Complex(1, 2);
        Complex b = new Complex(3, -1);
        Complex i = new Complex(0, 1);

        System.out.println("a + b   = " + a.add(b)      + "   ожидается 4 + 1i");
        System.out.println("a - b   = " + a.subtract(b) + "   ожидается -2 + 3i");
        System.out.println("a * b   = " + a.multiply(b) + "   ожидается 5 + 5i");
        System.out.println("a / b   = " + a.divide(b)   + "   ожидается 0.1 + 0.7i");
        System.out.println("conj(a) = " + a.conjugate() + "   ожидается 1 - 2i");
        System.out.println("|3+4i|  = " + new Complex(3, 4).abs() + "   ожидается 5");
        System.out.println("i * i   = " + i.multiply(i) + "   ожидается -1 + 0i");
    }
}