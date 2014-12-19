package de.treestudio.domain;

import com.google.common.collect.Lists;

import java.util.List;

public class Branch {

    private List<Line> branchLines = Lists.newArrayList();
    private List<Branch> branches = Lists.newArrayList();
    private Branch root;
    private double angleToParentBranch;
    private int numberOfBranchPoints;
    private int layer;

    public Branch getRoot() {
        return root;
    }

    public void setRoot(Branch root) {
        this.root = root;
    }

    public int getNumberOfBranchPoints() {
        return numberOfBranchPoints;
    }

    public void setNumberOfBranchPoints(int numberOfBranchPoints) {
        this.numberOfBranchPoints = numberOfBranchPoints;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public List<Line> getBranchSegments() {
        return branchLines;
    }

    public void setBranchSegments(List<Line> branchLines) {
        this.branchLines = branchLines;
    }


    public double getAngleToParentBranch() {
        return angleToParentBranch;
    }

    public void setAngleToParentBranch(double angleToParentBranch) {
        this.angleToParentBranch = angleToParentBranch;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Branch{");
        sb.append("branchLines=").append(branchLines);
        sb.append(", branches=").append(branches);
        sb.append(", root=").append(root);
        sb.append(", angleToParentBranch=").append(angleToParentBranch);
        sb.append(", numberOfBranchPoints=").append(numberOfBranchPoints);
        sb.append(", layer=").append(layer);
        sb.append('}');
        return sb.toString();
    }
}
