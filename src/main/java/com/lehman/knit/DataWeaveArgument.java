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
 * Class models a function argument.
 */
public class DataWeaveArgument {
    /**
     * The argument name.
     */
    private String name = "";

    /**
     * The argument datatype if specified.
     */
    private String datatype = "";

    /**
     * Default constructor.
     */
    public DataWeaveArgument() { }

    /**
     * Constructor with name arg.
     * @param Name is a String with the argument name.
     */
    public DataWeaveArgument(String Name) { this.name = Name; }

    /**
     * Constructor with name and datatype.
     * @param Name is a String with the argument name.
     * @param Datatype is a String with the datatype.
     */
    public DataWeaveArgument(String Name, String Datatype) {
        this.name = Name;
        this.datatype = Datatype;
    }

    /**
     * Gets the argument name.
     * @return A String with the argument name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the argument name.
     * @param name is a String with the argument name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the datatype.
     * @return A String with the datatype.
     */
    public String getDatatype() {
        return datatype;
    }

    /**
     * Sets the datatype.
     * @param datatype is a String with the datatype.
     */
    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }
}
