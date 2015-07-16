/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2015, Red Hat, Inc. and individual contributors
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.richfaces.component.UIOrderingList;
import org.richfaces.tests.metamer.Attributes;
import org.richfaces.tests.metamer.model.Capital;
import org.richfaces.tests.metamer.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * Simple bean for rich:orderingList component example.
 *
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@ManagedBean(name = "richOrderingListBean")
@SessionScoped
public class RichOrderingListBean implements Serializable {

    private static final String DEFAULT_COLLECTION_TYPE = "java.util.LinkedList";
    private static final Logger LOGGER = LoggerFactory.getLogger(RichOrderingListBean.class);
    private static final long serialVersionUID = 5868941019675985273L;

    private Attributes attributes;
    @ManagedProperty("#{model.capitals}")
    private List<Capital> capitals;
    private Object collectionType;
    @ManagedProperty("#{model.employees}")
    private List<Employee> employees;
    private List<Capital> emptyCapitals = Lists.newArrayList();
    private Collection<String> hiddenAttributes = new ArrayList<String>();
    private String validatorMessage;

    private Object extractCollectionType(String collectionString) {
        if (collectionString.startsWith("s:")) {
            return collectionString.substring(2);
        } else if (collectionString.startsWith("c:")) {
            try {
                return Class.forName(collectionString.substring(2));
            } catch (ClassNotFoundException e) {
                LOGGER.error(e + "\n Setting collectionType back to " + DEFAULT_COLLECTION_TYPE + '.');
                attributes.setAttribute("collectionType", "s:" + DEFAULT_COLLECTION_TYPE);
                return DEFAULT_COLLECTION_TYPE;
            }
        } else {
            attributes.setAttribute("collectionType", "s:" + DEFAULT_COLLECTION_TYPE);
            return DEFAULT_COLLECTION_TYPE;
        }
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public List<Capital> getCapitals() {
        return capitals;
    }

    public Object getCollectionType() {
        String collectionTypeString = attributes.get("collectionType").getValue().toString();
        if (collectionTypeString.contains(":")) {
            collectionType = extractCollectionType(collectionTypeString);
        }
        return collectionType;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Capital> getEmptyCapitals() {
        return emptyCapitals;
    }

    public Collection<String> getHiddenAttributes() {
        return hiddenAttributes;
    }

    public String getValidatorMessage() {
        return validatorMessage;
    }

    @PostConstruct
    public void init() {
        LOGGER.debug("initializing bean " + getClass().getName());
        attributes = Attributes.getComponentAttributesFromFacesConfig(UIOrderingList.class, getClass());

        attributes.setAttribute("downText", "Down");
        attributes.setAttribute("downBottomText", "Bottom");
        attributes.setAttribute("listWidth", 300);
        attributes.setAttribute("listHeight", 500);
        attributes.setAttribute("rendered", true);
        attributes.setAttribute("upText", "Up");
        attributes.setAttribute("upTopText", "Top");
        attributes.setAttribute("required", false);
        attributes.setAttribute("collectionType", "s:" + DEFAULT_COLLECTION_TYPE);

        employees = employees.subList(0, 10);

        // TODO has to be tested in another way
        String[] attrsToHide = new String[] { "itemLabel", "itemValue", "value", "var",
            "converter", "converterMessage", "validator", "validatorMessage", "valueChangeListener" };
        for (String attribute : attrsToHide) {
            hiddenAttributes.add(attribute);
            attributes.remove(attribute);
        }
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public void setCapitals(List<Capital> capitals) {
        this.capitals = capitals;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setEmptyCapitals(List<Capital> emptyCapitals) {
        this.emptyCapitals = emptyCapitals;
    }

    public void setHiddenAttributes(Collection<String> hiddenAttributes) {
        this.hiddenAttributes = hiddenAttributes;
    }

    public void setValidatorMessage(String validatorMessage) {
        this.validatorMessage = validatorMessage;
    }

}
