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

    public static final int MAX_BRANCH_LENGTH = 300;
    public static final int TRUNK_X = 400;
    public static final int TRUNK_HEIGHT_START = 0;
    public static final int TRUNK_HEIGHT_END = 600;
    public static final int MAX_BRANCH_HEIGHT_END = 750;
    public static final int TRUNK_HEIGHT_WITHOUT_BRANCHES = 300;
    public static final int MAX_BRANCH_HEIGHT_INCREASE = 100;
    public static final int NUMBER_OF_BRANCHES = 15;
    public static final Random RANDOM = new Random();
    public static final int MIN_BRANCH_LENGTH = 10;
    private int branchStartHeight;


    private int getBranchDirection() {
        return RANDOM.nextBoolean() ? 1 : -1;
    }

    public Tree generateTree() {
        Tree tree = new Tree();
        tree.getTrunk().setTrunkLine(new Line(TRUNK_X, TRUNK_HEIGHT_START, TRUNK_X, TRUNK_HEIGHT_END));
        List<Branch> branches = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            Branch firstLevelBranch = new Branch();
            List<Line> branchSegments = Lists.newArrayList();
            Line trunkLine = tree.getTrunk().getTrunkLine();

            // get angle between parent and branch
            double alpha = getRandomFromInterval(30, 70);
            firstLevelBranch.setAngleToParentBranch(alpha);

            Pair<Point, Point> pointsOfNewBranch = getPointsOfNewBranch(
                    new Point(trunkLine.getXStart(), trunkLine.getYStart()),
                    new Point(trunkLine.getXEnd(), trunkLine.getYEnd()), alpha);
            branchSegments.add(new Line(pointsOfNewBranch.getKey().getX(), pointsOfNewBranch.getKey().getY(),
                    pointsOfNewBranch.getValue().getX(), pointsOfNewBranch.getValue().getY()));
            firstLevelBranch.setBranchSegments(branchSegments);
            for (int j = 0; j < 2; j++) {
                Line parentBranchLine = firstLevelBranch.getBranchSegments().get(0);

                // get angle between parent and branch
                double childAlpha = getRandomFromInterval(30, 70);
                childAlpha += firstLevelBranch.getAngleToParentBranch();
                firstLevelBranch.setAngleToParentBranch(childAlpha);
                Pair<Point, Point> pointsOfNewBranch2 = getPointsOfNewBranch(
                        new Point(parentBranchLine.getXStart(), parentBranchLine.getYStart()),
                        new Point(parentBranchLine.getXEnd(), parentBranchLine.getYEnd()), childAlpha);

                addBranchToParentBranch(firstLevelBranch, pointsOfNewBranch2);
            }
            branches.add(firstLevelBranch);
        }
        tree.getTrunk().setBranches(branches);

        return tree;
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
        Point endPointOfBranch = getEndPointOfBranch(branchLength,
                alpha);

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
        return getRandomFromInterval(startParentBranchX + 0.3 * distance, endParentBranchX);
    }


    public Point getEndPointOfBranch(int length, double alpha){
        double alphaInRadians = Math.toRadians(alpha);
        double x = Math.sin(alphaInRadians) * length;
        double y = Math.cos(alphaInRadians) * length;
        return new Point(x, y);
    }

    public Point getPointOnLine(Point start, Point end, double xStartOfNewBranch) {
        if (start.getX()== end.getX()) {
            double y = RANDOM.nextDouble() * end.getY();
            if (y < TRUNK_HEIGHT_WITHOUT_BRANCHES) {
                y = y + TRUNK_HEIGHT_WITHOUT_BRANCHES;
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
