import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
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


public class CarComponent extends JComponent {
    protected Point anchorPoint;
    protected Cursor draggingCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    protected boolean overbearing = false;
    
	private boolean horizontal;
	
	private int carnum;
	private int moveDistance; // Used to limit it to one block per move
	private Point startingPosition;

    public CarComponent() {
        addDragListeners();
        setOpaque(true);
        setBackground(Color.red);
        setSize(300,300);
        setLocation( 0, 0 );

        this.startingPosition = new Point(0, 0);
        
        horizontal = true;
        
        moveDistance = 0;
    }
    
    public CarComponent( boolean horizontal, Color c ) {
    	this();
    	this.horizontal = horizontal;
    	setBackground( c );
    }
    
    public CarComponent( int carnum, boolean horizontal, int x, int y, int width, int height, Color c ) {
    	this( horizontal, c );
    	startingPosition.x = x;
    	startingPosition.y = y;
    	setLocation( x, y );
		setSize( width, height );
		this.carnum = carnum;
    }
    

    private void addDragListeners() {        
        DragListener listener = new DragListener( this );
        addMouseMotionListener( listener );
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

            Point oldPosition = getLocation();
            
            Point parentOnScreen = getParent().getLocationOnScreen();
            Point mouseOnScreen = e.getLocationOnScreen();
            Point position = new Point();
            if( horizontal ) {
                position = new Point(mouseOnScreen.x - parentOnScreen.x - 
						anchorX, getLocation().y);
                
                handle.moveDistance += oldPosition.x - position.x;
                if( Math.abs(moveDistance) <= BoardPanel.sizePerBlockW ) {
                	setLocation(position);
                } else {
                	if( position.x > startingPosition.x ) {
                		position.x = handle.startingPosition.x + BoardPanel.sizePerBlockW;
                	} else {
                		position.x = handle.startingPosition.x - BoardPanel.sizePerBlockW;
                	}
                	setLocation(position);
                }
                
            } else {
            	position = new Point(getLocation().x, mouseOnScreen.y - 
						parentOnScreen.y - anchorY);
            	handle.moveDistance += oldPosition.y - position.y;
                if( Math.abs(moveDistance) <= BoardPanel.sizePerBlockH ) {
                	setLocation(position);
                } else {
                	if( position.y > startingPosition.y ) {
                		position.y = handle.startingPosition.y + BoardPanel.sizePerBlockH;
                	} else {
                		position.y = handle.startingPosition.y - BoardPanel.sizePerBlockH;
                	}
                	setLocation(position);
                }
            }
            

            if (overbearing) {
                getParent().setComponentZOrder(handle, 0);
                repaint();
            }
        }
    }  
    
    public void moveEnded() {
    	this.moveDistance = 0;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

	public int getCarnum() {
		return carnum;
	}
	
	public void setDimensions(Rectangle rec) {
		startingPosition.x = rec.x;
		startingPosition.y = rec.y;
		this.setLocation(rec.x, rec.y);
	}
}
