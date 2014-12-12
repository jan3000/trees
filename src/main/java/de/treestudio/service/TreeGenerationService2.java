package de.treestudio.service;


import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import de.treestudio.domain.Branch;
import de.treestudio.domain.Line;
import de.treestudio.domain.Point;
import de.treestudio.domain.Tree;
import de.treestudio.domain.generator.fan.PartialTree;
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
            Branch branch = new Branch();
            List<Line> branchLines = Lists.newArrayList();
            Line trunkLine = tree.getTrunk().getTrunkLine();
            Pair<Point, Point> pointsOfNewBranch = getPointsOfNewBranch(
                    new Point(trunkLine.getXStart(), trunkLine.getYStart()),
                    new Point(trunkLine.getXEnd(), trunkLine.getYEnd()), MAX_BRANCH_LENGTH);
            branchLines.add(new Line(pointsOfNewBranch.getKey().getX(), pointsOfNewBranch.getKey().getY(),
                    pointsOfNewBranch.getValue().getX(), pointsOfNewBranch.getValue().getY()));
            branch.setBranchLines(branchLines);
            for (int j = 0; j < 1; j++) {
                Branch branch2 = new Branch();
                List<Line> branchLines2 = Lists.newArrayList();
                Line parentBranchLine = branch.getBranchLines().get(0);
                Pair<Point, Point> pointsOfNewBranch2 = getPointsOfNewBranch(
                        new Point(parentBranchLine.getXStart(), parentBranchLine.getYStart()),
                        new Point(parentBranchLine.getXEnd(), parentBranchLine.getYEnd()), Double.valueOf(parentBranchLine.getLength
                                ()).intValue());
                branchLines2.add(new Line(pointsOfNewBranch2.getKey().getX(), pointsOfNewBranch2.getKey().getY(),
                        pointsOfNewBranch2.getValue().getX(), pointsOfNewBranch2.getValue().getY()));
                branch2.setBranchLines(branchLines2);
                branch.getBranches().add(branch2);
            }
            branches.add(branch);
        }
        tree.getTrunk().setBranches(branches);

        return tree;
    }


    private double getRandomFromInterval(double x, double y) {
        return x + RANDOM.nextDouble() * (y - x);
    }

    public Pair<Point, Point> getPointsOfNewBranch(Point startParentBranch, Point endParentBranch, int
            maxBranchLength) {
        double xStartOfNewBranch = getXStartOfNewBranch(startParentBranch, endParentBranch);
        Point startPoint = getPointOnLine(startParentBranch, endParentBranch, xStartOfNewBranch);
        double alpha = getRandomFromInterval(10, 70);
        Point endPointOfBranch = getEndPointOfBranch(getBranchLength(maxBranchLength), alpha);

        endPointOfBranch.setX(endPointOfBranch.getX() + startPoint.getX());
        endPointOfBranch.setY(endPointOfBranch.getY() + startPoint.getY());

        return new Pair<Point, Point>(startPoint, endPointOfBranch);
    }

    private int getBranchLength(int maxBranchLength) {
        if (maxBranchLength <= MIN_BRANCH_LENGTH) {
            return MIN_BRANCH_LENGTH;
        }
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
        return new Point(xStartOfNewBranch, riseFactor * xStartOfNewBranch + add);
    }
}
