package edu.nju.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.nju.model.impl.StatisticModelImpl;
import edu.nju.model.po.IOoperation;
import edu.nju.model.po.StaPO;
import edu.nju.model.po.StaPOs;


public class RecordDialogs {
	
	private JFrame frame;
	private JDialog dialog;
	private StaPOs stas;
	private StatisticModelImpl statistic;

	private JPanel panel;

	private JButton okBtn;

	private JButton clearBtn;

	//private JSeparator line;

	private JPanel textPanel;
	
	public RecordDialogs(JFrame frame, StaPOs stas, StatisticModelImpl statistic){
		this.frame = frame;
		this.stas = stas;
		this.statistic = statistic;
		init();
	}
	
	private void init(){
		dialog = new JDialog(frame, "record", true);

		okBtn = new JButton("ok");
		okBtn.setFont(new Font("Monospaced", Font.PLAIN, 12));
		okBtn.setBounds(50, 155, 70, 23);
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});

		clearBtn = new JButton("clear");
		clearBtn.setFont(new Font("Monospaced", Font.PLAIN, 12));
		clearBtn.setBounds(150, 155, 70, 23);
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPanel.repaint();
			}
		});

		//line = new JSeparator();
		//line.setBounds(20, 105, 240, 4);

		panel = new JPanel();
		panel.setLayout(null);

		textPanel = new DescribeTextPanel();
		panel.add(textPanel);

		panel.add(okBtn);
		panel.add(clearBtn);
		//panel.add(line);

		dialog.setContentPane(panel);
		dialog.setBounds(frame.getLocation().x + 50,
				frame.getLocation().y + 50, 290, 230);

	}
	
	public void show(){
		statistic.showStatistics();
		IOoperation.print(stas);
		stas = statistic.statisticDao.getStatistic();
		textPanel.repaint();
		dialog.setVisible( true);
	}
	
	private class DescribeTextPanel extends JPanel {

		DescribeTextPanel() {
			super();
			setBounds(0, 0, 290, 140);
		}

		public void paintComponent(java.awt.Graphics g) {
			super.paintComponent(g);
			g.setFont(new Font("Monospaced", Font.PLAIN, 12));
//			int length = names.length;
//			System.out.println(stas.stas.size());
			for (int i = 0; i < stas.stas.size(); i++) {
				StaPO sta = stas.stas.get(i);
				g.drawString(sta.getLevel(), 20, 30 * (i + 1));
				g.drawString(sta.getWin()+"/"+sta.getSum(),150, 30 * (i + 1));
//				g.drawString(rank[i], 230, 30 * (i + 1));
			}
		}
	}
}
