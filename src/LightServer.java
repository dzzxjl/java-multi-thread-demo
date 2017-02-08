import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;

public class LightServer {
	static int[] flag;  
	static int count = 1;  //���ӷ������ĸ���
	ArrayList<PrintWriter> clientOutputStreams = new ArrayList<>(); //ArrayListΪ��̬����
	Map<Integer, PrintWriter> map = new HashMap<Integer, PrintWriter>(); //����map������ʹ��list
	
	public static void main(String[] args) {
		new LightServer().go();	
		
	}

	public void go() {
		//clientOutputStreams = new ArrayList();  //���ÿ��socket��writer
		try {
			ServerSocket serverSocket = new ServerSocket(5001);
			System.out.println("����·��Զ�̼��ϵͳ�������������ߣ��ȴ�������Ϣ...");
			System.out.println("����0+��Ϣ������·�Ʒ�����Ϣ...");
			System.out.println("����·��ID+��Ϣ������·�Ʒ�����Ϣ...");
			while (true) {
				//accept���û������������ͣ������ȴ���ֱ���пͻ����������˶˿�
				Socket clientSocket = serverSocket.accept();
				//PrinrWriterΪ������д��
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
		BufferedReader reader;  //��ȡ
		Socket sock;
		char flag;
		
		public ClientHandler (Socket clientSocket) {
			try {
				sock = clientSocket;
				//InputStreamReader������Ҫ����ֽ������ַ�����ת��
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
