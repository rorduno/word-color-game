import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.io.Console;
import java.util.Random;
import java.util.logging.Logger;

public class WordColorGame extends JFrame {
	 static Logger log = Logger.getLogger(WordColorGame.class.getName());
	private JLabel jlblStatus, jlblScore, jlblTimeLeft, jlblStrikesLeft;
	int tickytickytimetime = 31;// 
	private JButton yes, no, start;
	private Color[] colorArray = { Color.RED, Color.BLUE, Color.GREEN,
			Color.BLACK, Color.YELLOW };//Bottom colors
	private String[] colorNames = { "RED", "BLUE", "GREEN", "BLACK", "YELLOW" };//Top words
																			
	private Timer timer;
	private final int STRIKES_ALLOWED = 3;
	MessagePanel top = new MessagePanel("top");// topWord topColor
	MessagePanel bottom = new MessagePanel("bottom");// bottomWord bottomColor
	int strikes, strikesleft = 3, score;
	int x, y, x2, y2;
	double z;
	boolean clickable = false;
	boolean startClickable = true;

	public WordColorGame() {

		top.setCentered(true);
		bottom.setCentered(true);
		top.setFont(new Font("", Font.PLAIN, 40));
		bottom.setFont(new Font("", Font.PLAIN, 40));

		JPanel p1 = new JPanel(new GridLayout(2, 1));
		p1.add(top);
		p1.add(bottom);
		p1.setPreferredSize(new Dimension(0, 100));

		JPanel p2 = new JPanel(new GridLayout(1, 7));// add a border
		p2.setSize(700, 300);

		BtnListener listen = new BtnListener();

		JButton yes = new JButton("Yes");
		JButton no = new JButton("No");
		JButton start = new JButton("Start");
		yes.setBorder(BorderFactory.createLineBorder(Color.black));
		no.setBorder(BorderFactory.createLineBorder(Color.black));
		start.setBorder(BorderFactory.createLineBorder(Color.black));

		start.setPreferredSize(new Dimension(100, 40));
		yes.setPreferredSize(new Dimension(100, 40));
		no.setPreferredSize(new Dimension(100, 40));

		start.addActionListener(listen);
		start.setActionCommand("start");

		yes.addActionListener(listen);
		yes.setActionCommand("yes");

		no.addActionListener(listen);
		no.setActionCommand("no");

		jlblStatus = new JLabel(" ");
		jlblScore = new JLabel("Score: ");
		jlblTimeLeft = new JLabel("Time left: ");
		jlblStrikesLeft = new JLabel("Strikes left: " + strikesleft);

		jlblStatus.setBorder(BorderFactory.createLineBorder(Color.black));
		jlblScore.setBorder(BorderFactory.createLineBorder(Color.black));
		jlblTimeLeft.setBorder(BorderFactory.createLineBorder(Color.black));
		jlblStrikesLeft.setBorder(BorderFactory.createLineBorder(Color.black));

		jlblStatus.setPreferredSize(new Dimension(100, 40));
		jlblScore.setPreferredSize(new Dimension(100, 40));
		jlblTimeLeft.setPreferredSize(new Dimension(100, 40));
		jlblStrikesLeft.setPreferredSize(new Dimension(100, 40));
		p2.add(jlblStatus);
		p2.add(yes);
		p2.add(no);
		p2.add(start);
		p2.add(jlblScore);
		p2.add(jlblTimeLeft);
		p2.add(jlblStrikesLeft);

		setLayout(new GridLayout(2, 1));
		add(p1);
		add(p2, BorderLayout.SOUTH);
		setSize(700, 300);
		timer = new Timer(1000, new TimerListener());
	}

	private class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) { // this is where the top
														// and bottom are
														// changing

			tickytickytimetime--;
			if (tickytickytimetime <= 9)
				jlblTimeLeft.setText("Time left: 0" + tickytickytimetime);
			else
				jlblTimeLeft.setText("Time left: " + tickytickytimetime);

			if (tickytickytimetime == 0 || strikes >= STRIKES_ALLOWED) {
				timer.stop();
				JOptionPane.showMessageDialog(null, "Your final score: "
						+ score);// add score
				System.exit(0);
			}

		}
	}

	class BtnListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// This is where the game is played
			Random rand = new Random();// Between 5 colors and strings
			// Only changes when you click on yes or no

			if (e.getActionCommand() == "start" && startClickable) {// is start
																	// is
																	// pressed
				timer.start();
				clickable = true;
				System.out.println("start is pressed");
				// call matchProb()
				matchProb();
				x2 = rand.nextInt(5);
				top.setForeground(colorArray[x2]);// changes the font color
				y = rand.nextInt(5);
				bottom.setMessage(colorNames[y]);
				startClickable = false;
			}

			if (e.getActionCommand() == "yes" && clickable) {// if bottom yes is
				// Check if person is correct or not
				System.out.println("yes is pressed");
				if (x == y2) {
					jlblStatus.setText("Correct!");
					score++;
					jlblScore.setText("Score: " + score);

					// call matchProb()
					matchProb();
					x2 = rand.nextInt(5);
					top.setForeground(colorArray[x2]);// changes the font color
					y = rand.nextInt(5);
					bottom.setMessage(colorNames[y]);

				} else {
					jlblStatus.setText("Wrong!");
					strikes++;
					strikesleft--;
					if(strikesleft>=0)
					jlblStrikesLeft.setText("Strikes left: " + strikesleft);
					// call matchProb()

					matchProb();
					x2 = rand.nextInt(5);
					top.setForeground(colorArray[x2]);// changes the font color
					y = rand.nextInt(5);
					bottom.setMessage(colorNames[y]);
				}

			}
			if (e.getActionCommand() == "no" && clickable) {
				System.out.println("no is pressed");

				if (x != y2) {
					jlblStatus.setText("Correct!");
					score++;
					jlblScore.setText("Score: " + score);

					// call matchProb()

					matchProb();
					x2 = rand.nextInt(5);
					top.setForeground(colorArray[x2]);// changes the font color
					y = rand.nextInt(5);
					bottom.setMessage(colorNames[y]);

				} else {
					jlblStatus.setText("Wrong!");
					strikes++;
					strikesleft--;
					if(strikesleft>=0)
						jlblStrikesLeft.setText("Strikes left: " + strikesleft);

					// call matchProb()
					matchProb();
					x2 = rand.nextInt(5);
					top.setForeground(colorArray[x2]);// changes the font color
					y = rand.nextInt(5);
					bottom.setMessage(colorNames[y]);
				}

			}

			System.out.println("top idx: " + x);
			log.info("test");
			System.out.println("bottom idx: " + y2 + "\n");
		}//End actionPerformed()

	}

	public void matchProb() {
		Random rand = new Random();

		z = rand.nextDouble();
		if (z > 0.5) {
			System.out.println("match");
			// call match
			match();
		} else {
			System.out.println("not match");
			// no match
			noMatch();
		}
		// return false;
	}

	public void match() {
		Random rand = new Random();
		x = rand.nextInt(5);
		y2 = rand.nextInt(5);
		while (x != y2) {

			x = rand.nextInt(5);
			y2 = rand.nextInt(5);
		}
		top.setMessage(colorNames[x]);
		bottom.setForeground(colorArray[y2]);
	}

	public void noMatch() {
		Random rand = new Random();
		x = rand.nextInt(5);
		y2 = rand.nextInt(5);
		while (x == y2) {

			x = rand.nextInt(5);
			y2 = rand.nextInt(5);
		}
		top.setMessage(colorNames[x]);
		bottom.setForeground(colorArray[y2]);
	}

	public static void main(String[] args) {
		JFrame frame = new WordColorGame();
		frame.setTitle("Word Color Game");
		frame.setLocationRelativeTo(null); // Center the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();

	}
}
