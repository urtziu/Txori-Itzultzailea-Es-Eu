package org.eclipse.wb.swt;

public class TxioakStr {
	private String erabiltzailea;
	private String hizkuntza;
	private String txioa;
	private String kodea;
	
	//CONSTRUCTOR
	public TxioakStr(String erabiltzailea, String hizkuntza, String txioa){
		this.erabiltzailea = erabiltzailea;
		this.hizkuntza = hizkuntza;
		this.txioa = txioa;
	}
	public TxioakStr (String kodea, String txioa){
		this.kodea =kodea;
		this.txioa = txioa;
		
	}
	public TxioakStr(){
		
	}
	
	public String getTxioa(){return txioa;};
	public String getHizkuntza(){return hizkuntza;};
	public String getErabiltzailea(){return erabiltzailea;};
	public String getKodea(){return kodea;};
	
	public void setTxioa(String txioa){this.txioa = txioa;};
	public void setHizkuntza(String hizkuntza){this.hizkuntza = hizkuntza;};
	public void setErabiltzailea(String erabiltzailea){this.erabiltzailea = erabiltzailea;};
	public void setKodea(String kodea){this.kodea = kodea;};
	
}
