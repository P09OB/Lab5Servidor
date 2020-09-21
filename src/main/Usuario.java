package main;

public class Usuario {
	
	private String id;
	private String username;
	private String password;
	

	
	public Usuario(String id, String username, String password) {
		
		this.id = id;
		this.username = username;
		this.password = password;
			
		
	}
	
	public void registro() {
		
		
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String name) {
		this.username = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
