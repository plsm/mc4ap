/*
 * AbstractNavigateAction.java
 *
 * Created on 19 de Dezembro de 2013, 19:17
 *
 */
package ui.swing;

/**
 * The action that is performed when the user clicks the {@code UIFrame} ok
 * button. This class defines an abstract method that is called when the user
 * clicks the ok button. If the returned value is {@code true} it is assumed
 * that the data is valid and we can return to the previous panel. This class
 * holds the {@code UIPanel} key which is used to show the previous panel.
 *
 * <p> The interface is organised in a tree of {@code UIPanel}'s with a
 * {@code UIFrame} at the root. Some of the buttons in {@code UIFrame}'s toolbar
 * show a {@code UIPanel}. These panels can have buttons that show other
 * {@code UIPanel}'s. Navigating backwards in these panels is done by pressing
 * the ok or cancel buttons in {@code UIFrame}. The ok commits the data that the
 * user entered and shows the previous panel. If this was the toppest
 * {@code UIPanel} an empty label is shown in {@code UIFrame}.
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
	/**
	 * Construct a navigate action with the given key.
	 * 
	 * @param key The panel key that should be shown after the data has been sucessfully commited.
	 */
	NavigateAction (String key)
	{
		this.key = key;
	}
	/**
	 * Perform this action after the user pressed the ok button. If {@code true}
	 * is returned this action can be removed from the set of actions to be
	 * performed.
	 */
	abstract boolean perform ();

	@Override
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
