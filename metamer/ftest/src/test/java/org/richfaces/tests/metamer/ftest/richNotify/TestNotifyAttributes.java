/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2016, Red Hat, Inc. and individual contributors
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
 */
package org.richfaces.tests.metamer.ftest.richNotify;

import java.util.concurrent.TimeUnit;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.richfaces.fragment.common.Event;
import org.richfaces.fragment.common.Utils;
import org.richfaces.fragment.notify.NotifyMessage.NotifyMessagePosition;
import org.richfaces.fragment.notify.RichFacesNotifyMessage;
import org.richfaces.tests.metamer.bean.rich.RichNotifyBean;
import org.richfaces.tests.metamer.ftest.AbstractWebDriverTest;
import org.richfaces.tests.metamer.ftest.annotations.IssueTracking;
import org.richfaces.tests.metamer.ftest.extension.attributes.coverage.annotations.CoversAttributes;
import org.richfaces.tests.metamer.ftest.extension.configurator.skip.annotation.Skip;
import org.richfaces.tests.metamer.ftest.extension.configurator.templates.annotation.Templates;
import org.richfaces.tests.metamer.ftest.webdriver.Attributes;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class TestNotifyAttributes extends AbstractWebDriverTest {

    private final Attributes<NotifyAttributes> notifyAttributes = getAttributes();

    @FindBy(className = "rf-ntf")
    private RichFacesNotifyMessage message;
    @FindBy(id = "newSpan")
    private WebElement newSpan;

    public static void waitForOpacityChange(final double opacity, final WebElement element) {
        Graphene.waitGui().withTimeout(3, TimeUnit.SECONDS).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver from) {
                return element.getCssValue("opacity").equals((opacity == 0 ? "0" : (opacity == 1 ? "1" : String.valueOf(opacity))));
            }

            @Override
            public String toString() {
                return "opacity change to value '" + opacity + "'. Actual value '" + element.getCssValue("opacity") + "'";
            }
        });
    }

    @Override
    public String getComponentTestPagePath() {
        return "richNotify/simple.xhtml";
    }

    @Test(groups = "smoke")
    @CoversAttributes("detail")
    public void testDetail() {
        String detail = "Some detail";
        notifyAttributes.set(NotifyAttributes.detail, detail);
        Assert.assertEquals(message.getDetail(), detail);
        Assert.assertEquals(message.getSummary(), RichNotifyBean.DEFAULT_SUMMARY);
    }

    @Test(groups = "smoke")
    @CoversAttributes("escape")
    public void testEscape() {
        String newSpanString = "<span id='newSpan'>new span</span>";
        notifyAttributes.set(NotifyAttributes.detail, newSpanString);

        notifyAttributes.set(NotifyAttributes.escape, Boolean.FALSE);
        assertVisible(newSpan, "New created span should be visible");

        notifyAttributes.set(NotifyAttributes.escape, Boolean.TRUE);
        assertNotVisible(newSpan, "New created span should not be visible");
    }

    @Test
    public void testInit() {
        Assert.assertTrue(message.advanced().isVisible());
        Assert.assertEquals(message.getDetail(), RichNotifyBean.DEFAULT_DETAIL);
        Assert.assertEquals(message.getSummary(), RichNotifyBean.DEFAULT_SUMMARY);
    }

    @IssueTracking("https://issues.jboss.org/browse/RF-12925")
    @Test
    @Skip
    @CoversAttributes({ "detail", "summary" })
    public void testNoDetailNoSummary() {
        attsSetter()
            .setAttribute(NotifyAttributes.detail).toValue("")
            .setAttribute(NotifyAttributes.summary).toValue("")
            .asSingleAction().perform();
        Assert.assertFalse(message.advanced().isVisible());
    }

    @Test
    @CoversAttributes("nonblocking")
    public void testNonblocking() {
        attsSetter()
            .setAttribute(NotifyAttributes.nonblocking).toValue(true)
            .setAttribute(NotifyAttributes.nonblockingOpacity).toValue(0)
            .asSingleAction().perform();
        Utils.triggerJQ(executor, "mouseover", message.advanced().getRootElement());
        TestNotifyAttributes.waitForOpacityChange(0, message.advanced().getRootElement());
        Utils.triggerJQ(executor, "mouseout", message.advanced().getRootElement());
        TestNotifyAttributes.waitForOpacityChange(1, message.advanced().getRootElement());
    }

    @Test
    @CoversAttributes("nonblockingOpacity")
    public void testNonblockingOpacity() {
        attsSetter()
            .setAttribute(NotifyAttributes.nonblocking).toValue(true)
            .setAttribute(NotifyAttributes.nonblockingOpacity).toValue(0.5)
            .asSingleAction().perform();
        Utils.triggerJQ(executor, "mouseover", message.advanced().getRootElement());
        TestNotifyAttributes.waitForOpacityChange(0.5, message.advanced().getRootElement());
        Utils.triggerJQ(executor, "mouseout", message.advanced().getRootElement());
        TestNotifyAttributes.waitForOpacityChange(1, message.advanced().getRootElement());
    }

    @Test
    @CoversAttributes("onclick")
    @Templates(value = "plain")
    public void testOnclick() {
        testFireEvent(Event.CLICK, message.advanced().getRootElement());
    }

    @Test
    @CoversAttributes("ondblclick")
    @Templates(value = "plain")
    public void testOndblclick() {
        testFireEvent(Event.DBLCLICK, message.advanced().getRootElement());
    }

    @Test
    @CoversAttributes("onkeydown")
    @Templates(value = "plain")
    public void testOnkeydown() {
        testFireEvent(Event.KEYDOWN, message.advanced().getRootElement());
    }

    @Test
    @CoversAttributes("onkeypress")
    @Templates(value = "plain")
    public void testOnkeypress() {
        testFireEvent(Event.KEYPRESS, message.advanced().getRootElement());
    }

    @Test
    @CoversAttributes("onkeyup")
    @Templates(value = "plain")
    public void testOnkeyup() {
        testFireEvent(Event.KEYUP, message.advanced().getRootElement());
    }

    @Test
    @CoversAttributes("onmousedown")
    @Templates(value = "plain")
    public void testOnmousedown() {
        testFireEvent(Event.MOUSEDOWN, message.advanced().getRootElement());
    }

    @Test
    @CoversAttributes("onmousemove")
    @Templates(value = "plain")
    public void testOnmousemove() {
        testFireEvent(Event.MOUSEMOVE, message.advanced().getRootElement());
    }

    @Test
    @CoversAttributes("onmouseout")
    @Templates(value = "plain")
    public void testOnmouseout() {
        testFireEvent(Event.MOUSEOUT, message.advanced().getRootElement());
    }

    @Test
    @CoversAttributes("onmouseover")
    @Templates(value = "plain")
    public void testOnmouseover() {
        testFireEvent(Event.MOUSEOVER, message.advanced().getRootElement());
    }

    @Test
    @CoversAttributes("onmouseup")
    @Templates(value = "plain")
    public void testOnmouseup() {
        testFireEvent(Event.MOUSEUP, message.advanced().getRootElement());
    }

    @Test
    @CoversAttributes("rendered")
    @Templates(value = "plain")
    public void testRendered() {
        notifyAttributes.set(NotifyAttributes.rendered, Boolean.TRUE);
        assertVisible(message.advanced().getRootElement(), "Message should be visible.");
        notifyAttributes.set(NotifyAttributes.rendered, Boolean.FALSE);
        assertNotVisible(message.advanced().getRootElement(), "Message should not be visible.");
    }

    @Test
    @CoversAttributes("showCloseButton")
    public void testShowCloseButton() {
        notifyAttributes.set(NotifyAttributes.showCloseButton, Boolean.TRUE);
        message.close();
        assertNotVisible(message.advanced().getRootElement(), "Message should not be visible after close.");

        notifyAttributes.set(NotifyAttributes.showCloseButton, Boolean.FALSE);
        try {
            message.close();
        } catch (TimeoutException ok) {
            return;
        }
        Assert.fail("The notify message should not be closeable if there is no close button.");
    }

    @Test
    @CoversAttributes("showShadow")
    @IssueTracking("https://issues.jboss.org/browse/RF-13792")
    public void testShowShadow() {
        notifyAttributes.set(NotifyAttributes.showShadow, Boolean.TRUE);
        assertVisible(message.advanced().getShadowElement(), "Shadow should be visible");

        notifyAttributes.set(NotifyAttributes.showShadow, Boolean.FALSE);
        assertNotVisible(message.advanced().getShadowElement(), "Shadow should not be visible");
    }

    @Test
    @CoversAttributes("stack")
    public void testStack() {
        String[] stacks = { "topLeftStack", "bottomRightStack" };
        //default position is top right
        notifyAttributes.set(NotifyAttributes.stack, "");
        Assert.assertEquals(message.advanced().getPosition(), NotifyMessagePosition.TOP_RIGHT);

        notifyAttributes.set(NotifyAttributes.stack, stacks[0]);
        Assert.assertEquals(message.advanced().getPosition(), NotifyMessagePosition.TOP_LEFT);

        notifyAttributes.set(NotifyAttributes.stack, stacks[1]);
        Assert.assertEquals(message.advanced().getPosition(), NotifyMessagePosition.BOTTOM_RIGHT);
    }

    @Test
    @CoversAttributes("stayTime")
    public void testStaytime() {
        attsSetter()
            .setAttribute(NotifyAttributes.stayTime).toValue(1000)
            .setAttribute(NotifyAttributes.sticky).toValue(false)
            .asSingleAction().perform();
        waiting(3000);
        assertNotVisible(message.advanced().getRootElement(), "Message should not be visible now.");
    }

    @Test
    @CoversAttributes("sticky")
    @IssueTracking("https://issues.jboss.org/browse/RF-11558")
    public void testSticky() {
        attsSetter()
            .setAttribute(NotifyAttributes.stayTime).toValue(1000)
            .setAttribute(NotifyAttributes.sticky).toValue(true)
            .setAttribute(NotifyAttributes.showCloseButton).toValue(true)
            .asSingleAction().perform();
        waiting(3000);
        assertVisible(message.advanced().getRootElement(), "Message should be visible until closed.");
        message.close();
        assertNotVisible(message.advanced().getRootElement(), "Message should not be visible after closed.");

        // when sticky, the close button should be always visible ( https://issues.jboss.org/browse/RF-11558 )
        notifyAttributes.set(NotifyAttributes.showCloseButton, false);// this should reload the page and show the notify message again
        waiting(3000);
        assertVisible(message.advanced().getRootElement(), "Message should be visible until closed.");
        message.close();
        assertNotVisible(message.advanced().getRootElement(), "Message should not be visible after closed.");
    }

    @Test
    @CoversAttributes("styleClass")
    @Templates(value = "plain")
    public void testStyleClass() {
        testStyleClass(message.advanced().getRootElement());
    }

    @Test
    @CoversAttributes("summary")
    public void testSummary() {
        String summary = "Some summary";
        notifyAttributes.set(NotifyAttributes.summary, summary);
        Assert.assertEquals(message.getDetail(), RichNotifyBean.DEFAULT_DETAIL);
        Assert.assertEquals(message.getSummary(), summary);
    }
}
