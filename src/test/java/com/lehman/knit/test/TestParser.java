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

package com.lehman.knit.test;

import java.util.ArrayList;

import com.lehman.knit.Main;
import com.lehman.knit.DataWeaveDocWriter;
import com.lehman.knit.DataWeaveFile;
import com.lehman.knit.MarkdownDataWeaveDocWriterImpl;

/**
 * Test class for Knit functionality.
 */
public class TestParser extends Object {

    /**
     * Main entry point of the test application.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        Main mn = new Main();
        ArrayList<DataWeaveFile> parsedFiles = new ArrayList<DataWeaveFile>();
        mn.parseDirectory("src/test/resources/dwl", parsedFiles);

        DataWeaveDocWriter writer = new MarkdownDataWeaveDocWriterImpl();
        String doc = writer.writeDoc(parsedFiles);
        System.out.println(doc);
    }
}
