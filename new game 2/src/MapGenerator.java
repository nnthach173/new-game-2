import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class MapGenerator {
	
	public int map[][];
	public int brickWidth;
	public int brickHeight;
	
	public Image enemyMap[][];
	
	public MapGenerator(int row, int col) {
		map = new int[row][col];
		for(int i = 0; i<map.length;i++) {
			for(int j=0; j< map[0].length;j++) {
				map[i][j] = 1;
			}
		}
		brickWidth = 540/col;
		brickHeight = 150/row;
		 
		 // init enemy map
		 enemyMap = new Image[row][col];
		 for (int i=0; i<enemyMap.length; i++) {
			 for (int j=0; j<enemyMap[0].length;j++) {
				 Image image = new ImageIcon("enemy4.png").getImage();
				 enemyMap[i][j] = image;
			 }
		 }
	}
	
	public void draw(Graphics2D g) {
		for(int i = 0; i<map.length;i++) {
			for(int j=0; j< map[0].length;j++) {
				if(map[i][j]>0) {
					g.setColor(Color.white);
					g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
				
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
					

				}
				
				// draw enemy
				//enemyMap[i][j].paint(g);
				//g.drawImage(enemyMap[i][j],100,100,null);
			}
	}

}
	
	//public void setBrickValue(int value, int row, int col) {
	//map[row][col] = value;
//}
	//public void setEnemyValue(Image enemyMap, int row, int col) {
//	enemyMap[i][j]	= ;
//}
}