package de.treestudio.domain;

import com.google.common.collect.Lists;

import java.util.List;

public class Tree {

    private Branch trunk = new Branch();
    private List<Branch> branches = Lists.newArrayList();

    public Tree() {
        this.trunk.setLayer(0);
    }

    public Branch getTrunk() {
        return trunk;
    }


    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Tree{");
        sb.append("trunk=").append(trunk);
        sb.append(", branches=").append(branches);
        sb.append('}');
        return sb.toString();
    }
}
