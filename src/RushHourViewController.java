import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComponent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class RushHourViewController extends JFrame {

	private static final long serialVersionUID = 1L;
	
	final RushHour model;
	
	public RushHourViewController( RushHour model ) {
		this.model = model;
		showSplashScreen();
		setup();
	}
	
	private void showSplashScreen() {
		
	}
	
	private void setup() {
		setSize(640,520);
		setLocation(100, 100);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setLayout(null);
		
		BoardPanel boardPanel = new BoardPanel( model );
		boardPanel.setLocation(0, 0);
		add( boardPanel );
		
		JButton button = new JButton("Print Board");
		button.setLocation( 480, 430 );
		button.setSize(new Dimension( 160, 50 ));
		add( button );
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.printConfig();				
			}
		});
		
		setVisible(true);
	}
	
	public static void main( String args[] ) {
		
		RushHour myGame = RushHour.loadFile("test3.txt");		
		new RushHourViewController( myGame );
	}
}