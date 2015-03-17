/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.swing;

import data.closure.GetFieldFunc;
import data.closure.SetFieldFunc;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import ui.Key;
import ui.KeyGenerator;

/**
 * Main panel with the tool bar and panels arranged in a card layout.
 * 
 * @author pedro
 */
final public class UIFrame<D>
	extends AbstractDataPanel<D, DataReference<D> >
	implements WindowListener
{
	/**
	 * Frame title.
	 */
	final private String title;
	/**
	 * Actions to perform when the user presses the ok button.  If the
	 * action result is ok, we remove it from this list and show the next
	 * panel or label {@code emptyLabel}.
	 */
	final private List<NavigateAction> navigateActions;
	/**
	 * Semaphore used to block method {@code showFrame()} until the user closes the window.
	 */
	final private Semaphore close;
	/**
	 * Key generator used to assign keys to panels inserted in the {@code dynamicPanel}.
	 */
	final KeyGenerator keyGenerator = new KeyGenerator ();
	

	/** Creates new form UIFrame */
	private UIFrame (String title, D data)
	{
		super (new DataReference (null, data));
		this.title = title;
		this.navigateActions = new LinkedList<> ();
		this.close = new Semaphore (0);
		
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName ())) {
					javax.swing.UIManager.setLookAndFeel (info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			
		}
		initComponents ();
		this.buttonsPanel.setVisible (false);
	}
	/**
	 * Create the main panel with a tool bar and sub panels arranged by a card
	 * layout.
	 *
	 * @param <D> The mercury type of the data that is going to be edited by the
	 * returned panel.
	 * @param title The frame title.
	 * @param data The initial value of the data.
	 * @return main panel with a tool bar and sub panels arranged by a card
	 * layout.
	 */
	static public <D> UIFrame createFrame (String title, D data)
	{
		UIFrame<D> result = new UIFrame<> (title, data);
		result.data.frame = result;
		result.frame = result;
		return result;
	}
	/**
	 * Show a frame with this panel as the content pane and return the data after
	 * the frame has been closed.
	 */
	public Object showFrame ()
	{
		// add the exit button
		this.toolBar.addSeparator ();
		JButton exitButton = new JButton (new javax.swing.AbstractAction ("Exit") {
			@Override
			public void actionPerformed (ActionEvent e) {
				UIFrame.this.close.release ();
				UIFrame.this.setVisible (false);
			}
		});
		this.toolBar.add (exitButton);
		//
		//System.out.println (this.data.value.toString ());
		//System.out.println (this.data.value.getClass ().toString ());
		JFrame window = new JFrame (this.title);
		window.setDefaultCloseOperation (javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		window.setContentPane (this);
		window.addWindowListener (this);
		window.pack ();
		window.setVisible (true);
		this.close.acquireUninterruptibly ();
		
		return this.data.getValue ();
	}
	/**
	 * Construct a new {@code UIPanel} that can be used to display and edit the
	 * data or a field data managed by this {@code UIFrame}. The new panel has
	 * its own {@code DataReference}. Only when the user presses the ok button
	 * this frame data is updated.
	 *
	 * @return the constructed panel.
	 */
	public UIPanel<D> newUIPanel ()
	{
		UIPanel<D> result = new UIPanel<> (frame, this.keyGenerator.nextKey ());
		//this.addPanel (result);
		this.dynamicPanel.add (result, result.key.toString ());
		return result;
	}
	/**
	 * Construct a new {@code AnyTypeFieldListEditor} component to edit a field of type list.  The component is added to the card layout panel.
	 * @param <F> The type of the elements in the list.
	 * @param <SF>
	 * @param fieldName
	 * @param getFunc
	 * @param setFunc
	 * @param listSizeFunc
	 * @param listElementFunc
	 * @param defaultValue
	 * @return 
	 */
	public <F, SF> AnyTypeFieldListEditor<F, SF> newAnyTypeFieldListEditor (
		String fieldName,
		Object[] getFunc,
		Object[] setFunc,
		Object[] listSizeFunc,
		Object[] listElementFunc,
		SF defaultValue
		)
	{
		AnyTypeFieldListEditor<F, SF> result = new AnyTypeFieldListEditor<> (
			fieldName,
			this,
			this.keyGenerator.nextKey (),
			getFunc,
			setFunc,
			listSizeFunc,
			listElementFunc,
			defaultValue);
		this.dynamicPanel.add (result, result.key.toString ());
		return result;
	}
	/**
	 * Shows the panel with the given key.
	 */
	void showPanel (Key key, NavigateAction action)
	{
		CardLayout cl = (CardLayout) (this.dynamicPanel.getLayout ());
		this.navigateActions.add (0, action);
		this.buttonsPanel.setVisible (true);
		//System.out.println ("showPanel. panel = " + key + " navigateActions = " + this.navigateActions.toString ()); //DEBUG
		cl.show (this.dynamicPanel, key.toString ());
	}
	/**
	 * Adds the given panel to the set of panels that can be shown by pressing one of the buttons in the tool bar.
	 * @param panel 
	 */
	void addPanel (UIPanel<D> panel)
	{
		this.dynamicPanel.add (panel, panel.key.toString ());
	}
	/**
	 * Checks if there is any {@code UIPanel} being shown in {@code dynamicPanel} and if the user wants to discard any data.
	 * 
	 * @return True there is no {@code UIPanel} being shown or if the user discarded any data.
	 */
	private boolean checkShownPanels ()
	{
		if (this.navigateActions.isEmpty ()) {
			return true;
		}
		else {
			int option = JOptionPane.showConfirmDialog (
				this,
				"Are you sure you want to loose any changes made to the data?",
				"Loose changes",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE
			);
			if (option == JOptionPane.OK_OPTION) {
				this.navigateActions.clear ();
				return true;
			}
			else {
				return false;
			}
		}
	}
	public UIFrame handle_submenu_begin (JLabel label)
	{
		this.toolBar.addSeparator ();
		this.toolBar.add (label);
		return this;
	}
	public UIFrame<D> handle_submenu_end ()
	{
		this.toolBar.addSeparator ();
		return this;
	}
	/**
	 * Handles constructor {@code updateData(func(D)=D)}.  When the user presses the button, the data is updated with the given function.
	 */
	public UIFrame<D> handle_updateData (JButton button, final Object[] setFunc)
	{
		//System.out.println ("UIFrame.handle_updateData");
		ActionListener updateData = new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent evt)
			{
				if (UIFrame.this.checkShownPanels ())	{
					jmercury.runtime.MethodPtr2 funcMeth = ((jmercury.runtime.MethodPtr2) setFunc[1]);
					data.setValue ((D) funcMeth.call___0_0 (setFunc, data.getValue ()));
				}
			}
		};
		button.addActionListener (updateData);
		this.toolBar.add (button);
		return this;
	}
	/**
	 * Handles constructor {@code updateDataIO(pred(D,D,io.state,io.state))}.  When the user presses the button, the data is updated with the given predicate.  The predicate besides receiving the previous value of the data, also receives the I/O state.
	 */
	public UIFrame<D> handle_updateDataIO (JButton button, final Object[] predIO)
	{
		ActionListener updateDataIO = new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent evt)
			{
				if (UIFrame.this.checkShownPanels ())	{
					jmercury.runtime.MethodPtr3 funcMeth = ((jmercury.runtime.MethodPtr3) predIO [1]);
					java.lang.Object[] result;
					/** XXX: Hack to pass the IO state */
					result = (java.lang.Object[]) (funcMeth.call___0_0 (predIO, UIFrame.this.data.getValue (), null));
					UIFrame.this.data.setValue ((D) result [0]);
				}
			}
		};
		button.addActionListener (updateDataIO);
		this.toolBar.add (button);
		return this;
	}
	/**
	 * Handles constructor {@code actionDataIO(pred(D, io.state, io.state))}.  When the user presses the button, the given predicate is run.  The predicate receives the data and the IO state.
	 *
	 * <p><b>There is a hack to pass the IO state.</b>
	 */
	public UIFrame handle_actionDataIO (JButton button, final Object[] predIO)
	{
		ActionListener action = new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent evt)
			{
				jmercury.runtime.MethodPtr3 funcMeth = ((jmercury.runtime.MethodPtr3) predIO[1]);
				/** XXX: Hack to pass the IO state */
				funcMeth.call___0_0 (predIO, UIFrame.this.data.getValue (), null);
			}
		};
		button.addActionListener (action);
		this.toolBar.add (button);
		return this;
	}
	/**
	 * Handles constructor {@code actionIO(pred(io.state, io.state))}.  When the users presses the button, the given predicate is run.
	 */
	public UIFrame handle_actionIO (JButton button, final Object[] predIO)
	{
		ActionListener action = new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent evt)
			{
				jmercury.runtime.MethodPtr2 funcMeth = ((jmercury.runtime.MethodPtr2) predIO[1]);
				java.lang.Object result;
				/** XXX: Hack to pass the IO state */
				result = (funcMeth.call___0_0 (predIO, null));
			}
		};
		button.addActionListener (action);
		this.toolBar.add (button);
		return this;
	}
	public UIFrame<D> handle_edit (JButton button, final UIPanel<D> panel)
	{
		//System.out.println ("UIFrame.handle_edit");
		ActionListener action = new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent evt)
			{
				if (UIFrame.this.checkShownPanels ())	{
					//System.out.println ("Setting panel " + panel.key + " data to " + UIFrame.this.data.value); //DEBUG
					panel.setData (UIFrame.this.data.getValue ());
					NavigateAction action;
					action = new NavigateAction (KeyGenerator.EMPTY) {
						@Override
						public boolean perform ()
						{
							return
								panel.commitValue ()
								&& UIFrame.this.data.setValue (panel.data.getValue ());
						}
					};
					UIFrame.this.showPanel (panel.key, action);
				}
			}
		};
		button.addActionListener (action);
		this.toolBar.add (button);
		return this;
	}
	public <F> UIFrame<D> handle_edit (JButton button, final Object[] getFunc, final Object[] setFunc, final UIPanel<F> panel)
	{
		//System.out.println ("UIFrame.handle_edit");
		ActionListener action = new ActionListener () {
			final GetFieldFunc<D, F> getFieldFunc = new GetFieldFunc<> (getFunc);
			final SetFieldFunc<D, F> setFieldFunc = new SetFieldFunc<> (setFunc);
			@Override
			public void actionPerformed (ActionEvent evt)
			{
				if (UIFrame.this.checkShownPanels ())	{
					//System.out.println ("Setting panel " + panel.key + " data to " + UIFrame.this.data.value); //DEBUG
				
					panel.setData (UIFrame.this.data.applyGetFieldFunc (getFieldFunc));
				
					NavigateAction action;
					action = new NavigateAction (KeyGenerator.EMPTY) {
						@Override
						public boolean perform ()
						{
							return
								panel.commitValue ()
								&& UIFrame.this.data.applySetFieldFunc (setFieldFunc, panel.data.getValue ());
						}
					};
					UIFrame.this.showPanel (panel.key, action);
				}
			}
		};
		button.addActionListener (action);
		this.toolBar.add (button);
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

      javax.swing.JPanel statusPanel = new javax.swing.JPanel();
      buttonsPanel = new javax.swing.JPanel();
      okButton = new javax.swing.JButton();
      javax.swing.JButton cancelButton = new javax.swing.JButton();
      messagesLabel = new javax.swing.JLabel();
      toolBar = new javax.swing.JToolBar();
      dynamicPanel = new javax.swing.JPanel();
      emptyLabel = new javax.swing.JLabel();

      setLayout(new java.awt.BorderLayout());

      statusPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
      statusPanel.setLayout(new java.awt.BorderLayout());

      okButton.setText("OK");
      okButton.addActionListener(new java.awt.event.ActionListener()
      {
         public void actionPerformed(java.awt.event.ActionEvent evt)
         {
            okButtonActionPerformed(evt);
         }
      });
      buttonsPanel.add(okButton);

      cancelButton.setText("Cancel");
      cancelButton.addActionListener(new java.awt.event.ActionListener()
      {
         public void actionPerformed(java.awt.event.ActionEvent evt)
         {
            cancelButtonActionPerformed(evt);
         }
      });
      buttonsPanel.add(cancelButton);

      statusPanel.add(buttonsPanel, java.awt.BorderLayout.NORTH);

      messagesLabel.setText(" ");
      statusPanel.add(messagesLabel, java.awt.BorderLayout.CENTER);

      add(statusPanel, java.awt.BorderLayout.SOUTH);
      add(toolBar, java.awt.BorderLayout.NORTH);

      dynamicPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
      dynamicPanel.addComponentListener(new java.awt.event.ComponentAdapter()
      {
         public void componentResized(java.awt.event.ComponentEvent evt)
         {
            dynamicPanelComponentResized(evt);
         }
      });
      dynamicPanel.setLayout(new java.awt.CardLayout());
      dynamicPanel.add(emptyLabel, "EMPTY");

      add(dynamicPanel, java.awt.BorderLayout.CENTER);
   }// </editor-fold>//GEN-END:initComponents

   private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
		NavigateAction action = this.navigateActions.get (0);
		//System.out.println (this.navigateActions); //DEBUG
		if (action.perform ()) {
			this.navigateActions.remove (0);
			this.buttonsPanel.setVisible (!this.navigateActions.isEmpty ());
			CardLayout cl = (CardLayout) (this.dynamicPanel.getLayout ());
			cl.show (this.dynamicPanel, action.key.toString ());
		}
   }//GEN-LAST:event_okButtonActionPerformed

   private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
		NavigateAction action = this.navigateActions.remove (0);
		this.buttonsPanel.setVisible (!this.navigateActions.isEmpty ());
		CardLayout cl = (CardLayout) (this.dynamicPanel.getLayout ());
		cl.show (this.dynamicPanel, action.key.toString ());
		this.messagesLabel.setText (" ");
   }//GEN-LAST:event_cancelButtonActionPerformed

   private void dynamicPanelComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_dynamicPanelComponentResized
		//System.out.println ("New size of dynamic panel: " + this.dynamicPanel.getSize ());
   }//GEN-LAST:event_dynamicPanelComponentResized


   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JPanel buttonsPanel;
   private javax.swing.JPanel dynamicPanel;
   private javax.swing.JLabel emptyLabel;
   javax.swing.JLabel messagesLabel;
   javax.swing.JButton okButton;
   private javax.swing.JToolBar toolBar;
   // End of variables declaration//GEN-END:variables

	@Override
	public void windowOpened (WindowEvent e) {
	}

	@Override
	public void windowClosing (WindowEvent e)
	{
	}

	@Override
	public void windowClosed (WindowEvent e) {
	}

	@Override
	public void windowIconified (WindowEvent e) {
	}

	@Override
	public void windowDeiconified (WindowEvent e)
	{
	}

	@Override
	public void windowActivated (WindowEvent e)
	{
	}

	@Override
	public void windowDeactivated (WindowEvent e)
	{
	}

}
