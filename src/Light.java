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
		setId(i);    //Ϊ�˵����ID��ʶ
		sock = new Socket("localhost",5001);
		InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
		reader = new BufferedReader(streamReader);
		writer = new PrintWriter(sock.getOutputStream());
		
		writer.println( i +"·�����ߣ���ʱ�������Ҳ������һ��socket,��������˵Ʒֱ�����һ���߳������������socket֮�����Ϣ����");
		
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
					Thread.sleep(10000);     //·��ÿ��10s�����������һ��״̬��Ϣ
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			String msg = "ģ��·��" + id +"�������������Ϣ";
			writer.println(msg);
			writer.flush();
			}
			
		}
		
		
	}
	