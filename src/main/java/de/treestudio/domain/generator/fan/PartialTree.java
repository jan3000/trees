package de.treestudio.domain.generator.fan;

import com.google.common.collect.Lists;
import de.treestudio.domain.Line;

import java.util.List;

public class PartialTree {

    private Line trunk;
    private int angle;
    private List<PartialTree> branches = Lists.newArrayList();

    public Line getTrunk() {
        return trunk;
    }

    public void setTrunk(Line trunk) {
        this.trunk = trunk;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public List<PartialTree> getBranches() {
        return branches;
    }

    public void setBranches(List<PartialTree> branches) {
        this.branches = branches;
    }
}
