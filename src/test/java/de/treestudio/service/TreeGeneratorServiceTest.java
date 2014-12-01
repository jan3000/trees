package de.treestudio.service;

import de.treestudio.domain.Branch;
import de.treestudio.domain.Tree;
import javafx.scene.shape.Line;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class TreeGeneratorServiceTest {

    @Test
    public void test() {
            TreeGenerationService treeGenerationService = new TreeGenerationService();
            Tree tree = treeGenerationService.generateTree();
            assertThat(tree).isNotNull();
            assertThat(tree.getBranches()).isNotEmpty();
            assertThat(tree.getBranches()).hasSize(TreeGenerationService.NUMBER_OF_BRANCHES);
            Line trunkLine = tree.getTrunk().getBranchLines().get(0);
            assertThat(trunkLine.getStartX() == TreeGenerationService.TRUNK_X).isTrue();
            assertThat(trunkLine.getStartY() > TreeGenerationService.TRUNK_HEIGHT_WITHOUT_BRANCHES).isTrue();
            assertThat(trunkLine.getEndX() == TreeGenerationService.TRUNK_X).isTrue();
            assertThat(trunkLine.getEndY() == TreeGenerationService.TRUNK_HEIGHT_END).isTrue();

            assertThat(tree.getTrunk().getBranches().size()).isEqualTo(TreeGenerationService.NUMBER_OF_BRANCHES);
            for (Branch branch : tree.getTrunk().getBranches()) {
                assertThat(branch.getLayer()).isEqualTo(1);
                List<Line> branchLines = branch.getBranchLines();
                assertThat(branchLines.size() > 0).isTrue();
                for (Line branchLine : branchLines) {
                    assertThat(branchLine.getStartX() == TreeGenerationService.TRUNK_X).isTrue();
                    assertThat(branchLine.getStartY() > TreeGenerationService.TRUNK_HEIGHT_WITHOUT_BRANCHES).isTrue();
                    assertThat(branchLine.getStartY() > TreeGenerationService.MAX_BRANCH_HEIGHT_END).isTrue();

                    assertThat(branchLine.getEndX() != TreeGenerationService.TRUNK_X).isTrue();
                    assertThat(branchLine.getEndY() > TreeGenerationService.TRUNK_HEIGHT_WITHOUT_BRANCHES
                            && branchLine.getEndY() < TreeGenerationService.TRUNK_HEIGHT_WITHOUT_BRANCHES + TreeGenerationService.MAX_BRANCH_HEIGHT_INCREASE).isTrue();
                    assertThat(branchLine.getEndY() < TreeGenerationService.MAX_HEIGHT).isTrue();
                    assertThat(branchLine.getEndY() > TreeGenerationService.MAX_BRANCH_HEIGHT_END).isTrue();
                }
            }

    }
}