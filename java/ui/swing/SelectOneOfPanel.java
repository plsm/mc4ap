/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.swing;

import data.AbstractMercuryReference;
import data.MercuryReference;
import data.closure.SelectFieldChoiceFunc;
import data.closure.SelectedFieldChoiceFunc;
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
final class SelectOneOfPanel<D, F>
	extends AbstractSelectOneOfPanel<D, F>
//	extends AbstractDataPanel<D, AbstractMercuryReference<D> >
//	implements ComponentPopulate<D>
{
	/**
	 * Function used to select the correct radio button.
	 */
	final private SelectedFieldChoiceFunc<D, F> selectedChoiceFunc;
	
	/**
	 * Function used to select the correct radio button.
	 */
	final private SetFieldFunc<D, F> setFieldFunc;
	/**
	 * 
	 * @param data
	 * @param frame
	 * @param parentPanel
	 * @param panelName
	 * @param funcSelectedChoice
	 * @param funcSetData 
	 */
	SelectOneOfPanel (AbstractMercuryReference<D> data, UIFrame frame, UIPanel<D> parentPanel, String panelName, Object[] funcSelectedChoice, Object[] funcSetData)
	{
		super (data, frame, parentPanel, panelName);
		this.selectedChoiceFunc = new SelectedFieldChoiceFunc<> (funcSelectedChoice);
		this.setFieldFunc = new SetFieldFunc<> (funcSetData);
	}
	/**
	 * Construct an inline panel where swing components to edit this {@code UIPanel} Mercury data can be placed.  Inline panels are bordered panels that visually group swing components.
	 * @return 
	 */
	@Override
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
	@Override
	public SelectOneOfPanel<D, F> handle_choiceItem (JRadioButton button, final Object[] selectChoiceFunc)
	{
		ButtonPanelInfo bpi = new ButtonPanelInfo (button);
		this.buttonPanelInfo.insertElementAt (bpi, this.numberRadioButtons);
		this.radioButtonsPanel.add (button);
		this.buttonGroup.add (button);
		button.addActionListener (new ActionListener () {
			int index = SelectOneOfPanel.this.numberRadioButtons++;
			SelectFieldChoiceFunc<D,F> scf = new SelectFieldChoiceFunc<> (selectChoiceFunc);
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
	@Override
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
			SelectFieldChoiceFunc<D,F> scf = new SelectFieldChoiceFunc<> (selectChoiceFunc);
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents()
   {
   }// </editor-fold>//GEN-END:initComponents


   // Variables declaration - do not modify//GEN-BEGIN:variables
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
		if (this.selectedInlinePanel != null) {
			return this.selectedInlinePanel.commitValue ();
		}
		else {
			return true;
		}
	}
}
