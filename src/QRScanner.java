import javax.swing.*;


public class QRScanner {
	
	String QRCode;
	
	public QRScanner(){
	QRCode = "abcdefg";	
	}
	
	public void ScanQRCode() {
		QRCode = JOptionPane.showInputDialog("QR-Code:");
	}
	
	public String getQRCode() {
		return QRCode;
	}

}