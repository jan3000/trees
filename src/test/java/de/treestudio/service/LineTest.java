package de.treestudio.service;

import de.treestudio.domain.Line;
import org.fest.assertions.Assertions;
import org.junit.Assert;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class LineTest {

    @Test
    public void getLength() {
        Line line = new Line(0, 0, 10, 10);
        assertThat(line.getLength()).isEqualTo(Math.sqrt(200));
    }
}
