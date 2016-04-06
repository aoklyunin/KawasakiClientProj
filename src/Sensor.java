import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.ejml.simple.SimpleMatrix;

import com.atiia.automation.sensors.NetFTRDTPacket;
import com.atiia.automation.sensors.NetFTSensor;


public class Sensor {
	String m_strSensorAddress;
    public NetFTSensor m_netFT; /*the Net F/T controller*/
    public NetFTReaderThread m_NetFTReaderThread = null; 
    private DatagramSocket m_cNetFTSlowSocket;
    
    int vals[] = new int[6];
    int timeToSleep;
    
    public int [] getVals(){
    	return vals;
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
	    public SimpleMatrix calcRMatrix(int o,int a,int t){
	    	return new SimpleMatrix();
	    }
	    
	    public void dispData(NetFTRDTPacket displayRDT){
	    	vals[0] = displayRDT.getFx()/1000;
	    	vals[1] = displayRDT.getFy()/1000;
	    	vals[2] = displayRDT.getFz()/1000;
	    	vals[3] = displayRDT.getTx()/1000;
	    	vals[4] = displayRDT.getTy()/1000;
	    	vals[5] = displayRDT.getTz()/1000;
	    }
	}
	

}
