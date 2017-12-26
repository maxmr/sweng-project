import javax.swing.JOptionPane;


public class Scale {
	
	String Weight;
	
	public Scale(){
	Weight = "0.0";	
	}
	
	public void scaleLuggage() {
		Weight = JOptionPane.showInputDialog("in kg:");
	}
	
	public String getWeight() {
		if (Weight == null) {
			Weight ="";
		}
		  try{
			    double d = Double.parseDouble(Weight);			    
			  }
			  catch(NumberFormatException e2){Weight=""; }
		return Weight;
	}

}
