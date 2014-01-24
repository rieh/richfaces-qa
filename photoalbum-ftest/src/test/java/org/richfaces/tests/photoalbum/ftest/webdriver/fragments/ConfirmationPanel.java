/*******************************************************************************
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2013, Red Hat, Inc. and individual contributors
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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.fragment.panel.TextualFragmentPart;
import org.richfaces.fragment.popupPanel.RichFacesPopupPanel;
import org.richfaces.tests.photoalbum.ftest.webdriver.fragments.ConfirmationPanel.Body;
import org.richfaces.tests.photoalbum.ftest.webdriver.fragments.HowItWorksPopupPanel.Controls;

/**
 *
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class ConfirmationPanel extends RichFacesPopupPanel<TextualFragmentPart, Controls, Body> {

    public void cancel() {
        getBodyContent().getCancelButton().click();
        advanced().waitUntilPopupIsNotVisible().perform();
    }

    public void check(String msg) {
        assertEquals(getBodyContent().getConfirmMessage().getText(), msg);
        assertTrue(getBodyContent().getImage().getAttribute("src").endsWith("ico_warning.png"));
        assertEquals(getHeaderContent().getText(), "Confirmation:");
    }

    public void close() {
        getHeaderControlsContent().close();
        advanced().waitUntilPopupIsNotVisible().perform();
    }

    public void ok() {
        Graphene.guardAjax(getBodyContent().getOkButton()).click();
        advanced().waitUntilPopupIsNotVisible().perform();
    }

    public static class Body {

        @FindByJQuery("div:contains('OK') + input:visible")
        private WebElement okButton;
        @FindByJQuery("div:contains('Cancel') + input:visible")
        private WebElement cancelButton;
        @FindByJQuery("img")
        private WebElement image;
        @FindBy(css = "div[id$='confirmMessage']")
        private WebElement confirmMessage;

        public WebElement getCancelButton() {
            return cancelButton;
        }

        public WebElement getConfirmMessage() {
            return confirmMessage;
        }

        public WebElement getImage() {
            return image;
        }

        public WebElement getOkButton() {
            return okButton;
        }

    }
}
