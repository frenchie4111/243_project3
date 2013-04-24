import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;


public class BoardPanel extends JPanel implements Observer{
	
	RushHour model;
	
	HashMap<Integer, CarComponent> carButtons;
	
	public static int sizePerBlockH;
	public static int sizePerBlockW;
	
	// TEMPORARY
	public BoardPanel( ) {
		super();
		setSize(480,480);
		setPreferredSize( new Dimension(480, 480) );
		setBackground( Color.black );
		setLayout(null);
		
		carButtons = new HashMap<Integer, CarComponent>();
	}
	
	public BoardPanel( RushHour model ) {
		this();
		this.model = model;
		model.addObserver(this);

		sizePerBlockH = (480 / model.getHeight());
		sizePerBlockW = (480 / model.getWidth());
		
		loadCars();
		addListeners();
	}
	
	private void addCar( CarComponent car ) {
		add( car );
	}
	
	private void addCar( RushHourCar car ) {	
		boolean horiz = car.isHorizontal();
		
		Rectangle dim = computeDimensions(car);
		
		CarComponent new_car = new CarComponent( car.getCarnum(), horiz, dim.x, dim.y, dim.width, dim.height, new Color((int)(Math.random() * 0xFFFFFF)) );
		carButtons.put( car.getCarnum() ,  new_car );
		addCar( new_car );
	}
	
	private Rectangle computeDimensions( RushHourCar car ) {
		Rectangle new_rect = new Rectangle();
		int new_car_x = car.getX1() * sizePerBlockW ;
		int new_car_y = car.getY1() * sizePerBlockH ;
		int new_car_w = sizePerBlockW;
		int new_car_h = sizePerBlockH;
		if( car.isHorizontal() ) {
			new_car_w *= car.getBlocks().size();
			System.out.println(car.getBlocks().size());
		} else {
			new_car_h *= car.getBlocks().size();
		}
		new_rect.x = new_car_x;
		new_rect.y = new_car_y;
		new_rect.width = new_car_w;
		new_rect.height = new_car_h;
		return new_rect;
	}
	
	private void addListeners() {
		ArrayList< Integer > keys = new ArrayList<Integer>( carButtons.keySet() );
		for( Integer key : keys ) {
			carButtons.get(key).addMouseListener(new endDragListener(key));
		}
	}
	
	private class endDragListener extends MouseInputAdapter {
		
		private int key;
		
		public endDragListener( int key ) {
			super();
			this.key = key;
		}
		
		@Override
        public void mouseReleased( MouseEvent e ) {
        	System.out.println("IN PANEL Mouse Released");
        	BoardPanel board = ((BoardPanel) e.getComponent().getParent());
        	board.moveCar(key);
        }
	}
	
	public void moveCar( int key ) {
		System.out.println( carButtons.get(key).getCarnum() );
		int thisx = carButtons.get(key).getLocation().x;
		int thisy = carButtons.get(key).getLocation().y;
		int newxcoord = Math.round( (float)thisx / (float)BoardPanel.sizePerBlockW );
		int newycoord = Math.round( (float)thisy / (float)BoardPanel.sizePerBlockH );
		
		model.move( key, newxcoord, newycoord );
	}
	
	public void loadCars() {
		for( RushHourCar car : model.getCars() ) {
			addCar( car );
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		ArrayList< RushHourCar > cars = model.getCars();
		for( RushHourCar c : cars ) {
			CarComponent currentButton = this.carButtons.get(c.getCarnum());
			currentButton.setDimensions( computeDimensions( c ) );
		}
	}
}