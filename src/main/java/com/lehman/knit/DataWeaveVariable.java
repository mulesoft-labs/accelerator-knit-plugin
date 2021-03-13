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
 * Class models a dataweave variable.
 */
public class DataWeaveVariable {
    private String commentString = "";
    private DataWeaveComment comment = new DataWeaveComment();
    private String name = "";

    /**
     * Gets the comment string of the variable.
     * @return A String with the comment of the variable.
     */
    public String getCommentString() {
        return commentString;
    }

    /**
     * Sets the comment string of the variable.
     * @param commentString is a String to set as the comment of the variable.
     */
    public void setCommentString(String commentString) {
        this.commentString = commentString;
    }

    /**
     * Gets the comment object of the variable.
     * @return A dwComment object of the variable.
     */
    public DataWeaveComment getComment() {
        return comment;
    }

    /**
     * Sets the comment object of the variable.
     * @param comment is a dwComment object to set for the variable.
     */
    public void setComment(DataWeaveComment comment) {
        this.comment = comment;
    }

    /**
     * Gets the variable name.
     * @return A String with the variable name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the variable name.
     * @param name is a String with the variable name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
