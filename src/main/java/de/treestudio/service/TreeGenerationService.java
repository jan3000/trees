package de.treestudio.service;

import com.google.common.collect.Lists;
import de.treestudio.domain.Branch;
import javafx.scene.shape.Line;

import java.util.List;
import java.util.Random;

public class TreeGenerationService {

    private static final int MIN_LENGTH_OF_BRANCH_PART = 10;
    private static final int MAX_LENGTH_OF_BRANCH_PART = 100;
    private static final int MIN_HEIGHT = 200;
    private static final int MAX_HEIGHT = 700;
    private static final int MAX_SPREADING_LEFT = 20;
    private static final int MAX_SPREADING_RIGHT = 20;


    private static final int MAX_BRANCH_LENGTH = 300;
    public static final int TRUNK_X = 400;
    public static final int TRUNK_HEIGHT_START = 590;
    public static final int TRUNK_HEIGHT_END = 10;
    public static final int TRUNK_HEIGHT_WITHOUT_BRANCHES = 30;


    private int getBranchDirection() {
        if ((new Random()).nextBoolean()) {
            return 1;
        } else {
            return -1;
        }
    }
    public Branch generateBranch() {

        Branch branch = new Branch();

        List<Line> branchLines = Lists.newArrayList();
        branchLines.add(new Line(TRUNK_X, TRUNK_HEIGHT_START, TRUNK_X, TRUNK_HEIGHT_END));

        branchLines.add(new Line(TRUNK_X, generateBranchStartHeight(), generateBranchLength(), 490));
        branchLines.add(new Line(TRUNK_X, generateBranchStartHeight(), generateBranchLength(), 80));
        branchLines.add(new Line(TRUNK_X, generateBranchStartHeight(), generateBranchLength(), 210));
        branchLines.add(new Line(TRUNK_X, generateBranchStartHeight(), generateBranchLength(), 330));


        branch.setBranchLines(branchLines);

        return branch;
    }

    private int generateBranchStartHeight() {
        return TRUNK_HEIGHT_START - TRUNK_HEIGHT_WITHOUT_BRANCHES - new Random().nextInt(TRUNK_HEIGHT_WITHOUT_BRANCHES );
    }

    private int generateBranchLength() {
        return TRUNK_X + getBranchDirection() * new Random().nextInt(MAX_BRANCH_LENGTH);
    }


}
