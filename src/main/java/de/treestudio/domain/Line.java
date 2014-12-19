package de.treestudio.domain;

public class Line {

    private double xStart;
    private double yStart;
    private double xEnd;
    private double yEnd;

    public Line(Point start, Point end) {
        this.xStart = start.getX();
        this.yStart = start.getY();
        this.xEnd = end.getX();
        this.yEnd = end.getY();
    }

    public Line(double xStart, double yStart, double xEnd, double yEnd) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
    }

    public double getXStart() {
        return xStart;
    }

    public void setxStart(double xStart) {
        this.xStart = xStart;
    }

    public double getYStart() {
        return yStart;
    }

    public void setyStart(double yStart) {
        this.yStart = yStart;
    }

    public double getXEnd() {
        return xEnd;
    }

    public void setxEnd(double xEnd) {
        this.xEnd = xEnd;
    }

    public double getYEnd() {
        return yEnd;
    }

    public void setyEnd(double yEnd) {
        this.yEnd = yEnd;
    }

    public double getLength() {
        double xDistance = xEnd - xStart;
        double yDistance = yEnd - yStart;
        System.out.println("length = " + Math.sqrt(xDistance * xDistance + yDistance * yDistance));
        return Math.sqrt(xDistance * xDistance + yDistance * yDistance);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Line{");
        sb.append("xStart=").append(xStart);
        sb.append(", yStart=").append(yStart);
        sb.append(", xEnd=").append(xEnd);
        sb.append(", yEnd=").append(yEnd);
        sb.append('}');
        return sb.toString();
    }
}
