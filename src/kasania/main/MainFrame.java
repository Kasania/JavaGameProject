package kasania.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private Toolkit tkit = Toolkit.getDefaultToolkit();
	private JPanel MainPanel;

	public MainFrame(int WIDTH, int HEIGHT){
		init();
		create(WIDTH,HEIGHT);
	}
	
	private void init(){
		addKeyListener(GameManager.getKeyboard());
		MainPanel = new JPanel();
		MainPanel.setBackground(Color.WHITE);
	}
	
	private void create(int WIDTH, int HEIGHT){
		this.setBounds((tkit.getScreenSize().width-WIDTH)/2, (tkit.getScreenSize().height-(int)(HEIGHT*1.2))/2, WIDTH, HEIGHT);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(MainPanel);
		this.setContentPane(MainPanel);
//		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.pack();
		this.setVisible(true);
		this.createBufferStrategy(2);
	}
	
}
