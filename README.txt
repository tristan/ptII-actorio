This is a proof-of-concept example for the actor.io extension to ptolemy II

Install Instructions:

Prerequisits:
* Maven (version 2.0 or greater): maven.apache.org

If you haven't already download this package.

If you have git (http://git.or.cz/) installed, you can use the following
commands:

git clone git://git.hpc.jcu.edu.au/jc124742/ptII-actorio.git

This may take a while. If you wish to just download a snapshot, go to:
http://www.hpc.jcu.edu.au/git/?p=jc124742/ptII-actorio.git;a=tree
and click on the snapshot link at the top of the page.

from the ptII-actorio directory, run the following:

mvn compile
mvn exec:exec

the first step will download all the requirements required to run this example, 
including the actor-io branch of ptolemy II and compile the code. If you have
any errors at this step, please contact me at tristan.king@jcu.edu.au and send
me the error you're getting.

the 2nd step will start up Vergil from which you can load and run a workflow.
Load a workflow that contains Display actors and/or XYPlotter or TimedPlotter
actors to test the functionality.
