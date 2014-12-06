package de.treestudio.service;

import de.treestudio.domain.Branch;
import de.treestudio.domain.Line;
import de.treestudio.domain.Tree;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class TreeGeneratorServiceTest {

    @Test
    public void generateTree() {
        TreeGenerationService treeGenerationService = new TreeGenerationService();
        Tree tree = treeGenerationService.generateTree();
        assertThat(tree).isNotNull();
        assertThat(tree.getTrunk().getBranches()).isNotEmpty();
        assertThat(tree.getTrunk().getBranches()).hasSize(TreeGenerationService.NUMBER_OF_BRANCHES);
        Line trunkLine = tree.getTrunk().getTrunkLine();
        assertThat(trunkLine.getXStart() == TreeGenerationService.TRUNK_X).isTrue();
        assertThat(trunkLine.getXEnd() == TreeGenerationService.TRUNK_X).isTrue();
        assertThat(trunkLine.getYStart() == 0).isTrue();
        assertThat(trunkLine.getYEnd() == TreeGenerationService.TRUNK_HEIGHT_END).isTrue();

        assertThat(tree.getTrunk().getBranches().size()).isEqualTo(TreeGenerationService.NUMBER_OF_BRANCHES);
        for (Branch branch : tree.getTrunk().getBranches()) {
            assertThat(branch.getLayer()).isEqualTo(1);
            List<Line> branchLines = branch.getBranchLines();
            assertThat(branchLines.size() > 0).isTrue();
            for (Line branchLine : branchLines) {
                assertThat(branchLine.getXStart() == TreeGenerationService.TRUNK_X).isTrue();
                assertThat(branchLine.getXEnd() != TreeGenerationService.TRUNK_X).isTrue();

                assertThat(branchLine.getYStart() > TreeGenerationService.TRUNK_HEIGHT_WITHOUT_BRANCHES).isTrue();
                assertThat(branchLine.getYStart() < TreeGenerationService.TRUNK_HEIGHT_END).isTrue();
                assertThat(branchLine.getYEnd() >  TreeGenerationService.TRUNK_HEIGHT_WITHOUT_BRANCHES).isTrue();
                assertThat(branchLine.getYEnd() < TreeGenerationService.MAX_BRANCH_HEIGHT_END).isTrue();

                assertThat(branchLine.getYStart() - branchLine.getYEnd() <= TreeGenerationService.MAX_BRANCH_HEIGHT_INCREASE).isTrue();
            }
        }

    }
}