
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
import javax.swing.text.AbstractDocument.LeafElement;

public class ClientGui extends JFrame {
	final static int inputsCnt = 99; 
	private JButton btnOpenSocket = new JButton("OpenSocket");
	private JButton btnCloseSocket = new JButton("CloseSocket");
	private JButton btnClearInputs = new JButton("ClearInputs");
	private JButton btnSendPackage = new JButton("SendPackage");
	
	private JButton btnAddJPoint = new JButton("AddPoint");
	private JButton btnSendJPoinst = new JButton("SendJPoint");
	private JButton btnClearJPoinst = new JButton("ClearPoint");
	
	private JTextField portSocet = new JTextField("40000", 5);
	private JTextField adressSocket = new JTextField("192.168.0.79", 8);
	public JTextField [] inputs = new JTextField[inputsCnt];
	public JButton [] buttons = new JButton[10];
	private JLabel ipLabel = new JLabel();
	
	private JSlider [] sliders = new JSlider[6];
	private JLabel[] slederLables = new JLabel[6];
	
	final JTabbedPane tabbedPane = new JTabbedPane();
	ClientSocket client;
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
    }
    public void createJoindPage(){    
    	Container joindPage = new JPanel();
    	joindPage.setLayout(null);
    	Insets insets = joindPage.getInsets();
    	tabbedPane.addTab("Джоинды" ,joindPage);
    	 // кнопка отправки точек на контроллер
    	joindPage.add(btnSendJPoinst);
    	Dimension size = btnSendJPoinst.getPreferredSize(); 
	    btnSendJPoinst.setBounds(300 + insets.left, 50 + insets.top, 
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
	    btnClearJPoinst.setBounds(300 + insets.left, 110 + insets.top, 
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
	    btnAddJPoint.setBounds(300 + insets.left, 80 + insets.top, 
                     	 	   100, size.height); 
	    btnAddJPoint.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				client.addJPoint((double)sliders[0].getValue(),
							     (double)sliders[1].getValue(),
							     (double)sliders[2].getValue(),
							     (double)sliders[3].getValue(),
							     (double)sliders[4].getValue(),
							     (double)sliders[5].getValue(),JPoints.ABSOLUTE);
			}	    	
	    });
	  
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
	    	sliders[i].setBounds(30 + insets.left, 15+i*50 + insets.top, 
                     	 230, size.height);
	    	slederLables[i] = new JLabel("j"+i);
	    	size = slederLables[i].getPreferredSize(); 
	    	slederLables[i].setBounds(10 + insets.left, 15+i*50 + insets.top, 
                	 230, size.height);
	    	joindPage.add(slederLables[i]);
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
      
		client = new ClientSocket();
	}
	void defPackage(){
		//int iArr[] = {242,3423,5212};
		//double fArr[] = {123.7821,4234.0,123.12,23589.2,2344092.124879,532.129};
		int iArr[] = new int[3];
		double fArr[] = new double[6];
		for (int i=0;i<inputsCnt/9;i++){
			boolean flgTyped = false;
			for (int j=0;j<9;j++)
				if(!inputs[i*9+j].getText().equals(""))
					flgTyped = true;
			if (flgTyped){
				for (int j=0;j<3;j++)
					if(inputs[i*9+j].getText().equals(""))
						iArr[j]=0;
					else
						iArr[j]=Integer.parseInt(inputs[i*9+j].getText());
				for (int j=3;j<9;j++)
					if(inputs[i*9+j].getText().equals(""))
						fArr[j-3]=0;
					else
						fArr[j-3]=Double.parseDouble(inputs[i*9+j].getText());
				client.sendVals(iArr,fArr);
			}		
		}
	}
	
	
	public static void main(String[] args) {
		ClientGui app = new ClientGui();
		app.setVisible(true);
	}
}