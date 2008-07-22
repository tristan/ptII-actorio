/* Base class for plotters.

 @Copyright (c) 1998-2008 The Regents of the University of California.
 All rights reserved.

 Permission is hereby granted, without written agreement and without
 license or royalty fees, to use, copy, modify, and distribute this
 software and its documentation for any purpose, provided that the
 above copyright notice and the following two paragraphs appear in all
 copies of this software.

 IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
 FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
 ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.

 THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
 PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
 CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
 ENHANCEMENTS, OR MODIFICATIONS.

 PT_COPYRIGHT_VERSION 2
 COPYRIGHTENDKEY
 */
package ptolemy.actor.lib.gui;

import java.util.StringTokenizer;

import ptolemy.actor.TypedAtomicActor;
import ptolemy.actor.io.IOFactory;
import ptolemy.actor.io.GraphOutput;
import ptolemy.data.BooleanToken;
import ptolemy.data.expr.Parameter;
import ptolemy.data.type.BaseType;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.Attribute;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.kernel.util.Nameable;
import ptolemy.kernel.util.NamedObj;
import ptolemy.kernel.util.StringAttribute;
import ptolemy.kernel.util.Workspace;

//////////////////////////////////////////////////////////////////////////
//// PlotterBase

/**
 Base class for plotters.  This class contains an instance of the
 PlotBox class from the Ptolemy plot package as a public member,
 although which subclass of PlotBox is created is left to derived classes.
 It provides a parameter that determines whether to fill the plot
 when wrapup is invoked. It also has a <i>legend</i> parameter,
 which gives a comma-separated list of labels to attach to
 each dataset.  Normally, the number of elements in this list
 should equal the number of input channels, although this
 is not enforced.

 @see ptolemy.plot.PlotBox

 @author  Edward A. Lee
 @version $Id$
 @since Ptolemy II 2.1
 @Pt.ProposedRating Green (eal)
 @Pt.AcceptedRating Green (cxh)
 */
public class PlotterBase extends TypedAtomicActor {
    /** Construct an actor with the given container and name.
     *  @param container The container.
     *  @param name The name of this actor.
     *  @exception IllegalActionException If the actor cannot be contained
     *   by the proposed container.
     *  @exception NameDuplicationException If the container already has an
     *   actor with this name.
     */
    public PlotterBase(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);

        fillOnWrapup = new Parameter(this, "fillOnWrapup", new BooleanToken(
                true));
        fillOnWrapup.setTypeEquals(BaseType.BOOLEAN);

        legend = new StringAttribute(this, "legend");
        
        _attachText("_iconDescription", "<svg>\n"
                + "<rect x=\"-20\" y=\"-20\" " + "width=\"40\" height=\"40\" "
                + "style=\"fill:lightGrey\"/>\n" + "<rect x=\"-12\" y=\"-12\" "
                + "width=\"24\" height=\"24\" " + "style=\"fill:white\"/>\n"
                + "<rect x=\"2\" y=\"-18\" " + "width=\"4\" height=\"4\" "
                + "style=\"fill:grey\"/>\n" + "<rect x=\"8\" y=\"-18\" "
                + "width=\"4\" height=\"4\" " + "style=\"fill:grey\"/>\n"
                + "<rect x=\"14\" y=\"-18\" " + "width=\"4\" height=\"4\" "
                + "style=\"fill:grey\"/>\n"
                + "<polyline points=\"-10,0, -5,-8, 5,8, 10,0\" "
                + "style=\"stroke:red\"/>\n" + "</svg>\n");
    }

    ///////////////////////////////////////////////////////////////////
    ////                     ports and parameters                  ////

    /** If true, fill the plot when wrapup is called.
     *  This parameter has type BooleanToken, and default value true.
     */
    public Parameter fillOnWrapup;

    /** A comma-separated list of labels to attach to each data set.
     *  This is always a string, with no enclosing quotation marks.
     */
    public StringAttribute legend;

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    /** If the attribute is <i>legend</i>, then parse the string
     *  and set the legend.
     *  @param attribute The attribute that changed.
     *  @exception IllegalActionException If the superclass throws it.
     */
    public void attributeChanged(Attribute attribute)
            throws IllegalActionException {
        // NOTE: Do not react to changes in _windowProperties.
        // Those properties are only used when originally opening a window.
        if (attribute == legend) {
	    // TODO
            /*if (plot != null) {
                plot.clearLegends();

                String value = legend.getExpression();

                if ((value != null) && !value.trim().equals("")) {
                    StringTokenizer tokenizer = new StringTokenizer(value, ",");
                    int channel = 0;

                    while (tokenizer.hasMoreTokens()) {
                        plot.addLegend(channel++, tokenizer.nextToken().trim());
                    }
                }
		}*/
        } else {
            super.attributeChanged(attribute);
        }
    }

    /** Clone the actor into the specified workspace. This calls the
     *  base class and then creates new ports and parameters.
     *  @param workspace The workspace for the new object.
     *  @return A new actor.
     *  @exception CloneNotSupportedException If a derived class has an
     *   attribute that cannot be cloned.
     */
    public Object clone(Workspace workspace) throws CloneNotSupportedException {
        PlotterBase newObject = (PlotterBase) super.clone(workspace);
        return newObject;
    }

    /** Clear the plot, if there is one.  Notice that unlike
     *  initialize(), this clears the entire plot.
     *  @see #initialize()
     *  @exception IllegalActionException If the parent class throws it.
     */
    public void preinitialize() throws IllegalActionException {
        super.preinitialize();

        if (_output != null) {
            _output.reset();
        }
    }

    /** If the <i>fillOnWrapup</i> parameter is true, rescale the
     *  plot so that all the data is visible.
     *  @exception IllegalActionException If the superclass throws it.
     */
    public void wrapup() throws IllegalActionException {
        if (((BooleanToken) fillOnWrapup.getToken()).booleanValue()) {
        }
	if (_output != null) {
	    _output.wrapup();
	}
        super.wrapup();
    }

    ///////////////////////////////////////////////////////////////////
    ////                         protected members                 ////
    protected GraphOutput _output;

    protected void _initOutput() throws IllegalActionException {
	_output = IOFactory.getInstance().getGraphOutput(this, getName());
    }

    ///////////////////////////////////////////////////////////////////
    ////                         inner classes                     ////
}
