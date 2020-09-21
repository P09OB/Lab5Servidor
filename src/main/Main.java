package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.Gson;

import model.Confirmacion;
import model.Usuarios;
import processing.core.PApplet;

public class Main extends PApplet {
	
	ArrayList<Usuario> users;
	OutputStream os;
	BufferedReader reader;
	BufferedWriter writer;
	int cambio;
	boolean confi;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PApplet.main("main.Main");
	
	}
	
    public void settings() {
    	
    	size(500,500);
		
	}

	
	public void setup() {
	
		users = new ArrayList<Usuario>();
		cambio =0;
		String id = UUID.randomUUID().toString();
		String id2 = UUID.randomUUID().toString();
		
		users.add(new Usuario(id,"paula","372628hd"));
		users.add(new Usuario(id2,"aron","5938392"));
		
		servidor();
		
		
		Gson gson = new Gson();
	}
	
	public void draw() {
		
		
		switch(cambio){
		
		case 0:
			
			background(109, 104, 117);
			text("HOLA",50,50,50,50);
			
			break;
			
		case 1:
			background(181, 131, 141);
			text("ENTRAMOS",50,50,50,50);
			
			break;
			
			
		}
		
		
		
	}
	
	public void mousePressed() {
		
		
	}
	
	public void servidor() {
		
new Thread(
				
				()->{
					
					try {
						
						System.out.println("Esperando...");
						ServerSocket server = new ServerSocket(5000);
						Socket socket = server.accept();
						System.out.println("concetado...");
						
						os = socket.getOutputStream();
						os.write(45);
						
						//RECIBIR
						InputStream is = socket.getInputStream();
						//NECESITAMOS OTRO IMPUT QUE ME LEA EL MENSAJE
						InputStreamReader isr = new InputStreamReader(is);
						reader = new BufferedReader(isr);
						
						OutputStream os = socket.getOutputStream();
						OutputStreamWriter osw = new OutputStreamWriter(os);
						writer = new BufferedWriter(osw);
						
						while(true) {
							System.out.println("esperando mensaje");
							String mensaje = reader.readLine();
							System.out.println(mensaje);
							Gson gson = new Gson();
							Usuarios obj =gson.fromJson(mensaje,Usuarios.class);
							System.out.println(obj.getUsername());
							System.out.println(obj.getPassword());
							
							for(Usuario e : users) {
								
								String name = e.getUsername();
								String password = e.getPassword();
								
								if(name.equals(obj.getUsername()) && password.equals(obj.getPassword())) {
									
									
									System.out.println("ES CORRECTO");
									cambio =1;
									String ID = UUID.randomUUID().toString();
									confi = true;
									Confirmacion con = new Confirmacion(ID,confi);
									String json = gson.toJson(con);
									enviar(json);
									
					
								} else {
									
									System.out.println("NO REGISTRADO");
									cambio =0;
									confi = false;
									
								}

							
							}
							
							

							
						}
						
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				}
				
				
				).start();


	}
	
	
	public void enviar (String mensaje) {
        new Thread(

                ()->{
                    try {

                        writer.write(mensaje+ "\n");
                        writer.flush();



                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        ).start();




    }
}
