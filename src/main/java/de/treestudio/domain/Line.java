package de.treestudio.domain;

public class Line {

    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;


    public Line(int xStart, int yStart, int xEnd, int yEnd) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
    }

    public int getXStart() {
        return xStart;
    }

    public void setxStart(int xStart) {
        this.xStart = xStart;
    }

    public int getYStart() {
        return yStart;
    }

    public void setyStart(int yStart) {
        this.yStart = yStart;
    }

    public int getXEnd() {
        return xEnd;
    }

    public void setxEnd(int xEnd) {
        this.xEnd = xEnd;
    }

    public int getYEnd() {
        return yEnd;
    }

    public void setyEnd(int yEnd) {
        this.yEnd = yEnd;
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
