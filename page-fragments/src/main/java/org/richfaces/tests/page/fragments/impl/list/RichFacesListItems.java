/*******************************************************************************
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and individual contributors
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
package org.richfaces.tests.page.fragments.impl.list;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;

public class RichFacesListItems extends ArrayList<ListItem> implements ListItems {

    private static final long serialVersionUID = 1L;

    public RichFacesListItems() {
    }

    public RichFacesListItems(Collection<? extends ListItem> c) {
        super(c);
    }

    public RichFacesListItems(Iterable<? extends ListItem> it) {
        this.addAll(Lists.newArrayList(it));
    }

    @Override
    public ListItems filter(ListItemsFilterBuilder builder) {
        return new RichFacesListItems(Iterables.filter(this, builder.build()));
    }
}
