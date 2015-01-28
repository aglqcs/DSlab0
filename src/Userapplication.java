import java.io.IOException;
import java.util.Scanner;


public class Userapplication implements Runnable{
	// When user create message: should call set_seqnumber, set_dest,set_kind
	// server port should choose to what?
	public static MessagePasser mp;
	public Userapplication(){
		
	}
	public static void main(String[] args) throws IOException{
		String configuration_addr = "/home/chenshuo/18842/lab0/configuration";
		Scanner scanner = new Scanner(System.in);
		System.out.println("input local name:");
		String localname = scanner.nextLine();
		mp = new MessagePasser(configuration_addr,localname);
		Userapplication listener = new Userapplication();
		new Thread(listener).start();
		System.out.println("start send (input format: dest \\n kind \\n data \\n )");
		while(true){
			String dest = scanner.nextLine();
			String kind = scanner.nextLine();
			String data = scanner.nextLine();
			Message to_send = new Message(dest,kind,data);
			mp.send(to_send);
		}
	}

	public void run() {
		while(true){
			try {
				Thread.sleep(1000);
				if( mp != null){
					Message recv = mp.receive();
					if( recv != null){
						System.out.println("[RECV]	"+recv.get_src() +":"+ recv.get_data().toString());
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
