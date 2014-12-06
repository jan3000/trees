package de.treestudio.service;

import de.treestudio.domain.Branch;
import de.treestudio.domain.Line;
import de.treestudio.domain.Tree;
import java.util.Random;

public class TreeGenerationService {

    private static final int MAX_BRANCH_LENGTH = 300;
    public static final int TRUNK_X = 400;
    public static final int TRUNK_HEIGHT_START = 0;
    public static final int TRUNK_HEIGHT_END = 600;
    public static final int MAX_BRANCH_HEIGHT_END = 750;
    public static final int TRUNK_HEIGHT_WITHOUT_BRANCHES = 300;
    public static final int MAX_BRANCH_HEIGHT_INCREASE = 100;
    public static final int NUMBER_OF_BRANCHES = 15;
    private int branchStartHeight;


    private int getBranchDirection() {
        return new Random().nextBoolean() ? 1 : -1;
    }

    public Tree generateTree() {
        Tree tree = new Tree();
        tree.getTrunk().setTrunkLine(new Line(TRUNK_X, TRUNK_HEIGHT_START, TRUNK_X, TRUNK_HEIGHT_END));

        for (int i = 1; i <=  NUMBER_OF_BRANCHES; i++) {
            Branch branch = new Branch();
            branch.setLayer(1);
            Line line = new Line(TRUNK_X, generateBranchStartHeight(), generateBranchLength(), getBranchEndHeight());
            System.out.println(line);
            System.out.println("____________________________");
            branch.getBranchLines().add(line);
            tree.getTrunk().getBranches().add(branch);
        }
        return tree;
    }

    private int getBranchEndHeight() {
        int branchEndY = branchStartHeight + new Random().nextInt(MAX_BRANCH_HEIGHT_INCREASE);
        if (branchEndY > MAX_BRANCH_HEIGHT_END) {
            return MAX_BRANCH_HEIGHT_END;
        }
        return branchEndY;
    }

    private  int generateBranchStartHeight() {
        branchStartHeight = TRUNK_HEIGHT_WITHOUT_BRANCHES + new Random().nextInt(TRUNK_HEIGHT_END - TRUNK_HEIGHT_WITHOUT_BRANCHES);
        return branchStartHeight;
    }

    private int generateBranchLength() {
        return TRUNK_X + getBranchDirection() * new Random().nextInt(MAX_BRANCH_LENGTH);
    }


}
