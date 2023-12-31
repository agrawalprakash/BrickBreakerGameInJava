import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener{

	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 21;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 310;
	
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private MapGenerator mapGenerator;
	
	public Gameplay() {
		
		mapGenerator = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(play);
		timer = new Timer(delay, this);
		timer.start();
		
	}
	
	public void paint(Graphics gr) {
		// background
		gr.setColor(Color.black);
		gr.fillRect(1,  1, 692, 592);
		
		mapGenerator.draw((Graphics2D)gr);
		
		// borders
		gr.setColor(Color.yellow);
		gr.fillRect(0, 0, 3, 592);
		gr.fillRect(0, 0, 692, 3);
		gr.fillRect(691, 0, 3, 592);
		
		// paddle
		gr.setColor(Color.green);
		gr.fillRect(playerX, 550, 100, 8);
		
		// ball
		gr.setColor(Color.yellow);
		gr.fillOval(ballposX, ballposY, 20, 20);
		
		gr.dispose();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		timer.start();
		
		if (play) {
			
			if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYdir = -ballYdir;
			}
			
			A : for (int i = 0; i < mapGenerator.map.length; i++) {
				for (int j = 0; j < mapGenerator.map[0].length; j++) {
					
					if (mapGenerator.map[i][j] > 0) {
						
						int brickX = j*mapGenerator.brickWidth + 80;
						int brickY = i*mapGenerator.brickHeight + 50;
						
						int brickWidth = mapGenerator.brickWidth;
						int brickHeight = mapGenerator.brickHeight;
						
						Rectangle rect = new Rectangle
								(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						if (ballRect.intersects(brickRect)) {
							mapGenerator.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
							if (ballposX + 19 <= brickRect.x ||
									ballposX + 1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;
							} else {
								ballYdir = -ballYdir;
							}
							
							break A;
							
						}
						
					}
					
					
				}
			}
			
			ballposX = ballposX + ballXdir;
			ballposY = ballposY + ballYdir;
			
			if (ballposX < 0) {
				ballXdir = -ballXdir;
			}
			
			if (ballposY < 0) {
				ballYdir = -ballYdir;
			}
			
			if (ballposX > 670) {
				ballXdir = -ballXdir;
			}
			
		}
		
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			
			if (playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}
			
		}
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			
			if (playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
			}
			
		}
		
	}
	
	public void moveRight() {
		play = true;
		playerX = playerX + 20; 
	}
	
	public void moveLeft() {
		play = true;
		playerX = playerX - 20;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
