package de.treestudio.domain;

import com.google.common.collect.Lists;

import java.util.List;

public class Trunk{

    private Line trunkLine;
    private List<Branch> branches = Lists.newArrayList();

    public Line getTrunkLine() {
        return trunkLine;
    }

    public void setTrunkLine(Line trunkLine) {
        this.trunkLine = trunkLine;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }
}