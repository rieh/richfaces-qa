/*******************************************************************************
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2014, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *******************************************************************************/
package org.richfaces.tests.metamer.ftest.richColumn;

import static org.jboss.test.selenium.support.url.URLUtils.buildUrl;
import static org.richfaces.tests.metamer.ftest.richColumn.ColumnAttributes.breakRowBefore;
import static org.richfaces.tests.metamer.ftest.richColumn.ColumnAttributes.colspan;
import static org.richfaces.tests.metamer.ftest.richColumn.ColumnAttributes.rendered;
import static org.richfaces.tests.metamer.ftest.richColumn.ColumnAttributes.rowspan;
import static org.testng.Assert.assertEquals;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.richfaces.tests.metamer.ftest.BasicAttributes;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class TestColumnSimple extends AbstractColumnTest {

    private int bodyCount(int row) {
        return getTable().getRow(row).getRowElementsCount();
    }

    private void checkInitialState() {
        assertEquals(headerCount(0), 1);
        assertEquals(headerCount(1), 2);
        assertEquals(headerCount(2), 1);
        assertEquals(headerCount(3), 1);
        assertEquals(bodyCount(0), 2);

        assertEquals(headerCell(0, 0).getAttribute("colspan"), "2");
        assertEquals(headerCell(1, 1).getAttribute("rowspan"), "2");
    }

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richColumn/simple.xhtml");
    }

    private WebElement headerCell(int row, int column) {
        return getTable().getHeader().getCell(row, column);
    }

    private int headerCount(int row) {
        return getTable().getHeader().getRow(row).findElements(By.className("rf-dt-hdr-c")).size();
    }

    @Test
    public void testBreakRowBefore() {
        checkInitialState();
        columnAttributes.set(breakRowBefore, false);

        assertEquals(headerCount(0), 3);
        assertEquals(headerCount(1), 1);
        assertEquals(headerCount(2), 1);

        assertEquals(headerCell(0, 0).getAttribute("colspan"), "2");
        assertEquals(headerCell(0, 2).getAttribute("rowspan"), "2");
    }

    @Test
    public void testColspan() {
        checkInitialState();

        columnAttributes.set(colspan, 1);

        assertEquals(headerCount(0), 1);
        assertEquals(headerCount(1), 2);
        assertEquals(headerCount(2), 1);
        assertEquals(headerCount(3), 1);

        assertEquals(headerCell(0, 0).getAttribute("colspan"), "1");
        assertEquals(headerCell(1, 1).getAttribute("rowspan"), "2");
    }

    @Test
    public void testFooterClass() {
        testStyleClass(getTable().getColumnFooterElement(), BasicAttributes.footerClass);
    }

    @Test
    public void testHeaderClass() {
        testStyleClass(getTable().getColumnHeaderElement(), BasicAttributes.headerClass);
    }

    @Test
    public void testRendered() {
        checkInitialState();

        columnAttributes.set(rendered, false);

        assertEquals(headerCount(0), 1);
        assertEquals(headerCount(1), 2);
        assertEquals(headerCount(2), 1);
        assertEquals(headerCount(3), 1);
        assertEquals(bodyCount(0), 1);

        assertEquals(headerCell(0, 0).getAttribute("colspan"), "2");
        assertEquals(headerCell(1, 1).getAttribute("rowspan"), "2");
    }

    @Test
    public void testRowspanTo1() {
        checkInitialState();

        columnAttributes.set(rowspan, 1);

        assertEquals(headerCount(0), 1);
        assertEquals(headerCount(1), 2);
        assertEquals(headerCount(2), 2);

        assertEquals(headerCell(0, 0).getAttribute("colspan"), "2");
        assertEquals(headerCell(1, 1).getAttribute("rowspan"), "1");
    }

    @Test
    public void testRowspanTo3() {
        checkInitialState();

        columnAttributes.set(rowspan, 3);

        assertEquals(headerCount(0), 1);
        assertEquals(headerCount(1), 2);
        assertEquals(headerCount(2), 1);
        assertEquals(headerCount(3), 1);

        assertEquals(headerCell(0, 0).getAttribute("colspan"), "2");
        assertEquals(headerCell(1, 1).getAttribute("rowspan"), "3");
    }
}