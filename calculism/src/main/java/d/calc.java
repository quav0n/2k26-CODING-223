package d;

public class calc {
   public double calculate(double n1, double n2, String op) {
        switch (op) {
            case "+": return n1 + n2;
            case "-": return n1 - n2;
            case "×": return n1 * n2;
            case "÷": return n2 == 0 ? 0 : n1 / n2;
            default: return 0;
        }
    }

    public double specialshii(String type, double val) {
        if (type.equals("±")) return val * -1;
        if (type.equals("%")) return val / 100;
        return val;
    }
} 