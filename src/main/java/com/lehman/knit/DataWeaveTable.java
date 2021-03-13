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

/**
 * Class models a mapping table.
 */
public class DataWeaveTable {
    /**
     * The comment string.
     */
    private String commentString = "";

    /**
     * The comment object.
     */
    private DataWeaveComment comment = new DataWeaveComment();

    /**
     * Annotation table.
     */
    private AnnotationTable table = null;

    /**
     * Gets the comment string of the function.
     * @return A String with the comment of the function.
     */
    public String getCommentString() {
        return commentString;
    }

    /**
     * Sets the comment string of the function.
     * @param commentString is a String to set as the comment of the function.
     */
    public void setCommentString(String commentString) {
        this.commentString = commentString;
    }

    /**
     * Gets the comment object of the function.
     * @return A dwComment object with the function comment.
     */
    public DataWeaveComment getComment() {
        return comment;
    }

    /**
     * Sets the comment object of the function.
     * @param comment is a dwComment object to set for the function.
     */
    public void setComment(DataWeaveComment comment) {
        this.comment = comment;
    }

    /**
     * Gets the annotation table.
     * @return An annotationTable object if set or null if not.
     */
    public AnnotationTable getTable() {
        return table;
    }

    /**
     * Sets the annotation table.
     * @param table is an annotationTable object to set.
     */
    public void setTable(AnnotationTable table) {
        this.table = table;
    }
}
