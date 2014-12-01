package de.treestudio.service;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TestGenerator;
import de.treestudio.domain.Branch;
import javafx.scene.shape.Line;
import org.fest.assertions.Assertions;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class TreeGeneratorServiceTest {

    @Test
    public void test() {
        TreeGenerationService treeGenerationService = new TreeGenerationService();
        Branch branch = treeGenerationService.generateBranch();
        assertThat(branch).isNotNull();
        assertThat(branch.getBranchLines()).isNotEmpty();
        assertThat(branch.getBranchLines()).hasSize(TreeGenerationService.NUMBER_OF_BRANCHES);

        for (Line line : branch.getBranchLines()) {
            assertThat(line.getStartX() == TreeGenerationService.TRUNK_X);
            assertThat(line.getStartY() >  TreeGenerationService.TRUNK_HEIGHT_WITHOUT_BRANCHES);
//            assertThat(line.getEndX() == TreeGenerationService.TRUNK_X);
//            assertThat(line.getStartX() == TreeGenerationService.TRUNK_X);
        }

    }
}