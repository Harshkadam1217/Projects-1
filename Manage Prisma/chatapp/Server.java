package com.chatapp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Server 
{
	ServerSocket server;
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	
	
	//Constructor..
	
	
	public Server()
	{
		try 
		{
			server=new ServerSocket(7777);
			System.out.println("Server is ready to accept connection");
			System.out.println("Waiting...");
			socket=server.accept();
			
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(socket.getOutputStream()); 
			startReading();
			startWriting();
			
		} 
		catch (IOException e) 
		{
			
			System.out.println("The Client has terminated the connection 1");
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
						System.out.println("Client terminated the chat");
						break;
					}
					System.out.println("Client : "+ msg);
				}
//				System.out.println("Client Connection is disconnected c1.1");
			}
			catch (IOException e) 
			{
				//System.out.println("Client Connection is disconnected c1.2");
				//System.out.println("The Client has terminated the connection 2");
				
				
			}
		};
		
		new Thread(r1).start();
	}
	public void startWriting() 
	{
		//thread -  user data  lega and the send karega client tak 
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
						System.out.println("You have also disconnected");
						socket.close();
						break;
					}
				}
			}
			catch (Exception e) 
			{
//				System.out.println("Server Connection is disconnected c2.2");	
			}
		};
		new Thread(r2).start();
	}
	public static void main(String[] args) 
	{
		System.out.println("This is Server ..going to start Server");
		new Server(); 
	}

}
