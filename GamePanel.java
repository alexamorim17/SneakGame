import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{


	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int tamanho = 25;
	static final int unidades = (SCREEN_WIDTH * SCREEN_HEIGHT/tamanho);
	static final int delay = 175;
	final int x[] = new int [unidades];
	final int y[] = new int [unidades];
	int minhoca_inicio = 6;
	int appleEatens;
	int appleX;
	int appleY;
	char direcao = 'R';
	boolean correndo = false;
	Timer tempo;
	Random random;
	
	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
	}

	public void startGame() {
		
		
		correndo = true;
		tempo = new Timer(delay,this);
		tempo.start();
		newApple();
		
		
		

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);

	}

	public void draw(Graphics g) {
			
		if(correndo) {
				for(int i=0;i<SCREEN_HEIGHT/tamanho;i++) {
				
				}
				g.setColor(Color.red);
				g.fillOval(appleX, appleY,tamanho, tamanho);
				
				for(int i = 0; i<minhoca_inicio;i++) {
					
					if(i == 0) {
						g.setColor(Color.orange);
						g.fillRect(x[i], y[i], tamanho, tamanho);
					}
					else {
						g.setColor(new Color(45,180,0));
						g.fillRect(x[i], y[i], tamanho, tamanho);
					}
					
					g.setColor(Color.orange);
					g.setFont(new Font("Ink Free",Font.BOLD,40));
					FontMetrics metrics = getFontMetrics(g.getFont());
					g.drawString("Score" + appleEatens,(SCREEN_WIDTH - metrics.stringWidth("Score " + appleEatens))/2,g.getFont().getSize());
					
				}
				
		} else {
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.BOLD,40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score" + appleEatens,(SCREEN_WIDTH - metrics.stringWidth("Score " + appleEatens))/2,g.getFont().getSize());
		}
	}
		
		public void movimentar(){

			for(int i = minhoca_inicio;i>0;i--) {

				x[i] = x[i-1];

				y[i] = y[i-1];

			}

			

			switch(direcao) {

			case 'U':

				y[0] = y[0] - tamanho;

				break;

			case 'D':

				y[0] = y[0] + tamanho;

				break;

			case 'L':

				x[0] = x[0] - tamanho;

				break;

			case 'R':

				x[0] = x[0] + tamanho;

				break;

			}

			

		}


	
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_HEIGHT/tamanho))*tamanho;
		appleY = random.nextInt((int)(SCREEN_WIDTH/tamanho))*tamanho;
	}

	public void gameOver() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {


		if(correndo) {

			movimentar();

			checkApple();

			checkCollisions();

		}

		repaint();

	}

	public class MyKeyAdapter extends KeyAdapter{

		@Override

		public void keyPressed(KeyEvent e) {

			switch(e.getKeyCode()) {

			case KeyEvent.VK_LEFT:

				if(direcao != 'R') {

					direcao = 'L';

				}

				break;

			case KeyEvent.VK_RIGHT:

				if(direcao != 'L') {

					direcao = 'R';

				}

				break;

			case KeyEvent.VK_UP:

				if(direcao!= 'D') {

					direcao = 'U';

				}

				break;

			case KeyEvent.VK_DOWN:

				if(direcao != 'U') {

					direcao= 'D';

				}

				break;

			}
			
			

			
			
		}
	
			}
	
	public void checkApple() {
		if((x[0] == appleX) && (y[0] == appleY)) {
			minhoca_inicio++;
			appleEatens++;
			newApple();
		}
	}
	
	public void checkCollisions() {
		//checks if head collides with body
		for(int i = minhoca_inicio;i>0;i--) {
			if((x[0] == x[i])&& (y[0] == y[i])) {
				correndo = false;
			}
		}
		//check if head touches left border
		if(x[0] < 0) {
			correndo = false;
		}
		//check if head touches right border
		if(x[0] > SCREEN_WIDTH) {
			correndo = false;
		}
		//check if head touches top border
		if(y[0] < 0) {
			correndo = false;
		}
		//check if head touches bottom border
		if(y[0] > SCREEN_HEIGHT) {
			correndo= false;
		}
		
		if(!correndo) {
			tempo.stop();
		}
	}
}
