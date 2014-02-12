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
package org.richfaces.tests.photoalbum.ftest.webdriver.fragments;

import static org.testng.Assert.assertTrue;

import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.openqa.selenium.WebElement;
import org.richfaces.tests.photoalbum.ftest.webdriver.utils.PhotoalbumUtils;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class FooterPanel {

    @FindByJQuery("a:contains('Terms And Conditions')")
    private WebElement termAndConditionsLink;
    @FindByJQuery("a:contains('Privacy Statement')")
    private WebElement privacyStatementLink;
    @FindByJQuery("img:eq(0)")
    private WebElement bottomLogoImage;

    public void check() {
        PhotoalbumUtils.checkVisible(Lists.newArrayList(termAndConditionsLink, privacyStatementLink, bottomLogoImage));
        assertTrue(bottomLogoImage.getAttribute("src").contains("img/shell/logo_bottom.gif"));
    }

    public WebElement getBottomLogoImage() {
        return bottomLogoImage;
    }

    public WebElement getPrivacyStatementLink() {
        return privacyStatementLink;
    }

    public WebElement getTermAndConditionsLink() {
        return termAndConditionsLink;
    }
}
