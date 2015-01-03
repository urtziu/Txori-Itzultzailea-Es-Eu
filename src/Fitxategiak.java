import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.List;
import org.eclipse.wb.swt.TxioakStr;
import org.eclipse.jface.viewers.ListViewer;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;


public class Fitxategiak extends Shell {
	private Text t_fitxizena;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Fitxategiak shell = new Fitxategiak(display);
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
	public Fitxategiak(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		//TxioakStr[] csvTxioak= new TxioakStr[500];
		final ArrayList<TxioakStr> csvTxioak= new ArrayList<TxioakStr>();
		//final ArrayList<TxioakStr> csvTxioak2= new ArrayList<TxioakStr>();

		final Twitter twitter = new TwitterFactory().getInstance();
		//tokena
		twitter.setOAuthConsumer("l7oMMgZ9UPuPRiZ5Cw1ncg", "QwEH95p9SpApZUnW4xOdFVbuYdjaDBeuDDiJ7TAzGEk");//consumer key, comsumer secret
		twitter.setOAuthAccessToken(new AccessToken("95424083-X6DrhPTB9bsTeNY9zhBeO0hBj7GLuT4McKuE7ptqs","bGIiuRzFIF2eeMQghPQgZ2tIXwDmmU0fLiXmyfN0aSko4"));
		
		
		Label lblAukeratuFitxategiatxt = new Label(this, SWT.NONE);
		lblAukeratuFitxategiatxt.setBounds(10, 47, 222, 22);
		lblAukeratuFitxategiatxt.setText("Aukeratu fitxategia (.csv edo txio kodeak ):");
		
		
		final Label l_irakurtzen = new Label(this, SWT.NONE);
		l_irakurtzen.setBounds(224, 73, 181, 22);
		
		ListViewer listViewer = new ListViewer(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		final List l_scvTxioak = listViewer.getList();
		l_scvTxioak.setBounds(10, 183, 415, 152);
		createContents();
		
		Button btnAukeratu = new Button(this, SWT.NONE);
		btnAukeratu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser chooser = new JFileChooser();
		        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT eta CSV","txt","csv");
		        chooser.setFileFilter(filter);
		        int returnVal = chooser.showOpenDialog(null);
		        
		        try {
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                File file = chooser.getSelectedFile();
		                //System.out.println("You chose to open this file: " + file.getName());
		                
		                //KONPROBATU EA BENETAN .TXT EDO CSV DEN FITXATEGIA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		                t_fitxizena.setText(file.getName());
		                //t_fitxizena.setText(file.getPath());
		                if (file.getName().contains(".txt") ){
		                	
		                	l_scvTxioak.removeAll();
		            		l_irakurtzen.setText("");
		            		l_irakurtzen.setText("Irakurtzen...");
		                	
		                	String csvFile = file.getPath();
		            		
		            		BufferedReader br = null;
		            		String line = "";
		            		String cvsSplitBy = "\t";
		            		String[] txioak = null;
		            		
		            		
		            		try {
		            	 
		            			br = new BufferedReader(new FileReader(csvFile));
		            			while ((line = br.readLine()) != null) {
		            	 
		            			        // use comma as separator
		            				txioak = line.split(cvsSplitBy);
		            				
		            				TxioakStr proba = new TxioakStr();
		            				
		            				//System.out.println( proba.getErabiltzailea().toString());
		            				proba.setKodea(txioak[0]);
		            				proba.setTxioa(txioak[1]);
		            				
		            				
		            				//ID BIDEZ TXIOAK LORTU!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		            				
		            				try {
		            			        Status status = twitter.showStatus(Long.parseLong(txioak[0]));
		            			        
		            			        if (status == null) { // 
		            			        	l_scvTxioak.add("ARAZOA egon da txioa irakurtzerako garaian. Baliteke txioa jadanik ez egotea online.");
		            			            // don't know if needed - T4J docs are VERY BAD
		            			        } else {
		            			           // System.out.println("@" + status.getUser().getScreenName()+ " - " + status.getText());
		            			        	
		            			        	proba.setErabiltzailea(status.getUser().getScreenName());
		            			        	proba.setHizkuntza(status.getLang());
		            			        	csvTxioak.add(proba);
		            			        	//l_scvTxioak.add("Txioa=" + status.getText() +  " , erabiltzailea=" + status.getUser().getScreenName() );
		            			        	if (status.getLang().toString().equals("es")){
			            			        	l_scvTxioak.add(status.getLang().toString()+"_ @"+status.getUser().getScreenName()+": "+ status.getText());
	
		            			        	}else{
		            			        		//testua ez dago gaztelaniaz.
		            			        		//l_scvTxioak.add("ARAZOA "+status.getLang().toString());
		            			        	}
		            			        }
		            			    } catch (TwitterException e1) {
		            			    	
		            			    }
		            				
		            				
		            				
		            				
		            				//System.out.println("Txioa [hizkuntza= " + txioak[0]  + " , txioa=" + txioak[1] +  " , erabiltzailea=" + txioak[2] + "]");
		            				l_irakurtzen.setText("");
		            			}
		            	 
		            		} catch (FileNotFoundException e1) {
		            			e1.printStackTrace();
		            		} catch (IOException e1) {
		            			e1.printStackTrace();
		            		} finally {
		            			if (br != null) {
		            				try {
		            					br.close();
		            				} catch (IOException e1) {
		            					e1.printStackTrace();
		            				}
		            			}
		            		}
		            	 
		            		System.out.println("Done .txt irakurketa.");
		                	
		                	
		                }else if (file.getName().contains(".csv") ){
		                	// IRAKURRI CSV ETA PARSEATU!!!!!!!!!!!!!!!!!!!!!!!!!!!
		                	
		                	String csvFile = file.getPath();
		            		//String csvFile = "/Users/mkyong/Downloads/GeoIPCountryWhois.csv";
		            		BufferedReader br = null;
		            		String line = "";
		            		String cvsSplitBy = "\t";
		            		String[] txioak = null;
		            		l_scvTxioak.removeAll();
		            		
		            		try {
		            	 
		            			br = new BufferedReader(new FileReader(csvFile));
		            			while ((line = br.readLine()) != null) {
		            	 
		            			        // use comma as separator
		            				txioak = line.split(cvsSplitBy);
		            				
		            				TxioakStr proba = new TxioakStr();
		            				
		            				//System.out.println( proba.getErabiltzailea().toString());
		            				proba.setErabiltzailea(txioak[2]);
		            				proba.setHizkuntza(txioak[0]);
		            				proba.setTxioa(txioak[1]);
		            				
		            				csvTxioak.add(proba);
		            				//System.out.println("Txioa [hizkuntza= " + txioak[0]  + " , txioa=" + txioak[1] +  " , erabiltzailea=" + txioak[2] + "]");
		            				
		            				if (txioak[0].equals("es")){
		            					//l_scvTxioak.add("Txioa=" + txioak[1] +  " , erabiltzailea=" + txioak[2]+ ", hizkuntza" );
		            					l_scvTxioak.add(txioak[0]+ "_ @"+txioak[2]+": "+txioak[1]);
		            				}//else
		            					//l_scvTxioak.add("Beste hizkuntza bateko txioa.");
		            				
		            				//csvDenak[i]= txioak;
		            			}
		            	 
		            		} catch (FileNotFoundException e1) {
		            			e1.printStackTrace();
		            		} catch (IOException e1) {
		            			e1.printStackTrace();
		            		} finally {
		            			if (br != null) {
		            				try {
		            					br.close();
		            				} catch (IOException e1) {
		            					e1.printStackTrace();
		            				}
		            			}
		            		}
		            	 
		            		System.out.println("Done .csv irakurketa.");
		                	
		                	
		                	
		                }
		                
		            } 
		            else {
		                JOptionPane.showMessageDialog(null,
		                        "Fitxategi bat aukeratu behar duzu.", "Atzera itzultzen...",
		                        JOptionPane.WARNING_MESSAGE);
		            }
		        } catch (Exception iOException) {
		        }
			}
		});
		btnAukeratu.setBounds(274, 42, 151, 25);
		btnAukeratu.setText("Aukeratu");
		
		t_fitxizena = new Text(this, SWT.BORDER | SWT.WRAP | SWT.CENTER);
		t_fitxizena.setEnabled(false);
		t_fitxizena.setBounds(10, 113, 376, 21);
		
		final Label l_itzultzen = new Label(this, SWT.NONE);
		l_itzultzen.setBounds(292, 437, 113, 15);
		
		final Button btnNor = new Button(this, SWT.RADIO);
		btnNor.setEnabled(false);
		btnNor.setBounds(31, 364, 169, 16);
		btnNor.setText("Normalizatzaile arrunta");
		
		final Button btnHitz = new Button(this, SWT.RADIO);
		btnHitz.setEnabled(false);
		btnHitz.setBounds(275, 364, 89, 16);
		btnHitz.setText("Hitzez hitz");
		
	

		final Button btnNor2 = new Button(this, SWT.CHECK);
		btnNor2.setBounds(28, 386, 146, 16);
		btnNor2.setText("Normalizatzaile arrunta");
		
	
		
		Label lblAukeratutakoFitxategiarenIzena = new Label(this, SWT.NONE);
		lblAukeratutakoFitxategiarenIzena.setBounds(19, 92, 181, 15);
		lblAukeratutakoFitxategiarenIzena.setText("Aukeratutako fitxategiaren izena:");
		
		Label lblFitxategikoTxioak = new Label(this, SWT.NONE);
		lblFitxategikoTxioak.setBounds(20, 145, 101, 15);
		lblFitxategikoTxioak.setText("Fitxategiko txioak:");
		
		Button btnItzuliFitxategia = new Button(this, SWT.NONE);
		btnItzuliFitxategia.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				//String normalizatua = Laguntzaileak.proposamenak(Laguntzaileak.TestuOna);
				for(TxioakStr item: csvTxioak){
					
					//ITZULI ETA GORDE FITXATEGI BATEAN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					//String normalizatua = Laguntzaileak.proposamenak(item.toString());
					//NORMALIZATZAILEAK SARTZEA FALTA DA!!!!!!!!!!
					if(btnNor2.getSelection()){
						String normalizatua = item.getTxioa();
						
						String erabil = item.getErabiltzailea();
						
						String originala = item.getTxioa();
						
						normalizatua = Laguntzaileak.proposamenak(item.getTxioa().toString());
						
						String KodetuTest =null;
						byte[] utf8BytesStream;
						try {
							utf8BytesStream = normalizatua.getBytes("UTF-8");
							KodetuTest = new String(utf8BytesStream, "ISO-8859-1");
						} catch (UnsupportedEncodingException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						
					
						try {
							
							
							
							l_itzultzen.setText("Itzultzen...");
							//formalizatua= Testua.getTest();
							
							//System.out.println("Testu hau itzuliko da: "+ItzuliTest);
							Process process = Runtime.getRuntime().exec("perl .\\bin\\matxinEsEu.pl \""+KodetuTest +"\"");
							process.waitFor();
							if(process.exitValue() == 0)
							{
								//System.out.println("Command Successful");
								try {
									BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
									String line = null;
									while ((line = in.readLine()) != null) {
										//System.out.println(line);
										//t_itzulia.setText(line);
										String itzulitakoa = line.toString();
										//System.out.println(itzulitakoa);
										Laguntzaileak.fitx_idatzi2 (erabil, itzulitakoa,t_fitxizena.getText(), originala );
									}
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								
							}
							else
							{
								//System.out.println("Command Failure");
								l_itzultzen.setText("Arazo bat egon da Perl-ekin (komandoan)");
							}
						}
						catch(Exception e1)
						{
							//System.out.println("Exception: "+ e1.toString());
						}
						l_itzultzen.setText("");
					}else {
						String normalizatua = item.getTxioa();
						
						String erabil = item.getErabiltzailea();
						
						String originala = item.getTxioa();
						
						//normalizatua = Laguntzaileak.proposamenak(item.getTxioa().toString());
						
						String KodetuTest =null;
						byte[] utf8BytesStream;
						try {
							utf8BytesStream = normalizatua.getBytes("UTF-8");
							KodetuTest = new String(utf8BytesStream, "ISO-8859-1");
						} catch (UnsupportedEncodingException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						
					
						try {
							
							
							
							l_itzultzen.setText("Itzultzen...");
							//formalizatua= Testua.getTest();
							
							//System.out.println("Testu hau itzuliko da: "+ItzuliTest);
							Process process = Runtime.getRuntime().exec("perl .\\bin\\matxinEsEu.pl \""+KodetuTest +"\"");
							process.waitFor();
							if(process.exitValue() == 0)
							{
								//System.out.println("Command Successful");
								try {
									BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
									String line = null;
									while ((line = in.readLine()) != null) {
										//System.out.println(line);
										//t_itzulia.setText(line);
										String itzulitakoa = line.toString();
										//System.out.println(itzulitakoa);
										Laguntzaileak.fitx_idatzi2 (erabil, itzulitakoa,t_fitxizena.getText(), originala );
									}
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								
							}
							else
							{
								//System.out.println("Command Failure");
								l_itzultzen.setText("Arazo bat egon da Perl-ekin (komandoan)");
							}
						}
						catch(Exception e1)
						{
							//System.out.println("Exception: "+ e1.toString());
						}
						l_itzultzen.setText("");
					}
					
					
					
					
					
					}
			}
		});
		btnItzuliFitxategia.setBounds(96, 432, 190, 25);
		btnItzuliFitxategia.setText("Itzuli fitxategia");
		
		Label lblFormalizatutaItzuli = new Label(this, SWT.NONE);
		lblFormalizatutaItzuli.setBounds(34, 341, 140, 15);
		lblFormalizatutaItzuli.setText("Formalizatuta itzuli?");
		
		
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Txori Itzultzailea: Fitxategiak");
		setSize(451, 506);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
