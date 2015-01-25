
public class Host {
	private String name;
	private String ip;
	private String port;
	public Host(String name,String ip,String port){
		this.name = name;
		this.ip = ip;
		this.port = port;
	}
	public String get_name(){
		return name;
	}
	public String get_ip(){
		return ip;
	}
	public String get_port(){
		return port;
	}
}