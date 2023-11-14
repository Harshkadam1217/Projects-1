package com.chatapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

class Client {
	Socket socket;
	
	BufferedReader br;
	PrintWriter out; 
	public Client()
	{
		try {
			System.out.println("Sending request to server");
			socket =new Socket("127.0.0.1",7777);
			System.out.println("Connection is done..") ;
			
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(socket.getOutputStream()); 
			startReading();
			startWriting();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startReading()
	{
		//thread-read karke deta rahega
		Runnable r1=()->
		{ 
			System.out.println("Reader started..");
			try 
			{
				while(!socket.isClosed())
				{
				
					String msg=br.readLine();
					if(msg.equals("Exit"))
					{
						System.out.println("Server terminated the chat");
						System.out.println("Thank YOU FOR USING OUR APPLICATION");
						socket.close();
						break;
					}
					System.out.println("Server : "+ msg);
				} 
				
			}
			catch (IOException e) 
			{
				
				
				
			}
			
		};
		
		new Thread(r1).start();
	}
	
	public void startWriting() 
	{
		//thread -data user lega and the send karega client tak 
		Runnable r2=()->
		{
			System.out.println("Writer started...");
			try 
			{
				while(!socket.isClosed())
				{
					
					BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
					String content=br1.readLine();
					out.println(content);
					out.flush();
					
					if(content.equals("Exit"))
					{
						socket.close();
						break;
					}
				}
				System.out.println("You have disconnected yourself from the Server");
			}
			catch (Exception e) 
			{
				
				
			}
		};
		
		new Thread(r2).start();
	}

	public static void main(String[] args) {
		System.out.println("This is Client ");
		new Client();

	}

}
