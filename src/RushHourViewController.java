import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComponent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class RushHourViewController extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	
	RushHour model;
	
	public static final int MARGIN_SIZE = 10;
	
	JMenuItem resetGame;
	JMenuItem cheat;
	JLabel status;

	
	public RushHourViewController( RushHour model ) {
		this.model = model;
		model.addObserver(this);
		setup();
	}
	
	private void setup() {
		
		setSize(500,700);
		setLocation(100, 100);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		menuBar.setSize( 640, 20 );
		menuBar.setLocation(new Point(0,0));
		
		cheat = new JMenuItem( "Cheat" );
		cheat.setPreferredSize(new Dimension(100, 20));
		resetGame = new JMenuItem( "Restart Game" );
		
		menuBar.add(cheat);
		menuBar.add(resetGame);
		
		add(menuBar);
		
		JPanel borderPanel = new JPanel();
		borderPanel.setLayout(null);
		borderPanel.setSize(500,500);
		borderPanel.setLocation(0, 20);
		borderPanel.setBackground(Color.gray);
		
		BoardPanel boardPanel = new BoardPanel( model );
		boardPanel.setLocation(10, 10);
		
		borderPanel.add( boardPanel );
		
		boardPanel.addBorderBreak(borderPanel);
		
		add( borderPanel );
		
		JPanel scorePanel = new JPanel();

		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.X_AXIS));
		
		scorePanel.setLocation(0, 520);
		scorePanel.setSize(new Dimension( this.getSize().width, 130 ));
		
		status = new JLabel("", JLabel.CENTER);
		status.setPreferredSize(new Dimension( 200, 130 ));
		status.setFont(new Font("Arial", Font.PLAIN, 30));
		status.setBackground( Color.red );
		status.setOpaque(true);
		
		JPanel directionsPanel = new JPanel();
		directionsPanel.setLayout(new BoxLayout(directionsPanel, BoxLayout.Y_AXIS));
		
		JLabel title = new JLabel();
		title.setVerticalAlignment(JLabel.TOP);
		title.setFont(new Font("Arial", Font.PLAIN, 30));
		title.setText(" Jam Game");
		
		JLabel directions = new JLabel();
		directions.setFont(new Font("Arial", Font.PLAIN, 15));
		directions.setText("<html>Try to get the red car to the far <br/>right of the board by dragging the cars around</html>");
		
		directionsPanel.add(title);
		directionsPanel.add(directions);
		
		scorePanel.add(status);
		scorePanel.add(directionsPanel);
		add( scorePanel );
		
		
		addListeners();
		setVisible(true);
		update();
	}
	
	public void addListeners() {
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
		OutputStream noop = new OutputStream() {
			@Override
			public void write(int arg0) throws IOException {
				// NOOP
			}
		};
		System.setOut(new PrintStream(noop));
		
		RushHour myGame = RushHour.loadFile("test5.txt");		
		new RushHourViewController( myGame );
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		update();
	}
	
	public void update() {
		status.setText( "<html><center>" + 
						(model.isGoal()?"You Have Won!":"Please Make A Move") +
						"<br/>Moves: " + Integer.toString( model.getMoves() ) +
						"</center></html>" );
		status.setBackground( model.isGoal()?Color.green:Color.red );
	}
}