import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import org.yaml.snakeyaml.Yaml;

public class MessagePasser {
	private String configuration_file;
	private String local_name;
	private static ArrayList<Rule> send_rules;
	private static ArrayList<Rule> recv_rules;
	private HashMap<String, Socket> connections; // stores <dest_name, socket>
	private HashMap<String, Host> hosts;// stores <dest_name, host>
	private int server_port;
	
	public MessagePasser(String configuration_filename, String local_name) throws IOException{
		this.configuration_file = configuration_filename;
		this.local_name = local_name;
		send_rules = new ArrayList<Rule>();
		recv_rules = new ArrayList<Rule>();
		
		/*TODO use yaml to load configuration file, updates rules*/		
		if ( !parse_configuration(this.configuration_file) ){
			System.out.println("cannot init configuration exit");
			return;
		}
		
		/* start one thread to listen */
		ServerSocket socket = new ServerSocket(server_port);
		while(true){
			Socket connectionSocket = socket.accept();
        	InetAddress dest = connectionSocket.getInetAddress();
        	Listener listen = new Listener(connectionSocket);
        	new Thread(listen).start();
		}
	}
	public void send(Message message){
		/*TODO get info of this message and check send rules */
		/*TODO get a socket from connection list, if not exist, create another socket and send message*/
	}
	public Message receive(){
		Listener p = new Listener();
		if( p.get_recv_queue().isEmpty()){
			return null;
		}
		else{
			/* need to clear the delay queue because we deliver a non-delayed message*/
			Listener.clear_delay_queue();
			return p.get_recv_queue().poll();
		}
	}
	private boolean parse_configuration(String file_name){
		/* TODO how to use yaml? */
		return true;
	}
	public static int recv_check(Message recv){
		for(Rule r:recv_rules){
			boolean src = (null == r.get_src()) || ( null != r.get_src() && r.get_src().equalsIgnoreCase(recv.get_src()));
			boolean dest = (null == r.get_dest()) || (null != r.get_dest() && r.get_dest().equalsIgnoreCase(recv.get_dest()));
			boolean kind = (null == r.get_kind()) || (null != r.get_kind())&& r.get_kind().equalsIgnoreCase(recv.get_kind());
			boolean seq = (0 == r.get_int_seqNum()) || ((0 != r.get_int_seqNum()) && r.get_int_seqNum() == recv.get_int_seq());
			if(src && dest && kind && seq){
				if(r.get_action().equalsIgnoreCase("drop")) return 1;
				if(r.get_action().equalsIgnoreCase("delay")) return 2;
				if(r.get_action().equalsIgnoreCase("duplicate")) return 3;
			}
		}
		return 0;
	}
	public static void main(String[] args) throws IOException{
	/* TODO need delete, for testing */
		MessagePasser test = new MessagePasser("E:\\研究生二\\842\\config.txt","host");
	}
}