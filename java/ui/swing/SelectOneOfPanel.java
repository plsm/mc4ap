/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.swing;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.AbstractButton;
import javax.swing.JRadioButton;
import ui.KeyGenerator;
import ui.MercuryReference;

/**
 * A panel that presents a set of radio buttons.
 * @author pedro
 */
public class SelectOneOfPanel
	extends AbstractDataPanel
	implements ComponentPopulate
{

	/**
	 * Empty panel key.
	 */
	static private String EMPTY = "empty";
	
	
	final private Object[] selectedItemFunc;
	private final String panelName;
	/**
	 * A map betwween the radio buttons and the panels that they display.
	 */
	private final HashMap<JRadioButton, ButtonInfo> buttonPanels;
	private final UIPanel parentPanel;
	/** Creates new form SelectOneOfPanel */
	SelectOneOfPanel (MercuryReference data, UIFrame frame, UIPanel parentPanel, String panelName, Object[] selectedItemFunc)
	{
		super (data, frame);
		this.panelName = panelName;
		this.parentPanel = parentPanel;
		initComponents ();
		this.selectedItemFunc = selectedItemFunc;
		this.buttonPanels = new HashMap<JRadioButton, ButtonInfo> ();
	}
	/**
	 * Construct an inline panel where swing components to edit this {@code UIPanel} Mercury data can be placed.  Inline panels are bordered panels that visually group swing components.
	 * @return 
	 */
	public InlinePanelField newInlinePanelForChoice ()
	{
		InlinePanelField result = new InlinePanelField (this.data, this.frame, this.parentPanel);
		return result;
	}
	
	public SelectOneOfPanel handle_choiceItem (JRadioButton button, final Object value, final InlinePanelField panel)
	{
		this.radioButtonsPanel.add (button);
		this.buttonGroup.add (button);
		if (panel == null) {
			button.addActionListener (new ActionListener () {
				@Override
				public void actionPerformed (ActionEvent e) {
					CardLayout cl = (CardLayout) (SelectOneOfPanel.this.dialogsPanel.getLayout ());
					cl.show (SelectOneOfPanel.this.dialogsPanel, EMPTY);
					if (value != null) {
						SelectOneOfPanel.this.data.setValue (value);
					}
				}
			});
		}
		else {
			final ButtonInfo buttonInfo = new ButtonInfo (panel);
			this.dialogsPanel.add (panel, buttonInfo.key);
//			if (value != null) {
//				panel.setData (value);
//			}
			this.buttonPanels.put (button, buttonInfo);
			button.addActionListener (new ActionListener () {
				@Override
				public void actionPerformed (ActionEvent e) {
					CardLayout cl = (CardLayout) (SelectOneOfPanel.this.dialogsPanel.getLayout ());
					cl.show (SelectOneOfPanel.this.dialogsPanel, buttonInfo.key);
					SelectOneOfPanel.this.data.setValue (panel.data.getValue ());
					panel.fireValueChanged ();
				}
			});
		}
		return this;
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      buttonGroup = new javax.swing.ButtonGroup();
      radioButtonsPanel = new javax.swing.JPanel();
      dialogsPanel = new javax.swing.JPanel();
      emptyLabel = new javax.swing.JLabel();

      setBorder(javax.swing.BorderFactory.createTitledBorder(this.panelName));
      setLayout(new java.awt.BorderLayout());

      radioButtonsPanel.setLayout(new javax.swing.BoxLayout(radioButtonsPanel, javax.swing.BoxLayout.Y_AXIS));
      add(radioButtonsPanel, java.awt.BorderLayout.WEST);

      dialogsPanel.setLayout(new java.awt.CardLayout());
      dialogsPanel.add(emptyLabel, "empty");

      add(dialogsPanel, java.awt.BorderLayout.CENTER);
   }// </editor-fold>//GEN-END:initComponents


   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.ButtonGroup buttonGroup;
   private javax.swing.JPanel dialogsPanel;
   private javax.swing.JLabel emptyLabel;
   private javax.swing.JPanel radioButtonsPanel;
   // End of variables declaration//GEN-END:variables

	@Override
	public void valueChanged (MercuryReference dummy)
	{
		jmercury.runtime.MethodPtr2 funcMeth = ((jmercury.runtime.MethodPtr2) this.selectedItemFunc [1]);
		Object mindex = (jmercury.maybe.Maybe_1) funcMeth.call___0_0 (this.selectedItemFunc, this.data.getValue ());

		if (mindex instanceof jmercury.maybe.Maybe_1.Yes_1) {
			int index = (Integer) (((jmercury.maybe.Maybe_1.Yes_1) mindex).F1);
			java.util.Enumeration<AbstractButton> buttons = buttonGroup.getElements ();
			while (index > 0) {
				buttons.nextElement ();
				index--;
			}
			AbstractButton selectedButton = buttons.nextElement ();
			selectedButton.setSelected (true);
			if (this.buttonPanels.containsKey (selectedButton)) {
				ButtonInfo buttonInfo = this.buttonPanels.get (selectedButton);
				CardLayout cl = (CardLayout) (this.dialogsPanel.getLayout ());
				cl.show (this.dialogsPanel, buttonInfo.key);
				buttonInfo.panel.fireValueChanged ();
			}
		}
		else {
			buttonGroup.clearSelection ();
		}
	}

	static private class ButtonInfo<D>
	{
		static private KeyGenerator keyGenerator = new KeyGenerator ();
		final String key = keyGenerator.nextKey ();
		final InlinePanelField<D> panel;
		ButtonInfo (InlinePanelField<D> panel)
		{
			this.panel = panel;
		}
	}
}
