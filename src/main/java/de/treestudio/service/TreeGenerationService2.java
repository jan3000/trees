package de.treestudio.service;


import com.google.common.collect.Lists;
import de.treestudio.domain.Branch;
import de.treestudio.domain.Line;
import de.treestudio.domain.Point;
import de.treestudio.domain.Tree;
import javafx.util.Pair;

import java.util.List;
import java.util.Random;

public class TreeGenerationService2 implements TreeGenerator {

    public static final int MAX_BRANCH_LENGTH = 200;
    public static final int TRUNK_X = 400;
    public static final int TRUNK_HEIGHT_START = 0;
    public static final int TRUNK_HEIGHT_END = 600;
    public static final int TRUNK_HEIGHT_WITHOUT_BRANCHES = 100;
    public static final int NUMBER_OF_BRANCHES = 5;
    public static final Random RANDOM = new Random();
    private Integer lastDirection = 1;

    private int getBranchDirection() {
        lastDirection = this.lastDirection * - 1;
        return lastDirection;
    }

    public Tree generateTree() {
        Tree tree = new Tree();
        tree.getTrunk().setTrunkLine(new Line(TRUNK_X, TRUNK_HEIGHT_START, TRUNK_X, TRUNK_HEIGHT_END));
        List<Branch> branches = Lists.newArrayList();

        // TRUNK
        Line trunkLine = tree.getTrunk().getTrunkLine();

        // get angle between parent and branch
        double alpha = getRandomFromInterval(20, 25);

        List<Pair<Point, Point>> trunkBranchPoints = getTrunkBranchPoints(new Point(trunkLine.getXStart(), trunkLine.getYStart()),
                new Point(trunkLine.getXEnd(), trunkLine.getYEnd()), alpha);

        for (Pair<Point, Point> trunkBranchPoint : trunkBranchPoints) {
            Branch branchOfTrunk = new Branch();
            List<Line> branchSegments = Lists.newArrayList();
            branchOfTrunk.setAngleToParentBranch(alpha);
            branchSegments.add(new Line(trunkBranchPoint.getKey().getX(), trunkBranchPoint.getKey().getY(),
                    trunkBranchPoint.getValue().getX(), trunkBranchPoint.getValue().getY()));
            branchOfTrunk.setBranchSegments(branchSegments);

            for (int j = 0; j < 2; j++) {
                Line parentBranchLine = branchSegments.get(0);

                // get angle between parent and branch
                double childAlpha = getRandomFromInterval(30, 40);
                childAlpha += branchOfTrunk.getAngleToParentBranch();
                branchOfTrunk.setAngleToParentBranch(childAlpha);
                Pair<Point, Point> pointsOfNewBranch2 = getPointsOfNewBranch(
                        new Point(parentBranchLine.getXStart(), parentBranchLine.getYStart()),
                        new Point(parentBranchLine.getXEnd(), parentBranchLine.getYEnd()), childAlpha);

                System.out.println("pointsOfNewBranch2: " + pointsOfNewBranch2.getKey());
                System.out.println("pointsOfNewBranch2: " + pointsOfNewBranch2.getValue());
                addBranchToParentBranch(branchOfTrunk, pointsOfNewBranch2);

                for (int g = 0; g < 2; g++) {
                    Line parentBranchLine2 = branchOfTrunk.getBranches().get(j).getBranchSegments().get(0);

                    // get angle between parent and branch
                    double childAlpha2 = getRandomFromInterval(30, 40);
                    childAlpha += branchOfTrunk.getAngleToParentBranch();
                    branchOfTrunk.setAngleToParentBranch(childAlpha);
                    Pair<Point, Point> pointsOfNewBranch22 = getPointsOfNewBranch(
                            new Point(parentBranchLine2.getXStart(), parentBranchLine2.getYStart()),
                            new Point(parentBranchLine2.getXEnd(), parentBranchLine2.getYEnd()), childAlpha2);

                    System.out.println("pointsOfNewBranch3: " + pointsOfNewBranch22.getKey());
                    System.out.println("pointsOfNewBranch3: " + pointsOfNewBranch22.getValue());
                    addBranchToParentBranch(branchOfTrunk.getBranches().get(j), pointsOfNewBranch22);
                }
            }

            branches.add(branchOfTrunk);
        }
        tree.getTrunk().setBranches(branches);

        return tree;
    }

    private List<Pair<Point, Point>> getTrunkBranchPoints(Point startParentBranch, Point endParentBranch, double alpha) {
        List<Double> yPoints = Lists.newArrayList();
        List<Pair<Point, Point>> trunkBranchPoints = Lists.newArrayList();
        yPoints.add(TRUNK_HEIGHT_WITHOUT_BRANCHES + 20.0);
        for (int i = 0; i < NUMBER_OF_BRANCHES; i++) {
            Double precedingY = yPoints.get(yPoints.size() - 1);
            double nextYStart = getRandomFromInterval(precedingY + 50, precedingY + 100);
            yPoints.add(nextYStart);
            System.out.println("nextYStart: " + nextYStart);
        }
        for (Double yPoint : yPoints) {
            int branchLength = getBranchLength(new Line(startParentBranch, endParentBranch).getLength());
            Point endPointOfBranch = getEndPointOfBranch(branchLength, alpha, true);
            endPointOfBranch.setX(endPointOfBranch.getX() + TRUNK_X);
            endPointOfBranch.setY(endPointOfBranch.getY() + yPoint);
            trunkBranchPoints.add(new Pair<Point, Point>(new Point(TRUNK_X, yPoint), endPointOfBranch));
        }
        return trunkBranchPoints;
    }

    private void addBranchToParentBranch(Branch parentBranch, Pair<Point, Point> pointsOfNewBranch2) {
        List<Line> childBranchLines = Lists.newArrayList();
        childBranchLines.add(new Line(pointsOfNewBranch2.getKey().getX(), pointsOfNewBranch2.getKey().getY(),
                pointsOfNewBranch2.getValue().getX(), pointsOfNewBranch2.getValue().getY()));

        Branch childBranch = new Branch();
        childBranch.setBranchSegments(childBranchLines);
        parentBranch.getBranches().add(childBranch);
    }


    private double getRandomFromInterval(double x, double y) {
        return x + RANDOM.nextDouble() * (y - x);
    }

    public Pair<Point, Point> getPointsOfNewBranch(Point startParentBranch, Point endParentBranch, double alpha) {
        double xStartOfNewBranch = getXStartOfNewBranch(startParentBranch, endParentBranch);
        Point startPoint = getPointOnLine(startParentBranch, endParentBranch, xStartOfNewBranch);

        int branchLength = getBranchLength(new Line(startParentBranch, endParentBranch).getLength());
        Point endPointOfBranch = getEndPointOfBranch(branchLength, alpha, false);

        endPointOfBranch.setX(endPointOfBranch.getX() + startPoint.getX());
        endPointOfBranch.setY(endPointOfBranch.getY() + startPoint.getY());

        return new Pair<Point, Point>(startPoint, endPointOfBranch);
    }

    private int getBranchLength(double maxBranchLength) {
//        if (maxBranchLength <= MIN_BRANCH_LENGTH) {
//            return MIN_BRANCH_LENGTH;
//        }
        Double randomFromInterval = getRandomFromInterval(maxBranchLength * 30 / 100, maxBranchLength * 60 / 100);
        return randomFromInterval.intValue();
    }

    public double getXStartOfNewBranch(Point startParentBranch, Point endParentBranch) {
        double startParentBranchX = startParentBranch.getX();
        double endParentBranchX = endParentBranch.getX();
        double distance = endParentBranchX - startParentBranchX;
        if (distance == 0) {
            return startParentBranchX;
        }
        double xStartOfNewBranch = getRandomFromInterval(startParentBranchX + 0.3 * distance, endParentBranchX);
        return xStartOfNewBranch;
    }


    public Point getEndPointOfBranch(int length, double alpha, boolean isTrunk){
        double alphaInRadians = Math.toRadians(alpha);
        double x = Math.sin(alphaInRadians) * length;
        double y = Math.cos(alphaInRadians) * length;
        if (isTrunk && getBranchDirection() == -1) {
            x = x - TRUNK_X;
        }
        return new Point(x, y);
    }

    public Point getPointOnLine(Point start, Point end, double xStartOfNewBranch) {
        if (start.getX()== end.getX()) {
            double y = RANDOM.nextDouble() * end.getY();
            if (y < TRUNK_HEIGHT_WITHOUT_BRANCHES) {
                y = y + TRUNK_HEIGHT_WITHOUT_BRANCHES;
            }
            if (y > TRUNK_HEIGHT_END) {
                y = TRUNK_HEIGHT_END - 20;
            }
            Point point = new Point(start.getX(), y);
            return point;
        }

        double riseFactor = (end.getY() - start.getY()) / (end.getX() - start.getX());
        double add = -1 * (riseFactor * start.getX() - start.getY());
        double y = riseFactor * xStartOfNewBranch + add;
        return new Point(xStartOfNewBranch, y);
    }
}
