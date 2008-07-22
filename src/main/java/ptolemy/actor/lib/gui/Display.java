/* An actor that displays input data in a text area on the screen.

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

import ptolemy.actor.TypedIOPort;
import ptolemy.actor.TypedAtomicActor;
import ptolemy.actor.io.IOFactory;
import ptolemy.data.BooleanToken;
import ptolemy.data.IntToken;
import ptolemy.data.StringToken;
import ptolemy.data.Token;
import ptolemy.data.expr.Parameter;
import ptolemy.data.type.BaseType;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.Attribute;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.kernel.util.Nameable;
import ptolemy.kernel.util.StringAttribute;
import ptolemy.kernel.util.Workspace;

import ptolemy.actor.io.TextOutput;

//////////////////////////////////////////////////////////////////////////
//// Display

/**
 <p>
 Display the values of the tokens arriving on the input channels in a
 text area on the screen.  Each input token is written on a
 separate line.  The input type can be of any type.
 If the input happens to be a StringToken,
 then the surrounding quotation marks are stripped before printing
 the value of the token.  Thus, string-valued tokens can be used to
 generate arbitrary textual output, at one token per line.
 Tokens are read from the input only in
 the postfire() method, to allow them to settle in domains where they
 converge to a fixed point.
 </p><p>
 This actor has a <i>suppressBlankLines</i> parameter, whose default value
 is false. If this parameter is configured to be true, this actor does not
 put a blank line in the display.
 </p><p>
 Note that because of complexities in Swing, if you resize the display
 window, then, unlike the plotters, the new size will not be persistent.
 That is, if you save the model and then re-open it, the new size is
 forgotten.  To control the size, you should set the <i>rowsDisplayed</i>
 and <i>columnsDisplayed</i> parameters.
 </p><p>
 Note that this actor internally uses JTextArea, a Java Swing object
 that is known to consume large amounts of memory. It is not advisable
 to use this actor to log large output streams.</p>

 @author  Yuhong Xiong, Edward A. Lee
 @version $Id$
 @since Ptolemy II 1.0
 @Pt.ProposedRating Yellow (yuhong)
 @Pt.AcceptedRating Yellow (vogel)
 */
public class Display extends TypedAtomicActor {
    /** Construct an actor with an input multiport of type GENERAL.
     *  @param container The container.
     *  @param name The name of this actor.
     *  @exception IllegalActionException If the entity cannot be contained
     *   by the proposed container.
     *  @exception NameDuplicationException If the container already has an
     *   actor with this name.
     */
    public Display(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);

        input = new TypedIOPort(this, "input", true, false);
        input.setMultiport(true);
        input.setTypeEquals(BaseType.GENERAL);

        rowsDisplayed = new Parameter(this, "rowsDisplayed");
        rowsDisplayed.setExpression("10");
        columnsDisplayed = new Parameter(this, "columnsDisplayed");
        columnsDisplayed.setExpression("40");

        suppressBlankLines = new Parameter(this, "suppressBlankLines");
        suppressBlankLines.setTypeEquals(BaseType.BOOLEAN);
        suppressBlankLines.setToken(BooleanToken.FALSE);

        title = new StringAttribute(this, "title");
        title.setExpression("");

        _attachText("_iconDescription", "<svg>\n"
                + "<rect x=\"-20\" y=\"-15\" " + "width=\"40\" height=\"30\" "
                + "style=\"fill:lightGrey\"/>\n" + "<rect x=\"-15\" y=\"-10\" "
                + "width=\"30\" height=\"20\" " + "style=\"fill:white\"/>\n"
                + "<line x1=\"-13\" y1=\"-6\" x2=\"-4\" y2=\"-6\" "
                + "style=\"stroke:grey\"/>\n"
                + "<line x1=\"-13\" y1=\"-2\" x2=\"0\" y2=\"-2\" "
                + "style=\"stroke:grey\"/>\n"
                + "<line x1=\"-13\" y1=\"2\" x2=\"-8\" y2=\"2\" "
                + "style=\"stroke:grey\"/>\n"
                + "<line x1=\"-13\" y1=\"6\" x2=\"4\" y2=\"6\" "
                + "style=\"stroke:grey\"/>\n" + "</svg>\n");
    }

    ///////////////////////////////////////////////////////////////////
    ////        public variables and parameters                    ////

    /** The horizontal size of the display, in columns. This contains
     *  an integer, and defaults to 40.
     */
    public Parameter columnsDisplayed;

    /** The input port, which is a multiport.
     */
    public TypedIOPort input;

    /** The vertical size of the display, in rows. This contains an
     *  integer, and defaults to 10.
     */
    public Parameter rowsDisplayed;

    /** The flag indicating whether this display actor suppress
     *  blank lines. The default value is false.
     */
    public Parameter suppressBlankLines;

    /** The title to put on top. */
    public StringAttribute title;

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    /** If the specified attribute is <i>rowsDisplayed</i>, then set
     *  the desired number of rows of the textArea, if there is one.
     *  @param attribute The attribute that has changed.
     *  @exception IllegalActionException If the specified attribute
     *   is <i>rowsDisplayed</i> and its value is not positive.
     */
    public void attributeChanged(Attribute attribute)
            throws IllegalActionException {
	if (attribute == suppressBlankLines) {
            _suppressBlankLines = ((BooleanToken) suppressBlankLines.getToken())
                    .booleanValue();
        }
    }

    /** Clone the actor into the specified workspace. This calls the
     *  base class and then sets the textArea public variable to null.
     *  @param workspace The workspace for the new object.
     *  @return A new actor.
     *  @exception CloneNotSupportedException If a derived class contains
     *   an attribute that cannot be cloned.
     */
    public Object clone(Workspace workspace) throws CloneNotSupportedException {
        Display newObject = (Display) super.clone(workspace);
        return newObject;
    }

    /** Initialize this display.  If place() has not been called
     *  with a container into which to place the display, then create a
     *  new frame into which to put it.
     *  @exception IllegalActionException If the parent class throws it,
     *   or if the numRows or numColumns parameters are incorrect, or
     *   if there is no effigy for the top level container, or if a problem
     *   occurs creating the effigy and tableau.
     */
    public void initialize() throws IllegalActionException {
        super.initialize();
	_initOutput();
        _initialized = true;
    }

    /** Read at most one token from each input channel and display its
     *  string value on the screen.  Each value is terminated
     *  with a newline character.
     *  @exception IllegalActionException If there is no director.
     */
    public boolean postfire() throws IllegalActionException {
        int width = input.getWidth();

        boolean currentInputIsBlankLine = true;

        for (int i = 0; i < width; i++) {
            if (input.hasToken(i)) {
                Token token = input.get(i);

                if (!_initialized) {
                    _initialized = true;
		    _initOutput();
                }

                // FIXME: There is a race condition here.
                // textArea can be set to null during execution of this method
                // if another thread closes the display window.

                // The toString() method yields a string that can be parsed back
                // in the expression language to get the original token.
                // However, if the token is a StringToken, that probably is
                // not what we want. So we treat StringToken separately.
                String value = token.toString();
                if (token instanceof StringToken) {
                    value = ((StringToken) token).stringValue();
                }

                // If the value is not an empty string, set the
                // currentInputIsBlankLine to false.
                // Note that if there are multiple input ports, and if any of
                // the input ports has data, the current input is considered
                // to be non-empty.
                if (value.length() > 0) {
                    currentInputIsBlankLine = false;
                }

                _output.write(value);

                // Append a newline character.
                if (width > (i + 1)) {
                    _output.write("\n");
                }

            }
        }

        // If the current input is not a blank line, or the supressBlankLines
        // parameter is configured to false, append a newline character.
        if ((_output != null)
                && !(_suppressBlankLines && currentInputIsBlankLine)) {
            _output.write("\n");
        }

        return super.postfire();
    }

    ///////////////////////////////////////////////////////////////////
    ////                         private members                   ////

    // Indicator that the display window has been opened.
    private boolean _initialized = false;

    // Record of previous columns.
    private int _previousNumColumns = 0;

    // Record of previous rows.
    private int _previousNumRows = 0;

    // The flag indicating whether the blank lines will be suppressed.
    private boolean _suppressBlankLines = false;

    protected TextOutput _output = null;

    private void _initOutput() throws IllegalActionException {
	_output = IOFactory.getInstance().getTextOutput(this, getName());
    }
}
