import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.User;

import org.eclipse.swt.widgets.List;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class TxoriItzultzailea {

	protected Shell shell;
	private Text t_originala;
	private Text t_hizkuntza;
	private static User erab=null;
	private static Status est=null;
	private Process process;
	private String tratatua;
	private Text t_itzulia;
	private String itzulitakoa;
	public String ItzuliTest = tratatua;
	//Integer indizeaj;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TxoriItzultzailea window = new TxoriItzultzailea();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void hasi(java.util.List<Status> Estaduak, Integer indizea) {
		try {
			TxoriItzultzailea window = new TxoriItzultzailea();
			erab = Estaduak.get(indizea).getUser();
			est = Estaduak.get(indizea);
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
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setToolTipText("");
		shell.setSize(552, 515);
		shell.setText("Txori Itzultzailea: Itzuli txio bat");
		
		Label lblTestuOriginala = new Label(shell, SWT.NONE);
		lblTestuOriginala.setBounds(33, 24, 94, 15);
		lblTestuOriginala.setText("Testu originala:");
		
		
		
		t_originala = new Text(shell, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		t_originala.setBounds(33, 45, 459, 51);
		

		if (est.isRetweet()){
			t_originala.setText(est.getRetweetedStatus().getText());
			
		}else{
			t_originala.setText(est.getText());
		}
		
		
		Label lblHizkuntza = new Label(shell, SWT.NONE);
		lblHizkuntza.setBounds(33, 102, 138, 15);
		lblHizkuntza.setText("Aurreikusitako hizkuntza:");
		
		t_hizkuntza = new Text(shell, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP);
		t_hizkuntza.setBounds(188, 102, 304, 43);
		
		t_itzulia = new Text(shell, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		t_itzulia.setBounds(33, 336, 459, 100);
		
		Label lblHashta = new Label(shell, SWT.NONE);
		lblHashta.setBounds(33, 155, 55, 15);
		lblHashta.setText("Hashtag:");

		final Label lb_itzultzen = new Label(shell, SWT.NONE);
		lb_itzultzen.setBounds(10, 442, 120, 25);
		lb_itzultzen.setText("");
		
		final Button btnHitzezHitzItzuli = new Button(shell, SWT.NONE);
		btnHitzezHitzItzuli.setToolTipText("Hitzez hitz itzuliko du txioa normalizatu ondoren");
		btnHitzezHitzItzuli.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//HITZEZ HITZEKO ITZULPENA!
				
				t_itzulia.setText("");
				//String testua = Laguntzaileak.TestuOna;
				String normalizatua = Laguntzaileak.proposamenak(Laguntzaileak.TestuOna);
				
				String itzulitakoa2="";
				
				String[] hitzak = normalizatua.split("\\s+|,\\s*|\\.\\s*");
				int tamaina=hitzak.length;
				lb_itzultzen.setText("Itzultzen..."+tamaina);
				for (String ss: hitzak){
					if (ss.matches("")){
						
					}else{
						ItzuliTest= ss;
						byte[] utf8BytesStream;
						try {
							utf8BytesStream = ItzuliTest.getBytes("UTF8");
							String KodetuTest = new String(utf8BytesStream, "ISO-8859-1");
							

							
							process = Runtime.getRuntime().exec("perl .\\bin\\matxinEsEu.pl \""+KodetuTest.toString() +"\"");
							process.waitFor();
							if(process.exitValue() == 0)
							{
								//System.out.println("Command Successful");
								try {
									BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
									String line = null;
									while ((line = in.readLine()) != null) {
										//System.out.println(line);
										
										
										itzulitakoa2 =itzulitakoa2+ " " +line;
									}
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								
							}
							else
							{
								//System.out.println("Command Failure");
								lb_itzultzen.setText("Arazo bat egon da Perl-ekin (komandoan)");
							}
							tamaina=tamaina-1;
							lb_itzultzen.setText("Itzultzen..."+tamaina);
							t_itzulia.setText(itzulitakoa2);
							//lb_itzultzen.setText("");
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} catch (InterruptedException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						
						
					}
				}
				
			}
		});
		btnHitzezHitzItzuli.setEnabled(false);
		btnHitzezHitzItzuli.setBounds(377, 288, 105, 43);
		btnHitzezHitzItzuli.setText("Hitzez hitz itzuli ");
		
		Label lblUrlEdoBestelako = new Label(shell, SWT.NONE);
		lblUrlEdoBestelako.setBounds(33, 211, 138, 15);
		lblUrlEdoBestelako.setText("URL edo bestelako Linkak:");
		
		List l_hashtag = new List(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		l_hashtag.setBounds(94, 155, 200, 50);
		
		ListViewer listViewer = new ListViewer(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		List l_linkak = listViewer.getList();
		l_linkak.setBounds(83, 232, 283, 51);
		
		final Button bt_itzuli = new Button(shell, SWT.NONE);
		
		final Button btnBai = new Button(shell, SWT.RADIO);
		btnBai.setEnabled(false);
		btnBai.setSelection(true);
		btnBai.setBounds(402, 235, 37, 16);
		btnBai.setText("Bai");
		
		final Button btnEz = new Button(shell, SWT.RADIO);
		btnEz.setEnabled(false);
		btnEz.setBounds(445, 235, 37, 16);
		btnEz.setText("Ez");
		
		//Button bt_itzuli = new Button(shell, SWT.NONE);
		bt_itzuli.setEnabled(false);
		bt_itzuli.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				t_itzulia.setText("");
				//NORMALIZATU TESTU FORMALA? BAI
				if(btnBai.getSelection()){
					//System.out.println("Testu hau itzuliko da1: "+Laguntzaileak.TestuOna);
					String normalizatua = Laguntzaileak.proposamenak(Laguntzaileak.TestuOna);
					try {
						lb_itzultzen.setText("Itzultzen...");
						//formalizatua= Testua.getTest();
						ItzuliTest=normalizatua;
						
						
						byte[] utf8BytesStream = ItzuliTest.getBytes("UTF8");
						String KodetuTest = new String(utf8BytesStream, "ISO-8859-1");
						
					
						
						process = Runtime.getRuntime().exec("perl .\\bin\\matxinEsEu.pl \""+KodetuTest.toString() +"\"");
						process.waitFor();
						if(process.exitValue() == 0)
						{
							//System.out.println("Command Successful");
							try {
								BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
								String line = null;
								while ((line = in.readLine()) != null) {
									//System.out.println(line);
									t_itzulia.setText(line);
									itzulitakoa =line;
								}
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							lb_itzultzen.setText("");
						}
						else
						{
							//System.out.println("Command Failure");
							lb_itzultzen.setText("Arazo bat egon da Perl-ekin (komandoan)");
						}
					}
					catch(Exception e1)
					{
						//System.out.println("Exception: "+ e1.toString());
					}
					
				}
				
				//NORMALIZATU TESTU FORMALA? EZ
				if(btnEz.getSelection()){

					try {
						lb_itzultzen.setText("Itzultzen...");
						//formalizatua= Testua.getTest();
						ItzuliTest=Laguntzaileak.TestuOna;
						//System.out.println("Testu hau itzuliko da: "+ItzuliTest);
						
						byte[] utf8BytesStream = ItzuliTest.getBytes("UTF-8");
						String KodetuTest = new String(utf8BytesStream, "ISO-8859-1");
						
				
						
						process = Runtime.getRuntime().exec("perl .\\bin\\matxinEsEu.pl \""+KodetuTest +"\"");
						process.waitFor();
						if(process.exitValue() == 0)
						{
							//System.out.println("Command Successful");
							try {
								BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
								String line = null;
								while ((line = in.readLine()) != null) {
									//System.out.println(line);
									t_itzulia.setText(line);
									itzulitakoa =line;
								}
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							lb_itzultzen.setText("");
						}
						else
						{
							//System.out.println("Command Failure");
							lb_itzultzen.setText("Arazo bat egon da Perl-ekin (komandoan)");
						}
					}
					catch(Exception e1)
					{
						//System.out.println("Exception: "+ e1.toString());
					}
				}
					
					
			}
		});
		bt_itzuli.setBounds(235, 289, 120, 41);
		bt_itzuli.setText("Itzuli testu Formala");
		
		Label lblItzulpena = new Label(shell, SWT.NONE);
		lblItzulpena.setBounds(33, 315, 70, 15);
		lblItzulpena.setText("ITZULPENA:");
		


		final Button btnItzuliTestuInformala = new Button(shell, SWT.NONE);
		btnItzuliTestuInformala.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String normalizatua = Laguntzaileak.proposamenak(Laguntzaileak.TestuOna);
				try {
					lb_itzultzen.setText("Itzultzen...");
					//formalizatua= Testua.getTest();
					ItzuliTest=normalizatua;
					//System.out.println("Testu hau itzuliko da: "+ItzuliTest);
					
					
					byte[] utf8BytesStream = ItzuliTest.getBytes("UTF8");
				
					String KodetuTest = new String(utf8BytesStream, "ISO-8859-1");

					process = Runtime.getRuntime().exec("perl .\\bin\\matxinEsEu.pl \""+KodetuTest +"\"");
					process.waitFor();
					if(process.exitValue() == 0)
					{
						//System.out.println("Command Successful");
						try {
							BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
							String line = null;
							while ((line = in.readLine()) != null) {
								//System.out.println(line);
								
								t_itzulia.setText(line);
								itzulitakoa=line;
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						lb_itzultzen.setText("");
					}
					else
					{
						//System.out.println("Command Failure");
						lb_itzultzen.setText("Arazo bat egon da Perl-ekin (komandoan)");
					}
				}
				catch(Exception e1)
				{
					//System.out.println("Exception: "+ e1.toString());
				}
				
					
				
			}
		});
		btnItzuliTestuInformala.setEnabled(false);
		btnItzuliTestuInformala.setBounds(109, 289, 120, 41);
		btnItzuliTestuInformala.setText("Itzuli testu Informala");
		
		
		final Label lb_formala = new Label(shell, SWT.NONE);
		lb_formala.setBounds(322, 197, 170, 15);
		lb_formala.setText("");
		
		
		
		if (est.getLang().toString().equals("es")){
			t_hizkuntza.setText("Gaztelaniaz dago testua.");
			//bt_itzuli.setEnabled(true);
			
		}else if(est.getLang().toString().equals("en")){
			t_hizkuntza.setText("Ingeleraz dago testua.");
		}else
		t_hizkuntza.setText("Ez da aplikazioak onartzen duen hizkuntza bat. Hizkuntza hau izan liteke: "+est.getLang().toString());

		final HashtagEntity[] hashtagak = est.getHashtagEntities();
		for (HashtagEntity i : hashtagak) {
			l_hashtag.add(i.getText());
	    }
		
		final URLEntity[] linkak = est.getURLEntities();
		for (URLEntity i : linkak) {
			l_linkak.add(i.getExpandedURL());
	    }
		
		final Label lb_fitx = new Label(shell, SWT.NONE);
		lb_fitx.setBounds(377, 451, 149, 15);
		
		//SymbolEntity[] emotikonoak = est.getSymbolEntities();
		
		Button bt_tratatu = new Button(shell, SWT.NONE);
		bt_tratatu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Laguntzaileak.RegexeFindText(est, "rt");
				tratatua=Laguntzaileak.TestuaTratatu(est);
				
				
				//Formala ala ez den begiratu
				//emaitza = Laguntzaileak.InfAlaFor(tratatua);
				//boolean formala=Testua.getBool();
				
				boolean formala =Laguntzaileak.InfAlaFor(tratatua);
				
				if (formala==true) {
					lb_formala.setText("Txoria formala da.");
					btnBai.setEnabled(true);
					btnEz.setEnabled(true);
					btnEz.setFocus();
					bt_itzuli.setEnabled(true);
				}else{
					lb_formala.setText("Txoria informala da.");
					btnItzuliTestuInformala.setEnabled(true);
					btnHitzezHitzItzuli.setEnabled(true);
					
					
				}
			}
		});
		bt_tratatu.setBounds(322, 155, 158, 36);
		bt_tratatu.setText("Testua Tratatu");
		
		Button bt_fitxGorde = new Button(shell, SWT.NONE);
		bt_fitxGorde.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				// Indicate that you are planning to use a file
				File fileExample = new File(".\\Fitxategiak\\EmaitzaIndibidualak.txt");
			        PrintStream idatzi = null;
			        FileOutputStream fos = null;
			        try {
						fos = new FileOutputStream(fileExample, true);
					} catch (FileNotFoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					idatzi = new PrintStream(fos);
			       
					if(fileExample.exists() && !fileExample.isDirectory()) { 
						
						idatzi.println("Originala: @"+erab.getScreenName().toString()+": "+est.getText().toString()+"\r \n");
						
						String hash ="";
						for (HashtagEntity i : hashtagak) {
							hash=hash+" #"+i.getText();
					    }
						String link ="";
						for (URLEntity i : linkak) {
							link=link+" "+i.getExpandedURL();
					    }
						String ret="";
						if (est.isRetweet()){
							ret=ret+" RT @"+est.getRetweetedStatus().getUser().getScreenName().toString() +": ";
						}
						
						idatzi.println("Itzulpena: @"+erab.getScreenName().toString()+": "+ret+" "+itzulitakoa+" "+hash+" "+link+"\r \n");
						idatzi.close();
						
						//System.out.println("Ondo gorde da fitxategian.");  
						lb_fitx.setText("Ondo gorde da fitxategian.");
					}

				
				
			    
			        
			    
			}
		});
		//writer.append("Originala: @"+erab.getScreenName().toString()+": "+est.getText().toString()+"\n");

		bt_fitxGorde.setBounds(143, 441, 216, 25);
		bt_fitxGorde.setText("Gorde fitxategi batean");
		
		
		
		Label lblNormalizatu = new Label(shell, SWT.NONE);
		lblNormalizatu.setBounds(402, 211, 90, 15);
		lblNormalizatu.setText("Normalizatu?");
		
		

		
		
	}
}
