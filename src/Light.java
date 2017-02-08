import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Light {
	int id;
	static BufferedReader reader;
	static PrintWriter writer;
	Socket sock;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public static void main(String[] args) {
		new Light(1);
	}
	
	public Light(int i) {
		try {
		setId(i);    //为此灯添加ID标识
		sock = new Socket("localhost",5001);
		InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
		reader = new BufferedReader(streamReader);
		writer = new PrintWriter(sock.getOutputStream());
		
		writer.println( i +"路灯上线，此时与服务器也建立了一个socket,服务器与此灯分别建立了一个线程用来处理这个socket之间的信息传发");
		
		writer.flush();
		
		Thread thread = new Thread(new IncomingReader());
		Thread thread2 = new Thread(new SendMessage());
		thread.start();
		thread2.start();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
		public class IncomingReader implements Runnable {
			
			public void run() {
				String message;
				//sendMessage();
				try {
					while ((message = reader.readLine()) != null) {
						System.out.println("read " + message);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}	
		
		public class SendMessage implements Runnable {
			
			public void run() {
				String message;
				sendMessage(id);
				try {
					while ((message = reader.readLine()) != null) {
						System.out.println("read " + message);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}	
		
	
		public static void sendMessage(int id) {
//			Scanner sc = new Scanner(System.in);
//			while (true) {
//				String msg = sc.nextLine();
//				System.out.println(msg);
//				writer.println(msg);
//				writer.flush();
//			}
			
			
			while (true){
				try {
					Thread.sleep(10000);     //路灯每隔10s向服务器发送一次状态信息
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			String msg = "模拟路灯" + id +"向服务器发送消息";
			writer.println(msg);
			writer.flush();
			}
			
		}
		
		
	}
	