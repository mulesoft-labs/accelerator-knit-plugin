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

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Knit parser class implements the DW parser functionality.
 */
public class KnitParser {
    /**
     * Default constructor.
     */
    public KnitParser() {}

    /**
     * Parses a DW file with the provided root directory name and file name
     * and returns the parsed dwFile object.
     * @param rootDirName is a String with the root directory of the file to parse.
     * @param fileName is a String with the file name to parse.
     * @param dwlFileExt is a String with the dataweave file extension. (Default dwl)
     * @return A dwParse object.
     * @throws IOException
     */
    public DataWeaveFile parseFile(String rootDirName, String fileName, String dwlFileExt) throws IOException {
        DataWeaveFile ret = new DataWeaveFile(fileName.replaceFirst(rootDirName, ""), dwlFileExt);
        String fileStr = Utility.read(fileName);
        this.parseModuleComment(fileStr, ret);
        ret.setVariables(this.parseVariables(fileStr));
        ret.setFunctions(this.parseFunctions(fileStr));
        return ret;
    }

    /**
     * Parses the module comment with the provided file contents
     * and sets the comment information in the provided dwFile object.
     * @param text is a String with the file contents.
     * @param ret is the return dwFile object to set the comment information in.
     */
    private void parseModuleComment(String text, DataWeaveFile ret) {
        // Get the module section.
        String funPatternStr = "(\\/\\*\\*(.+?)\\*\\/\\s*%dw)";
        Pattern r = Pattern.compile(funPatternStr, Pattern.DOTALL | Pattern.MULTILINE);
        Matcher m = r.matcher(text);
        if (m.find()) {
            String commentStr = m.group(2).toString();
            ret.setCommentString(this.parseCommentString(commentStr).trim());
            ret.setComment(this.parseComment(ret.getCommentString()));
        }
    }

    /**
     * Parses the DW functions with the provided file text and returns an
     * array of dwFunction object with the results.
     * @param text is a String with the file text.
     * @return An ArrayList of dwFunction objects with the function list.
     */
    private ArrayList<DataWeaveFunction> parseFunctions(String text) {
        ArrayList<DataWeaveFunction> ret = new ArrayList<DataWeaveFunction>();

        // Get functions sections.
        String funPatternStr = "(\\/\\*\\*[^\\/]+?\\*\\/\\s*fun\\s*\\w*\\s*\\(.*?\\))";
        Pattern r = Pattern.compile(funPatternStr, Pattern.DOTALL | Pattern.MULTILINE);
        Matcher m = r.matcher(text);
        while (m.find()) {
            for (int i = 0; i < m.groupCount(); i++) {
                ret.add(this.parseFunctionString(m.group(i).toString()));
            }
        }

        return ret;
    }

    /**
     * Parses each individual function text and returns a dwFunction
     * object with the result.
     * @param functionString is a String with the function text.
     * @return A dwFunction object with the result.
     */
    private DataWeaveFunction parseFunctionString(String functionString) {
        DataWeaveFunction funct = new DataWeaveFunction();

        String funPatternStr = "\\/\\*\\*(.+?)\\*\\/\\s*fun\\s*(\\w*)\\s*\\((.*?)\\)";
        Pattern r = Pattern.compile(funPatternStr, Pattern.DOTALL | Pattern.MULTILINE);
        Matcher m = r.matcher(functionString);
        while (m.find()) {
            funct.setCommentString(this.parseCommentString(m.group(1).toString()).trim());
            funct.setName(m.group(2).toString());
            funct.setComment(this.parseComment(funct.getCommentString()));
            funct.setTable(this.parseAnnotationTable(funct.getComment()));
            funct.setArguments(this.parseArguments(m.group(3).toString()));
        }

        return funct;
    }

    /**
     * Processes the provided comment block line by line replacing any space
     * and * characters preceding the text.
     * @param str is a comment block to process.
     * @return A String with the leading space and * removed.
     */
    private String parseCommentString(String str) {
        String ret = "";

        for (String line : str.toString().split(System.lineSeparator())) {
            ret += line.replaceFirst("^\\s\\*\\s?", "") + System.lineSeparator();
        }

        return ret;
    }

    /**
     * Parses the actual comment block and returns a dwComment object with the result.
     * @param str is the comment string to parse.
     * @return A dwComment object with the result.
     */
    private DataWeaveComment parseComment(String str) {
        DataWeaveComment comment = new DataWeaveComment();

        String pstr = "(.*?)(^@.*)";

        Pattern r = Pattern.compile(pstr, Pattern.DOTALL | Pattern.MULTILINE);
        Matcher m = r.matcher(str);
        if (m.find()) {
            comment.setText(m.group(1).toString());
            String annStr = m.group(2).toString();
            comment.setAnnotations(this.parseAnnotations(annStr));
        } else {
            comment.setText(str);
        }

        return comment;
    }

    /**
     * Parses the provided comment string and returns a list of annotations.
     * @param str is a comment string to parse.
     * @return An ArrayList of dwCommentAnnotation objects.
     */
    private ArrayList<DataWeaveCommentAnnotation> parseAnnotations(String str) {
        ArrayList<DataWeaveCommentAnnotation> ret = new ArrayList<DataWeaveCommentAnnotation>();
        String pstr = "^@(\\w+)\\s(.*?(?=@))";
        Pattern r = Pattern.compile(pstr, Pattern.DOTALL | Pattern.MULTILINE);
        Matcher m = r.matcher(str + System.lineSeparator() + "@");
        while (m.find()) {
            DataWeaveCommentAnnotation ann = new DataWeaveCommentAnnotation();
            ann.setName(m.group(1).toString());
            String kvStr = m.group(2).toString();
            if (ann.getName().equals("param")) {
                this.parseAnnotationValue(kvStr, ann);
            } else {
                ann.setValue(kvStr);
            }
            ret.add(ann);
        }
        return ret;
    }

    /**
     * Parses the annotation value for param type annotations with the
     * provided annotation string and dwCommentAnnotation object to update.
     * @param str is a String with the annotation text.
     * @param ann is a dwCommentAnnotation object to update.
     */
    private void parseAnnotationValue(String str, DataWeaveCommentAnnotation ann) {
        String pstr = "(\\w+)\\s(.*)";
        Pattern r = Pattern.compile(pstr, Pattern.DOTALL | Pattern.MULTILINE);
        Matcher m = r.matcher(str);
        if (m.find()) {
            ann.setKey(m.group(1).toString());
            ann.setValue(m.group(2).toString());
        }
    }

    /**
     * Parses the function arguments and returns a list of dwArgument objects
     * as the result.
     * @param str is a String with the arguments to parse.
     * @return An ArrayList of dwArgument objects with the result.
     */
    private ArrayList<DataWeaveArgument> parseArguments(String str) {
        ArrayList<DataWeaveArgument> args = new ArrayList<DataWeaveArgument>();
        String parts[] = str.split(",");
        for(String part : parts) {
            DataWeaveArgument arg = new DataWeaveArgument();
            if (part.contains(":")) {
                String argParts[] = part.split(":");

                arg.setName(argParts[0].trim());
                arg.setDatatype(argParts[1].trim());
            } else {
                arg.setName(part.trim());
            }
            args.add(arg);
        }
        return args;
    }

    /**
     * Parses the provided file text and returns a list of dwVariable
     * objects as the result.
     * @param text is a String with the file text.
     * @return An ArrayList of dwVariable objects.
     */
    private ArrayList<DataWeaveVariable> parseVariables(String text) {
        ArrayList<DataWeaveVariable> variables = new ArrayList<DataWeaveVariable>();

        // Get functions sections.
        String funPatternStr = "(\\/\\*\\*[^\\/]+?\\*\\/\\s*var\\s*\\w*)";
        Pattern r = Pattern.compile(funPatternStr, Pattern.DOTALL | Pattern.MULTILINE);
        Matcher m = r.matcher(text);
        while (m.find()) {
            for (int i = 0; i < m.groupCount(); i++) {
                variables.add(this.parseVariableString(m.group(i).toString()));
            }
        }

        return variables;
    }

    /**
     * Parses the provided variable string and returns a dwVariable object.
     * @param variableString is a String with the variable text.
     * @return A dwVariable object with the result.
     */
    private DataWeaveVariable parseVariableString(String variableString) {
        DataWeaveVariable var = new DataWeaveVariable();

        String varPatternStr = "\\/\\*\\*(.+?)\\*\\/\\s*var\\s*(\\w*)";
        Pattern r = Pattern.compile(varPatternStr, Pattern.DOTALL | Pattern.MULTILINE);
        Matcher m = r.matcher(variableString);
        while (m.find()) {
            var.setCommentString(this.parseCommentString(m.group(1).toString()).trim());
            var.setName(m.group(2).toString());
            var.setComment(this.parseComment(var.getCommentString()));
        }

        return var;
    }

    /**
     * Looks for annotations for table and returns an annotationTable object if found
     * and null if not.
     * @param comment is a dwComment object to search.
     * @return An annotationTable object if found or null if not.
     */
    private AnnotationTable parseAnnotationTable(DataWeaveComment comment) {
        AnnotationTable tbl = null;

        // Look for the table definition
        for (DataWeaveCommentAnnotation ann : comment.getAnnotations()) {
            if (ann.getName().toLowerCase().equals("table")) {
                tbl = new AnnotationTable();

                ArrayList<String> cols = new ArrayList<String>();

                for (String col : Utility.fromArray(ann.getValue().split("(?<!\\\\\\\\),"))) {
                    cols.add(col.replaceAll(System.lineSeparator(), ""));
                }

                tbl.setColumns(cols);
                break;
            }
        }

        // Look for rows now.
        if (tbl != null) {
            ArrayList<AnnotationRow> rows = new ArrayList<AnnotationRow>();
            for (DataWeaveCommentAnnotation ann : comment.getAnnotations()) {
                if (ann.getName().toLowerCase().equals("row")) {
                    AnnotationRow row = new AnnotationRow();

                    ArrayList<String> fields = new ArrayList<String>();
                    for (String str : ann.getValue().split("(?<!\\\\\\\\),")) {
                        // Replace escaped commas.
                        fields.add(str.replaceAll("\\\\\\\\,", ",").replaceAll(System.lineSeparator(), ""));
                    }

                    row.setFields(fields);
                    rows.add(row);
                }
            }
            tbl.setRows(rows);
        }

        return tbl;
    }
}
