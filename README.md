NOTE: This was initially build for ptolemy 7.1 (devel version, snapshot revision 51499). It's not guaranteed that it will work on newer versions.

This is a proof-of-concept example for the actor.io extension to ptolemy II. This install process is not for the faint of heart!

Install Instructions
====================

### Maven (version 2.0 or greater)

maven.apache.org

### Ptolemy II

since this was built with a specific version of Ptolemy II, use svn to check out the old revision

> svn co -r 51499 svn://source.eecs.berkeley.edu/chess/ptII/trunk ptII

set the PTII environment variable

> export PTII=/path/to/ptII

> cd $PTII

run the following to install the needed dependencies into your local maven repository:

> mvn install:install-file -DgroupId=jython -DartifactId=jython -Dversion=svn -Dpackaging=jar -Dfile=$PTII/lib/jython.jar
> mvn install:install-file -DgroupId=javajoystick -DartifactId=joystick -Dversion=0.7 -Dpackaging=jar -Dfile=$PTII/vendors/misc/joystick/Joystick.jar 
> mvn install:install-file -DgroupId=jxta -DartifactId=jxta -Dversion=1.0 -Dpackaging=jar -Dfile=$PTII/vendors/sun/jxta/jxta.jar
> mvn install:install-file -DgroupId=jxta -DartifactId=jxtasecurity -Dversion=1.0 -Dpackaging=jar -Dfile=$PTII/vendors/sun/jxta/jxtasecurity.jar
> mvn install:install-file -DgroupId=soot -DartifactId=sootclasses -Dversion=ptII -Dpackaging=jar -Dfile=$PTII/lib/sootclasses.jar
> mvn install:install-file -DgroupId=mapss -DartifactId=mapss -Dversion=ptII -Dpackaging=jar -Dfile=$PTII/lib/mapss.jar
> mvn install:install-file -DgroupId=caltrop -DartifactId=ptCal -Dversion=ptII -Dpackaging=jar -Dfile=$PTII/lib/ptCal.jar
> mvn install:install-file -DgroupId=chic -DartifactId=chic -Dversion=ptII -Dpackaging=jar -Dfile=$PTII/lib/chic.jar
> mvn install:install-file -DgroupId=jacl -DartifactId=jacl -Dversion=ptII -Dpackaging=jar -Dfile=$PTII/lib/ptjacl.jar

compile ptII with maven:

> make maven
> ./configure --without-eclipse
> mvn compile

then install the ptII jar into your local maven repository

> mvn jar:jar
> mvn install:install-file -DpomFile=pom.xml -Dfile=ptII-7.1.devel.jar

You can test that it's installed correctly by running

### ptII-actorio

If you haven't already download this package.

If you have git (http://git.or.cz/) installed, you can use the following command:

> git clone git://github.com/tristan/ptII-actorio.git

Or If you wish to just download a snapshot, go to http://github.com/tristan/ptII-actorio and click on the zip link at the top of the page.

From the ptII-actorio directory, run the following to compile the project:

> mvn compile
> mvn install

You can test this with

> ./run-vergil

Load up input_test.xml and run it. You should get a command line prompt to enter your name, which it will output.