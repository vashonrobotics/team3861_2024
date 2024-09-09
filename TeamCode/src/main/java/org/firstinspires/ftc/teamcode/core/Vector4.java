package org.firstinspires.ftc.teamcode.core;

public class Vector4 {
    private double val[];

    public Vector4(double a, double b, double c, double d) {
        val = new double[4];
        val[0] = a;
        val[1] = b;
        val[2] = c;
        val[3] = d;
    }

    public Vector4(double[] a) {
        val = a;
    }

    public Vector4(double a) {
        this(a, a, a, a);
    }

    public Vector4() {
        this(0);
    }

    public double get(int i) {
        return val[i];
    }

    public void set(int i, double val) {
        this.val[i] = val;
    }

    public Vector4 add(Vector4 arg) {
        return new Vector4(val[0] + arg.val[0], val[1] + arg.val[1], val[2] + arg.val[2], val[3] + arg.val[3]);
    }

    public Vector4 sub(Vector4 arg) {
        return new Vector4(val[0] - arg.val[0], val[1] - arg.val[1], val[2] - arg.val[2], val[3] - arg.val[3]);
    }

    public Vector4 mul(double arg) {
        return new Vector4(arg * val[0], arg * val[1], arg * val[2], arg * val[3]);
    }

    public Vector4 cap(double cap) {
        double max = Math.max(val[0], val[1]);
        max = Math.max(val[3], max);
        max = Math.max(val[2], max);
        if(max <= cap) {
            return new Vector4(val);
        }
        return mul(cap/max);
    }
}
