//© A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Class -
//Lab  -

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class OuterSpace extends Canvas implements KeyListener, Runnable {
	private Ship ship;
	private Alien alienOne;
	private Alien alienTwo;
	

	private int rando = 0;
	
	private int count;
	

	private ArrayList<Alien> aliens;
	private ArrayList<Ammo> shots;
	private Ammo shot;

	private int cooldown = 0;

	private boolean[] keys;
	private BufferedImage back;

	public OuterSpace() {
		setBackground(Color.black);

		keys = new boolean[5];

		// instantiate other stuff

		this.addKeyListener(this);
		new Thread(this).start();
		ship = new Ship(300, 400, 5);
		alienOne = new Alien(400, 75, 1);
		alienTwo = new Alien(200, 75, 1);
		aliens = new ArrayList<Alien>();
		shots = new ArrayList<Ammo>();
		aliens.add(alienOne);
		aliens.add(alienTwo);
		aliens.add(new Alien(500,75,1));
		aliens.add(new Alien(500,150,1));
		aliens.add(new Alien(400,150,1));
		aliens.add(new Alien(200,150,1));

		setVisible(true);
	}

	public void update(Graphics window) {
		paint(window);
	}

	public void paint(Graphics window) {
		// set up the double buffering to make the game animation nice and
		// smooth
		Graphics2D twoDGraph = (Graphics2D) window;

		// take a snap shop of the current screen and same it as an image
		// that is the exact same width and height as the current screen
		if (back == null)
			back = (BufferedImage) (createImage(getWidth(), getHeight()));

		// create a graphics reference to the back ground image
		// we will draw all changes on the background image
		Graphics graphToBack = back.createGraphics();

		graphToBack.setColor(Color.BLUE);
		graphToBack.drawString("StarFighter ", 25, 50);
		graphToBack.setColor(Color.BLACK);
		graphToBack.fillRect(0, 0, 800, 600);

		/*
		 * alienOne.move("RIGHT"); if(alienOne.getX() >= 720){
		 * alienOne.setSpeed(-1); } if(alienOne.getX() <= 200){
		 * alienOne.setSpeed(1); }
		 * 
		 * alienTwo.move("RIGHT"); if(alienTwo.getX() >= 520){
		 * alienTwo.setSpeed(-1); } if(alienTwo.getX() <= 0){
		 * alienTwo.setSpeed(1); }
		 */
		
		for(int i = 0; i < aliens.size(); i++){
			aliens.get(i).move("RIGHT");
			if(aliens.get(i).getX() >= 720){
				aliens.get(i).setSpeed(-1);
			
			}
			if(aliens.get(i).getX() <= 0){
				aliens.get(i).setSpeed(1);
				
			}
			
		}

		if (keys[0] == true) {
			if (ship.getX() >= 0) {
				ship.move("LEFT");
			}

		}

		if (keys[1] == true) {
			if (ship.getX() <= 719) {
				ship.move("RIGHT");
			}

		}

		if (keys[2] == true) {
			if (ship.getY() >= 250) {
				ship.move("UP");
			}

		}

		if (keys[3] == true) {
			if (ship.getY() <= 460) {
				ship.move("DOWN");
			}

		}

		if (keys[4] == true) {

			if (cooldown <= 0 && shots.size() < 5) {
				Ammo temp = new Ammo(ship.getX() + 35, ship.getY(), 1);
				shots.add(temp);
			}
			cooldown = 15;

		}

		for (int i = 0; i < shots.size(); i++) {
			shots.get(i).move("UP");
			if (shots.get(i).getY() <= 0) {
				shots.remove(i);
			}
		}

		

		cooldown -= 1;
	for(int k = 0; k < aliens.size(); k++){
		for(int j = 0; j < shots.size(); j++){
			
			if(shots.get(j).getY() <= aliens.get(k).getY() + 50 && shots.get(j).getX() >= aliens.get(k).getX() && shots.get(j).getX() <= aliens.get(k).getX() + 50 && shots.get(j).getY() >= aliens.get(k).getY()){
				aliens.get(k).setPos(10000, 10000);
				shots.remove(j);
				count ++;
			}
			
			if(count >= 6){
				setVisible(false);
				window.dispose();
			}
			
			
		}
	}
	
	System.out.println(shots.size());
		/*for (int j = 0; j < shots.size(); j++) {
			if (shots.get(j).getY() <= alienOne.getY() + 80
					&& shots.get(j).getX() >= alienOne.getX()
					&& shots.get(j).getX() <= alienOne.getX() + 80) {
				alienOne.setPos(10000, 10000);
				if (alienTwo.getX() > 810 && alienTwo.getY() > 810) {
					setVisible(false);
					window.dispose();
				}
			}
			if (shots.get(j).getY() <= alienTwo.getY() + 80
					&& shots.get(j).getX() >= alienTwo.getX()
					&& shots.get(j).getX() <= alienTwo.getX() + 80) {
				alienTwo.setPos(10000, 10000);
				if (alienOne.getX() > 810 && alienOne.getY() > 810) {
					setVisible(false);
					window.dispose();
				}
			}
		}*/

		for (int i = 0; i < shots.size(); i++) {
			shots.get(i).draw(graphToBack);
		}
		for(int i = 0; i < aliens.size(); i++){
			aliens.get(i).draw(graphToBack);
		}
		ship.draw(graphToBack);
		twoDGraph.drawImage(back, null, 0, 0);
	}
	
	

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keys[0] = true;

		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keys[1] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			keys[2] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			keys[3] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys[4] = true;
		}
		repaint();
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keys[0] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keys[1] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			keys[2] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			keys[3] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys[4] = false;
		}
		repaint();
	}

	public void keyTyped(KeyEvent e) {

	}

	public void run() {
		try {
			while (true) {
				Thread.currentThread().sleep(5);
				repaint();
			}
		} catch (Exception e) {
		}
	}
}
