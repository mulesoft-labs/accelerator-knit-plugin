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

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides utility functions.
 */
public class Utility {

	/**
	 * Joins a list of strings provided using the provided delimiter into a single string.
	 * @param delim is a String with the glue.
	 * @param arr is an ArrayList of String objects to join.
	 * @return A String with the joined parts.
	 */
    public static String join(String delim, List<String> arr) {
        String ret = "";
        for (int i = 0; i < arr.size(); i++) {
            if (i > 0) ret += delim;
            ret += arr.get(i);
        }
        return ret;
    }

	/**
	 * Converts an Array of Strings to an ArrayList of Strings. It also strips
	 * blank strings out as it goes.
	 * @param arr is an array of Strings.
	 * @return An ArrayList of Strings.
	 */
	public static ArrayList<String> fromArray(String[] arr) {
        ArrayList<String> ret = new ArrayList<String>();
        for (String str : arr) {
        	if (!str.trim().equals("")) {
				ret.add(str);
			}
        }
        return ret;
    }

    /**
	 * Reads a file with the provided file name and returns it as
	 * a String.
	 * @param FileName is a String with the file name to read.
	 * @return A String with the file contents.
	 * @throws IOException on IO exception.
	 */
	public static String read(String FileName) throws IOException
	{
		FileReader fr = null;
		try {
			fr = new FileReader(FileName);

			int len = -1;
			char[] buff = new char[4096];
			final StringBuffer buffer = new StringBuffer();
			while ((len = fr.read(buff)) > 0) { buffer.append(buff, 0, len); }

	        return buffer.toString();
	    } catch (IOException e) {
			throw e;
		} finally {
			if(fr != null) {
		        try { fr.close(); }
		        catch (IOException e) { throw e; }
			}
	    }
	}

	/**
	 * Writes the provided String to file. If append is set to true, it will append
	 * the text to file, otherwise it will replace it if the file already exists.
	 * @param FileName is a String with the file name to save.
	 * @param Data is a String with the text data to write.
	 * @param Append is a boolean with true for append and false for not.
	 * @throws IOException on IO exception.
	 */
	public static void write(String FileName, String Data, boolean Append) throws IOException {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(FileName, Append));
			bw.write(Data);
		} catch (IOException e) {
			throw e;
		} finally {
			if(bw != null) {
		        try { bw.close(); }
		        catch (IOException e) { throw e; }
			}
	    }
	}

	/**
	 * Strips all newline characters and replaces with a space. If there are multiple
	 * spaces in a row it also makes them a single space.
	 * @param in is the String to fix.
	 * @return A String with newline replaced.
	 */
	public static String stripNewLines(String in) {
		return
				in.replaceAll("\n", " ")
						.replaceAll("\r", " ")
						.replaceAll("\\s+", " ");
	}
}
