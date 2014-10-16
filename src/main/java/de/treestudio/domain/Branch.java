package de.treestudio.domain;

import com.google.common.collect.Lists;

import java.util.List;

public class Branch {

    private List<Line> branchLines = Lists.newArrayList();

    public List<Line> getBranchLines() {
        return branchLines;
    }

    public void setBranchLines(List<Line> branchLines) {
        this.branchLines = branchLines;
    }
}
