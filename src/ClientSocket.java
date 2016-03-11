import java.awt.List;
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
	final static int C_STOP = 4;// ���������
	final static int C_J = 5; //���������� ���������� ��������
	final static int C_GetPositionAxis = 6; //�������� ��������� ���
	final static int C_GetPosition = 7; //�������� ��������� ����������
	final static int C_J_POINT = 8; //���������� �� ����
	final static int C_D_POINT = 9; //���������� �� �����������
	final static int C_HOME1 = 10; //����� � �������� ���� 1
	final static int C_HOME2 = 11;// ����� � �������� ���� 2
	final static int C_ERR = 12; // ������
	final static int ERR_INRANGE_J1 = 1; //�� ������� 1 �� �������
	final static int ERR_INRANGE_J2 = 2; //�� ������� 2 �� �������
	final static int ERR_INRANGE_J3 = 4; //�� ������� 3 �� �������
	final static int ERR_INRANGE_J4 = 8; //�� ������� 4 �� �������
	final static int ERR_INRANGE_J5 = 16; //�� ������� 5 �� �������
	final static int ERR_INRANGE_J6 = 32; //�� ������� 6 �� �������
	final static int ERR_NOT_INRANGE = 32786; //��� ������������
	
	final static int[] ULIMIMT = { 160,  140,  120,  270,  145,  360};
	final static int[] LLIMIMT = {-160, -105, -155, -270, -145, -360};
	
	int port=40000;
	final Timer time = new Timer();
	boolean flgOpenSocket = false;
  
    Socket socket;
    InputStream sin;
    OutputStream sout;
    DataInputStream in;
    DataOutputStream out;
    
    String inStr = "";
    
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
    int pos = 0;
    boolean flgSpace = false;
    boolean flgFirstSpace = true;
    int [] lst = new int[9];
    int rotations[] = new int [6];
    int positions[] = new int [6];
    
    public int [] getRotations(){
    	return rotations;
    }
    public int [] getPositions(){
    	return positions;
    }
    public void runInPointD(int j1,int j2,int j3,int j4,int j5,int j6,String type,int speed){
    	int [] arr  = {0,C_D_POINT,speed,j1,j2,j3,j4,j5,j6};   
    	sendVals2(arr);
    }
    public void home1(int speed){
    	int [] arr  = {0,C_HOME1,speed,0,0,0,0,0,0};
		sendVals2(arr);
    }
    
    public void home2(int speed){
    	int [] arr  = {0,C_HOME2,speed,0,0,0,0,0,0};
		sendVals2(arr);
    }
    public void getChars(){
    	if (flgOpenSocket){ 	        		
      	   if (!socket.isConnected()){
      		   Custom.showMessage("Client disconnected");
      		   closeSocket();
      	   }else
      	   try {
      		   if(flgOpenSocket){ 	        			  
      			   while(in.available()!=0){	
      				   char c = (char)in.readByte();
      				   if (c==' '){
      					   if (!flgSpace&&!flgFirstSpace){
      						   pos++;
      						   lst[pos-1] = Integer.parseInt(inStr);
      						   inStr = "";
      						   if (pos>=9){
      							   switch(lst[1]){
      							   		case  C_GetPositionAxis:
      							   			for (int i=0;i<6;i++)
      							   				rotations[i]=lst[i+3];
      							   		break;
      							   		case  C_GetPosition:
      							   			for (int i=0;i<6;i++)
      							   				positions[i]=lst[i+3];
  							   			break;
      							   		case C_ERR:
      							   			switch (lst[2]){
      							   				case ERR_NOT_INRANGE:
      							   					Custom.showMessage("�������� ����� ��������� ��� ������������");		
      							   				break;
      							   			}
      							   		break;
      							   }
      							   pos = 0;
      						   }
      					   }
      					   flgSpace = true;      					   
      				   }else{
      					   inStr += c;
      					   flgSpace = false; 
      					   flgFirstSpace = false;
      				   }
      				
      			   }
      		   }
      	   } catch (IOException e) {
      		   e.printStackTrace();
      	   }
    	}
    }
    boolean flgCom = false;
    public ClientSocket() {
    	 time.schedule(new TimerTask() {
 	        @Override
 	        public void run() { //������������� ����� RUN � ������� ������� �� ��� ��� ����
 	        		getChars();	
 	        }
 	    }, 100, 100); //(4000 - ��������� ����� ������� � �������, ���������� 4 ������� (1 ��� = 1000 �������)) 	    
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
	    	InetAddress ipAddress = InetAddress.getByName(adress); // ������� ������ ������� ���������� ������������� IP-�����.
            socket = new Socket(ipAddress, Integer.parseInt(port)); // ������� ����� ��������� IP-����� � ���� �������.
            // ����� ������� � �������� ������ ������, ������ ����� �������� � �������� ������ ��������. 
            sin = socket.getInputStream();
            sout = socket.getOutputStream();
            // ������������ ������ � ������ ���, ���� ����� ������������ ��������� ���������.
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
		int [] arr  = {0,C_STOP,0,0,0,0,0,0,0};
		getChars();
		flush();
		sendVals2(arr);
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
	public void addJPoint(int j1,int j2,int j3,int j4,int j5,int j6,String type,int speed){
		if (speed<=0) speed = 1;
		jp.add(j1,j2,j3,j4,j5,j6,type,speed);
	}
	public void sendJPoints(){
		ArrayList<String> lst = jp.getRequests();
		if (lst!=null){
			for (String s:lst){
				sendPackage(s);
			}
		}else{
			Custom.showMessage("��� �� ����� ����� �� ��������");
		}
	}
	public void clearJPoints(){
		jp.clear();
	}
	public void test(){
		if(flgOpenSocket)
			System.out.println("Test Operation");
		else
			System.out.println("Socket is closed");	
	}

}
