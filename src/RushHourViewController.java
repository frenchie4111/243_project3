import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComponent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class RushHourViewController extends JFrame {

	private static final long serialVersionUID = 1L;
	
	final RushHour model;
	
	JMenuItem openFile;
	JMenuItem resetGame;
	JMenuItem cheat;

	
	public RushHourViewController( RushHour model ) {
		this.model = model;
		showSplashScreen();
		setup();
	}
	
	private void showSplashScreen() {
		
	}
	
	private void setup() {
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		menuBar.setSize( 640, 20 );
		menuBar.setLocation(new Point(0,0));
		
		openFile = new JMenuItem( "Open File" );
		openFile.setPreferredSize(new Dimension(100, 20));
		cheat = new JMenuItem( "Cheat" );
		cheat.setPreferredSize(new Dimension(100, 20));
		resetGame = new JMenuItem( "Restart Game" );
		
		menuBar.add(openFile);
		menuBar.add(cheat);
		menuBar.add(resetGame);
		
		add(menuBar);
		
		setSize(640,520);
		setLocation(100, 100);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setLayout(null);
		
		BoardPanel boardPanel = new BoardPanel( model );
		boardPanel.setLocation(0, 20);
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
		addListeners();
		setVisible(true);
	}
	
	public void addListeners() {
		openFile.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Open File Pressed");
			}
		});
		cheat.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Cheat Pressed");
				model.cheat();
			}
		});
		resetGame.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Reset Game");
				model.reset();
			}
		});
	}
	
	public static void main( String args[] ) {
		
		RushHour myGame = RushHour.loadFile("test2.txt");		
		new RushHourViewController( myGame );
	}
}