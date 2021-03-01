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
 * Class that models a DW comment annotation table.
 */
public class AnnotationTable {
    /**
     * The column names for the table.
     */
    private List<String> columns = new ArrayList<String>();

    /**
     * The rows of the table.
     */
    private List<AnnotationRow> rows = new ArrayList<AnnotationRow>();

    /**
     * Gets the list of columns of the table.
     * @return A List of Strings with the columns.
     */
    public List<String> getColumns() {
        return columns;
    }

    /**
     * Sets the list of columns for the table.
     * @param columns is a List of String objects with the table columns.
     */
    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    /**
     * Gets a List of annotationRow objects with the table rows.
     * @return A List of annotationRow objects.
     */
    public List<AnnotationRow> getRows() {
        return rows;
    }

    /**
     * Sets the rows of the table.
     * @param rows is a List of annotationRow objects to set.
     */
    public void setRows(List<AnnotationRow> rows) {
        this.rows = rows;
    }
}
