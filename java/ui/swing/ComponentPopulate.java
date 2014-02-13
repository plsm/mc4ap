/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.swing;

import data.MercuryReference;

/**
 * Represents a component in a {@code UIPanel} that must be initialised with data or field data.
 * @author pedro
 */
interface ComponentPopulate<D>
//	extends Observer
{
	void valueChanged (MercuryReference<D> data);
	/**
	 * This method is invoked when the panel where this component is located is going to close and we have to the tell the component to commit its value.
	 * @return {@code true} if the component was able to commit its value.
	 */
	boolean commitValue ();
}
