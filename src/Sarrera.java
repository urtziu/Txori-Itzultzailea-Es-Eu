import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

import org.eclipse.swt.widgets.List;
import org.eclipse.jface.viewers.ListViewer;


public class Sarrera {

	protected Shell shlTxoriItzultzailea;
	private Text t_Bilaketa;
	private QueryResult BilaketaTestua;
	//private ArrayList<String> Emaitza_lista=new ArrayList<String>();
	private Text t_informazioa;
	 java.util.List<Status> Estaduak;
	 Integer indizea;
	 //private Process process;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			Sarrera window = new Sarrera();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlTxoriItzultzailea.open();
		shlTxoriItzultzailea.layout();
		while (!shlTxoriItzultzailea.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	

	
	protected void createContents() {
		shlTxoriItzultzailea = new Shell();
		shlTxoriItzultzailea.setSize(456, 562);
		shlTxoriItzultzailea.setText("Txori Itzultzailea: Bilaketak");
		
		t_Bilaketa = new Text(shlTxoriItzultzailea, SWT.BORDER);
		t_Bilaketa.setBounds(108, 7, 143, 21);
		
		Label lblBilatu = new Label(shlTxoriItzultzailea, SWT.NONE);
		lblBilatu.setBounds(26, 10, 76, 15);
		lblBilatu.setText("Bilatu twittak");
		
		Label lblEmaitzak = new Label(shlTxoriItzultzailea, SWT.NONE);
		lblEmaitzak.setBounds(26, 63, 55, 15);
		lblEmaitzak.setText("Emaitzak:");
		
		
		final Button btnPertsonak = new Button(shlTxoriItzultzailea, SWT.RADIO);
		
		btnPertsonak.setBounds(16, 34, 86, 16);
		btnPertsonak.setText("Pertsonalak");
		
		final Button btnHashtag = new Button(shlTxoriItzultzailea, SWT.RADIO);
		btnHashtag.setBounds(108, 34, 65, 16);
		btnHashtag.setText("Hashtag");
		
		final Button btnTestua = new Button(shlTxoriItzultzailea, SWT.RADIO);
		
		btnTestua.setSelection(true);
		btnTestua.setBounds(189, 34, 55, 16);
		btnTestua.setText("Testua");
		
		final Button btnAipatu = new Button(shlTxoriItzultzailea, SWT.RADIO);
		btnAipatu.setBounds(253, 34, 90, 16);
		btnAipatu.setText("Aipatu");
		

		//DENAK AUTOMATIKOKI EGITEKO BOTOIA
		final Button bt_aut = new Button(shlTxoriItzultzailea, SWT.NONE);
		bt_aut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				//TxoriItzultzailea.hasi(Estaduak,indizea);
				Integer indizea=0;
				for (Status status : BilaketaTestua.getTweets()) {
					//BANAKA BIDALI ESTATUAK TRATATZEKO
					indizea=indizea+1;
					Laguntzaileak.Automat  (status);
					
			    }
				
			}
		});
		bt_aut.setEnabled(false);
		bt_aut.setBounds(134, 475, 193, 29);
		bt_aut.setText("Txori denak itzuli automatikoki");
		
		
		
		ListViewer listViewer = new ListViewer(shlTxoriItzultzailea, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		final List l_Emaitzak2 = listViewer.getList();
		l_Emaitzak2.setBounds(26, 91, 398, 165);
		
		final Label l_Errorea = new Label(shlTxoriItzultzailea, SWT.WRAP);
		l_Errorea.setBounds(114, 56, 284, 29);
		
		final Button btn_Itzuli = new Button(shlTxoriItzultzailea, SWT.NONE);
		
		//BILAKETA BOTOIA-----------------------------------------
		Button btnBilatu = new Button(shlTxoriItzultzailea, SWT.NONE);
		btnBilatu.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					
					
					//Ezabatu lista, bilaketa berri bat egiteko.
					l_Emaitzak2.removeAll();
					l_Errorea.setText("");
					l_Errorea.setText("Itxaron...");
					//Begiratu ea hutsunea den bilatzen duguna, ala ez.
					if (t_Bilaketa.getText().isEmpty()){
						l_Errorea.setText("Idatzi bilatu nahi duzun hitza bere lekuan.");
					}else{
						//Bilaketa egin, funtzioari deia eginez.
						int aukerak=0;
						if (btnPertsonak.getSelection()){
							aukerak=1;
						}
						if(btnHashtag.getSelection()){
							aukerak=2;
						}
						if(btnTestua.getSelection()){
							aukerak=3;
						}
						if(btnAipatu.getSelection()){
							aukerak=4;
						}
						BilaketaTestua=Laguntzaileak.bilaketa(t_Bilaketa.getText(),aukerak);
						//Begiratu ea bilaketarik dagoen, ala ez den ezer aurkitu.
						if (BilaketaTestua.getTweets().isEmpty()){
							l_Errorea.setText("Ez dago emaitzarik " + t_Bilaketa.getText()+ " bilaketarako.");
						}
						 Estaduak = BilaketaTestua.getTweets();
						for (Status status : BilaketaTestua.getTweets()) {
							//Listara gehitu emaitzak.
							l_Emaitzak2.add("@" + status.getUser().getScreenName() + ":" + status.getText());
							l_Errorea.setText("");
					    }
						
						bt_aut.setEnabled(true);
					}
					
				} catch (TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		
		btnBilatu.setBounds(349, 27, 75, 25);
		btnBilatu.setText("Bilatu");
		
		
		l_Emaitzak2.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  
		        int[] selectedItems = l_Emaitzak2.getSelectionIndices();
		        String outString = "";
		        String erabiltzailea="";
		        for (int loopIndex = 0; loopIndex < selectedItems.length; loopIndex++){
		        	outString += selectedItems[loopIndex] + " ";
		        	indizea= selectedItems[loopIndex];
		        	//erabiltzailea += l_Emaitzak2.getItem(selectedItems[loopIndex]).toString();
		        	erabiltzailea = Laguntzaileak.informazioa(Estaduak,selectedItems[loopIndex]);
		        	
		        }
		        t_informazioa.setText(erabiltzailea);
		        //t_informazioa.setText("Selected Items: " + outString);
		        btn_Itzuli.setEnabled(true);
		        
		      }

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

		      /*public void widgetDefaultSelected(SelectionEvent event) {
		        int[] selectedItems = l_Emaitzak2.getSelectionIndices();
		        String outString = "";
		        for (int loopIndex = 0; loopIndex < selectedItems.length; loopIndex++)
		          outString += selectedItems[loopIndex] + " ";
		        System.out.println("Selected Items: " + outString);
		      }*/
		    });


		t_informazioa = new Text(shlTxoriItzultzailea, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		t_informazioa.setEditable(false);
		t_informazioa.setBounds(26, 299, 398, 170);
		
		Label lblInformazioa = new Label(shlTxoriItzultzailea, SWT.NONE);
		lblInformazioa.setBounds(26, 278, 76, 15);
		lblInformazioa.setText("Informazioa:");
		
		//Button btn_Itzuli = new Button(shlTxoriItzultzailea, SWT.NONE);
		btn_Itzuli.setEnabled(false);
		btn_Itzuli.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//System.out.println(indizea.toString());
				TxoriItzultzailea.hasi(Estaduak,indizea);
			}
		});
		btn_Itzuli.setBounds(141, 262, 163, 25);
		btn_Itzuli.setText("Txori baten itzulpena egin");
		
	

		
		
		
	
		

		


	}
}
