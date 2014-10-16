package de.treestudio.service;

import com.google.common.collect.Lists;
import de.treestudio.domain.Branch;
import de.treestudio.domain.Line;

import java.util.ArrayList;
import java.util.List;

public class TreeGenerationService {

    private static final int MIN_LENGTH_OF_BRANCH_PART = 10;
    private static final int MAX_LENGTH_OF_BRANCH_PART = 100;
    private static final int MIN_HEIGHT = 200;
    private static final int MAX_HEIGHT = 700;
    private static final int MAX_SPREADING_LEFT = 20;
    private static final int MAX_SPREADING_RIGHT = 20;


    public Branch generateBranch() {
        Branch branch = new Branch();

        List<Line> branchLines = Lists.newArrayList();



//        Line line = new Line();
//        branchLines.add(line);

        branch.setBranchLines(branchLines);

        return branch;
    }


}
