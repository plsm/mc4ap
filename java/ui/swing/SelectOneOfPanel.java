/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.swing;

import data.AbstractMercuryReference;
import data.MercuryReference;
import data.closure.SelectChoiceFunc;
import data.closure.SelectedChoiceFunc;
import data.closure.SetFieldFunc;

import ui.KeyGenerator;

import jmercury.userInterface.SelectChoice_2;
import jmercury.userInterface.SetResult_1;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.JRadioButton;
import jmercury.maybe.Maybe_1;
import jmercury.userInterface;
import jmercury.userInterface.CurrentChoice_1;

/**
 * A panel that presents a set of radio buttons.
 * @author pedro
 */
final public class SelectOneOfPanel<D, F>
	extends AbstractDataPanel<D, AbstractMercuryReference<D> >
	implements ComponentPopulate<D>
{

	/**
	 * Empty panel key.  Some radio buttons do not have any panel associated.  When the user presses them or when the field corresponds to such radio button, an empty label is displayed.
	 */
	static private String EMPTY = "empty";
	
	/**
	 * Function used to select the correct radio button.
	 */
	final private SelectedChoiceFunc<D, F> selectedChoiceFunc;
	
	/**
	 * Function used to select the correct radio button.
	 */
	final private SetFieldFunc<D, F> setFieldFunc;
	
	transient final private String panelName;
	/**
	 * The panel that has been selected after pressing a radio button.
	 */
	private InlinePanelField<D, F> selectedInlinePanel;
	/**
	 * A vector with radio buttons and the panels that they display.
	 */
	final private Vector<ButtonPanelInfo> buttonPanelInfo;
	private final UIPanel<D> parentPanel;
	transient private int numberRadioButtons = 0;
	static private KeyGenerator keyGenerator = new KeyGenerator ();
		/** Creates new form SelectOneOfPanel */
//	SelectOneOfPanel (MercuryReference<D> data, UIFrame frame, UIPanel<D> parentPanel, String panelName, Object[] selectedItemFunc)
//	{
//		super (data, frame);
//		this.panelName = panelName;
//		this.parentPanel = parentPanel;
//		initComponents ();
//		this.selectedItemFunc = selectedItemFunc;
//		this.buttonPanels = new HashMap<JRadioButton, ButtonInfo> ();
//	}

	SelectOneOfPanel (AbstractMercuryReference<D> data, UIFrame frame, UIPanel<D> parentPanel, String panelName, Object[] funcSelectedChoice, Object[] funcSetData)
	{
		super (data, frame);
		this.panelName = panelName;
		this.parentPanel = parentPanel;
		this.selectedChoiceFunc = new SelectedChoiceFunc<> (funcSelectedChoice);
		this.setFieldFunc = new SetFieldFunc<> (funcSetData);
		initComponents ();
		this.buttonPanelInfo = new Vector<> (10, 10);
		this.selectedInlinePanel = null;
		
	}
	/**
	 * Construct an inline panel where swing components to edit this {@code UIPanel} Mercury data can be placed.  Inline panels are bordered panels that visually group swing components.
	 * @return 
	 */
	public InlinePanelField<D, F> newInlinePanelForChoice ()
	{
		AbstractMercuryReference<F> amr = new AbstractMercuryReference<F> () {
			final int index = SelectOneOfPanel.this.numberRadioButtons;
			@Override
			public boolean handle_setResult (SetResult_1<F> mdata) {
				if (mdata instanceof userInterface.SetResult_1.Ok_1) {
					userInterface.SetResult_1.Ok_1<F> newData = (userInterface.SetResult_1.Ok_1<F>) mdata;
					this.setValue (newData.F1);
					return true;
				}
				else if (mdata instanceof userInterface.SetResult_1.Error_1) {
					userInterface.SetResult_1.Error_1<F> error = (userInterface.SetResult_1.Error_1<F>) mdata;
					SelectOneOfPanel.this.data.handle_setResult (new userInterface.SetResult_1.Error_1<D> (error.F1));
					return false;
				}
				else {
					throw new UnsupportedOperationException ("Unsupported SetResult");
				}
			}

			@Override
			public boolean setValue (F value)
			{
				return SelectOneOfPanel.this.data.applySetFieldFunc (setFieldFunc, value);
			}

			@Override
			public F getValue ()
			{
				Maybe_1<CurrentChoice_1<F> > msc = SelectOneOfPanel.this.selectedChoiceFunc.apply (SelectOneOfPanel.this.data.getValue ());
				if (msc instanceof Maybe_1.Yes_1) {
					Maybe_1.Yes_1<CurrentChoice_1<F> > yes = (Maybe_1.Yes_1<CurrentChoice_1<F> >) msc;
					if (yes.F1.F1 == index) {
						return yes.F1.F2;
					}
					else {
						return null;
					}
				}
				else {
					return null;
				}
			}
		};
		InlinePanelField<D, F> result = new InlinePanelField<> (amr, this.frame, this.parentPanel);
		return result;
	}
	public SelectOneOfPanel<D, F> handle_choiceItem (JRadioButton button, final Object[] selectChoiceFunc)
	{
		ButtonPanelInfo bpi = new ButtonPanelInfo (button);
		this.buttonPanelInfo.insertElementAt (bpi, this.numberRadioButtons);
		this.radioButtonsPanel.add (button);
		this.buttonGroup.add (button);
		button.addActionListener (new ActionListener () {
			int index = SelectOneOfPanel.this.numberRadioButtons++;
			SelectChoiceFunc<D,F> scf = new SelectChoiceFunc<> (selectChoiceFunc);
			@Override
			public void actionPerformed (ActionEvent e) {
				CardLayout cl = (CardLayout) (SelectOneOfPanel.this.dialogsPanel.getLayout ());
				cl.show (SelectOneOfPanel.this.dialogsPanel, EMPTY);
				SelectOneOfPanel.this.selectedInlinePanel = null;
				SetResult_1<SelectChoice_2<D, F> > object = scf.apply (SelectOneOfPanel.this.data.getValue (), index);
				if (object instanceof SetResult_1.Ok_1) {
					SetResult_1.Ok_1<SelectChoice_2<D, F> > ok = (SetResult_1.Ok_1<SelectChoice_2<D, F> >) object;
					SelectOneOfPanel.this.data.setValue (ok.F1.data);
				}
				else if (object instanceof SetResult_1.Error_1) {
					SelectOneOfPanel.this.buttonGroup.clearSelection ();
				}
			}
		});
		return this;
	}
	public SelectOneOfPanel<D, F> handle_choiceItem (JRadioButton button, final Object[] selectChoiceFunc, final InlinePanelField<D, F> panel)
	{
		this.radioButtonsPanel.add (button);
		this.buttonGroup.add (button);
		final String key = keyGenerator.nextKey ();
		ButtonPanelInfo bpi = new ButtonPanelInfo (button, panel, key);
		this.buttonPanelInfo.insertElementAt (bpi, this.numberRadioButtons);
		this.dialogsPanel.add (panel, key);
		button.addActionListener (new ActionListener () {
			int index = SelectOneOfPanel.this.numberRadioButtons++;
			SelectChoiceFunc<D,F> scf = new SelectChoiceFunc<> (selectChoiceFunc);
			@Override
			public void actionPerformed (ActionEvent e) {
				SetResult_1<SelectChoice_2<D, F> > object = scf.apply (SelectOneOfPanel.this.data.getValue (), index);
				if (object instanceof SetResult_1.Ok_1) {
					SetResult_1.Ok_1<SelectChoice_2<D, F> > ok = (SetResult_1.Ok_1<SelectChoice_2<D, F> >) object;
					CardLayout cl = (CardLayout) (SelectOneOfPanel.this.dialogsPanel.getLayout ());
					cl.show (SelectOneOfPanel.this.dialogsPanel, key);
					panel.setData (ok.F1.field);
					SelectOneOfPanel.this.data.setValue (ok.F1.data);
					SelectOneOfPanel.this.selectedInlinePanel = panel;
				}
				else if (object instanceof SetResult_1.Error_1) {
					SelectOneOfPanel.this.buttonGroup.clearSelection ();
					CardLayout cl = (CardLayout) (SelectOneOfPanel.this.dialogsPanel.getLayout ());
					cl.show (SelectOneOfPanel.this.dialogsPanel, EMPTY);
					SelectOneOfPanel.this.selectedInlinePanel = null;
				}
			}
		});
		return this;
	}
	
//	public SelectOneOfPanel handle_choiceItem (JRadioButton button, final Object value, final InlinePanelField panel)
//	{
//		this.radioButtonsPanel.add (button);
//		this.buttonGroup.add (button);
//		if (panel == null) {
//			button.addActionListener (new ActionListener () {
//				@Override
//				public void actionPerformed (ActionEvent e) {
//					CardLayout cl = (CardLayout) (SelectOneOfPanel.this.dialogsPanel.getLayout ());
//					cl.show (SelectOneOfPanel.this.dialogsPanel, EMPTY);
//					if (value != null) {
//						SelectOneOfPanel.this.data.setValue (value);
//					}
//				}
//			});
//		}
//		else {
//			final ButtonInfo buttonInfo = new ButtonInfo (panel);
//			this.dialogsPanel.add (panel, buttonInfo.key);
////			if (value != null) {
////				panel.setData (value);
////			}
//			this.buttonPanels.put (button, buttonInfo);
//			button.addActionListener (new ActionListener () {
//				@Override
//				public void actionPerformed (ActionEvent e) {
//					CardLayout cl = (CardLayout) (SelectOneOfPanel.this.dialogsPanel.getLayout ());
//					cl.show (SelectOneOfPanel.this.dialogsPanel, buttonInfo.key);
//					panel.setData (SelectOneOfPanel.this.data.getValue ());
////					SelectOneOfPanel.this.data.setValue (panel.data.getValue ());
//					panel.fireValueChanged ();
//				}
//			});
//		}
//		return this;
//	}

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

	/**
	 *
	 * @param dummy
	 */
	@Override
	public void valueChanged (MercuryReference<D> dummy)
	{
		Maybe_1<CurrentChoice_1<F> > msc = this.selectedChoiceFunc.apply (dummy.getValue ());
		if (msc instanceof Maybe_1.Yes_1) {
			Maybe_1.Yes_1<CurrentChoice_1<F> > yes = (Maybe_1.Yes_1<CurrentChoice_1<F> >) msc;
			ButtonPanelInfo bpi = this.buttonPanelInfo.get (yes.F1.F1);
			bpi.button.setSelected (true);
			CardLayout cl = (CardLayout) (this.dialogsPanel.getLayout ());
			if (bpi.panel != null) {
				bpi.panel.setData (yes.F1.F2);
				cl.show (this.dialogsPanel, bpi.key);
				this.selectedInlinePanel = bpi.panel;
			}
			else {
				cl.show (this.dialogsPanel, EMPTY);
				this.selectedInlinePanel = null;
			}
		}
		else {
			this.buttonGroup.clearSelection ();
			CardLayout cl = (CardLayout) (SelectOneOfPanel.this.dialogsPanel.getLayout ());
			cl.show (SelectOneOfPanel.this.dialogsPanel, EMPTY);
				this.selectedInlinePanel = null;
		}
	}

	@Override
	public boolean commitValue ()
	{
		return this.selectedInlinePanel.commitValue ();
	}

	
	private class ButtonPanelInfo
	{
		final JRadioButton button;
		final InlinePanelField<D, F> panel;
		final String key;
		ButtonPanelInfo (JRadioButton button)
		{
			this (button, null, null);
		}
		ButtonPanelInfo (JRadioButton button, InlinePanelField<D, F> panel, String key)
		{
			this.panel = panel;
			this.button = button;
			this.key = key;
		}
	}
}
