import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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

	private int totalEnemy = 15;
	private Timer timer;
	private int delay = 8;

	private int playerX = 300;

	private int ballposX = 340;
	private int ballposY = 778;
	private int ballXdir = -2;
	private int ballYdir = -2;
	
	private MapGenerator enemyMap;
	Image enemies;
	Graphics graphics;
	Image backgroundImage;

	public Gameplay() {
		backgroundImage = new ImageIcon("space.jpg").getImage();
		enemyMap = new MapGenerator(3,5);  
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
		timer.start();
	}


	public void paint(Graphics g) {
		// background
//		g.setColor(Color.black);
//		g.fillRect(1, 1, 700, 850);
		g.drawImage(backgroundImage,0,0,null);

		
		//draw Map
		enemyMap.draw((Graphics2D)g);

		// borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 850);
		g.fillRect(0, 0, 692, 4);
		g.fillRect(681, 0, 3, 842);
		
		// scores
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+score, 640, 30);

		// the paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 800, 100, 8);

		// the ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		((Graphics2D) g).setStroke(new BasicStroke(3));
		g.setColor(Color.black);
		g.drawOval(ballposX, ballposY,20,20);
		
		if(ballposY > 830) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD,35));
			g.drawString("Game Over. Scores: " + score, 190, 400);
			
			g.setFont(new Font("serif", Font.BOLD,25));
			g.drawString("Press Enter to Restart", 230, 500);
			
		}

		if(totalEnemy <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD,35));
			g.drawString("You Win. Scores: " + score, 205, 400);
			
			g.setFont(new Font("serif", Font.BOLD,25));
			g.drawString("Press Enter to Restart", 250, 500);
		}
		

		
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
			 for(int i = 0; i< enemyMap.enemyMap.length; i++) {
					for(int j = 0; j<enemyMap.enemyMap[0].length;j++) {
						if(enemyMap.enemyMap[i][j] != null) {
							int brickX = j*enemyMap.brickWidth + 35;
							int brickY = i*enemyMap.brickHeight + 27;
							int brickWidth = enemyMap.brickWidth;
							int brickHeight = enemyMap.brickHeight;
							
							Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
							Rectangle ballRect = new Rectangle(ballposX, ballposY, 20,20);
							Rectangle brickRect = rect;
							
							if(ballRect.intersects(brickRect)) {
								enemyMap.setEnemyValue(null, i, j);
								totalEnemy--;
								score += 5;
								
								if(ballposX +19 <=brickRect.x || ballposX +1 >=brickRect.x +brickRect.width) {
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
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballposX = 340;
				ballposY = 778;
				ballXdir = -2;
				ballYdir = -2;
				playerX = 300;
				score = 0;
				totalEnemy = 15;
				enemyMap = new MapGenerator(3,5);
				
				repaint();
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