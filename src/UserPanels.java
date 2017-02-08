import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class UserPanels extends JFrame {
	BufferedReader reader;
	PrintWriter writer;
	
	public UserPanels() {
		Container container = getContentPane();
		
		container.setLayout(new BorderLayout());
		
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(3,3));
		
		for (int i = 1; i < 10; i++) {
			JButton jButton = new JButton("路灯" + i);
			jButton.addActionListener(new HeyButtonListener());
			p1.add(new JButton("路灯" + i){});
		}
		
		JPanel p2 = new JPanel();
		
		JTextArea info = new JTextArea(10,10);
		info.setLineWrap(true);
		info.setWrapStyleWord(true);
		info.setEditable(false);
		
		p2.setLayout(new BorderLayout());
		p2.add(info,BorderLayout.NORTH);
		p2.add(p1,BorderLayout.CENTER);
		
		container.add(p2, BorderLayout.CENTER);
		//container.add(new Button("Food"), BorderLayout.CENTER);
		
	}
	public static void main(String[] args) {
		UserPanels frame = new UserPanels();
		frame.setTitle("节能路灯远程监控系统");
		frame.setSize(400,600);
		frame.setVisible(true);
	}
	
	public class HeyButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev){
						
			writer.println("0路灯上线了！");
			writer.flush();
			
			
		}
	}
}
