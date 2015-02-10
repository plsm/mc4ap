/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.swing;

import java.awt.Dimension;
import data.AbstractMercuryReference;
import data.MercuryReference;
import javax.swing.table.TableCellEditor;
import ui.Key;

/**
 * This component allows the user to edit a field list of generic type.
 * 
 * @author pedro
 */
final public class AnyTypeFieldListEditor<D, F>
	extends AbstractFieldListEditorPanel<D, F>
//	implements ComponentPopulate<D>
{
	/**
	 * Field name.  This is used to initalise the panel border.
	 */
	final transient private String fieldName;
	/**
	 * Default value used when adding a new element to the list.
	 */
	final private F defaultValue;
	/**
	 * Table model used by {@code JTable} component.
	 */
	final private FieldList_TableModel fieldList_tableModel;
	final Key key;
	
	DataReference<D> dataReference;

	AnyTypeFieldListEditor (
		String fieldName, UIFrame frame, Key key, Object[] getFunc, Object[] setFunc, Object[] listSizeFunc, Object[] listElementFunc,
		F defaultValue)
	{
		this (
			fieldName,
			frame,
			key,
			getFunc,
			setFunc,
			listSizeFunc,
			listElementFunc,
			defaultValue,
			new DataReference<D> (frame));
	}
	
	private AnyTypeFieldListEditor (
		String fieldName, UIFrame frame, Key key, Object[] getFunc, Object[] setFunc, Object[] listSizeFunc, Object[] listElementFunc,
		F defaultValue,
		DataReference<D> dataReference)
	{
		super (dataReference, frame, getFunc, setFunc, listSizeFunc, listElementFunc);
		this.fieldName = fieldName;
		this.defaultValue = defaultValue;
		this.fieldList_tableModel = new FieldList_TableModel ();
		this.key = key;
		this.dataReference = dataReference;
		initComponents ();
	}
	
	void setCellRendererEditor (FieldListCellRendererEditorPanel<D, F> cellRendererEditor)
	{
		cellRendererEditor.validate ();
		int rowHeight =
			16 +
			Math.max (
				cellRendererEditor.getSize ().height,
				cellRendererEditor.getPreferredSize ().height
			);
		this.fieldList_table.setRowHeight (rowHeight);
		this.fieldList_table.getTableHeader ().setVisible (false);
		this.fieldList_table.setDefaultRenderer (Object.class, cellRendererEditor);
		this.fieldList_table.setDefaultEditor (Object.class, cellRendererEditor);
		this.validate ();
		this.repaint ();
	}

	void setValue (D value)
	{
		
	}
//	@Override
//	public void valueChanged (MercuryReference<D> dummy)
//	{
//		this.fieldList_tableModel.fireTableDataChanged ();
//	}

	boolean commitValue ()
	{
		TableCellEditor tce = this.fieldList_table.getCellEditor ();
		if (tce != null) {
			return tce.stopCellEditing ();
		}
		return true;
	}
	
	private void replaceElement (F value, int index)
	{
		System.out.println ("Replacing " + value + " @" + index);
		jmercury.list.List_1<F> previousList = this.data.getValue ();
		jmercury.list.List_1<F> newList;
		newList = new jmercury.list.List_1.F_nil_0 ();
		jmercury.list.List_1.F_cons_2<F> insert, newInsert;
		insert = null;
		while (index > 0) {
			jmercury.list.List_1.F_cons_2<F> cons = (jmercury.list.List_1.F_cons_2) previousList;
			index--;
			if (insert == null) {
				insert = new jmercury.list.List_1.F_cons_2<> (cons.F1, null);
				newList = insert;
			}
			else {
				newInsert = new jmercury.list.List_1.F_cons_2<> (cons.F1, null);
				insert.F2 = newInsert;
				insert = newInsert;
			}
			previousList = cons.F2;
		}
		jmercury.list.List_1.F_cons_2<F> elementReplace = (jmercury.list.List_1.F_cons_2) previousList;
		if (insert != null) {
			// elements were inserted, so put nil constructor
			insert.F2 = new jmercury.list.List_1.F_cons_2<> (value, elementReplace.F2);
		}
		else {
			newList = new jmercury.list.List_1.F_cons_2<> (value, elementReplace.F2);
		}
		// set the list field
		this.data.setValue (newList);
	}
	
	final private class FieldList_TableModel
//		implements javax.swing.table.TableModel
//		extends javax.swing.table.DefaultTableModel
			extends javax.swing.table.AbstractTableModel
	{

		@Override
		public int getRowCount ()
		{
			return AnyTypeFieldListEditor.this.applyListSizeFunc ();
		}

		@Override
		public int getColumnCount ()
		{
			return 1;
		}

		@Override
		public String getColumnName (int columnIndex)
		{
			return "Not used";
		}

		@Override
		public Class<?> getColumnClass (int columnIndex) {
			return Object.class;
		}

		@Override
		public boolean isCellEditable (int rowIndex, int columnIndex) {
			return true;
		}

		@Override
		public Object getValueAt (int rowIndex, int columnIndex)
		{
			//System.out.println ("FieldList_TableModel.getValueAt(" + rowIndex + "," + columnIndex + ")");
			Object result = AnyTypeFieldListEditor.this.applyListElementFunc (rowIndex);
			System.out.println ("getValueAt (" + rowIndex + ", " + columnIndex + ") -> " + result.toString ());
			return result;
		}

		@Override
		public void setValueAt (Object aValue, int rowIndex, int columnIndex)
		{
			AnyTypeFieldListEditor.this.replaceElement ((F) aValue, rowIndex);
			//System.out.println ("FieldList_TableModel.setValueAt(Object,int,int)");
		}
		
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {
      java.awt.GridBagConstraints gridBagConstraints;

      javax.swing.JPanel buttonsPanel = new javax.swing.JPanel();
      javax.swing.JButton addButton = new javax.swing.JButton();
      dupButton = new javax.swing.JButton();
      javax.swing.JButton delButton = new javax.swing.JButton();
      jScrollPane2 = new javax.swing.JScrollPane();
      fieldList_table = new javax.swing.JTable();

      setBorder(javax.swing.BorderFactory.createTitledBorder(fieldName));
      addComponentListener(new java.awt.event.ComponentAdapter() {
         public void componentResized(java.awt.event.ComponentEvent evt) {
            formComponentResized(evt);
         }
      });
      setLayout(new java.awt.BorderLayout());

      buttonsPanel.setLayout(new java.awt.GridBagLayout());

      addButton.setText("Add");
      addButton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            addButtonActionPerformed(evt);
         }
      });
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
      gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
      buttonsPanel.add(addButton, gridBagConstraints);

      dupButton.setText("Duplicate");
      dupButton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            dupButtonActionPerformed(evt);
         }
      });
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
      gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
      buttonsPanel.add(dupButton, gridBagConstraints);

      delButton.setText("Delete");
      delButton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            delButtonActionPerformed(evt);
         }
      });
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
      gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
      buttonsPanel.add(delButton, gridBagConstraints);

      add(buttonsPanel, java.awt.BorderLayout.EAST);

      fieldList_table.setModel(this.fieldList_tableModel);
      fieldList_table.setRowHeight(300);
      jScrollPane2.setViewportView(fieldList_table);

      add(jScrollPane2, java.awt.BorderLayout.CENTER);
   }// </editor-fold>//GEN-END:initComponents

   private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
		jmercury.list.List_1<F> previousList = this.data.getValue ();
		jmercury.list.List_1<F> nextList = new jmercury.list.List_1.F_cons_2<> (this.defaultValue, previousList);
		this.data.setValue (nextList);
		this.fieldList_tableModel.fireTableDataChanged ();
//		System.out.println ("AnyTypeFieldListEditor.addButtonActionPerformed/1");//DEBUG
//		System.out.println (previousList);         //DEBUG
//		System.out.println (" =>");                //DEBUG
//		System.out.println (this.applyGetFunc ()); //DEBUG
   }//GEN-LAST:event_addButtonActionPerformed

   private void dupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dupButtonActionPerformed
		int index = this.fieldList_table.getSelectedRow ();
		if (index != -1) {
			F o = this.applyListElementFunc (index);
			jmercury.list.List_1<F> previousList = this.data.getValue ();
			jmercury.list.List_1<F> nextList = new jmercury.list.List_1.F_cons_2<> (o, previousList);
			this.data.setValue (nextList);
			this.fieldList_tableModel.fireTableDataChanged ();
		}
		
   }//GEN-LAST:event_dupButtonActionPerformed

   private void delButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delButtonActionPerformed
		if (this.deleteElements (this.fieldList_table.getSelectedRows ())) {
			this.fieldList_tableModel.fireTableDataChanged ();
		}
   }//GEN-LAST:event_delButtonActionPerformed

   private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
//		System.out.println ("New size: " + this.getSize ());
//		System.out.print ("Changed from " + this.getPreferredSize ());
////		this.jScrollPane2.setPreferredSize (new Dimension (
////			Math.max (
////				200,
////				this.getSize ().width - 100 - this.dupButton.getSize ().width
////			),
////			400));
////		
//		this.jScrollPane2.setPreferredSize (new Dimension (
//			(int) (this.getSize ().width * 0.9),
//			400));
//		
//		System.out.println (" to " + this.getPreferredSize ());
   }//GEN-LAST:event_formComponentResized


   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton dupButton;
   private javax.swing.JTable fieldList_table;
   private javax.swing.JScrollPane jScrollPane2;
   // End of variables declaration//GEN-END:variables

}
