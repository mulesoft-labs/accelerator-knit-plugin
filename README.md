![Knit Logo](knit.png)

# Knit - Dataweave Document Generator

![Knit Intro](knit-intro.png)

Original source written by Austin Lehman

Knit is a Maven plugin that generates documentation from source code 
comments in DataWeave files. 

Here's how it works. You write comments like this in your dwl files:

```
/**
 * Maps a color object to a result color object.
 * @param data is an input color object.
 * @return a result color object.
 */
fun mapColor(data) = {
...
```
 
You add the plugin to your pom like this:

```
<plugin>
    <groupId>org.mule.tools.maven</groupId>
    <artifactId>knit-maven-plugin</artifactId>
    <version>2.0.0</version>
    <executions>
        <execution>
            <goals>
                <goal>knit</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

Then when you build your project it will generate a knit-doc.md file in the 
target directory. It's that simple.

# Dependencies

Knit requires JDK 8 or later. It may work on previous versions but you will 
need to update the pom.xml in that case. It also requires Maven, of course.

# Usage

Since this is a Maven plugin all you have to do is reference it in the 
pom.xml of the project you want to use it in.

```
<plugin>
    <groupId>org.mule.tools.maven</groupId>
    <artifactId>knit-maven-plugin</artifactId>
    <version>2.0.0</version>
    <executions>
        <execution>
            <goals>
                <goal>knit</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <!-- Generate docs ... run the plugin? -->
        <generate>true</generate>
        
        <!-- Make a single output file. Default is to create separate files. -->
        <consolidateOutput>true</consolidateOutput>
        
        <!-- Direcories to look for files to parse. By default src/main/resources/dwl is set by the plugin.
        <directories>
            <dir>src/main/resources/dwl</dir>
        </directories>
        -->
        
        <!-- Individual files can be listed to parse.
        <files>
            <file>test1.dwl</file>
            <file>test2.dwl</file>
        </files>
         -->
         
         <!-- Specify the output file to save to. By default it saves to target/knit-doc.md.
         <outputFile>target/knit-doc.md</outputFile>
         -->
         
         <!-- Optional output header text can be specified which is set at the 
            begining of the generated doc. This allows you to set a heading 
            section for the doc.
         -->
         <!--
         <outputHeaderText>
<![CDATA[
# knit-test

This app is for testing the knit maven plugin.

]]>
          </outputHeaderText>
          -->
          
          <!-- If set to true a header table will be generated.
          <writeHeaderTable>true</writeHeaderTable>
          -->
          
          <!-- Provide a list of modules in the order you want them to appear.
          <moduleList>
              <module>main</module>
              <module>palette</module>
          </moduleList>
          -->
         
    </configuration>
</plugin>
```

The configuration section is optional, if not specified it will use 
the default values which should work for most cases.

- **skip** - A flag to run or not to run the Knit doc generator. Set 
  to true if you want it to skip generation.
- **consolidateOutput** - A flag to specify if it should generate a single 
  output file or a file for each module. Currently only a single output file 
  is supported.
- **directories** - A list of directories to look for .dwl files. If not 
  specified it will look in src/main/resources/dwl. If specified it will look 
  at just those directories you set.
- **files** - A list of files parse.
- **outputFile** - A string with the output file to write to. By default this 
  writes to target/knit-doc.md.
- **outputHeaderText** - Text to be set at the very begining of the generated 
  doc. This is optional and exists to allow some custom introduction content 
  to be provided.
- **outputFooterText** - Text to be set at the very end of the generated 
  doc. This is optional and exists to allow some custom footer content 
  to be provided.
- **writeHeaderTable** - A flag to specify if a header table is to be generated. 
  If set to true a table with each module name and description will be built 
  below the outputHeaderText and before the regular documentation. Each module 
  will link to it's place in the document.
- **moduleList** - A list of strings with the module names. This list 
  allows you to specify the order that modules will be written.
- **dwlFileExt** - A String with the file extension for DataWeave files. The default 
is dwl. This needs to be set if your DataWeave files have a different file extension.
- **showAbout** - A flag to specify whether program information is output upon
execution. Default is false.

## Generating the documentation

Knit is executed from the maven package lifecycle phase. In Anypoint Studio, 
certain conditions will cause it to run the Maven package goal automatically. 
To manually trigger the build from the command line, run the following command
from your project dictory (where the pom.xml file is): 

```
mvn clean package
```

# Comments

There are several types of comment blocks, which can be used to generate docs:

- module
- variable
- function
- table

## Module comments

Module level documentation is set at the very begining of the module, before 
the %dw declaration, and starting with /** like this.
```
/**
 * This module supports color related functions.
 */

%dw 2.0
...
```

## Variable comments

Variable documentation is implemented by writing a comment block above the 
variable declaration.

```
/**
 * First name of the author.
 */
var first = "Austin"
```

## Function comments

Documentation of a function is accomplished by writing a comment block 
above the function. In function comments you can set annotations to define 
parameters and return values by using the tags @param and @return, respectively. 

```
/**
 * Maps a color object to a result color object.
 * @param data is an input color object.
 * @return a result color object.
 */
fun mapColor(data) = {
...
```

## Table comments

You can also specify a data mapping table for transformations. Here's the general syntax. 
Columns and fields are separated by commas, and you can escape a comma with two back 
slashes like this '\\,'.

```
/**
 * Maps a PIM DB object to a result Product object.
 * @param product is an input DB Product object.
 * @return a result Product object.
 * @table Product object, Database field, Description
 * @row productId, product.PRODUCT_ID,  The id of the product\\, a new ID.
 * @row productType,product.product_type, to indicate master or project or variant
 * @row masterProductId, product.master_product_id, The id of the associated master product
 * @row productName, product.product_name, The localized name of the product
 * @row brand, product.BRAND, The brand of the product.
 */
fun transformProduct(product) = {
...
```

The @table is set with the comma separated column names, and then the @row annotations 
follow in order with each row of the table.

# Installing Locally

Normally you should just be able to add the plugin to your pom and away you go. In 
the event that you want to builld and install from source, here's how it's done. 
To install the plugin locally, just clone the repo and then install with maven.
```
$ git clone https://github.com/mulesoft-labs/accelerator-knit-plugin.git
$ cd accelerator-knit-plugin
$ mvn clean install -Dgpg.skip
```

Done and done, that's all you need to use in your project. Just add it to 
the pom.xml and you're golden.

# License

Copyright 2020 Roseville Code Inc. (austin@rosevillecode.com)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
