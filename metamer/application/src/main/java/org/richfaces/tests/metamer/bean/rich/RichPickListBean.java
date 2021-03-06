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
package org.richfaces.tests.metamer.bean.rich;

import static org.richfaces.tests.metamer.bean.rich.RichOrderingListBean.extractCollectionType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import org.richfaces.component.UIPickList;
import org.richfaces.tests.metamer.Attributes;
import org.richfaces.tests.metamer.model.Capital;
import org.richfaces.tests.metamer.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Backing Bean for rich:pickList component at
 *
 * @author <a href="mailto:jjamrich@redhat.com">Jan Jamrich</a>
 * @version $Revision$
 */
@ManagedBean(name = "richPickListBean")
@SessionScoped
public class RichPickListBean implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(RichPickListBean.class);

    private static final long serialVersionUID = 2158237918061200181L;

    private Attributes attributes;
    @ManagedProperty("#{model.capitals}")
    private List<Capital> capitals;
    private List<SelectItem> capitalsOptions = null;
    @ManagedProperty(value = "#{model.employees}")
    private List<Employee> employees;
    private List<SelectItem> validationOptions = null;
    private List<SelectItem> value = null;

    /**
     *
     * @return attributes
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     *
     * @return capitals
     */
    public List<Capital> getCapitals() {
        return capitals;
    }

    /**
     * @return the capitalsOptions
     */
    public List<SelectItem> getCapitalsOptions() {
        return capitalsOptions;
    }

    public Object getCollectionType() {
        return extractCollectionType(attributes);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * @return the validationOptions
     */
    public List<SelectItem> getValidationOptions() {
        return validationOptions;
    }

    /**
     * @return the value
     */
    public List<SelectItem> getValue() {
        return value;
    }

    @PostConstruct
    public void init() {
        attributes = Attributes.getComponentAttributesFromFacesConfig(UIPickList.class, getClass());
        capitalsOptions = new ArrayList<SelectItem>();
        validationOptions = new ArrayList<SelectItem>();
        value = new ArrayList<SelectItem>();

        for (Capital capital : capitals) {
            capitalsOptions.add(new SelectItem(capital.getState(), capital.getState()));
            validationOptions.add(new SelectItem(capital.getState(), capital.getState()));
        }
        validationOptions.add(new SelectItem("@@", "@@"));
        validationOptions.add(new SelectItem("", ""));
        validationOptions.add(new SelectItem("RichFaces", "RichFaces"));
        validationOptions.add(new SelectItem("richfaces", "richfaces"));

        attributes.setAttribute("rendered", Boolean.TRUE);
        attributes.setAttribute("minListHeight", "100");

        attributes.setAttribute("requiredMessage", "Not empty target list is required!");
        attributes.setAttribute("validatorMessage", "We are sorry, but @ is not allowed to join us!");

        attributes.setAttribute("collectionType", "string-LinkedList");

        attributes.setAttribute("addText", ">");
        attributes.setAttribute("addAllText", ">>");
        attributes.setAttribute("removeText", "<");
        attributes.setAttribute("removeAllText", "<<");

        attributes.setAttribute("upTopText", "⇑ First");
        attributes.setAttribute("upText", "↑ Up");
        attributes.setAttribute("downText", "↓ Down");
        attributes.setAttribute("downBottomText", "⇓ Last");

        attributes.setAttribute("listWidth", "220");
        attributes.setAttribute("valueChangeListener", "valueChangeListenerImproved");

        attributes.remove("converter");
        attributes.remove("converterMessage");
        attributes.remove("columnVar");

        employees = employees.subList(0, 10);
    }

    /**
     *
     * @param attributes
     */
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    /**
     *
     * @param capitals
     */
    public void setCapitals(List<Capital> capitals) {
        this.capitals = capitals;
    }

    /**
     * @param capitalsOptions the capitalsOptions to set
     */
    public void setCapitalsOptions(List<SelectItem> capitalsOptions) {
        this.capitalsOptions = capitalsOptions;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * @param validationOptions the validationOptions to set
     */
    public void setValidationOptions(List<SelectItem> validationOptions) {
        this.validationOptions = validationOptions;
    }

    /**
     * @param value the value to set
     */
    public void setValue(List<SelectItem> value) {
        this.value = value;
    }

    /**
     * Test method for verify validator attribute
     *
     * @param context
     * @param component
     * @param value
     */
    public void validatePickListValue(FacesContext context, UIComponent component, Object value) {
        LOG.info(" #validatePickListValue: Custom validation for rich:pickList ");

        @SuppressWarnings("unchecked")
        List<String> l = (List<String>) value;
        if (l.contains("@@")) {
            LOG.info(" Validation rule violation found! One of picked items contains '@'!");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "PickList don't like '@' char!",
                "PickList don't like '@' char!"));
        }
    }
}
