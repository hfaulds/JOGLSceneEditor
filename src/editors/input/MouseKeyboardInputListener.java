package editors.input;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

public class MouseKeyboardInputListener implements MouseInputListener,
    MouseWheelListener, KeyListener {

  public static int MULTI_SELECT_KEY = KeyEvent.VK_CONTROL;
  
  public static int SELECT_BUTTON = MouseEvent.BUTTON1;
  public static int PAN_BUTTON = MouseEvent.BUTTON2;
  public static int ROTATE_BUTTON = MouseEvent.BUTTON3;
  
  public static int ZOOM_SPEED = 1;

  public boolean bContinousSelection = false;

  public int mouseWheelRotation = 5;

  public boolean bSelecting = false;
  public Point selectionStart = new Point();
  public Point selectionEnd = new Point();
  
  public boolean bRotating = false;
  public int xMovement = 0;
  public int yMovement = 0;

  @Override
  public synchronized void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == MULTI_SELECT_KEY) {
      this.bContinousSelection = true;
    }
  }

  @Override
  public synchronized void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == MULTI_SELECT_KEY) {
      this.bContinousSelection = false;
    }
  }

  @Override
  public synchronized void mousePressed(MouseEvent e) {
    if (!bRotating && e.getButton() == SELECT_BUTTON) {
      this.bSelecting = true;
      this.selectionStart = e.getPoint();
    }
    
    if(!bSelecting && e.getButton() == ROTATE_BUTTON) {
      this.bRotating = true;
      this.xMovement = this.yMovement = 0;
    }
  }

  @Override
  public synchronized void mouseReleased(MouseEvent e) {
    if (!bRotating && e.getButton() == SELECT_BUTTON) {
      this.bSelecting = false;
    }
    
    if(!bSelecting && e.getButton() == ROTATE_BUTTON) {
      this.bRotating = false;
    }
  }

  @Override
  public synchronized void mouseMoved(MouseEvent e) {
    Point lastSelectionEnd = e.getPoint();
    
    this.xMovement = (this.selectionEnd.x - lastSelectionEnd.x);
    this.yMovement = (this.selectionEnd.y - lastSelectionEnd.y);
    
    this.selectionEnd = lastSelectionEnd;
    
  }

  @Override
  public synchronized void mouseWheelMoved(MouseWheelEvent e) {
    mouseWheelRotation += e.getWheelRotation() * ZOOM_SPEED;
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    Point lastSelectionEnd = e.getPoint();
    
    this.xMovement = (this.selectionEnd.x - lastSelectionEnd.x);
    this.yMovement = (this.selectionEnd.y - lastSelectionEnd.y);
    
    this.selectionEnd = lastSelectionEnd;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }
}
