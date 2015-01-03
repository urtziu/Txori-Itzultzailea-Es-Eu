

public class TxioakStr {
	private String erabiltzailea;
	private String hizkuntza;
	private String txioa;
	private int kodea;
	
	//CONSTRUCTOR
	public TxioakStr(String erabiltzailea, String hizkuntza, String txioa){
		this.erabiltzailea = erabiltzailea;
		this.hizkuntza = hizkuntza;
		this.txioa = txioa;
	}
	public TxioakStr (int kodea, String txioa){
		this.kodea =kodea;
		this.txioa = txioa;
		
	}
	
	public String getTxioa(){return txioa;};
	public String getHizkuntza(){return hizkuntza;};
	public String getErabiltzailea(){return erabiltzailea;};
	public int getKodea(){return kodea;};
	
	public void setTxioa(String txioa){this.txioa = txioa;};
	public void setHizkuntza(String hizkuntza){this.hizkuntza = hizkuntza;};
	public void setErabiltzailea(String erabiltzailea){this.erabiltzailea = erabiltzailea;};
	public void setKodea(int kodea){this.kodea = kodea;};
	
}
