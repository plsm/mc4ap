/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.swing;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import data.MercuryReference;
import data.AbstractMercuryReference;
import data.FieldReference;
import data.closure.GetFieldFunc;
import data.closure.SetFieldFunc;
import data.closure.UpdateDataFunc;
import java.awt.GridBagConstraints;
import java.text.ParseException;
import jmercury.list.List_1;
import ui.Key;

/**
 * Base class of swing panels that are initialised during runtime by the mercury
 * backend. The public methods in this class match the constructors of mercury
 * type {@code dialog(D)}.
 *
 * <p>This panel uses a {@code GridBagLayout}. Components added by methods
 * {@code handle_XXX} are arranged in <i>n</i> rows by 2 columns.
 *
 * @author Pedro Mariano
 */
abstract public class DynamicDataPanel<P extends DynamicDataPanel<P, D, R>, D, R extends AbstractMercuryReference<D> >
	extends AbstractDataPanel<D, R>
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
	
	transient protected boolean debug = true;
	private int nextComponentGridY = 0;
	/**
	 * Creates new form DynamicDataPanel
	 */
	protected DynamicDataPanel (R data, UIFrame frame)
	{
		super (data, frame);
		this.initComponents ();
//		this.add (this.strechSeparator);
		this.componentsPopulate = new LinkedList<> ();
//		this.panels = new LinkedList<DynamicPanel> ();
//		this.panels.addFirst (new DynamicPanel (this));
	}
//	protected DynamicDataPanel (UIFrame frame)
//	{
//		super (frame);
//		this.initComponents ();
////		this.add (this.strechSeparator);
//		this.componentsPopulate = new LinkedList<> ();
//	}
	/**
	 * Sets the data shown by this panel and updates the data displayed of any swing components.
	 */
	boolean setData (D value)
	{
		if (this.data.setValue (value)) {
			this.fireValueChanged ();
			return true;
		}
		else {
			return false;
		}
	}
	
	protected void fireValueChanged ()
	{
		for (ComponentPopulate cp : this.componentsPopulate) {
			cp.valueChanged (this.data);
		}
	}
	
	/**
	 * Construct an inline panel where swing components to edit this
	 * {@code UIPanel} Mercury data can be placed. Inline panels are bordered
	 * panels that visually group swing components.
	 *
	 * @return 
	 */
	public InlinePanelField<D, D> newInlinePanelForData (String panelName)
	{
		InlinePanelField<D, D> result = new InlinePanelField<> (
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
	 * Construct an inline panel where swing components to edit this
	 * {@code UIPanel} Mercury data can be placed. Inline panels are bordered
	 * panels that visually group swing components.
	 *
	 * @return 
	 */
	public <F> InlinePanelField<D, F> newInlinePanelForFieldData (String panelName, Object[] getFieldFunc, Object[] setFieldFunc)
	{
		InlinePanelField<D, F> result = new InlinePanelField<> (
			new FieldReference<> (this.data, new data.closure.GetFieldFunc<D,F> (getFieldFunc), new data.closure.SetFieldFunc<D,F> (setFieldFunc)),
			this.frame,
			this.getUIPanel (),
			panelName);
		this.componentsPopulate.add (result);
		//this.addComponent (result, true, true, true);
		this.addDynamicComponent (result);
		//System.out.println ("newInlinePanelForFieldData (" + panelName + ", ...)");
		return result;
	}
	public <F> SelectOneOfPanel<D, F> newSelectOneOfFieldPanel (String panelName, Object[] funcSelectedChoice, Object[] funcSetData)
	{
		SelectOneOfPanel<D, F> result = new SelectOneOfPanel<> (this.data, this.frame, this.getUIPanel (), panelName, funcSelectedChoice, funcSetData);
		this.componentsPopulate.add (result);
		//this.addComponent (result, true, true, true);
		this.addDynamicComponent (result);
		return result;
	}
	public SelectOneOfDataPanel<D> newSelectOneOfDataPanel (String panelName, Object[] funcSelectedChoice)
	{
		SelectOneOfDataPanel<D> result = new SelectOneOfDataPanel<> (this.data, this.frame, this.getUIPanel (), panelName, funcSelectedChoice);
		this.componentsPopulate.add (result);
		//this.addComponent (result, true, true, true);
		this.addDynamicComponent (result);
		return result;
	}
//	public FieldListCellRendererPanel newFieldListCellRendererPanel ()
//	{
//		FieldListCellRendererPanel result = new FieldListCellRendererPanel (this.frame, this.getUIPanel ());
//		return result;
//	}
	public <D1, F1> FieldListCellRendererEditorPanel<D1, F1> newFieldListCellRendererEditorPanel (AnyTypeFieldListEditor<D1, F1> listEditor, Object[] setFieldListElement)
	{
		FieldListCellRendererEditorPanel<D1, F1> result
			= new FieldListCellRendererEditorPanel<> (
				this.frame,
				this.getUIPanel (),
				setFieldListElement,
				listEditor);
		return result;
	}
	
	//************************************************************
	/**
	 * Get the {@code UIPanel} where this panel belongs to.  
	 * @return 
	 */
	abstract UIPanel getUIPanel ();
	/**
	 * Get the key this panel is known by {@code UIFrame} dynamic panel.
	 * 
	 * @return a key
	 */
	abstract Key getKey ();
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
		GridBagConstraints gridBagConstraints;
		this.nextComponentGridY++;
		gridBagConstraints = new GridBagConstraints ();
		gridBagConstraints.gridy = this.nextComponentGridY;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = DynamicDataPanel.defaultInsets;
		this.add (component, gridBagConstraints);
	}
	
	protected void addDynamicComponents (JComponent leftComponent, JComponent rightComponent)
	{
		GridBagConstraints gridBagConstraints;
		this.nextComponentGridY++;
		gridBagConstraints = new GridBagConstraints ();
		gridBagConstraints.gridy = this.nextComponentGridY;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = DynamicDataPanel.defaultInsets;
		this.add (leftComponent, gridBagConstraints);
		gridBagConstraints = new GridBagConstraints ();
		gridBagConstraints.gridy = this.nextComponentGridY;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = DynamicDataPanel.defaultInsets;
		this.add (rightComponent, gridBagConstraints);
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
	 * Handles constructor {@code di(interfaceData)} from type
	 * {@code dialogItem(D)}. This constructor does not have an associated
	 * action. Therefore we only insert a label in the panel.
	 */
	final public P handle_noaction (JLabel label)
	{
		//this.addComponent (label, true, true, true);
		this.addDynamicComponent (label);
		return (P) this;
	}
	/**
	 * Handles constructor {@code subdialog(D)}. When the user clicks the button
	 * a new panel appers to edit the same data of this panel.
	 */
	public P handle_subdialog (JButton button, final UIPanel<D> childPanel)
	{
		this.addDynamicComponent (button);
		NavigateActionListener nal = new NavigateActionListener (this.getKey ())
		{
			@Override
			boolean perform ()
			{
				return
					childPanel.commitValue ()
					&& DynamicDataPanel.this.setData (childPanel.data.getValue ());
			}
			@Override
			public void actionPerformed (ActionEvent e)
			{
				childPanel.setData (DynamicDataPanel.this.data.getValue ());
				DynamicDataPanel.this.frame.showPanel (childPanel.key, this);
			}
		};
		button.addActionListener (nal);
		return (P) this;
	}
	  
	/**
	 * Handles constructor {@code newValue(T)}. When the user selects this dialog
	 * option, the data takes value {@code T}.
	 */
	final public P handle_newValue (JButton button, final D newValue)
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
		return (P) this;
	}
	/**
	 * Handles constructor {@code updateData(func(T)=T)}. When the user selects
	 * this dialog item, the data is updated according to the given function.
	 */
	final public P handle_updateData (JButton button, final Object[] setFunc)
	{
		//this.addComponent (button, true, true, true);
		
		this.addDynamicComponent (button);
		ActionListener action;
		action = new ActionListener () {
			UpdateDataFunc<D> updateDataFunc = new UpdateDataFunc<> (setFunc);
			@Override
			public void actionPerformed (ActionEvent evt)
			{
				DynamicDataPanel.this.setData (updateDataFunc.apply (DynamicDataPanel.this.data.getValue ()));
//				jmercury.runtime.MethodPtr2 funcMeth = ((jmercury.runtime.MethodPtr2) setFunc[1]);
//				DynamicDataPanel.this.setData (funcMeth.call___0_0 (setFunc, data.getValue ()));
			}
		};
		button.addActionListener (action);
		return (P) this;
	}
	/**
	 * Handles constructor {@code editField(get(D,F),set(D,F),dialog(F))}. Adds a
	 * button that shows the panel to edit a field of the data shown by this
	 * panel.
	 */
	public <F> P handle_editField (JButton button, final Object[] getFunc, final Object[] setFunc, final UIPanel<F> childPanel)
	{
		this.addDynamicComponent (button);
		NavigateActionListener nal = new NavigateActionListener (this.getKey ())
		{
			GetFieldFunc<D, F> getFieldFunc = new GetFieldFunc<> (getFunc);
			SetFieldFunc<D, F> setFieldFunc = new SetFieldFunc<> (setFunc);
			@Override
			boolean perform ()
			{
				return
					childPanel.commitValue ()
					&& DynamicDataPanel.this.data.applySetFieldFunc (setFieldFunc, childPanel.data.getValue ());
			}
			
			@Override
			public void actionPerformed (ActionEvent e)
			{
				childPanel.setData (DynamicDataPanel.this.data.applyGetFieldFunc (getFieldFunc));
				DynamicDataPanel.this.frame.showPanel (childPanel.key, this);
			}
		};
		button.addActionListener (nal);
		return (P) this;
	}
	/**
	 * Handles a {@code updateFieldInt(get(D,int), set(D, int))}. Adds a
	 * {@code JFormattedTextField} to edit an integer value.
	 */
	public P handle_updateFieldInt (JLabel label, Object[] getFunc, Object[] setFunc)
	{
		//this.addComponent (label, true, false, false);
		
		FieldCheck<Integer> inputVerifier;
		inputVerifier = new FieldCheck<Integer> (setFunc) {
			@Override
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

		this.componentsPopulate.add (new FormattedTextFieldPopulate<Integer> (getFunc, setFunc, textField));
		this.addDynamicComponents (label, textField);
		
		//this.addComponent (textField, false, false, true);
		return (P) this;
	}
	/**
	 * Handles a {@code updateFieldInt(get(D,string), set(D,string))}. Adds a
	 * {@code JFormattedTextField} to edit an integer value.
	 */
	public P handle_updateFieldString (JLabel label, Object[] getFunc, Object[] setPred)
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

		this.componentsPopulate.add (new TextFieldPopulate (getFunc, setPred, textField));
		this.addDynamicComponents (label, textField);
		
		//this.addComponent (textField, false, false, true);
		return (P) this;
	}
	/**
	 * Handles a {@code updateFieldFloat(get(D,float), set(D,float))}. Adds a
	 * {@code JFormattedTextField} to edit a floating point value.
	 */
	public P handle_updateFieldFloat (JLabel label, Object[] getFunc, Object[] setFunc)
	{
		//this.addComponent (label, true, false, false);
		
		FieldCheck<Double> inputVerifier;
		inputVerifier = new FieldCheck<Double> (setFunc) {
			@Override
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

		this.componentsPopulate.add (new FormattedTextFieldPopulate<Double> (getFunc, setFunc, textField));
		this.addDynamicComponents (label, textField);
			
		//this.addComponent (textField, false, false, true);
		return (P) this;
	}
	/**
	 * Handles a {@code updateFieldFloat(get(D,float), set(D,float))}. Adds a
	 * {@code JFormattedTextField} to edit a floating point value.
	 */
	public P handle_updateFieldBool (final JCheckBox checkBox, Object[] getFunc, final Object[] setFunc)
	{
		//this.addComponent (checkBox, true, true, true);
		//jmercury.runtime.MethodPtr2 funcMeth = ((jmercury.runtime.MethodPtr2) getFunc [1]);

		this.addDynamicComponent (checkBox);
		ActionListener action = new java.awt.event.ActionListener () {
			@Override
			public void actionPerformed (ActionEvent evt)
			{
				DynamicDataPanel.this.data.applySetFieldFunc (
					new SetFieldFunc<D,jmercury.bool.Bool_0> (setFunc),
					checkBox.isSelected () ? jmercury.bool.YES : jmercury.bool.NO);
			}
		};
		checkBox.addActionListener (action);

		this.componentsPopulate.add (new CheckBoxPopulate (getFunc, setFunc, checkBox));
		return (P) this;
	}
	/**
	 * 
	 * @param <F>
	 * @param button
	 * @param getFunc
	 * @param setFunc
	 * @param listSizeFunc
	 * @param listElementFunc
	 * @param cellRenderer
	 * @param cellEditor
	 * @param defaultValue
	 * @return 
	 */
	public <F> P handle_editListFieldAny (JButton button, final Object[] getFunc, final Object[] setFunc, Object[] listSizeFunc, Object[] listElementFunc,
		final AnyTypeFieldListEditor<D, F> listEditor,
		FieldListCellRendererEditorPanel<D, F> cellRendererEditor,
		F defaultValue)
	{
		this.addDynamicComponent (button);
		listEditor.setCellRendererEditor (cellRendererEditor);
		NavigateActionListener nal = new NavigateActionListener (this.getKey ())
		{
			GetFieldFunc<D, List_1<F>> getFieldFunc = new GetFieldFunc<> (getFunc);
			SetFieldFunc<D, List_1<F>> setFieldFunc = new SetFieldFunc<> (setFunc);
			@Override
			boolean perform ()
			{
				return
					listEditor.commitValue ()
					&& DynamicDataPanel.this.data.applySetFieldFunc (setFieldFunc, listEditor.data.getValue ());
			}
			
			@Override
			public void actionPerformed (ActionEvent e)
			{
				listEditor.dataReference.setValue (DynamicDataPanel.this.data.getValue ());
				listEditor.data.setValue (DynamicDataPanel.this.data.applyGetFieldFunc (getFieldFunc));
				DynamicDataPanel.this.frame.showPanel (listEditor.key, this);
			}
		};
		button.addActionListener (nal);
		return (P) this;
	}
	/**
	 * Handles a {@code updateListFieldInt(get(D,list(int)),set(D,list(int)))}.
	 * @param field
	 * @param getFunc
	 * @param setFunc
	 * @return 
	 */
	public P handle_updateListFieldInt (String field, Object[] getFunc, Object[] setFunc)
	{
		PrimitiveTypeFieldListEditorPanel<D, Integer> listEditor
			 = new PrimitiveTypeFieldListEditorPanel<> (
				  this.data, this.frame, field, getFunc, setFunc,
				  new Integer (0), PrimitiveTypeFieldListEditorPanel.MercuryType.INT);
		this.componentsPopulate.add (listEditor);
		this.addDynamicComponent (listEditor);
		//this.addComponent (listEditor, true, true, true);
		return (P) this;
	}
	/**
	 * Handles a {@code updateListFieldString(get(D,list(string)),set(D,list(string)))}.
	 * @param field
	 * @param getFunc
	 * @param setFunc
	 * @return 
	 */
	final public P handle_updateListFieldString (String field, Object[] getFunc, Object[] setFunc)
	{
		PrimitiveTypeFieldListEditorPanel<D, String> listEditor
			= new PrimitiveTypeFieldListEditorPanel<> (
				  this.data, this.frame, field, getFunc, setFunc,
				  new String ("-"), PrimitiveTypeFieldListEditorPanel.MercuryType.STRING);
		this.componentsPopulate.add (listEditor);
		this.addDynamicComponent (listEditor);
		//this.addComponent (listEditor, true, true, true);
		return (P) this;
	}
	/**
	 * Handles a {@code updateListFieldFloat(get(D,list(float)),set(D,list(float)))}.
	 * @param field
	 * @param getFunc
	 * @param setFunc
	 * @return 
	 */
	final public P handle_updateListFieldFloat (String field, Object[] getFunc, Object[] setFunc)
	{
		PrimitiveTypeFieldListEditorPanel<D, Double> listEditor
			= new PrimitiveTypeFieldListEditorPanel<> (
				this.data, this.frame, field, getFunc, setFunc,
				new Double (0.001), PrimitiveTypeFieldListEditorPanel.MercuryType.FLOAT);
		this.componentsPopulate.add (listEditor);
		
		this.addDynamicComponent (listEditor);
		//this.addComponent (listEditor, true, true, true);
		return (P) this;
	}
	
	//************************************************************
	
	/**
	 * Represents components that must be populated after new data must be shown by the {@code UIPanel}.
	 */
	private abstract class AbstractComponentPopulate<F>
		implements ComponentPopulate<D>
	{
		final GetFieldFunc<D,F> getFieldFunc;
		final SetFieldFunc<D,F> setFieldFunc;
		AbstractComponentPopulate (Object[] getFunc, Object[] setFunc)
		{
			this.getFieldFunc = new GetFieldFunc<> (getFunc);
			this.setFieldFunc = new SetFieldFunc<> (setFunc);
		}
	}

	final private class FormattedTextFieldPopulate<F>
		extends AbstractComponentPopulate<F>
	{
		final javax.swing.JFormattedTextField textField;
		FormattedTextFieldPopulate (Object[] getFunc, Object[] setFunc, javax.swing.JFormattedTextField textField)
		{
			super (getFunc, setFunc);
			this.textField = textField;
		}
		@Override
		public void valueChanged (MercuryReference<D> data)
		{
			this.textField.setValue (data.applyGetFieldFunc (this.getFieldFunc));
		}

		@Override
		public boolean commitValue ()
		{
			try {
				this.textField.commitEdit ();
			} catch (ParseException ex) {
				return false;
			}
			return DynamicDataPanel.this.data.applySetFieldFunc (setFieldFunc, (F) this.textField.getValue ());
		}
	}
	final private class TextFieldPopulate
		extends AbstractComponentPopulate<String>
	{
		final javax.swing.JTextField textField;
		TextFieldPopulate (Object[] getFunc, Object[] setFunc, javax.swing.JTextField textField)
		{
			super (getFunc, setFunc);
			this.textField = textField;
		}
		@Override
		public void valueChanged (MercuryReference<D> data)
		{
			this.textField.setText (data.applyGetFieldFunc (this.getFieldFunc));
		}

		@Override
		public boolean commitValue ()
		{
			return DynamicDataPanel.this.data.applySetFieldFunc (setFieldFunc, (String) this.textField.getText ());
		}
	}
	final private class CheckBoxPopulate
		extends AbstractComponentPopulate<jmercury.bool.Bool_0>
	{
		final javax.swing.JCheckBox checkbox;
		CheckBoxPopulate (Object[] getFunc, Object[] setFunc, javax.swing.JCheckBox checkbox)
		{
			super (getFunc, setFunc);
			this.checkbox = checkbox;
		}
		@Override
		public void valueChanged (MercuryReference<D> data)
		{
			this.checkbox.setSelected (data.applyGetFieldFunc (this.getFieldFunc) == jmercury.bool.YES);
		}

		@Override
		public boolean commitValue ()
		{
			jmercury.bool.Bool_0 value = (this.checkbox.isSelected () ? jmercury.bool.YES : jmercury.bool.NO);
			return DynamicDataPanel.this.data.applySetFieldFunc (setFieldFunc, value);
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
		final private SetFieldFunc<D, T> setFieldFunc;
		/**
		 */
		private FieldCheck (Object[] setPred)
		{
			this.setFieldFunc = new SetFieldFunc<> (setPred);
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
				return DynamicDataPanel.this.data.applySetFieldFunc (this.setFieldFunc, value);
			}
			else if (input instanceof JTextField) {
				String value;
				value = ((JTextField) input).getText ();
				if (DynamicDataPanel.this.debug) System.out.println ("Verifying: " + value);//DEBUG
				return DynamicDataPanel.this.data.applySetFieldFunc (this.setFieldFunc, (T) value);
			}
			else {
				throw new Error ("Unhandled swing component");
			}
		}
	}
//	/**
//	 * Information about the panel currently being initialised.  All panels have a grid bag layout.
//	 * 
//	 */
//	static private class DynamicPanel
//	{
//		/**
//		 * Vertical position of the next component to insert in this panel.
//		 */
//		private int nextComponentGridY = 0;
//		
//		final private JPanel panel;
//		/**
//		 * Construct the instance for the enclosing {@code DynamicDataPanel} instance.
//		 * 
//		 * @param firstPanel 
//		 */
//		DynamicPanel (DynamicDataPanel firstPanel)
//		{
//			this.panel = firstPanel;
//		}
//		/**
//		 * Construct a sub panel of a {@code DynamicDataPanel} instance.
//		 * @param title 
//		 */
//		DynamicPanel (String title)
//		{
//			this.panel = new JPanel (new GridBagLayout ());
//			this.panel.setBorder (javax.swing.BorderFactory.createTitledBorder (title));
//		}
//		/**
//		 * Adds the given component to this panel.
//		 * @param component The component to be added.
//		 * @param newRow Whether the new component starts a new row
//		 * @param fillRow  whether the new component fills two cells of the row
//		 */
//		private void add (JComponent component, boolean newRow, boolean fillRow)
//		{
//			this.panel.add (component, this.nextGridBagConstraints (newRow, fillRow));
//		}
//		/**
//		 * Return the next grid bag constraints to be used with the component that is going to be added to this panel.
//		 * @param newRow indicates if a new row should be added to this panel.
//		 * @param fillRow indicates if the component should occupy two grid cells in this panel.
//		 */
//		private java.awt.GridBagConstraints nextGridBagConstraints (boolean newRow, boolean fillRow)
//		{
//			java.awt.GridBagConstraints gridBagConstraints;
//			if (newRow) {
//				this.nextComponentGridY++;
//			}
//			gridBagConstraints = new java.awt.GridBagConstraints ();
//			gridBagConstraints.gridy = this.nextComponentGridY;
//			if (fillRow) {
//				gridBagConstraints.gridx = 0;
//				gridBagConstraints.gridwidth = 2;
//			}
//			else {
//				gridBagConstraints.gridx = java.awt.GridBagConstraints.RELATIVE;
//			}
//			gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
//			gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
//			gridBagConstraints.insets = DynamicDataPanel.defaultInsets;
//			return gridBagConstraints;
//		}
//	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents()
   {

      setLayout(new java.awt.GridBagLayout());
   }// </editor-fold>//GEN-END:initComponents

//			Dimension d = new Dimension ((int) (this.getSize ().width * 0.75), 0);
//		this.strechSeparator.setSize (d);
//		this.strechSeparator.setMinimumSize (d);
//		this.strechSeparator.setPreferredSize (d);

   // Variables declaration - do not modify//GEN-BEGIN:variables
   // End of variables declaration//GEN-END:variables

}
