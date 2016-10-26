package com.qtest.minitouch;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.android.ddmlib.IDevice;
import com.qtest.minicap.Banner;

public class MinitouchDemo {
	private String IP = "127.0.0.1";
	private int PORT = 1111;
	private Socket socket;
	private IDevice device;
	private Banner banner = new Banner();
	private InputStream stream = null;
	
	public MinitouchDemo(IDevice device) {
		this.device = device;
		init();
		try {
			socket = new Socket(IP, PORT);
			parserBanner(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private void init() {
		
	}
	
	private void parserBanner(Socket socket) {
		try {
			stream = socket.getInputStream();
			byte[] buffer =new byte[64];
			
//			int len = 64;
//			byte[] buffer;
//			buffer = new byte[len];
//			int realLen = stream.read(buffer);
//			if (buffer.length != realLen) {
//				buffer = subByteArray(buffer, 0, realLen);
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private byte[] subByteArray(byte[] byte1, int start, int end) {
		byte[] byte2 = new byte[0];
		try {
			byte2 = new byte[end - start];
		} catch (NegativeArraySizeException e) {
			e.printStackTrace();
		}
		System.arraycopy(byte1, start, byte2, 0, end - start);
		return byte2;
	}
}
