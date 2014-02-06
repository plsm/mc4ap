/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.swing;

import java.util.Observer;
import ui.MercuryReference;

/**
 * Represents a component in a {@code UIPanel} that must be initialised with data or field data.
 * @author pedro
 */
interface ComponentPopulate
//	extends Observer
{
	void valueChanged (MercuryReference data);
}
