import javax.swing.*;


public class QRScanner {
	
	String QRCode;
	
	public QRScanner(){
	QRCode = "";	
	}
	
	public void ScanQRCode() {
		QRCode = JOptionPane.showInputDialog("QR-Code:");
	}
	
	public String getQRCode() {
		if(QRCode == null) {
			QRCode = "";
		}
		return QRCode;
	}

}
