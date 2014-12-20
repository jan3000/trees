package de.treestudio.service;

import de.treestudio.domain.Line;
import de.treestudio.domain.Point;
import de.treestudio.domain.Tree;
import de.treestudio.domain.generator.fan.PartialTree;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class TreeGenerationService2Test {

    public static final double MIN_ALPHA = 0.3;
    public static final int ALPHA = 30;
    private TreeGenerationService2 treeGenerationService2;

    @Before
    public void setUp() {
        treeGenerationService2 = new TreeGenerationService2();
    }

    @Test
    public void generateTree() {
        for (int i = 0; i < 10000; i++  ) {
            Tree tree = treeGenerationService2.generateTree();
            assertThat(tree).isNotNull();
            assertLine(tree.getTrunk().getTrunkLine(), TreeGenerationService2.TRUNK_X, TreeGenerationService2.TRUNK_X,
                    TreeGenerationService2.TRUNK_HEIGHT_START, TreeGenerationService2.TRUNK_HEIGHT_END);

            assertThat(tree.getTrunk().getBranches()).isNotEmpty();
            assertThat(tree.getTrunk().getBranches().get(0)).isNotNull();

            Line branchLine = tree.getTrunk().getBranches().get(0).getBranchSegments().get(0);
            assertThat(branchLine.getXStart()).isEqualTo(TreeGenerationService2.TRUNK_X);
            assertThat(branchLine.getYStart()).isGreaterThan(0);
            assertThat(branchLine.getYStart()).isLessThan(TreeGenerationService2.TRUNK_HEIGHT_END);

            assertThat(branchLine.getXEnd()).isNotEqualTo(TreeGenerationService2.TRUNK_X);
            assertThat(branchLine.getXEnd()).isGreaterThan(branchLine.getXStart());
            assertThat(branchLine.getYEnd()).isGreaterThan(branchLine.getYStart());
        }
    }

    @Test
    public void generateTreeWith2Layers() {
        Tree tree = treeGenerationService2.generateTree();
        assertThat(tree).isNotNull();
        assertLine(tree.getTrunk().getTrunkLine(), TreeGenerationService2.TRUNK_X, TreeGenerationService2.TRUNK_X,
                TreeGenerationService2.TRUNK_HEIGHT_START, TreeGenerationService2.TRUNK_HEIGHT_END);

        assertThat(tree.getTrunk().getBranches()).isNotEmpty();
        assertThat(tree.getTrunk().getBranches().get(0)).isNotNull();

        Line branchLine = tree.getTrunk().getBranches().get(0).getBranchSegments().get(0);
        assertThat(branchLine.getXStart()).isEqualTo(TreeGenerationService2.TRUNK_X);
        assertThat(branchLine.getYStart()).isGreaterThan(0);
        assertThat(branchLine.getYStart()).isLessThan(TreeGenerationService2.TRUNK_HEIGHT_END);

        assertThat(branchLine.getXEnd()).isNotEqualTo(TreeGenerationService2.TRUNK_X);
        assertThat(branchLine.getXEnd()).isGreaterThan(branchLine.getXStart());
        assertThat(branchLine.getYEnd()).isGreaterThan(branchLine.getYStart());
    }

    @Test
    public void getPointOnLineWith0() {
        Point pointOnLine = treeGenerationService2.getPointOnLine(new Point(0, 0), new Point(1, 1), 0.5);
        assertThat(pointOnLine).isEqualTo(new Point(0.5, 0.5));
    }
    @Test
    public void getPointOnLine() {
        Point pointOnLine = treeGenerationService2.getPointOnLine(new Point(1, 1), new Point(3, 3), 2);
        assertThat(pointOnLine).isEqualTo(new Point(2, 2));
    }

    @Test
    public void getEndPointOfBranchAngle0() {
        Point endPointOfBranch = treeGenerationService2.getEndPointOfBranch(2, 0, true);
        assertThat(endPointOfBranch).isEqualTo(new Point(0, 2));
    }
    @Test
    public void getEndPointOfBranchAngle45() {
        Point endPointOfBranch = treeGenerationService2.getEndPointOfBranch(2, 45, true);
        assertEquals(1.414, endPointOfBranch.getX(), 0.001);
        assertEquals(1.414, endPointOfBranch.getY(), 0.001);
    }
    @Test
    public void getEndPointOfBranchAngle90() {
        Point endPointOfBranch = treeGenerationService2.getEndPointOfBranch(2, 90, true);
        assertEquals(2, endPointOfBranch.getX(), 0.000001);
        assertEquals(0, endPointOfBranch.getY(), 0.001);
    }
    @Test
    public void getEndPointOfBranchAngle30() {
        Point endPointOfBranch = treeGenerationService2.getEndPointOfBranch(4, 30, true);
        assertEquals(2, endPointOfBranch.getX(), 0.000001);
        assertEquals(0, endPointOfBranch.getY(), 0.001);
    }

    @Test
    public void getPointsOfNewBranchForSimpleTrunk() {
        Pair<Point, Point> pointsOfNewBranch = treeGenerationService2.getPointsOfNewBranch(new Point(10, 0), new
                Point(10, 10), ALPHA);
        Point startPoint = pointsOfNewBranch.getKey();
        assertThat(startPoint.getX()).isEqualTo(10);
        assertThat(startPoint.getY()).isGreaterThan(0);
        assertThat(startPoint.getY()).isLessThan(TreeGenerationService2.MAX_BRANCH_LENGTH + 10);

        Point endPoint = pointsOfNewBranch.getValue();
        assertThat(endPoint.getX()).isNotEqualTo(10);
        assertThat(endPoint.getX()).isGreaterThan(startPoint.getX());
        assertThat(endPoint.getY()).isGreaterThan(startPoint.getY());
    }

    // WEITER: winkel berechnung durchschauen
    @Test
    public void getPointsOfNewBranchForSimpleBranch() {
        for (int i = 0; i < 10000; i++) {
            Point parentStartPoint = new Point(0, 0);
            Point parentEndPoint = new Point(10, 10);
            Pair<Point, Point> childBranchPoints = treeGenerationService2.getPointsOfNewBranch(parentStartPoint,
                    parentEndPoint, 30);
            Point childStartPoint = childBranchPoints.getKey();
            double parentXDistance = parentEndPoint.getX() - parentStartPoint.getX();
            org.assertj.core.api.Assertions.assertThat(childStartPoint.getX()).isBetween(parentStartPoint.getX() + MIN_ALPHA *
                    parentXDistance, parentEndPoint.getX());
            org.assertj.core.api.Assertions.assertThat(childStartPoint.getY()).isBetween(parentStartPoint.getY(),
                    parentEndPoint.getY());

            // and point is on parent line
            Point childEndPoint = childBranchPoints.getValue();
            assertThat(childEndPoint.getX()).isGreaterThan(childStartPoint.getX());
            assertThat(childEndPoint.getY()).isGreaterThan(childStartPoint.getY());
            Line childBranchLine = new Line(childStartPoint, childEndPoint);
            Line parentBranchLine = new Line(parentStartPoint, parentEndPoint);
            assertThat(parentBranchLine.getLength()).isGreaterThan(childBranchLine.getLength() * (1 + MIN_ALPHA));

            // check alpha
            double sinAlpha = Math.sin(Math.toRadians(60));
            assertThat(childBranchLine.getLength() / parentBranchLine.getLength()).isLessThanOrEqualTo(sinAlpha);
        }
    }

    @Test
    public void getPointsOfNewBranchForSimpleBranchWithDistanceFromTrunk() {
        Pair<Point, Point> pointsOfNewBranch = treeGenerationService2.getPointsOfNewBranch(new Point(10, 5), new
                Point(15, 7), ALPHA);
        Point startPoint = pointsOfNewBranch.getKey();
        org.assertj.core.api.Assertions.assertThat(startPoint.getX()).isBetween(Double.valueOf(10), Double.valueOf(15));
        org.assertj.core.api.Assertions.assertThat(startPoint.getY()).isBetween(Double.valueOf(5), Double.valueOf(7));

        // and point is on parent line
        Point endPoint = pointsOfNewBranch.getValue();
        assertThat(endPoint.getX()).isGreaterThan(startPoint.getX());
        assertThat(endPoint.getY()).isGreaterThan(startPoint.getY());
    }

    private void assertTrunk(PartialTree partialTree) {
        assertThat(partialTree.getTrunk()).isNotNull();
        assertBranch(partialTree, TreeGenerationService2.TRUNK_X, TreeGenerationService2.TRUNK_X,
                TreeGenerationService2.TRUNK_HEIGHT_START, TreeGenerationService2.TRUNK_HEIGHT_END);
    }

    private void assertBranch(PartialTree partialTree, int xStart, int xEnd, int yStart, int yEnd) {
        assertThat(partialTree.getTrunk().getXStart()).isEqualTo(xStart);
        assertThat(partialTree.getTrunk().getXEnd()).isEqualTo(xEnd);
        assertThat(partialTree.getTrunk().getYStart()).isEqualTo(yStart);
        assertThat(partialTree.getTrunk().getYEnd()).isEqualTo(yEnd);
    }

    private void assertLine(Line line, int xStart, int xEnd, int yStart, int yEnd) {
        assertThat(line.getXStart()).isEqualTo(xStart);
        assertThat(line.getXEnd()).isEqualTo(xEnd);
        assertThat(line.getYStart()).isEqualTo(yStart);
        assertThat(line.getYEnd()).isEqualTo(yEnd);
    }

}
