import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class MapGenerator {
	
//	public int map[][];
	public int brickWidth;
	public int brickHeight;
	
	public Image enemyMap[][];
	
	public MapGenerator(int row, int col) {
		brickWidth = 540/col;
		brickHeight = 250/row;
		 
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

		for (int i = 0; i<enemyMap.length; i++) {
			 for (int j = 0; j<enemyMap[0].length;j++) {
				 if(enemyMap[i][j] != null) {
				 g.drawImage(enemyMap[i][j],j * brickWidth + 80, i * brickHeight + 50,null); 
				 g.setColor(Color.white);

			 }
			 }
		}

}
	
	public void setEnemyValue(Image value, int row, int col) {
		enemyMap[row][col] = value;
}
}