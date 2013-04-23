import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;


public class CarComponent extends JComponent implements Observer {
    protected Point anchorPoint;
    protected Cursor draggingCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    protected boolean overbearing = false;
    
	private boolean horizontal;
	
	private RushHourCar thisCar;
	private RushHour parentBoard;

    public CarComponent() {
        addDragListeners();
        setOpaque(true);
        setBackground(Color.red);
        setSize(300,300);
        setLocation( 0, 0 );

        horizontal = true;
    }
    
    public CarComponent( boolean horizontal, Color c ) {
    	this();
    	this.horizontal = horizontal;
    	setBackground( c );
    }
    
    public CarComponent( RushHourCar car, int width, int height, Color c ) {
    	this( car.isHorizontal(), c );
		int sizePerBlockW = ( 480 / width );
		int sizePerBlockH = ( 480 / height );
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
		System.out.printf("New car %d %d %d %d", new_car_x, new_car_y, new_car_w, new_car_h);
		setLocation( new_car_x, new_car_y );
		setSize( new_car_w, new_car_h );
    }
    

    private void addDragListeners() {        
        DragListener listener = new DragListener( this );
        addMouseMotionListener( listener );
        addMouseListener( listener );
    }
    
    private class DragListener extends MouseInputAdapter {
    	
    	CarComponent handle;
    	
    	public DragListener( CarComponent handle ) {
    		super();
    		this.handle = handle;
    	}
    	
    	@Override
        public void mouseMoved(MouseEvent e) {
            anchorPoint = e.getPoint();
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int anchorX = anchorPoint.x;
            int anchorY = anchorPoint.y;

            Point parentOnScreen = getParent().getLocationOnScreen();
            Point mouseOnScreen = e.getLocationOnScreen();
            Point position = new Point();
            if( horizontal ) {
                position = new Point(mouseOnScreen.x - parentOnScreen.x - 
						anchorX, getLocation().y);
            } else {
            	position = new Point(getLocation().x, mouseOnScreen.y - 
						parentOnScreen.y - anchorY);
            }
            setLocation(position);

            if (overbearing) {
                getParent().setComponentZOrder(handle, 0);
                repaint();
            }
        }
        
        @Override
        public void mouseReleased( MouseEvent e ) {
        	System.out.println("Mouse Released");
        	handle.moveCar();
        }
    }
    
    /**
     * Moves the RushHourCar that this button is representing
     */
    public void moveCar() {
    	int thisx = getLocation().x;
    	int thisy = getLocation().y;
    	int newx = Math.round( thisx / BoardPanel.sizePerBlockW ) * BoardPanel.sizePerBlockW;
    	int newy = Math.round( thisy / BoardPanel.sizePerBlockH ) * BoardPanel.sizePerBlockH;
    	setLocation( newx, newy );
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
