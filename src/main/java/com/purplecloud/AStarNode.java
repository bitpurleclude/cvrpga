package com.purplecloud;

public class AStarNode extends Node {
    private int i, j;
    private double g,h,f;
    private AStarNode parent;
    private double totalDistance;


    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }
    public AStarNode getParent() {
        return parent;
    }
    public void setParent(AStarNode parent) {
        this.parent = parent;
    }

    public AStarNode(int booksToPickUp, int booksToDropOff, int id) {
        super(booksToPickUp, booksToDropOff, id);
        this.i = calculateI(id);
        this.j = calculateJ(id);
    }

    private int calculateI(int id) {
        return id%6;
        // 根据你的编号系统计算i的值
    }

    private int calculateJ(int id) {
        return id/6;
        // 根据你的编号系统计算j的值
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }
}