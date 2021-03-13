/*
 * Copyright 2020 Roseville Code Inc. (austin@rosevillecode.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.lehman.knit;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that models a DW comment annotation table row.
 */
public class AnnotationRow {
    /**
     * A list of strings with the fields of a row.
     */
    private List<String> fields = new ArrayList<String>();

    /**
     * Gets a List of Strings with the fields of the row.
     * @return A List of Strings.
     */
    public List<String> getFields() {
        return fields;
    }

    /**
     * Sets a List of Strings with the fields of the row.
     * @param fields is a List of Strings to set.
     */
    public void setFields(List<String> fields) {
        this.fields = fields;
    }
}
