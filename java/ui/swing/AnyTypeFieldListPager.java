/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.swing;

import data.AbstractMercuryReference;

/**
 * Represents a pager where the user can navigate a list of elements. This panel
 * is used to edit mercury value of type {@code list(F)}.
 *
 * @param <D> The type of the data that contains a field list.
 * @param <F> The type of the elements in the field list.
 * @author Pedro Mariano
 */
public class AnyTypeFieldListPager<D, F>
	extends AbstractFieldListEditorPanel<D, F>
{

	/**
	 * Component used to edit list elements.
	 */
	final FieldListCellRendererEditorPanel<D, F> editor;
	/**
	 * Index of current element.
	 */
	int index;
	/**
	 * Default value used when adding a new element to the list.
	 */
	final private F defaultValue;

	/**
	 * Creates a new form to edit a list of values.
	 *
	 * @param editor The editor of the values in the list.
	 * @param defaultValue A default value used when inserting new elements in
	 * the list.
	 * @param data The data that contains a field list.
	 * @param frame The frame where this panel is contained.
	 * @param getFunc The function to get the field list.
	 * @param setFunc The function to set the field list.
	 * @param listSizeFunc The function to obtain the field list size.
	 * @param listElementFunc The function to obtain an element at a given index.
	 */
	public AnyTypeFieldListPager (FieldListCellRendererEditorPanel<D, F> editor, F defaultValue, AbstractMercuryReference<D> data, UIFrame frame, Object[] getFunc, Object[] setFunc, Object[] listSizeFunc, Object[] listElementFunc)
	{
		super (data, frame, getFunc, setFunc, listSizeFunc, listElementFunc);
		initComponents ();
		this.editor = editor;
		this.defaultValue = defaultValue;
		this.add (editor, java.awt.BorderLayout.CENTER);
		this.index = 0;
		this.updateInfo ();

	}

	/**
	 * Updates the swing components at initialisation time and after the user has
	 * pressed one of the buttons.
	 */
	private void updateInfo ()
	{
		int size = this.applyListSizeFunc ();
		this.infoLabel.setText (index + "/" + size);
		this.nextButton.setEnabled (index + 1 < size);
		this.previousButton.setEnabled (index > 0);
		this.duplicateButton.setEnabled (size > 0);
		this.deleteButton.setEnabled (size > 0);
		if (index < size) {
			this.editor.setData (this.applyListElementFunc (this.index));
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents()
   {
      java.awt.GridBagConstraints gridBagConstraints;

      controlPanel = new javax.swing.JPanel();
      nextButton = new javax.swing.JButton();
      previousButton = new javax.swing.JButton();
      infoLabel = new javax.swing.JLabel();
      insertButton = new javax.swing.JButton();
      deleteButton = new javax.swing.JButton();
      duplicateButton = new javax.swing.JButton();

      setLayout(new java.awt.BorderLayout());

      controlPanel.setLayout(new java.awt.GridBagLayout());

      nextButton.setText("Next");
      nextButton.addActionListener(new java.awt.event.ActionListener()
      {
         public void actionPerformed(java.awt.event.ActionEvent evt)
         {
            nextButtonActionPerformed(evt);
         }
      });
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
      gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
      controlPanel.add(nextButton, gridBagConstraints);

      previousButton.setText("Previous");
      previousButton.addActionListener(new java.awt.event.ActionListener()
      {
         public void actionPerformed(java.awt.event.ActionEvent evt)
         {
            previousButtonActionPerformed(evt);
         }
      });
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
      gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
      controlPanel.add(previousButton, gridBagConstraints);

      infoLabel.setText("0/0");
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
      controlPanel.add(infoLabel, gridBagConstraints);

      insertButton.setText("Insert");
      insertButton.addActionListener(new java.awt.event.ActionListener()
      {
         public void actionPerformed(java.awt.event.ActionEvent evt)
         {
            insertButtonActionPerformed(evt);
         }
      });
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
      gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
      controlPanel.add(insertButton, gridBagConstraints);

      deleteButton.setText("Delete");
      deleteButton.addActionListener(new java.awt.event.ActionListener()
      {
         public void actionPerformed(java.awt.event.ActionEvent evt)
         {
            deleteButtonActionPerformed(evt);
         }
      });
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
      gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
      controlPanel.add(deleteButton, gridBagConstraints);

      duplicateButton.setText("Duplicate");
      duplicateButton.addActionListener(new java.awt.event.ActionListener()
      {
         public void actionPerformed(java.awt.event.ActionEvent evt)
         {
            duplicateButtonActionPerformed(evt);
         }
      });
      gridBagConstraints = new java.awt.GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
      gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
      controlPanel.add(duplicateButton, gridBagConstraints);

      add(controlPanel, java.awt.BorderLayout.EAST);
   }// </editor-fold>//GEN-END:initComponents

   private void nextButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_nextButtonActionPerformed
   {//GEN-HEADEREND:event_nextButtonActionPerformed
		this.index++;
		this.updateInfo ();
   }//GEN-LAST:event_nextButtonActionPerformed

   private void previousButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_previousButtonActionPerformed
   {//GEN-HEADEREND:event_previousButtonActionPerformed
		index--;
		this.updateInfo ();
   }//GEN-LAST:event_previousButtonActionPerformed

   private void insertButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_insertButtonActionPerformed
   {//GEN-HEADEREND:event_insertButtonActionPerformed
		jmercury.list.List_1<F> previousList = this.data.getValue ();
		jmercury.list.List_1<F> nextList = new jmercury.list.List_1.F_cons_2<> (this.defaultValue, previousList);
		this.data.setValue (nextList);
		index = 0;
		this.updateInfo ();
   }//GEN-LAST:event_insertButtonActionPerformed

   private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_deleteButtonActionPerformed
   {//GEN-HEADEREND:event_deleteButtonActionPerformed
		this.deleteElements (new int[]{this.index});
		if (index > 0) {
			index--;
		}
		this.updateInfo ();
   }//GEN-LAST:event_deleteButtonActionPerformed

   private void duplicateButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_duplicateButtonActionPerformed
   {//GEN-HEADEREND:event_duplicateButtonActionPerformed
		F o = this.applyListElementFunc (index);
		jmercury.list.List_1<F> previousList = this.data.getValue ();
		jmercury.list.List_1<F> nextList = new jmercury.list.List_1.F_cons_2<> (o, previousList);
		this.data.setValue (nextList);
		index = 0;
		this.updateInfo ();
   }//GEN-LAST:event_duplicateButtonActionPerformed


   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JPanel controlPanel;
   private javax.swing.JButton deleteButton;
   private javax.swing.JButton duplicateButton;
   private javax.swing.JLabel infoLabel;
   private javax.swing.JButton insertButton;
   private javax.swing.JButton nextButton;
   private javax.swing.JButton previousButton;
   // End of variables declaration//GEN-END:variables
}