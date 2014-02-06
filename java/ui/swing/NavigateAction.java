/*
 * AbstractNavigateAction.java
 *
 * Created on 19 de Dezembro de 2013, 19:17
 *
 */

package ui.swing;

/**
 *
 * @author <a href="mailto:mariano.pedro@gmail.com">Pedro Mariano</a>
 *
 * @version 1.0 19 de Dezembro de 2013
 */
abstract class NavigateAction
{
	/**
	 * The panel to show after the data has been sucessfully edited.
	 */
	final String key;
	
	NavigateAction (String key)
	{
		this.key = key;
	}
	/**
	 * Perform this action after the user pressed the ok button.  If {@code true} is performed this action can be removed from the set of actions to be performed.
	 */
	abstract boolean perform ();
	
	public String toString ()
	{
		return "NavigateAction " + this.key;
	}
}

// Local Variables:
// mode: java
// mode: flyspell-prog
// ispell-local-dictionary: "british"
// End:
