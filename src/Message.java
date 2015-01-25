import java.io.Serializable;

public class Message implements Serializable{
	private String src;
	private String dest;
	private String kind;
	private int seq;
	private Object data;
	
	public Message(String dest, String kind, Object data){
		
	}
	public Message(Message recv) {
		
	}
	public void set_source(String source){
		
	}
	public void set_seqNum(int sequenceNumber){
		
	}
	public void set_duplicate(Boolean dupe){
		
	}
	public String get_src(){return src;}
	public String get_dest(){return dest;}
	public String get_kind(){return kind;}
	public int get_int_seq(){return seq;}
	public String get_seq(){return String.valueOf(seq);}
}