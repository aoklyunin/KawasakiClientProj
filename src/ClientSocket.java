import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.ShortMessage;
import javax.swing.JOptionPane;

public class ClientSocket {
	int port=40000;
	final Timer time = new Timer();
	boolean flgOpenSocket = false;
  
    Socket socket;
    InputStream sin;
    OutputStream sout;
    DataInputStream in;
    DataOutputStream out;
    
    JPoints jp= new JPoints();
    LinkedList<String> fifo = new LinkedList<String>();
    public void flush(){
    	try {
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void sendPositionPoints(){
    	
    }
    boolean flgCom = false;
    public ClientSocket() {
    	 time.schedule(new TimerTask() {
 	        @Override
 	        public void run() { //ПЕРЕЗАГРУЖАЕМ МЕТОД RUN В КОТОРОМ ДЕЛАЕТЕ ТО ЧТО ВАМ НАДО
 	        	if (flgOpenSocket){
 	        		
 	        	   if (!socket.isConnected()){
 	        		   Custom.showMessage("Client disconnected");
 	        		   closeSocket();
 	        	   }else
 	        	   try {
 	        		   if(flgOpenSocket){
 	        			 /* if (!fifo.isEmpty()){
 	        				  test();  
 	        				  try {
								Thread.sleep(50);
								
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
 	        			   }*/
 	        			   while(in.available()!=0){	        			   
 	        				   char b = (char)in.readByte();
 	        				  /* if (b=='c'){
 	        					   flgCom = true;
 	        				   }else if (b>='0'&&b<='9'&&flgCom){
 	        					   flgCom = false;
 	        					   int com = (byte)b-(byte)'0';
 	        					   switch (com) {
 							   		case 0:
 							  //		if (!fifo.isEmpty())
 							   	//			sendPackage(fifo.removeFirst());
 							   		break;
 	        					   }
 	        				   }*/
 	        				   System.out.print(b);
 	        			   }
 	        			   
 	        		   }
 	        	   } catch (IOException e) {
 	        		   e.printStackTrace();
 	        	   }
 	        	}	
 	        }
 	    }, 100, 100); //(4000 - ПОДОЖДАТЬ ПЕРЕД НАЧАЛОМ В МИЛИСЕК, ПОВТОРЯТСЯ 4 СЕКУНДЫ (1 СЕК = 1000 МИЛИСЕК))
 	    
	}
   
 
	void sendVals(int []iVal, double []fVal){
		if(flgOpenSocket){
			String s=Custom.getVali(iVal[0],6)+" "+Custom.getVali(iVal[1],3)+" "+Custom.getVali(iVal[2],4)+" ";
			for (double a:fVal){
				s = s+Custom.getVald(a)+" ";
			}
			s = s.substring(0,s.length()-1);
			sendPackage(s);
		}
	}
	void sendVals(int []iVal){
		if(flgOpenSocket){
			String s="";
			for(int i=0;i<9;i++)
				s+=(char)((byte)'a'+i)+Custom.getVali(iVal[i],6);			
			s+="/";
			sendPackage(s);
		}
	}
	void sendVals2(int []iVal){
		if(flgOpenSocket){
			String s="";
			for(int i=0;i<9;i++)
				s+=Custom.getVali(iVal[i],6)+" ";			
			//s+="/";
			sendPackage(s);
		}
	}
	void sendPackage(String s){
		try {
			
			for (int i=0;i<s.length();i++){
				out.writeByte((byte)s.charAt(i));	
			}	
		    
			
			//out.writeUTF(s);
			
			//System.out.println(s);
			
		} catch (IOException e) {
			System.out.println("IO Error");
			e.printStackTrace();
		} 
	}
	void openSocket(String adress,String port){
	    try {
	    	InetAddress ipAddress = InetAddress.getByName(adress); // создаем объект который отображает вышеописанный IP-адрес.
            socket = new Socket(ipAddress, Integer.parseInt(port)); // создаем сокет используя IP-адрес и порт сервера.
            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом. 
            sin = socket.getInputStream();
            sout = socket.getOutputStream();
            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            in = new DataInputStream(sin);
            out = new DataOutputStream(sout);
	        flgOpenSocket = true;
	        Custom.showMessage("Socket connected");
	      } catch(Exception x) { 
	    	  x.printStackTrace(); 
	    	  Custom.showMessage("Socket open error");  
	     }
	}
	void closeSocket(){
		flgOpenSocket = false;
		try {
			in.close();
			sin.close();
			out.close();
			sout.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		Custom.showMessage("Socket closed");
	}
	public void addJPoint(double j1,double j2,double j3,double j4,double j5,double j6,String type){
		jp.add(j1,j2,j3,j4,j5,j6,type);
	}
	public void sendJPoints(){
		ArrayList<String> lst = jp.getRequests();
		if (lst!=null){
			for (String s:lst){
				sendPackage(s);
			}
		}else{
			Custom.showMessage("Нет ни одной точки на отправку");
		}
	}
	public void clearJPoints(){
		jp.clear();
	}
	public void test(){
		if(flgOpenSocket)
		try {
			for (int i=0;i<4;i++){
				out.writeByte((byte)'b');	
			}	
			out.flush();
		} catch (IOException e) {
			System.out.println("IO Error");
			e.printStackTrace();
		} 
	}

}
