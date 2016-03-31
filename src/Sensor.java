import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.ejml.simple.SimpleMatrix;

import com.atiia.automation.sensors.NetFTRDTPacket;
import com.atiia.automation.sensors.NetFTSensor;
import Jama.*; 

public class Sensor {
	String m_strSensorAddress;
    public NetFTSensor m_netFT; /*the Net F/T controller*/
    public NetFTReaderThread m_NetFTReaderThread = null; 
    private DatagramSocket m_cNetFTSlowSocket;
    
    int vals[] = new int[6];
    int rVals[]=new int[6];
    int angles[] = new int[3];
    int timeToSleep;
    double rMatrix[][] = new double[3][3];
    
    public int [] getVals(){
    	return vals;
    }
    public int [] getRVals(){
    	return rVals;
    }
    
    public void setAngles( int [] angles){
    	this.angles = angles;
    }
    
    // конструктор по умолчанию   
    Sensor(){
    	m_strSensorAddress = "192.168.1.1";
    }
    // конструктор от конкретного ip
    Sensor(String ip){
    	m_strSensorAddress = ip;
    }
   
    public void start(int timeToSleep){
    	this.timeToSleep = timeToSleep;
    	 // если чтение уже было запущено
    	 if ( null != m_NetFTReaderThread ){
    		 // останавливаем его
             m_NetFTReaderThread.stopReading();   
             m_NetFTReaderThread = null;
         }
         try{
        	 // подключаемся к сенсору
             m_netFT = new NetFTSensor( InetAddress.getByName( 
                                        m_strSensorAddress ) );
         } catch ( UnknownHostException uhex ){     
        	 System.out.println("Ошибка подключения к сенсору");
             return;
         }   
         try{
             m_cNetFTSlowSocket = m_netFT.initLowSpeedData();
         }catch ( SocketException sexc ){
        	 System.out.println("Ошибка подключения к сокету");       
         }catch ( IOException iexc ){
        	 System.out.println("Ошибка ввода/вывода");
         }
         // создаём и запускаем поток чтения данных с датчика
         m_NetFTReaderThread = new NetFTReaderThread( m_netFT);        
         m_NetFTReaderThread.start();
    }
    public void stop(){
    	// если был создан поток
    	if(m_NetFTReaderThread!=null)
    		m_NetFTReaderThread.stopReading();
    	System.out.println("Sensor stopped");
    }
	
	
	public class NetFTReaderThread extends Thread{
	    
	    private NetFTSensor m_netFT;                                     
	    private boolean m_bKeepGoing = true;
	    
	    public NetFTReaderThread( NetFTSensor setNetFT){
	        m_netFT = setNetFT;
	    }   
	    public void stopReading(){
	        m_bKeepGoing = false;
	        this.interrupt();
	    }
	    @Override
	    public void run(){
	        NetFTRDTPacket cRDTData; /*the latest f/t data from the Net F/T*/
	        while ( m_bKeepGoing ){                
	            try{
	                Thread.yield(); 
	                synchronized ( m_netFT ){	       
	                    cRDTData = m_netFT.readLowSpeedData(m_cNetFTSlowSocket);               
	                }
	                dispData( cRDTData );
	            }catch ( SocketException sexc ){
	            }catch ( IOException iexc ){
	                           
	            }
	            try{           
	                    Thread.sleep(timeToSleep);
	            }catch ( java.lang.InterruptedException iexc ){             
	            }
	            
	        }
	    }
	    public void dispData(NetFTRDTPacket displayRDT){
	    	vals[0] = displayRDT.getFx()/1000;
	    	vals[1] = displayRDT.getFy()/1000;
	    	vals[2] = displayRDT.getFz()/1000;
	    	vals[3] = displayRDT.getTx()/1000;
	    	vals[4] = displayRDT.getTy()/1000;
	    	vals[5] = displayRDT.getTz()/1000;
	    	int x = vals[0];
	    	int y = vals[1];
	    	int z = vals[2];
	    	
	    	double o = angles[0]/360*Math.PI;
	    	double a = angles[1]/360*Math.PI;
	    	double t = angles[2]/360*Math.PI;
	    	
	    	double arrayX [][]={{1,0,0},
	    					    {0,Math.cos(o),-Math.sin(o)},
	    					    {0,Math.sin(o),Math.cos(o)}};
	    	SimpleMatrix Rx = new SimpleMatrix(arrayX);
	    	double arrayY [][]={{Math.cos(a),0,Math.sin(a)},
	    						{0,	1, 0},
	    						{-Math.sin(a),0,Math.cos(a)}};
	    	SimpleMatrix Ry = new SimpleMatrix(arrayY);
	    	double arrayZ [][]={{Math.cos(t),-Math.sin(a),0},
								{Math.sin(a),Math.cos(a), 0},
								{0,0,1}};
	    	SimpleMatrix Rz = new SimpleMatrix(arrayZ);
	    	
	    	SimpleMatrix R  = Rz.mult(Ry).mult(Rx);
	    	SimpleMatrix Ro = R.invert();
	    	double arrayV[][] = {{x,y,z}}; 
	    	SimpleMatrix V = new SimpleMatrix(arrayV);
	    	SimpleMatrix Vn = V.mult(Ro);
	    	rVals[0] = (int)Vn.get(0, 0);
	    	rVals[1]  = (int)Vn.get(0, 1);
	    	rVals[2]  = (int)Vn.get(0, 2);
	    	for(int i=0;i<3;i++)
	    	for(int j=0;j<3;j++)
	    		rMatrix[i][j] = Ro.get(i, j);
	    	
	    }
	}
	public double[][] getRMatrix(){
		return rMatrix;
	}
	

}
