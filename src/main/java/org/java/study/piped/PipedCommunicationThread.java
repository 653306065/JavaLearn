package org.java.study.piped;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class PipedCommunicationThread {
	
	public static void main(String[] args) throws IOException {
		PipedWriter pipedWriter= new PipedWriter();
		PipedReader pipedReader=new PipedReader(pipedWriter);
		ReadThread readThread=new ReadThread(pipedReader);
		WriteThread writeThread=new WriteThread(pipedWriter);
		readThread.start();
		writeThread.start();
	}

	
	
	public static class ReadThread extends Thread {
		
		PipedReader pipedReader;
		
		public ReadThread(PipedReader pipedReader) {
			this.pipedReader=pipedReader;
		}
		
		public void run () {
			while(true) {
				 try {
					int i= pipedReader.read();
					if(i==-1) {
						break;
					}
					char c=(char)i;
					System.out.println(c);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static class WriteThread extends Thread{
		PipedWriter pipedWriter;
		
		public WriteThread(PipedWriter pipedWriter) {
			this.pipedWriter=pipedWriter;
		}
		
		public void run () {
			String text="通信测试";
			try {
				pipedWriter.write(text.toCharArray());
				pipedWriter.flush();
				pipedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
