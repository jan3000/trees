package de.treestudio.domain;

public class Tree {

    private Trunk trunk = new Trunk();


    public Trunk getTrunk() {
        return trunk;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Tree{");
        sb.append("trunk=").append(trunk);
        sb.append('}');
        return sb.toString();
    }
}
