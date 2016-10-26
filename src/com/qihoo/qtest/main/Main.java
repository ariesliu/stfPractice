package com.qihoo.qtest.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import com.android.ddmlib.IDevice;
import com.qihoo.qtest.device.*;
import com.qihoo.qtest.minicap.*;

/**
 * @author hui.qian qianhui@58.com
 * @date 2015年8月14日 下午7:40:17
 */

@SuppressWarnings("serial")
public class Main extends JFrame {
	private static final Logger LOG = Logger.getLogger("PageTest.class");

	private MyPanel mp = null;
	private IDevice device;
	private int width = 300;
	private int height = 500;
//	private Thread thread = null;

	public Main() {
		ADB adb = new ADB();
		if (adb.getDevices().length <= 0) {
			LOG.error("无连接设备,请检查");
			return;
		}
		device = adb.getDevices()[0];
		mp = new MyPanel(device,this);
		this.getContentPane().add(mp);
		this.setSize(300, height);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((dim.width - this.getWidth()) / 2, 0);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

			}
		});
		this.setVisible(true);
		pack();

	}

	public static void main(String[] args) {
		new Main();
	}

	class MyPanel extends JPanel implements AndroidScreenObserver {

		BufferedImage image = null;
		MinicapDemo minicap = null;

		public MyPanel(IDevice device,Main frame) {
			minicap = new MinicapDemo(device);
			minicap.registerObserver(this);
			minicap.takeScreenShotOnce();
			minicap.startScreenListener();

		}

		public void paint(Graphics g) {
			try {
				if (image == null)
					return;
				Main.this.setSize(width, height);
				g.drawImage(image, 0, 0, width, height, null);
				this.setSize(300, height + 300);
				image.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void frameImageChange(Image image) {
			this.image = (BufferedImage) image;
			int w = this.image.getWidth();
			int h = this.image.getHeight();
			float radio = (float) width / (float) w;
			height = (int) (radio * h);
			System.out.println("width : " + w + ",height : " + h);
			this.repaint();
		}
	}

}
