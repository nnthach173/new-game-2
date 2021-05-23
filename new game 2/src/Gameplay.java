import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
	private boolean play = false;
	private int score = 0;

	private int totalEnemy = 21;
	private Timer timer;
	private int delay = 8;

	private int playerX = 300;

	private int ballposX = 340;
	private int ballposY = 778;
	private int ballXdir = -5;
	private int ballYdir = -5;
	
	private MapGenerator map;
	private MapGenerator enemyMap;
	Image enemies;
	Graphics graphics;

	public Gameplay() {
//		enemy = new Enemy();
		//enemies = new ImageIcon("enemy4.png").getImage();
		//map = new MapGenerator(5,7);
		enemyMap = new MapGenerator(5,7);  /// ca mới add enemyMap vào
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
		timer.start();
	}


	public void paint(Graphics g) {
		// background
		g.setColor(Color.black);
		g.fillRect(1, 1, 700, 850);
		
		//draw Map
		
		map.draw((Graphics2D)g);
		enemyMap.draw((Graphics2D)g);
				

		// borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 850);
		g.fillRect(0, 0, 692, 4);
		g.fillRect(681, 0, 3, 842);

		// the paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 800, 100, 8);

		// the ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
		

		
		g.dispose();
		
		
	}
	/////////////      BALL INTERSECTS WITH BRICKS  /////////////////
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,800,100,8))) {
				ballYdir = -ballYdir;
			}
			for(int i = 0; i<map.map.length; i++) {
				for(int j = 0; j<map.map[0].length;j++) {
					if(map.map[i][j] > 0) {
						int brickX = j*map.brickWidth + 80;
						int brickY = i*map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20,20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
	//						map.setBrickValue(0, i, j);
							totalEnemy--;
							score += 5;
							
							if(ballposX + 19 <=brickRect.x || ballposX +1 >=brickRect.x +brickRect.width) {
								ballXdir = -ballXdir;
							} else {
								ballYdir = -ballYdir;
							}
							break;
						}
					}
				}
		}
			
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX < 0) {
				ballXdir = -ballXdir;
			}
			if(ballposY < 0) {
				ballYdir = -ballYdir;
			}
			if(ballposX > 665) {
				ballXdir = -ballXdir;
			}
		}
		
		repaint(); 
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 580) {
				playerX = 580;
			} else {
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX < 1) {
				playerX = 0;
			} else {
				moveLeft();
			}
		}

	}

	public void moveRight() {
		play = true;
		playerX += 20;
	}

	public void moveLeft() {
		play = true;
		playerX -= 20;
	}
}