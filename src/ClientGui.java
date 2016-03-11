/*  TODO:
 *  1) кнопка остановки выполнения программы роботом
 * 
 * 
 */

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.Subject;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AbstractDocument.LeafElement;

public class ClientGui extends JFrame {
	// элементы вкладки "пакеты
	private JButton btnOpenSocket = new JButton("OpenSocket");
	private JButton btnCloseSocket = new JButton("CloseSocket");
	private JButton btnClearInputs = new JButton("ClearInputs");
	private JButton btnSendPackage = new JButton("SendPackage");
	private JTextField portSocet = new JTextField("40000", 5);
	private JTextField adressSocket = new JTextField("192.168.1.0", 8);
	final static int inputsCnt = 99;	
	private JTextField [] inputs = new JTextField[inputsCnt];
	private JButton btnTest = new JButton("Test");
	private JLabel ipLabel = new JLabel();	
	// элементы вкладки джоиндов
	private JButton btnAddJPoint = new JButton("AddPoint");
	private JButton btnSendJPoinst = new JButton("SendJPoint");
	private JButton btnClearJPoinst = new JButton("ClearPoint");	
	private JButton btnSendPosition = new JButton("SendPosition");
	private JButton [] buttons = new JButton[10];
	private JSlider [] sliders = new JSlider[6];
	private JLabel[] sliderVals = new JLabel[6];
	private JLabel[] slederLables = new JLabel[6];
	private JLabel[] limitLables = new JLabel[6];
	private JTextField jSpeedInput = new JTextField("10");
	// элементы вкладки позиций
	private JSlider [] slidersPos = new JSlider[6];
	private JLabel[] sledersPosLables = new JLabel[6];
	private JLabel[] slierDekartVals = new JLabel[6];
	private JLabel[] slierDekartPoss = new JLabel[6];
	private JButton btnHome1 = new JButton("Home1");
	private JButton btnHome2 = new JButton("Home2");
	private JButton btnSet = new JButton("Set");
	private JTextField dSpeedInput = new JTextField("10");
	// элементы обратной связи
	public JLabel [] jPosLables = new JLabel[6]; 
	
	final JTabbedPane tabbedPane = new JTabbedPane();
	ClientSocket client;
	
	final Timer time = new Timer();
	
    public void clearInputs(){
    	for (int j=0;j<inputsCnt;j++)
			inputs[j].setText("");
    }
    // создаём страницу для работы с пакетами
    public void createPackagePage(){
    	Container packagePage = new JPanel();
    	packagePage.setLayout(null);
  	    tabbedPane.addTab("Пакеты" , packagePage);
  	    Insets insets = packagePage.getInsets();
  	    // поля ввода
	    for (int i=0;i<inputsCnt;i++){
	    	inputs[i] = new JTextField("", 5);
	    	packagePage.add(inputs[i]);
	    	Dimension size = inputs[i].getPreferredSize(); 
	    	inputs[i].setBounds(10 + (i%9)*70 + insets.left, 80+(i/9)*30 + insets.top, 
	                     size.width, size.height); 
	    }
	    // поле ввода с адресом сервера
	    packagePage.add(adressSocket);
    	Dimension size = adressSocket.getPreferredSize(); 
    	adressSocket.setBounds(130 + insets.left, 40 + insets.top, 
                     size.width, size.height);
    	// поле ввода порта сокета
    	packagePage.add(portSocet);
    	size = portSocet.getPreferredSize(); 
    	portSocet.setBounds(130 + insets.left, 15 + insets.top, 
                     size.width, size.height);
    	// кнопка открытия сокета
    	packagePage.add(btnOpenSocket);
	    size = btnOpenSocket.getPreferredSize(); 
	    btnOpenSocket.setBounds(15 + insets.left, 10 + insets.top, 
                     	 size.width, size.height); 
	    btnOpenSocket.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				client.openSocket(adressSocket.getText(),portSocet.getText());
			}	    	
	    });
	    // кнопка тест
    	packagePage.add(btnTest);
	    size = btnTest.getPreferredSize(); 
	    btnTest.setBounds(485+ insets.left, 40 + insets.top, 
                     	 size.width, size.height); 
	    btnTest.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				client.test();
			}	    	
	    });
	    
	    // кнопка отправки пакета
	    packagePage.add(btnSendPackage);
	    size = btnSendPackage.getPreferredSize(); 
	    btnSendPackage.setBounds(360 + insets.left, 40 + insets.top, 
                     	 size.width, size.height); 
	    btnSendPackage.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				defPackage();
			}	    	
	    });
	    // кнопка очистки полей ввода
	    packagePage.add(btnClearInputs);
	    size = btnClearInputs.getPreferredSize(); 
	    btnClearInputs.setBounds(250 + insets.left, 40 + insets.top, 
                     	 size.width, size.height); 
	    btnClearInputs.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clearInputs();
			}	    	
	    });	    
	    // IP Адрес
		try {
			InetAddress IP = InetAddress.getLocalHost();
			ipLabel.setText(IP.getHostAddress());
			packagePage.add(ipLabel);
		    size = ipLabel.getPreferredSize(); 
		    ipLabel.setBounds(300 + insets.left, 10 + insets.top, 
	                     	 size.width, size.height); 
		} catch (UnknownHostException e) {
			e.printStackTrace();
			Custom.showMessage("Не получилось определить IP");	
		}
		// кнопка закрытия сокета
		packagePage.add(btnCloseSocket);
	    size = btnCloseSocket.getPreferredSize(); 
	    btnCloseSocket.setBounds(15 + insets.left, 45 + insets.top, 
                     	 size.width, size.height);
	    btnCloseSocket.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				client.closeSocket();
			}	    	
	    });	    
    }
    public void createJoindPage(){    
    	Container joindPage = new JPanel();
    	joindPage.setLayout(null);
    	Insets insets = joindPage.getInsets();
    	tabbedPane.addTab("Джоинды" ,joindPage);
    	 // кнопка отправки точек на контроллер
    	joindPage.add(btnSendJPoinst);
    	Dimension size = btnSendJPoinst.getPreferredSize(); 
	    btnSendJPoinst.setBounds(460 + insets.left, 50 + insets.top, 
	    					     100, size.height); 
	    btnSendJPoinst.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				client.sendJPoints();
			}	    	
	    });
	    // кнопка удаления всех точек
	    joindPage.add(btnClearJPoinst);
	    size = btnClearJPoinst.getPreferredSize(); 
	    btnClearJPoinst.setBounds(460 + insets.left, 110 + insets.top, 
	    					   100, size.height); 
	    btnClearJPoinst.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				client.clearJPoints();
			}	    	
	    });
	    // кнопка добавления точек поворота
	    joindPage.add(btnAddJPoint);
	    size = btnAddJPoint.getPreferredSize(); 
	    btnAddJPoint.setBounds(460 + insets.left, 80 + insets.top, 
                     	 	   100, size.height); 
	    btnAddJPoint.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				client.addJPoint(sliders[0].getValue(),
							     sliders[1].getValue(),
							     sliders[2].getValue(),
							     sliders[3].getValue(),
							     sliders[4].getValue(),
							     sliders[5].getValue(),JPoints.ABSOLUTE,Integer.parseInt(jSpeedInput.getText()));
			}	    	
	    });	  
	    size = jSpeedInput.getPreferredSize(); 
	    jSpeedInput.setBounds(460 + insets.left, 140 + insets.top, 
      	 	   100, size.height); 
    	joindPage.add(jSpeedInput);
	    // слайдеры
	    for (int i=0;i<6;i++){
	    	sliders[i] = new JSlider(-360, 360, 0);
	    	joindPage.add(sliders[i]);
	    	sliders[i].setMajorTickSpacing(120);
	    	sliders[i].setMinorTickSpacing(30);
	    	sliders[i].setPaintLabels(true);
	    	sliders[i].setPaintTicks(true);
	    	sliders[i].setPaintTrack(true);
	    	sliders[i].setAutoscrolls(true);
	    	size = sliders[i].getPreferredSize(); 
	    	sliders[i].setBounds(30 + insets.left, 40+i*50 + insets.top, 
                     	 230, size.height);
	    	final int pos = i;
	    	sliders[pos].addChangeListener(new ChangeListener() {				
				@Override
				public void stateChanged(ChangeEvent arg0) {
					// TODO Auto-generated method stub
					sliderVals[pos].setText(sliders[pos].getValue()+"");
				}
			});
	    	slederLables[i] = new JLabel("j"+i);
	    	size = slederLables[i].getPreferredSize(); 
	    	slederLables[i].setBounds(10 + insets.left, 40+i*50 + insets.top, 
                	 230, size.height);
	    	joindPage.add(slederLables[i]);
	    	sliderVals[i] = new JLabel("0");
	    	size = sliderVals[i].getPreferredSize(); 
	    	sliderVals[i].setBounds(280 + insets.left, 40+i*50 + insets.top, 
                	 230, size.height);
	    	joindPage.add(sliderVals[i]);	    	
	    }
	    // монитор положений
	    for (int i=0;i<6;i++){
	    	jPosLables[i] = new JLabel("0");
	    	size = jPosLables[i].getPreferredSize(); 
	    	jPosLables[i].setBounds(320 + insets.left, 40+i*50 + insets.top, 
                	 230, size.height);
	    	joindPage.add(jPosLables[i]);
	    	limitLables[i] = new JLabel("["+ClientSocket.ULIMIMT[i]+":"+ClientSocket.LLIMIMT[i]+"]");
	    	size = limitLables[i].getPreferredSize(); 
	    	limitLables[i].setBounds(360 + insets.left, 40+i*50 + insets.top, 
                	 230, size.height);
	    	joindPage.add(limitLables[i]);
	    }  	    
    }
    
    
    public void createPositionPage(){    
    	Container positionPage = new JPanel();
    	positionPage.setLayout(null);
    	Insets insets = positionPage.getInsets();
    	tabbedPane.addTab("Декард" ,positionPage);
    	 // кнопка отправки точек на контроллер
    	positionPage.add(btnSendPosition);
    	Dimension size = btnSendPosition.getPreferredSize(); 
    	btnSendPosition.setBounds(460 + insets.left, 50 + insets.top, 
    								size.width, size.height); 
    	btnSendPosition.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				client.runInPointD(slidersPos[0].getValue(),
								   slidersPos[1].getValue(),
								   slidersPos[2].getValue(),
								   slidersPos[3].getValue(),
								   slidersPos[4].getValue(),
								   slidersPos[5].getValue(),JPoints.ABSOLUTE,Integer.parseInt(dSpeedInput.getText()));
			}	    	
	    });	
    	size = dSpeedInput.getPreferredSize(); 
    	dSpeedInput.setBounds(460 + insets.left, 80 + insets.top, 
      	 	   100, size.height); 
    	positionPage.add(dSpeedInput);
    	positionPage.add(btnSet);
    	size = btnSet.getPreferredSize(); 
    	btnSet.setBounds(180 + insets.left, 350 + insets.top, 
    								size.width, size.height); 
    	btnSet.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int [] arr = client.getPositions();
				for (int i=0;i<6;i++){
					slidersPos[i].setValue(arr[i]);
				}
			}	    	
	    });	
    	
    	positionPage.add(btnHome1);
    	size = btnHome1.getPreferredSize(); 
    	btnHome1.setBounds(20 + insets.left, 350 + insets.top, 
    								size.width, size.height); 
    	btnHome1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				client.home1(Integer.parseInt(dSpeedInput.getText()));
			}	    	
	    });	
    	
    	
    	positionPage.add(btnHome2);
    	size = btnHome2.getPreferredSize(); 
    	btnHome2.setBounds(100 + insets.left, 350 + insets.top, 
    								size.width, size.height); 
    	btnHome2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				client.home2(Integer.parseInt(dSpeedInput.getText()));
			}	    	
	    });	
    	
    	sledersPosLables[0] = new JLabel("x");
    	sledersPosLables[1] = new JLabel("y");
    	sledersPosLables[2] = new JLabel("z");
    	sledersPosLables[3] = new JLabel("a");
    	sledersPosLables[4] = new JLabel("o");
    	sledersPosLables[5] = new JLabel("t");
	    // слайдеры
	    for (int i=0;i<6;i++){
	    	slidersPos[i] = new JSlider(-1000, 1000, 0);
	    	positionPage.add(slidersPos[i]);
	    	slidersPos[i].setMajorTickSpacing(500);
	    	slidersPos[i].setMinorTickSpacing(250);
	    	slidersPos[i].setPaintLabels(true);
	    	slidersPos[i].setPaintTicks(true);
	    	slidersPos[i].setPaintTrack(true);
	    	slidersPos[i].setAutoscrolls(true);
	    	size = slidersPos[i].getPreferredSize(); 
	    	slidersPos[i].setBounds(30 + insets.left, 15+i*50 + insets.top, 
                     	 230, size.height);
	    	size = sledersPosLables[i].getPreferredSize(); 
	    	sledersPosLables[i].setBounds(10 + insets.left, 15+i*50 + insets.top, 
	            	 230, size.height);
	    	positionPage.add(sledersPosLables[i]);
	    	final int pos = i;
	    	slidersPos[pos].addChangeListener(new ChangeListener() {				
				@Override
				public void stateChanged(ChangeEvent arg0) {
					// TODO Auto-generated method stub
					slierDekartVals[pos].setText(slidersPos[pos].getValue()+"");
				}
			});
	    	slierDekartVals[i] = new JLabel("0");
	    	size = slierDekartVals[i].getPreferredSize(); 
	    	slierDekartVals[i].setBounds(280 + insets.left, 15+i*50 + insets.top, 
	            	 230, size.height);
	    	positionPage.add(slierDekartVals[i]);
	    	slierDekartPoss[i] = new JLabel("0");
	    	size = slierDekartPoss[i].getPreferredSize(); 
	    	slierDekartPoss[i].setBounds(310 + insets.left, 15+i*50 + insets.top, 
	            	 230, size.height);
	    	positionPage.add(slierDekartPoss[i]);
	    }	
    }    
   
	public ClientGui() {
	    super("Simple Example");
	    this.setBounds(100,100,660,480);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    // Контейнер        
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        
        getContentPane().add(content);
        content.add(tabbedPane);
        createPackagePage();
        createJoindPage();
        createPositionPage();
		client = new ClientSocket();
		client.openSocket(adressSocket.getText(),portSocet.getText());
		
		this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	if (client.flgOpenSocket)
            		client.closeSocket();
            }
        });
		time.schedule(new TimerTask() {
	 	        @Override
	 	        public void run() { //ПЕРЕЗАГРУЖАЕМ МЕТОД RUN В КОТОРОМ ДЕЛАЕТЕ ТО ЧТО ВАМ НАДО
	 	        	int [] paramsJ = client.getRotations();
	 	        	int [] paramsD = client.getPositions();
	 	        	for (int i=0;i<6;i++){
	 	        		jPosLables[i].setText(paramsJ[i]+"");
	 	        		slierDekartPoss[i].setText(paramsD[i]+"");
	 	        	}
	 	        }
	 	    }, 100, 100); //(4000 - ПОДОЖДАТЬ ПЕРЕД НАЧАЛОМ В МИЛИСЕК, ПОВТОРЯТСЯ 4 СЕКУНДЫ (1 СЕК = 1000 МИЛИСЕК)) 	    
	}
    
	void defPackage(){
		//int iArr[] = {242,3423,5212};
		//double fArr[] = {123.7821,4234.0,123.12,23589.2,2344092.124879,532.129};
		int iArr[] = new int[9];
		double fArr[] = new double[6];
		client.flush();
		for (int i=0;i<inputsCnt/9;i++){
			boolean flgTyped = false;
			for (int j=0;j<9;j++)
				if(!inputs[i*9+j].getText().equals(""))
					flgTyped = true;
			if (flgTyped){
				for (int j=0;j<9;j++)
					if(inputs[i*9+j].getText().equals(""))
						iArr[j]=0;
					else{
						//System.out.println(inputs[i*9+j].getText());
						iArr[j]=Integer.parseInt(inputs[i*9+j].getText());
					}
				for (int j=3;j<9;j++)
					if(inputs[i*9+j].getText().equals(""))
						fArr[j-3]=0;
					else
						fArr[j-3]=Double.parseDouble(inputs[i*9+j].getText());
				//client.sendVals(iArr,fArr);
				client.sendVals2(iArr);
			}		
		}
		
	}
	
	
	public static void main(String[] args) {
		ClientGui app = new ClientGui();
		app.setVisible(true);
	}
}