package com.liner.twod_exp.engine.geom;

public class Vector3 {
    private final double x;
    private final double y;
    private final double z;
    private final double length;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.length = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public Vector3(Vector3 vector3) {
        this(vector3.x, vector3.y, vector3.z);
    }

    public Vector3() {
        this(0, 0, 0);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getLength() {
        return length;
    }

    public Vector3 add(double x, double y, double z) {
        return new Vector3(this.x + x, this.y + y, this.z + z);
    }

    public Vector3 add(Vector3 vector3) {
        return new Vector3(this.x + vector3.x, this.y + vector3.y, this.z + vector3.z);
    }

    public Vector3 subtract(double x, double y, double z) {
        return new Vector3(this.x - x, this.y - y, this.z - z);
    }

    public Vector3 subtract(Vector3 vector3) {
        return new Vector3(this.x - vector3.x, this.y - vector3.y, this.z - vector3.z);
    }

    public Vector3 scale(double scaleX, double scaleY, double scaleZ) {
        return new Vector3(this.x * scaleX, this.y * scaleY, this.z * scaleZ);
    }

    public Vector3 scale(Vector3 vector3) {
        return new Vector3(this.x * vector3.x, this.y * vector3.y, this.z * vector3.z);
    }

    public Vector3 scale(double factor) {
        return new Vector3(this.x * factor, this.y * factor, this.z * factor);
    }

    public Vector3 normalize() {
        return scale(1 / length);
    }


    public Vector3 crossProduct(Vector3 vector3) {
        return new Vector3(
                this.y * vector3.z - this.z * vector3.y,
                this.z * vector3.x - this.x * vector3.z,
                this.x * vector3.y - this.y * vector3.x);
    }

    public Vector3 dotProduct(Vector3 vector3) {
        return new Vector3(
                this.y * vector3.z + this.z * vector3.y,
                this.z * vector3.x + this.x * vector3.z,
                this.x * vector3.y + this.y * vector3.x);
    }


    public Vector3 copy(){
        return this;
    }

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", length=" + length +
                '}';
    }
}
