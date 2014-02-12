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
package org.richfaces.tests.showcase.param;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Page;
import static org.testng.Assert.assertEquals;

import org.richfaces.tests.showcase.AbstractWebDriverTest;
import org.richfaces.tests.showcase.param.page.ParamPage;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jhuska@redhat.com">Juraj Huska</a>
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 * @version $Revision$
 */
public class TestParam extends AbstractWebDriverTest {

    /* *******************************************************************************************************
     * Locators ****************************************************************** *************************************
     */

    @Page
    private ParamPage page;

    /* ********************************************************************************************************
     * Tests ********************************************************************* ***********************************
     */

    @Test
    public void testClickToButtonAndCheckTheSelectedName() {
        assertEquals(page.output.getText().trim(), "Selected Name:",
            "There can not be anything selected at first after page first load!");
        for(ParamPage.Name name : ParamPage.Name.getAll()) {
            page.setName(name);
            Graphene.waitGui()
                    .until("After selecting name, the output should contain the name.")
                    .element(page.output)
                    .text()
                    .equalTo("Selected Name:" + name);
        }
    }

}
