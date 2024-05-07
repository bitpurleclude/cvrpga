package com.purplecloud;

import java.util.List;

public class PathAndWay {
    List<Integer> path;
    double distance;

    public PathAndWay() {
    }

    public PathAndWay(List<Integer> path, double distance) {
        this.path = path;
        this.distance = distance;
    }

    /**
     * 获取
     * @return path
     */
    public List<Integer> getPath() {
        return path;
    }

    /**
     * 设置
     * @param path
     */
    public void setPath(List<Integer> path) {
        this.path = path;
    }

    /**
     * 获取
     * @return distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * 设置
     * @param distance
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String toString() {
        return "PathAndWay{path = " + path + ", distance = " + distance + "}";
    }
}
