import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;


public class Menua extends Shell {
	private static class ContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			return new Object[0];
		}
		public void dispose() {
		}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Menua shell = new Menua(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public Menua(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Sarrera.main(null);
			}
		});
		btnNewButton.setBounds(43, 213, 331, 48);
		btnNewButton.setText("Bilaketa bidez");
		
		Button btnNewButton_1 = new Button(this, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Fitxategiak.main(null);
			}
		});
		btnNewButton_1.setBounds(43, 291, 331, 48);
		btnNewButton_1.setText("Fitxategien bidez");
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("FangSong", 25, SWT.BOLD));
		lblNewLabel.setBounds(56, 10, 331, 31);
		lblNewLabel.setText("Txio Itzultzailea");
		
		Label lblNondikNoraItzuli = new Label(this, SWT.NONE);
		lblNondikNoraItzuli.setBounds(31, 88, 112, 15);
		lblNondikNoraItzuli.setText("Nondik nora itzuli:");
		
		ComboViewer comboViewer = new ComboViewer(this, SWT.NONE);
		Combo combo = comboViewer.getCombo();
		combo.setItems(new String[] {"Gaztelaniatik", "Euskaratik", "Ingeleratik"});
		combo.setBounds(52, 122, 112, 23);
		combo.select(0);
		
		ComboViewer comboViewer_1 = new ComboViewer(this, SWT.NONE);
		Combo combo_1 = comboViewer_1.getCombo();
		combo_1.setItems(new String[] {"Euskarara", "Gaztelaniara", "Ingelerara"});
		combo_1.setBounds(214, 122, 91, 23);
		combo_1.select(0);
		
		Label label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(31, 47, 376, 2);
		comboViewer.setContentProvider(new ContentProvider());
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Txori Itzultzailea");
		setSize(450, 419);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
