/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.swing;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Deque;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import ui.MercuryReference;

/**
 * Base class of swing panels that are initialised during runtime by the mercury backend.  The public methods in this class match the constructors of mercury type {@code dialog(D)}.
 * 
 * <p>This panel uses a {@code GridBagLayout}.  Components added by methods {@code handle_XXX} are arranged in <i>n</i> rows by 2 columns.
 * 
 * @author Pedro Mariano
 */
abstract public class DynamicDataPanel<T>
	extends AbstractDataPanel
{
	/**
	 * Components that must be populated when the data is set.
	 */
	final protected List<ComponentPopulate> componentsPopulate;
	/**
	 * Default insets used when laying the components in the dialog form.
	 */
	static final private java.awt.Insets defaultInsets
		= new java.awt.Insets (2, 2, 2, 2);
//	/**
//	 * LIFO panel queue used in the runtime user interface initialisation.  The first panel in this queue is the enclosing class.  Further panels are inserted by method {@code beginPanel(String)} which receives the panel title border.
//	 */
//	final private Deque<DynamicPanel> panels;
	
	transient protected boolean debug = false;
	private int nextComponentGridY = 0;
	/**
	 * Creates new form DynamicDataPanel
	 */
	protected DynamicDataPanel (MercuryReference data, UIFrame frame)
	{
		super (data, frame);
		this.initComponents ();
//		this.add (this.strechSeparator);
		this.componentsPopulate = new LinkedList<ComponentPopulate> ();
//		this.panels = new LinkedList<DynamicPanel> ();
//		this.panels.addFirst (new DynamicPanel (this));
	}
	/**
	 * Sets the data shown by this panel and updates the data displayed of any swing components.
	 */
	void setData (Object value)
	{
		this.data.setValue (value);
		this.fireValueChanged ();
	}
	
	protected void fireValueChanged ()
	{
		for (ComponentPopulate cp : this.componentsPopulate) {
			cp.valueChanged (this.data);
		}
	}
	
	/**
	 * Construct an inline panel where swing components to edit this {@code UIPanel} Mercury data can be placed.  Inline panels are bordered panels that visually group swing components.
	 * @return 
	 */
	public InlinePanelField newInlinePanelForData (String panelName)
	{
		InlinePanelField result = new InlinePanelField (
			this.data,
			this.frame,
			this.getUIPanel (),
			panelName);
		this.componentsPopulate.add (result);
		//this.addComponent (result, true, true, true);
		this.addDynamicComponent (result);
		return result;
	}
	/**
	 * Construct an inline panel where swing components to edit this {@code UIPanel} Mercury data can be placed.  Inline panels are bordered panels that visually group swing components.
	 * @return 
	 */
	public InlinePanelField newInlinePanelForFieldData (String panelName, Object[] getFieldFunc, Object[] setFieldFunc)
	{
		InlinePanelField result = new InlinePanelField (
			new FieldDataReference (this.frame, this.data, getFieldFunc, setFieldFunc),
			this.frame,
			this.getUIPanel (),
			panelName);
		this.componentsPopulate.add (result);
		//this.addComponent (result, true, true, true);
		this.addDynamicComponent (result);
		//System.out.println ("newInlinePanelForFieldData (" + panelName + ", ...)");
		return result;
	}
	public SelectOneOfPanel newSelectOneOfPanel (String panelName, Object[] selectedItemFunc)
	{
		SelectOneOfPanel result = new SelectOneOfPanel (this.data, this.frame, this.getUIPanel (), panelName, selectedItemFunc);
		this.componentsPopulate.add (result);
		//this.addComponent (result, true, true, true);
		this.addDynamicComponent (result);
		return result;
	}
	public SelectOneOfPanel newSelectOneOfPanel (String panelName, Object[] getFunc, Object[] setFunc, Object[] selectedItemFunc)
	{
		SelectOneOfPanel result = new SelectOneOfPanel (
			new FieldDataReference (frame, this.data, getFunc, setFunc),
			this.frame,
			this.getUIPanel (),
			panelName,
			selectedItemFunc);
		this.componentsPopulate.add (result);
		//this.addComponent (result, true, true, true);
		this.addDynamicComponent (result);
		return result;
	}
	public FieldListCellRendererPanel newFieldListCellRendererPanel ()
	{
		FieldListCellRendererPanel result = new FieldListCellRendererPanel (this.frame, this.getUIPanel ());
		return result;
	}
	public FieldListCellEditorPanel newFieldListCellEditorPanel (Object[] setFieldListElement)
	{
		FieldListCellEditorPanel result = new FieldListCellEditorPanel (this.frame, this.getUIPanel (), setFieldListElement);
		return result;
	}
	
	//************************************************************
	/**
	 * Get the {@code UIPanel} where this panel belongs to.  
	 * @return 
	 */
	abstract UIPanel getUIPanel ();
	//************************************************************
//	/**
//	 * Adds a component to this panel.  This panel uses a {@code GridBagLayout}.  Components added by methods {@code handle_XXX} are arranged in <i>n</i> rows by 2 columns.
//	 * @param component The component to be added.
//	 * @param newRow Whether the new component should start in a new row.
//	 * @param fillRow  Whether the new component should fill an entire row in this panel.
//	 */
//	protected void addComponent (JComponent component, boolean newRow, boolean fillRow, boolean horizontalFill)
//	{
//		java.awt.GridBagConstraints gridBagConstraints;
//		if (newRow) {
//			this.nextComponentGridY++;
//		}
//		gridBagConstraints = new java.awt.GridBagConstraints ();
//		gridBagConstraints.gridy = this.nextComponentGridY;
//		if (fillRow) {
//			gridBagConstraints.gridx = 0;
//			gridBagConstraints.gridwidth = 2;
//		}
//		else {
//			gridBagConstraints.gridx = java.awt.GridBagConstraints.RELATIVE;
//			gridBagConstraints.gridwidth = 1;
//		}
//		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
//		if (horizontalFill) {
//			gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
//		}
//		gridBagConstraints.insets = DynamicDataPanel.defaultInsets;
//		this.add (component, gridBagConstraints);
////		DynamicPanel dpanel = this.panels.getFirst ();
////		dpanel.panel.add (component, dpanel.nextGridBagConstraints (newRow, fillRow));
//	}
	
	
	protected void addDynamicComponent (JComponent component)
	{
		this.add (component);
	}
	
	protected void addDynamicComponents (JComponent leftComponent, JComponent rightComponent)
	{
		JPanel panel = new JPanel (new FlowLayout ());
		panel.add (leftComponent);
		panel.add (rightComponent);
		this.add (panel);
	}
	
	//************************************************************
//	/**
//	 * Insert a new panel where the next components will be inserted.  After the components have been inserted, method {@code panelEnd()} must be called in order for other components be inserted in the main panel.
//	 * 
//	 * <p> This method with {@code panelEnd()} can be used to group components in titled panels in the main panel.
//	 * 
//	 * @param title The new border title of the panel
//	 * @return  This.
//	 */
//	public T panelBegin (String title)
//	{
//		this.panels.addFirst (new DynamicPanel (title));
//		return (T) this;
//	}
//	/**
//	 * Ends insertion of components in the subpanel.  The next components will be inserted in the parent panel.
//	 * 
//	 * <p> This method with {@code panelBegin(String)} can be used to group components in titled panels in the main panel.
//	 * 
//	 * @see DynamicDataPanel#panelBegin(java.lang.String) 
//	 * 
//	 * @return This.
//	 */
//	public T panelEnd ()
//	{
//		this.panels.removeFirst ();
//		return (T) this;
//	}
	//************************************************************

	// methods to handle {@code dialogAction(D)} type constructors.
	/**
	 * Handles constructor {@code di(interfaceData)} from type {@code dialogItem(D)}.  This constructor does not have an associated action.  Therefore we only insert a label in the panel.
	 */
	final public T handle_noaction (JLabel label)
	{
		//this.addComponent (label, true, true, true);
		this.addDynamicComponent (label);
		return (T) this;
	}
	/**
	 * Handles constructor {@code subdialog(D)}.  When the user clicks the button a new panel appers to edit the same data of this panel.
	 */
	abstract public T handle_subdialog (JButton button, final UIPanel childPanel);
	/**
	 * Handles constructor {@code newValue(T)}.   When the user selects this dialog option, the data takes value {@code T}.
	 */
	final public T handle_newValue (JButton button, final Object newValue)
	{
		//System.out.println ("UIPanel.handle_newValue");
		//this.addComponent (button, true, true, true);
		
		this.addDynamicComponent (button);
		ActionListener action;
		action = new ActionListener () {
			public void actionPerformed (ActionEvent evt)
			{
				DynamicDataPanel.this.setData (newValue);
			}
		};
		button.addActionListener (action);
		return (T) this;
	}
	/**
	 * Handles constructor {@code updateData(func(T)=T)}.  When the user selects this dialog item, the data is updated according to the given function.
	 */
	final public T handle_updateData (JButton button, final Object[] setFunc)
	{
		//this.addComponent (button, true, true, true);
		
		this.addDynamicComponent (button);
		ActionListener action;
		action = new ActionListener () {
			public void actionPerformed (ActionEvent evt)
			{
				jmercury.runtime.MethodPtr2 funcMeth = ((jmercury.runtime.MethodPtr2) setFunc[1]);
				DynamicDataPanel.this.setData (funcMeth.call___0_0 (setFunc, data.getValue ()));
			}
		};
		button.addActionListener (action);
		return (T) this;
	}
	/**
	 * Handles constructor {@code editField(get(D,F),set(D,F),dialog(F))}.  Adds a button that shows the panel to edit a field of the data shown by this panel.
	 */
	abstract public T handle_editField (JButton button, final Object[] getFunc, final Object[] setFunc, final UIPanel childPanel);
	/**
	 * Handles a {@code updateFieldInt(get(D,int), set(D, int))}. Adds a {@code JFormattedTextField} to edit an integer value.
	 */
	public T handle_updateFieldInt (JLabel label, Object[] getFunc, Object[] setFunc)
	{
		//this.addComponent (label, true, false, false);
		
		FieldCheck<Integer> inputVerifier;
		inputVerifier = new FieldCheck<Integer> (setFunc) {
			protected Integer getValue (javax.swing.JFormattedTextField input) throws Exception
			{
				try {
					return Integer.valueOf (input.getText ());
				}
				catch (java.lang.NumberFormatException e1) {
					try {
						return NumberFormat.getIntegerInstance ().parse (input.getText ()).intValue ();
					}
					catch (java.lang.NumberFormatException e2) {
						throw new Exception ();
					}
				}
			}
		};
		
		JFormattedTextField textField;
		textField = new JFormattedTextField (new Integer (0));
		textField.setColumns (10);
		textField.setInputVerifier (inputVerifier);
		textField.addPropertyChangeListener ("value", inputVerifier);

		this.componentsPopulate.add (new FormattedTextFieldPopulate (getFunc, textField));
		this.addDynamicComponents (label, textField);
		
		//this.addComponent (textField, false, false, true);
		return (T) this;
	}
	/**
	 * Handles a {@code updateFieldInt(get(D,string), set(D,string))}. Adds a {@code JFormattedTextField} to edit an integer value.
	 */
	public T handle_updateFieldString (JLabel label, Object[] getFunc, Object[] setPred)
	{
		//this.addComponent (label, true, false, false);
		
		FieldCheck<String> inputVerifier;
		inputVerifier = new FieldCheck<String> (setPred) {
			@Override
			protected String getValue (javax.swing.JFormattedTextField input)
			{
				return input.getText ();
			}
		};
		
		JTextField textField;
		textField = new JTextField ("");
		textField.setColumns (30);
		textField.setInputVerifier (inputVerifier);
		textField.addPropertyChangeListener ("value", inputVerifier);

		this.componentsPopulate.add (new TextFieldPopulate (getFunc, textField));
		this.addDynamicComponents (label, textField);
		
		//this.addComponent (textField, false, false, true);
		return (T) this;
	}
	/**
	 * Handles a {@code updateFieldFloat(get(D,float), set(D,float))}. Adds a {@code JFormattedTextField} to edit a floating point value.
	 */
	public T handle_updateFieldFloat (JLabel label, Object[] getFunc, Object[] setFunc)
	{
		//this.addComponent (label, true, false, false);
		
		FieldCheck<Double> inputVerifier;
		inputVerifier = new FieldCheck<Double> (setFunc) {
			protected Double getValue (JFormattedTextField input)
				throws Exception
			{
				try {
					return Double.valueOf (input.getText ());
				}
				catch (java.lang.NumberFormatException e1) {
					try {
						return NumberFormat.getInstance ().parse (input.getText ()).doubleValue ();
					}
					catch (java.lang.NumberFormatException e2) {
						throw new Exception ();
					}
				}
			}
		};
		
		JFormattedTextField textField;
		textField = new JFormattedTextField (new Double (0.001));
		textField.setColumns (10);
		textField.setInputVerifier (inputVerifier);
		textField.addPropertyChangeListener ("value", inputVerifier);

		this.componentsPopulate.add (new FormattedTextFieldPopulate (getFunc, textField));
		this.addDynamicComponents (label, textField);
			
		//this.addComponent (textField, false, false, true);
		return (T) this;
	}
	/**
	 * Handles a {@code updateFieldFloat(get(D,float), set(D,float))}. Adds a {@code JFormattedTextField} to edit a floating point value.
	 */
	public T handle_updateFieldBool (final JCheckBox checkBox, Object[] getFunc, final Object[] setFunc)
	{
		//this.addComponent (checkBox, true, true, true);
		//jmercury.runtime.MethodPtr2 funcMeth = ((jmercury.runtime.MethodPtr2) getFunc [1]);

		this.addDynamicComponent (checkBox);
		ActionListener action = new java.awt.event.ActionListener () {
			@Override
			public void actionPerformed (ActionEvent evt)
			{
				DynamicDataPanel.this.applySetFunc (setFunc, checkBox.isEnabled () ? jmercury.bool.YES : jmercury.bool.NO);
			}
		};
		checkBox.addActionListener (action);

		this.componentsPopulate.add (new CheckBoxPopulate (getFunc, checkBox));
		return (T) this;
	}
	public T handle_editListFieldAny (String field, Object[] getFunc, Object[] setFunc, Object[] listSizeFunc, Object[] listElementFunc,
		FieldListCellRendererPanel cellRenderer, FieldListCellEditorPanel cellEditor, Object defaultValue)
	{
		AnyTypeFieldListEditor listEditor = new AnyTypeFieldListEditor (
			field, this.data, this.frame,
			getFunc, setFunc, listSizeFunc, listElementFunc,
			cellRenderer, cellEditor,
			defaultValue);
		this.componentsPopulate.add (listEditor);
		this.addDynamicComponent (listEditor);
		//this.addComponent (listEditor, true, true, true);
		return (T) this;
	}
	/**
	 * Handles a {@code updateListFieldInt(get(D,list(int)),set(D,list(int)))}.
	 * @param field
	 * @param getFunc
	 * @param setFunc
	 * @return 
	 */
	public T handle_updateListFieldInt (String field, Object[] getFunc, Object[] setFunc)
	{
		PrimitiveTypeFieldListEditorPanel<Integer> listEditor = new PrimitiveTypeFieldListEditorPanel<Integer> (this.data, this.frame, field, getFunc, setFunc, new Integer (0), PrimitiveTypeFieldListEditorPanel.MercuryType.INT);
		this.componentsPopulate.add (listEditor);
		this.addDynamicComponent (listEditor);
		//this.addComponent (listEditor, true, true, true);
		return (T) this;
	}
	/**
	 * Handles a {@code updateListFieldString(get(D,list(string)),set(D,list(string)))}.
	 * @param field
	 * @param getFunc
	 * @param setFunc
	 * @return 
	 */
	final public T handle_updateListFieldString (String field, Object[] getFunc, Object[] setFunc)
	{
		PrimitiveTypeFieldListEditorPanel<String> listEditor = new PrimitiveTypeFieldListEditorPanel<String> (this.data, this.frame, field, getFunc, setFunc, new String ("-"), PrimitiveTypeFieldListEditorPanel.MercuryType.STRING);
		this.componentsPopulate.add (listEditor);
		this.addDynamicComponent (listEditor);
		//this.addComponent (listEditor, true, true, true);
		return (T) this;
	}
	/**
	 * Handles a {@code updateListFieldFloat(get(D,list(float)),set(D,list(float)))}.
	 * @param field
	 * @param getFunc
	 * @param setFunc
	 * @return 
	 */
	final public T handle_updateListFieldFloat (String field, Object[] getFunc, Object[] setFunc)
	{
		PrimitiveTypeFieldListEditorPanel<Double> listEditor = new PrimitiveTypeFieldListEditorPanel<Double> (this.data, this.frame, field, getFunc, setFunc, new Double (0.001), PrimitiveTypeFieldListEditorPanel.MercuryType.FLOAT);
		this.componentsPopulate.add (listEditor);
		
		this.addDynamicComponent (listEditor);
		//this.addComponent (listEditor, true, true, true);
		return (T) this;
	}
	
	//************************************************************
	
	/**
	 * Represents components that must be populated after new data must be shown by the {@code UIPanel}.
	 */
	static private abstract class AbstractComponentPopulate
		implements ComponentPopulate
	{
		final Object[] getFunc;
		AbstractComponentPopulate (Object[] getFunc)
		{
			this.getFunc = getFunc;
		}
	}

	static private class FormattedTextFieldPopulate
		extends AbstractComponentPopulate
	{
		final javax.swing.JFormattedTextField textField;
		FormattedTextFieldPopulate (Object[] getFunc, javax.swing.JFormattedTextField textField)
		{
			super (getFunc);
			this.textField = textField;
		}
		@Override
		public void valueChanged (MercuryReference data)
		{
			this.textField.setValue (data.applyGetFunc (this.getFunc));
		}
	}
	static private class TextFieldPopulate
		extends AbstractComponentPopulate
	{
		final javax.swing.JTextField textField;
		TextFieldPopulate (Object[] getFunc, javax.swing.JTextField textField)
		{
			super (getFunc);
			this.textField = textField;
		}
		@Override
		public void valueChanged (MercuryReference data)
		{
			this.textField.setText ((String) data.applyGetFunc (this.getFunc));
		}
	}
	static private class CheckBoxPopulate
		extends AbstractComponentPopulate
	{
		final javax.swing.JCheckBox checkbox;
		CheckBoxPopulate (Object[] getFunc, javax.swing.JCheckBox checkbox)
		{
			super (getFunc);
			this.checkbox = checkbox;
		}
		@Override
		public void valueChanged (MercuryReference data)
		{
			this.checkbox.setEnabled (data.applyGetFunc (this.getFunc) == jmercury.bool.YES);
		}
	}

	//************************************************************

	/**
	 * This class is responsible for checking the value of a data field.
	 */
	abstract private class FieldCheck<T>
		extends javax.swing.InputVerifier
		implements java.beans.PropertyChangeListener
	{
		/**
		 * Holds the set function used to update a data's field.
		 */
		final private java.lang.Object[] funcArray6;
		/**
		 */
		private FieldCheck (Object[] setPred)
		{
			this.funcArray6 = setPred;
		}

		abstract protected T getValue (javax.swing.JFormattedTextField input)
			throws Exception;


		@Override
		public void propertyChange (java.beans.PropertyChangeEvent e)
		{
			DynamicDataPanel.this.frame.okButton.setEnabled (true);
		}

		@Override
		public boolean verify (javax.swing.JComponent input)
		{
			 if (input instanceof JFormattedTextField) {
				T value;
				try {
					value = getValue ((javax.swing.JFormattedTextField) input);
				}
				catch (Exception e) {
					DynamicDataPanel.this.frame.okButton.setEnabled (false);
					if (e instanceof NumberFormatException)
						DynamicDataPanel.this.frame.messagesLabel.setText ("Invalid number");
					else {
						DynamicDataPanel.this.frame.messagesLabel.setText ("Parse error");
					}
					return false;
				}
				if (DynamicDataPanel.this.debug) System.out.println ("Verifying: " + value);//DEBUG
				return DynamicDataPanel.this.applySetFunc (this.funcArray6, value);
			}
			else if (input instanceof JTextField) {
				String value;
				value = ((JTextField) input).getText ();
				if (DynamicDataPanel.this.debug) System.out.println ("Verifying: " + value);//DEBUG
				return DynamicDataPanel.this.applySetFunc (this.funcArray6, value);
			}
			else {
				throw new Error ("Unhandled swing component");
			}
		}
	}
	/**
	 * Information about the panel currently being initialised.  All panels have a grid bag layout.
	 * 
	 */
	static private class DynamicPanel
	{
		/**
		 * Vertical position of the next component to insert in this panel.
		 */
		private int nextComponentGridY = 0;
		
		final private JPanel panel;
		/**
		 * Construct the instance for the enclosing {@code DynamicDataPanel} instance.
		 * 
		 * @param firstPanel 
		 */
		DynamicPanel (DynamicDataPanel firstPanel)
		{
			this.panel = firstPanel;
		}
		/**
		 * Construct a sub panel of a {@code DynamicDataPanel} instance.
		 * @param title 
		 */
		DynamicPanel (String title)
		{
			this.panel = new JPanel (new GridBagLayout ());
			this.panel.setBorder (javax.swing.BorderFactory.createTitledBorder (title));
		}
		/**
		 * Adds the given component to this panel.
		 * @param component The component to be added.
		 * @param newRow Whether the new component starts a new row
		 * @param fillRow  whether the new component fills two cells of the row
		 */
		private void add (JComponent component, boolean newRow, boolean fillRow)
		{
			this.panel.add (component, this.nextGridBagConstraints (newRow, fillRow));
		}
		/**
		 * Return the next grid bag constraints to be used with the component that is going to be added to this panel.
		 * @param newRow indicates if a new row should be added to this panel.
		 * @param fillRow indicates if the component should occupy two grid cells in this panel.
		 */
		private java.awt.GridBagConstraints nextGridBagConstraints (boolean newRow, boolean fillRow)
		{
			java.awt.GridBagConstraints gridBagConstraints;
			if (newRow) {
				this.nextComponentGridY++;
			}
			gridBagConstraints = new java.awt.GridBagConstraints ();
			gridBagConstraints.gridy = this.nextComponentGridY;
			if (fillRow) {
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridwidth = 2;
			}
			else {
				gridBagConstraints.gridx = java.awt.GridBagConstraints.RELATIVE;
			}
			gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints.insets = DynamicDataPanel.defaultInsets;
			return gridBagConstraints;
		}
	}
/*
 * 
		Container parent = this.getParent ();
		if (parent != null) {
			System.out.println ("Parent: " + parent.getSize ());
		}
		System.out.println ("Panel size: " + this.getSize ());
		Dimension d = new Dimension ((int) (this.getSize ().width * 0.9), 0);
		this.strechSeparator.setSize (d);
		this.strechSeparator.setMinimumSize (d);
		this.strechSeparator.setPreferredSize (d);
				System.out.println ("New strech  " + d);
 */
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
   }// </editor-fold>//GEN-END:initComponents


   // Variables declaration - do not modify//GEN-BEGIN:variables
   // End of variables declaration//GEN-END:variables

}
