package common;

import java.io.Serializable;

public class Calcul implements Serializable {
    private static final long serialVersionUID = 1L;
    private double x;
    private double y;
    private String op;
    public Calcul(double x, double y, String op){this.x=x;this.y=y;this.op=op;}

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getOp() {
        return op;
    }
}
