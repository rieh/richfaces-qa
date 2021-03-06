package org.richfaces.tests.metamer.ftest.richColumnGroup;

import org.openqa.selenium.support.FindBy;
import org.richfaces.tests.metamer.ftest.annotations.IssueTracking;
import org.richfaces.tests.metamer.ftest.extension.configurator.skip.annotation.Skip;
import org.richfaces.tests.metamer.ftest.richDataTable.fragment.ColumnGroupEDT;
import org.testng.annotations.Test;

/**
 *
 * @author <a href="mailto:mtomasek@redhat.com">Martin Tomasek</a>
 *
 */
public class TestColumnGroupHeaderFooterEDT extends TestColumnGroupHeaderFooter {

    @FindBy(css = ".rf-edt[id$=richEDT]")
    private ColumnGroupEDT table;

    @Override
    protected ColumnGroupEDT getTable() {
        return table;
    }

    @Override
    public String getComponentTestPagePath() {
        return "richColumnGroup/headerFooterEDT.xhtml";
    }

    @Test
    @Skip
    @IssueTracking("https://issues.jboss.org/browse/RF-11749")
    public void testRendered() {
        //After fix remove future annotation and test it on localhost, maybe EDT component wont work as expected.
        super.testRendered();
    }

}
