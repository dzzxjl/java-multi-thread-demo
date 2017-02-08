import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;

public class LightServer {
	static int[] flag;  
	static int count = 1;  //连接服务器的个数
	ArrayList<PrintWriter> clientOutputStreams = new ArrayList<>(); //ArrayList为动态数组
	Map<Integer, PrintWriter> map = new HashMap<Integer, PrintWriter>(); //改用map，不再使用list
	
	public static void main(String[] args) {
		new LightServer().go();	
		
	}

	public void go() {
		//clientOutputStreams = new ArrayList();  //存放每个socket的writer
		try {
			ServerSocket serverSocket = new ServerSocket(5001);
			System.out.println("节能路灯远程监控系统服务器端已上线，等待接收消息...");
			System.out.println("输入0+消息向所有路灯发送消息...");
			System.out.println("输入路灯ID+消息向所有路灯发送消息...");
			while (true) {
				//accept调用会阻塞，程序会停在这里等待，直到有客户端连接至此端口
				Socket clientSocket = serverSocket.accept();
				//PrinrWriter为过滤书写器
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
//				System.out.println(writer);
				
				map.put(count, writer);
//				System.out.println(map.get(count));
				//ClientHandler ch = new ClientHandler(clientSocket);
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
				
				Thread sendThread = new Thread(new ServerHandler());
				sendThread.start();
				
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public class ClientHandler implements Runnable {
		BufferedReader reader;  //读取
		Socket sock;
		char flag;
		
		public ClientHandler (Socket clientSocket) {
			try {
				sock = clientSocket;
				//InputStreamReader方法主要完成字节流到字符流的转换
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isReader);
			} catch (Exception e) {
				e.printStackTrace();
			}			
			
		}
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					
						System.out.println(message);
						tellEveryone(message);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	public class ServerHandler implements Runnable {
		
		Scanner sc = new Scanner(System.in);
		public void run() {
			
			
			while (true){
			try {
				String msg = sc.nextLine();
				System.out.println("mark");
				int index = Integer.parseInt(String.valueOf(msg.charAt(0)));
				if(index == 0){
					tellEveryone(msg);
				}else {
					PrintWriter writer = (PrintWriter)map.get(index);
					System.out.println(writer);
					writer.println(msg);
					writer.flush();
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		}
		
	}	
	
	public void tellEveryone(String message) {
		//Iterator it = clientOutputStreams.iterator();
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			try {

			    Map.Entry<Integer, PrintWriter> entry = (Entry<Integer, PrintWriter>) it.next(); 
				PrintWriter writer =(PrintWriter) entry.getValue();
				writer.println(message);
				writer.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
