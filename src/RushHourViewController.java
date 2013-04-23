import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class RushHourViewController extends JFrame implements Observer{

	private static final long serialVersionUID = 1L;
	
	RushHour model;
	
	public RushHourViewController( RushHour model ) {
		this.model = model;
		showSplashScreen();
		setup();
	}
	
	private void showSplashScreen() {
		
	}
	
	private void setup() {
		setSize(640,480);
		setLocation(100, 100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setLayout( new FlowLayout() );
		
		JPanel gamePanel = new JPanel( new GridLayout( model.getWidth(), model.getHeight() ) );
		gamePanel.setSize(480,480);
		gamePanel.setBackground(Color.BLACK);
		add( gamePanel );
		
		setVisible(true);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main( String args[] ) {
		new RushHourViewController( new RushHour(3,3,0,0) );
	}
}