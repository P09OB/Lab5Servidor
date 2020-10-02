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
	String name;
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
		String id3 = UUID.randomUUID().toString();

		
		users.add(new Usuario(id,"paula","372628hd"));
		users.add(new Usuario(id2,"aron","5938392"));
		users.add(new Usuario(id3,"maria09","123456"));
		
		servidor();
		
		
		Gson gson = new Gson();
	}
	
	public void draw() {
		
	
		switch(cambio){
		
		case 0:
			
			background(109, 104, 117);
			textSize(20);
			text("Ingrese su usuario y contraseña desde su teléfono móvil",100,200,334, 80);
			
			break;
			
		case 1:
			background(181, 131, 141);
			text("Bienvenido",100,200);
			text(name,100,240);
			
			break;
			
		case 3:
			background(181, 131, 141);
			text("los datos ingresados son incorrectos",100,200);
			break;
			
			
		}
		
		text("X:"+" " +mouseX+"  "+"Y:"+" "+mouseY,mouseX,mouseY);
		
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
								
								name = e.getUsername();
								String password = e.getPassword();
								
								if(name.contains(obj.getUsername()) && password.contains(obj.getPassword())) {
									
									
									System.out.println("ES CORRECTO");
									cambio =1;
									
									confi = true;
									
					
								} else {
									
									confi = false;
									System.out.println("NO REGISTRADO");
									cambio =3;
									
									
								}
								String ID = UUID.randomUUID().toString();
								Confirmacion con = new Confirmacion(ID,confi);
								String json = gson.toJson(con);
								enviar(json);
								

							
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
