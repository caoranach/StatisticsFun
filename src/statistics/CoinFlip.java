package statistics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class CoinFlip extends JPanel implements ActionListener {
	static final int WIDTH = 1400;
	static final int HEIGHT = 800;
	static final int GRAPH_WIDTH = 800;
	static final int GRAPH_HEIGHT = 500;
	Timer timer;
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	JPanel inputPanel = new JPanel();
	JPanel chartPanel = new JPanel();
	JLabel flipLabel = new JLabel("FLIPS:");
	JLabel trialLabel = new JLabel("TRIALS:");
	JTextField flipField = new JTextField(30);
	JTextField trialField = new JTextField(30);
	JButton myBoobs = new JButton("FLIP!");
	Random gen = new Random();
	int trials = 100000;
	int flipsPerTrial = 100;
	double widthPerResult = (double)GRAPH_WIDTH/(double)flipsPerTrial;
	double heightPerResult = 1;
	HashMap<Integer, Integer> outputs = new HashMap<Integer, Integer>();
	
	public static void main(String[] args) {
		CoinFlip cf = new CoinFlip();
		cf.setup();
	}
	
	void setup() {
		frame.setVisible(true);
		frame.setPreferredSize(new Dimension(WIDTH+50,HEIGHT));
		frame.add(panel);
		inputPanel.add(trialLabel);
		inputPanel.add(trialField);
		inputPanel.add(flipLabel);
		inputPanel.add(flipField);	
		inputPanel.add(myBoobs);		
		myBoobs.setPreferredSize(new Dimension(200,40));
		myBoobs.addActionListener(this);
		panel.add(this);
		panel.add(inputPanel);
		panel.add(chartPanel);
		inputPanel.setPreferredSize(new Dimension(GRAPH_WIDTH,160));
		chartPanel.setPreferredSize(new Dimension(GRAPH_WIDTH,GRAPH_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(GRAPH_WIDTH,GRAPH_HEIGHT));
		frame.pack();
		
		initializeMap();
		timer = new Timer(1000/60, this);
		timer.start();
		frame.pack();
	}
	
	void initializeMap() {
		for (int i = 0; i < flipsPerTrial; i++) {
			outputs.put(i, 0);
		}
	}
	void calculateFlips() {
		int highestResult = 0;
		for(int i = 0; i < trials; i++) {
			int heads = runTrial();
			//System.out.println("Trial " + i + " had " + heads + "/" + flipsPerTrial + " heads.");
			outputs.put(heads, outputs.get(heads)+1);
			if(outputs.get(heads) > highestResult) {
				highestResult = outputs.get(heads);
			}
		}
		System.out.println("Highest result count was " + highestResult);
		heightPerResult = (GRAPH_HEIGHT-10)/(double)highestResult;
		System.out.println("Height per result point is " + heightPerResult);
	}
	
	int runTrial() {
		int heads = 0;
		for(int i = 0; i < flipsPerTrial; i++) {
			if(new Random().nextBoolean() == true) {
				heads++;
			}
		}
		return heads;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		for(int key : outputs.keySet()) {
			int value = outputs.get(key);
			double size = value*heightPerResult;
			g.setColor(Color.BLUE);
			g.fillRect((int)(key*widthPerResult), (int)(GRAPH_HEIGHT-(int)size), (int)widthPerResult, (int)size);
			g.setColor(Color.BLACK);
			g.drawRect((int)(key*widthPerResult), (int)(GRAPH_HEIGHT-(int)size), (int)widthPerResult, (int)size);
			int xOffset = 0;
			int yOffset = 0;
			if(key >= 50) {
				xOffset = 100;
				yOffset = 500;
			}
			g.drawString(key+":"+value, GRAPH_WIDTH-200+xOffset, 20+key*10-yOffset);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == myBoobs) {
			calculateFlips();
		}
		frame.repaint();
		//repaint();
	}
}
