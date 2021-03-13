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
 * Class models a comment annotation.
 */
public class DataWeaveCommentAnnotation {
    private String name = "";
    private String key = "";
    private String value = "";

    /**
     * Gets the annotation name.
     * @return A String with the annotation name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the annotation name.
     * @param name is a String with the annotation name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the annotation key.
     * @return A String with the annotation key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the annotation key.
     * @param key is a String with the annotation key.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets the annotation value.
     * @return A String with the annotation value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the annotation value.
     * @param value is a String with the annotation value.
     */
    public void setValue(String value) {
        this.value = value;
    }
}
