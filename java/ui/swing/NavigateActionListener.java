/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.swing;

import ui.Key;

/**
 *
 * @author pedro
 */
public abstract class NavigateActionListener
	extends NavigateAction
	implements java.awt.event.ActionListener
{
	
	NavigateActionListener (Key key)
	{
		super (key);
	}
}
