package de.treestudio.service;


import com.google.common.collect.Lists;
import de.treestudio.domain.Branch;
import de.treestudio.domain.Line;
import de.treestudio.domain.Point;
import de.treestudio.domain.Tree;
import de.treestudio.domain.generator.fan.PartialTree;
import javafx.util.Pair;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TreeGenerationService2 implements TreeGenerator {

    private static final int MAX_BRANCH_LENGTH = 300;
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
        for (int i = 0; i < 1; i++) {
            Branch branch = new Branch();
            List<Line> branchLines = Lists.newArrayList();
            Line trunkLine = tree.getTrunk().getTrunkLine();
            Pair<Point, Point> pointsOfNewBranch = getPointsOfNewBranch(
                    new Point(trunkLine.getXStart(), trunkLine.getYStart()),
                    new Point(trunkLine.getXEnd(), trunkLine.getYEnd()));
            branchLines.add(new Line(pointsOfNewBranch.getKey().getX(), pointsOfNewBranch.getKey().getY(),
                    pointsOfNewBranch.getValue().getX(), pointsOfNewBranch.getValue().getY()));
            branch.setBranchLines(branchLines);
            for (int j = 0; j < 1; j++) {
                Branch branch2 = new Branch();
                List<Line> branchLines2 = Lists.newArrayList();
                Line parentBranchLine = branch.getBranchLines().get(0);
                Pair<Point, Point> pointsOfNewBranch2 = getPointsOfNewBranch(
                        new Point(parentBranchLine.getXStart(), parentBranchLine.getYStart()),
                        new Point(parentBranchLine.getXEnd(), parentBranchLine.getYEnd()));
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

    public Pair<Point, Point> getPointsOfNewBranch(Point startParentBranch, Point endParentBranch) {
        Point startPoint = getPointOnLine(startParentBranch, endParentBranch,
                getXStartOfNewBranch(startParentBranch, endParentBranch));
        double alpha = getRandomFromInterval(10, 70);
        System.out.println("alpha: "+ alpha);
        Point endPointOfBranch = getEndPointOfBranch(getBranchLength(), alpha);

        endPointOfBranch.setX(endPointOfBranch.getX() + startPoint.getX());
        endPointOfBranch.setY(endPointOfBranch.getY() + startPoint.getY());

        return new Pair<Point, Point>(startPoint, endPointOfBranch);
    }

    private int getBranchLength() {
        return RANDOM.nextInt(MAX_BRANCH_LENGTH - MIN_BRANCH_LENGTH) + MIN_BRANCH_LENGTH;
    }

    private double getXStartOfNewBranch(Point startParentBranch, Point endParentBranch) {
        return getRandomFromInterval(startParentBranch.getX(), endParentBranch.getX());
    }


    public Point getEndPointOfBranch(int length, double alpha){
        double alphaInRadians = Math.toRadians(alpha);
        double x = Math.sin(alphaInRadians) * length;
        double y = Math.cos(alphaInRadians) * length;
        System.out.println("x: " + x);
        System.out.println("y: " + y);
        return new Point(x, y);
    }

    public Point getPointOnLine(Point start, Point end, double x) {
        if (start.getX()== end.getX()) {
            Point point = new Point(start.getX(), RANDOM.nextDouble() * end.getY());
            return point;
        }

        double riseFactor = (end.getY() - start.getY()) / (end.getX() - start.getX());
        double add = -1 * (riseFactor * start.getX() - start.getY());
        return new Point(x, riseFactor * x + add);
    }

    public PartialTree generatePartialTree(int layers) {
        PartialTree tree = new PartialTree();
        tree.setTrunk(new Line(TRUNK_X, TRUNK_HEIGHT_START, TRUNK_X, TRUNK_HEIGHT_END));

        for (int i = 1; i < layers; i++) {
            tree.getBranches().add(generateBranches());
        }
        return tree;
    }

    private PartialTree generateBranches() {
        PartialTree partialTree = new PartialTree();
        partialTree.setTrunk(new Line(TRUNK_X, generateBranchStartHeight(), TRUNK_X,
                getBranchEndHeight()));
        return partialTree;
    }

    private int getBranchEndHeight() {
        int branchEndY = branchStartHeight + RANDOM.nextInt(MAX_BRANCH_HEIGHT_INCREASE);
        if (branchEndY > MAX_BRANCH_HEIGHT_END) {
            return MAX_BRANCH_HEIGHT_END;
        }
        return branchEndY;
    }

    private  int generateBranchStartHeight() {
        branchStartHeight = TRUNK_HEIGHT_WITHOUT_BRANCHES + RANDOM.nextInt(TRUNK_HEIGHT_END - TRUNK_HEIGHT_WITHOUT_BRANCHES);
        return branchStartHeight;
    }

    private int generateBranchLength() {
        return TRUNK_X + getBranchDirection() * RANDOM.nextInt(MAX_BRANCH_LENGTH);
    }


}
