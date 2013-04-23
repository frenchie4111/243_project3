import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;


public class BoardPanel extends JPanel implements Observer{
	
	RushHour model;
	
	public static int sizePerBlockH;
	public static int sizePerBlockW;
	
	// TEMPORARY
	public BoardPanel( ) {
		super();
		setSize(480,480);
		setPreferredSize( new Dimension(480, 480) );
		setBackground( Color.black );
		setLayout(null);
	}
	
	public BoardPanel( RushHour model ) {
		this();
		this.model = model;

		sizePerBlockH = (480 / model.getHeight());
		sizePerBlockW = (480 / model.getWidth());
		
		loadCars();
	}
	
	private void addCar( CarComponent car ) {
		add( car );
	}
	
	private void addCar( RushHourCar car ) {
		CarComponent new_car = new CarComponent( car, model.getWidth(), model.getHeight(), new Color((int)(Math.random() * 0xFFFFFF)) );
		addCar( new_car );
	}
	
	public void loadCars() {
		for( RushHourCar car : model.getCars() ) {
			addCar( car );
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
