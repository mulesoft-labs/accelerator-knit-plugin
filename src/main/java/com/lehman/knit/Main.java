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

import org.apache.commons.lang3.SystemUtils;
import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The main entry point class implements the normal main
 * function as well as the Mojo execute function for
 * Maven plugin support.
 */
@Mojo(name = "knit", defaultPhase = LifecyclePhase.PACKAGE)
public class Main extends AbstractMojo {
    /**
     * This isn't used but there in case it's needed later. It provides
     * the Maven project information to the execute() function.
     */
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    /**
     * Maven config value skip.
     * Flag to run the doc parser/generator. This is the on/off switch for the Maven plugin..
     */
    @Parameter(property = "skip")
    boolean skip = false;

    /**
     * Maven config value files.
     * A list of DW files to parse.
     */
    @Parameter(property = "files")
    String[] files;

    /**
     * Maven config value directories.
     * A list of DW directories to parse.
     */
    @Parameter(property = "directories")
    String[] directories = new String[]{ "src/main/resources/dwl" };

    /**
     * Maven config value consolidateOutput.
     * Flag to switch between files for each module and a single output file.
     */
    @Parameter(property = "consolidateOutput")
    boolean consolidateOutput = true;

    /**
     * Maven config value outputFile.
     * The output file to write to when singleOutputFile == true.
     */
    @Parameter(property = "outputFile")
    String outputFile = "target/knit-doc.md";

    /**
     * Maven config value outputHeaderText.
     * If set this will be output in the document at
     * the top of the document as is. Set as a CDATA element.
     */
    @Parameter(property = "outputHeaderText")
    String outputHeaderText = "";

    /**
     * Maven config value outputFooterText.
     * If set this will be output in the document at
     * the bottom of the document as is. Set as a CDATA element.
     */
    @Parameter(property = "outputFooterText")
    String outputFooterText = "";

    /**
     * Maven config value writeHeaderTable.
     * If set to true this will write a table towards
     * the top of the doc that links to each module below. This
     * works in conjunction with headerTableModuleList.
     */
    @Parameter(property = "writeHeaderTable")
    boolean writeHeaderTable = false;

    /**
     * Maven config value moduleList.
     * If writeHeaderTable is set to true then this list
     * can be provided to provide the specified order of modules
     * in the header table.
     */
    @Parameter(property = "moduleList")
    String[] moduleList = new String[0];

    /**
     * Maven config value dwlFileExt.
     * This provides the ability to define DataWeave files
     * with different file extensions. The default is dwl.
     */
    @Parameter(property = "dwlFileExt")
    String dwlFileExt = "dwl";

    /**
     * Maven config value showAbout.
     * Flag to print program information when run.
     */
    @Parameter(property = "showAbout")
    boolean showAbout = false;

    /**
     * Accessor to set the directories. So as to not overwrite the initial value, this checks
     * to see if the provided list is > 0 before replacing.
     * @param Directories is an array of strings with the list of directories to parse.
     */
    public void setDirectories(String[] Directories) { if (Directories.length > 0) { this.directories = Directories; } }

    /**
     * Accessor to set the files.
     * @param Files is an array of strings with the list of files to parse.
     */
    public void setFiles(String[] Files) { this.files = Files; }

    /**
     * Accessor to set the module list.
     * @param ModuleList is an array of strings with the module list specified.
     */
    public void setModuleList(String[] ModuleList) { this.moduleList = ModuleList; }

    /**
     * Parses a DW directory with the provided arguments.
     * @param dirName is a String with the directory name.
     * @param parsedFiles is an ArrayList of dwFile objects to store the parsed results.
     * @throws Exception
     */
    public void parseDirectory(String dirName, ArrayList<DataWeaveFile> parsedFiles) throws Exception {
        KnitParser parser = new KnitParser();
        File dir = new File(dirName);
        if (dir.exists()) {
            if (dir.isDirectory()) {
                for (String name : dir.list()) {
                    String relName = dirName + "/" + name;
                    File f = new File(relName);
                    if (f.isFile() && relName.endsWith("." + dwlFileExt)) {
                        parsedFiles.add(parser.parseFile(dirName, relName, dwlFileExt));
                    } else if (f.isDirectory()) {
                        parseDirectory(relName, parsedFiles);
                    }
                }
            } else {
                System.err.println("Provided directory '" + dirName + "' isn't a directory.");
            }
        } else {
            System.err.println("Provided directory '" + dirName + "' doesn't exist.");
        }
    }

    /**
     * The entry point of the Maven plugin.
     */
    public void execute() {
    	if( this.showAbout ) {
    		this.printAbout();
    	}

    	System.out.println("Running Knit doc generator ...");
        try {
            if (!this.skip) {
                if (!this.consolidateOutput) {
                    System.err.println("Error: knit-maven-plugin <singleOutputFile> is set to false but only single file is currently implemented.");
                    System.exit(1);
                }

                if (this.files.length > 0 || this.directories.length > 0) {
                    this.writeDwFile();
                } else {
                    System.err.println("Error: knit-maven-plugin <srcFiles> or <srcDirectories> aren't specified.");
                    System.exit(1);
                }
            } else {
                System.out.println("Info: knit-maven-plugin skipping doc generation. (skip=true)");
            }
        } catch (Exception e) {
            System.err.println("Bad news, the knit plugin ran into trouble. If it continues please report it at https://github.com/rsv-code/knit." + System.lineSeparator());
            e.printStackTrace();
        }
    }

    /**
     * Writes the dataweave doc file.
     */
    private void writeDwFile() {
        ArrayList<DataWeaveFile> parsedFiles = new ArrayList<DataWeaveFile>();

        try {
            // Parse directories
            for (String dir : this.directories) {
                this.parseDirectory(this.getWorkingDirectory() + "/" + dir, parsedFiles);
            }

            // Parse files
            KnitParser parser = new KnitParser();
            for (String fname : this.files) {
                parsedFiles.add(parser.parseFile(this.getWorkingDirectory(), fname, dwlFileExt));
            }

            // Create the doc writer and write the doc.
            DataWeaveDocWriter writer = new MarkdownDataWeaveDocWriterImpl();
            String doc = "";

            // If header text is set.
            if (!"".equals(this.outputHeaderText)) {
                doc += this.outputHeaderText + System.lineSeparator() + System.lineSeparator();
            }

            // If write header table is set.
            if (this.writeHeaderTable) {
                doc += writer.writeHeaderTable(parsedFiles, Arrays.asList(this.moduleList));
            }

            // Write the doc.
            doc += writer.writeDoc(parsedFiles, Arrays.asList(this.moduleList));

            // If footer text is set.
            if (!"".equals(this.outputFooterText)) {
                doc += this.outputFooterText + System.lineSeparator();
            }

            // Output to file.
            Utility.write(
                    this.getWorkingDirectory() + "/" + this.outputFile,
                    doc,
                    false
            );
            System.out.println("Document has been written to '" + this.outputFile + "'.");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: knit-maven-plugin parse failed.");
        }
    }

    /**
     * Gets the working directory for the plugin. This should return the maven
     * directory that's the parent to 'target'.
     * @return the current working directory.
     */
    private String getWorkingDirectory() {
        Model model = this.project.getModel();
        Build build = model.getBuild();
        File dir = new File(build.getDirectory());
        String ret = dir.getParent();
        if (SystemUtils.IS_OS_WINDOWS){
            ret = ret.replace("\\", "/");
        }
        return ret;
    }

    /**
     * Prints the about text to standard output.
     */
    private void printAbout() {
        String out = "";
        out += " __  __     __   __     __     ______      _____     ______     ______                            " + System.lineSeparator() +
                "/\\ \\/ /    /\\ \"-.\\ \\   /\\ \\   /\\__  _\\    /\\  __-.  /\\  __ \\   /\\  ___\\                           " + System.lineSeparator() +
                "\\ \\  _\"-.  \\ \\ \\-.  \\  \\ \\ \\  \\/_/\\ \\/    \\ \\ \\/\\ \\ \\ \\ \\/\\ \\  \\ \\ \\____                          " + System.lineSeparator() +
                " \\ \\_\\ \\_\\  \\ \\_\\\\\"\\_\\  \\ \\_\\    \\ \\_\\     \\ \\____-  \\ \\_____\\  \\ \\_____\\                         " + System.lineSeparator() +
                "  \\/_/\\/_/   \\/_/ \\/_/   \\/_/     \\/_/      \\/____/   \\/_____/   \\/_____/                         " + System.lineSeparator() +
                "                                                                                                  " + System.lineSeparator() +
                " ______     ______     __   __     ______     ______     ______     ______   ______     ______    " + System.lineSeparator() +
                "/\\  ___\\   /\\  ___\\   /\\ \"-.\\ \\   /\\  ___\\   /\\  == \\   /\\  __ \\   /\\__  _\\ /\\  __ \\   /\\  == \\   " + System.lineSeparator() +
                "\\ \\ \\__ \\  \\ \\  __\\   \\ \\ \\-.  \\  \\ \\  __\\   \\ \\  __<   \\ \\  __ \\  \\/_/\\ \\/ \\ \\ \\/\\ \\  \\ \\  __<   " + System.lineSeparator() +
                " \\ \\_____\\  \\ \\_____\\  \\ \\_\\\\\"\\_\\  \\ \\_____\\  \\ \\_\\ \\_\\  \\ \\_\\ \\_\\    \\ \\_\\  \\ \\_____\\  \\ \\_\\ \\_\\ " + System.lineSeparator() +
                "  \\/_____/   \\/_____/   \\/_/ \\/_/   \\/_____/   \\/_/ /_/   \\/_/\\/_/     \\/_/   \\/_____/   \\/_/ /_/ " + System.lineSeparator() +
                "                                                                                                  " + System.lineSeparator();
        out += "Knit 1.0.10 - DataWeave Document Generator" + System.lineSeparator();
        out += "Written By Austin Lehman" + System.lineSeparator();
        out += "austin@rosevillecode.com" + System.lineSeparator();
        out += "Copyright 2020 Roseville Code Inc." + System.lineSeparator();
        System.out.println(out);
    }
}