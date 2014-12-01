package de.treestudio.service;

import com.google.common.collect.Lists;
import de.treestudio.domain.Branch;
import de.treestudio.domain.Tree;
import javafx.scene.shape.Line;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

public class TreeGenerationService {

    private static final int MIN_LENGTH_OF_BRANCH_PART = 10;
    private static final int MAX_LENGTH_OF_BRANCH_PART = 100;
    public static final int MIN_HEIGHT = 200;
    public static final int MAX_HEIGHT = 700;
    private static final int MAX_SPREADING_LEFT = 20;
    private static final int MAX_SPREADING_RIGHT = 20;


    private static final int MAX_BRANCH_LENGTH = 300;
    public static final int TRUNK_X = 400;
    public static final int TRUNK_HEIGHT_START = 590;
    public static final int TRUNK_HEIGHT_END = 10;
    public static final int MAX_BRANCH_HEIGHT_END = 15;
    public static final int TRUNK_HEIGHT_WITHOUT_BRANCHES = 300;
    public static final int MAX_BRANCH_HEIGHT_INCREASE = 100;
    public static final int NUMBER_OF_BRANCHES = 15;
    private int branchStartHeight;


    private int getBranchDirection() {
        return new Random().nextBoolean() ? 1 : -1;
    }

    public Tree generateTree() {
        Tree tree = new Tree();
        tree.getTrunk().setBranchLines(Lists.newArrayList(new Line(TRUNK_X, TRUNK_HEIGHT_START, TRUNK_X, TRUNK_HEIGHT_END)));

        for (int i = 1; i <=  NUMBER_OF_BRANCHES; i++) {
            Branch branch = new Branch();
            branch.setLayer(1);
            Line line = new Line(TRUNK_X, generateBranchStartHeight(), generateBranchLength(), getBranchEndHeight());
            System.out.println(line.getStartX());
            System.out.println(line.getEndX());
            System.out.println(line.getStartY());
            System.out.println(line.getEndY());
            System.out.println("____________________________");
            branch.getBranchLines().add(line);
            tree.getBranches().add(branch);
        }
        return tree;
    }

    private int getBranchEndHeight() {
        return branchStartHeight - new Random().nextInt(MAX_BRANCH_HEIGHT_INCREASE);
    }

    private int generateBranchStartHeight() {
        branchStartHeight = TRUNK_HEIGHT_START - new Random().nextInt(TRUNK_HEIGHT_START + TRUNK_HEIGHT_WITHOUT_BRANCHES);
        return branchStartHeight;
    }

    private int generateBranchLength() {
        return TRUNK_X + getBranchDirection() * new Random().nextInt(MAX_BRANCH_LENGTH);
    }


}
