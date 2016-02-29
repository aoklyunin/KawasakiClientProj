import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
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
 	        		   boolean flgEnable = true;
 	        		   while(flgOpenSocket&&in.available()!=0){	        			   
 						   byte b = in.readByte();
 						   System.out.print((char)b);
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
	void sendPackage(String s){
		try {
			for (int i=0;i<s.length();i++){
				out.writeByte((byte)s.charAt(i));	
			}	
			out.flush();
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

}
