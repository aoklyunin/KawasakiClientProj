
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
	private JButton btnInitInputs = new JButton("InitInputs");
	
	private JButton btnTest = new JButton("Test");
	
	private JButton btnSendPosition = new JButton("SendPosition");
	
	private JTextField portSocet = new JTextField("40000", 5);
	private JTextField adressSocket = new JTextField("192.168.1.0", 8);
	public JTextField [] inputs = new JTextField[inputsCnt];
	public JButton [] buttons = new JButton[10];
	private JLabel ipLabel = new JLabel();
	
	private JSlider [] sliders = new JSlider[6];
	private JLabel[] slederLables = new JLabel[6];
	
	private JSlider [] slidersPos = new JSlider[3];
	private JLabel[] sledersPosLables = new JLabel[3];
	
	final JTabbedPane tabbedPane = new JTabbedPane();
	ClientSocket client;
    public void clearInputs(){
    	for (int j=0;j<inputsCnt;j++)
			inputs[j].setText("");
    }
    // ������ �������� ��� ������ � ��������
    public void createPackagePage(){
    	Container packagePage = new JPanel();
    	packagePage.setLayout(null);
  	    tabbedPane.addTab("������" , packagePage);
  	    Insets insets = packagePage.getInsets();
  	    // ���� �����
	    for (int i=0;i<inputsCnt;i++){
	    	inputs[i] = new JTextField("", 5);
	    	packagePage.add(inputs[i]);
	    	Dimension size = inputs[i].getPreferredSize(); 
	    	inputs[i].setBounds(10 + (i%9)*70 + insets.left, 80+(i/9)*30 + insets.top, 
	                     size.width, size.height); 
	    }
	    // ���� ����� � ������� �������
	    packagePage.add(adressSocket);
    	Dimension size = adressSocket.getPreferredSize(); 
    	adressSocket.setBounds(130 + insets.left, 40 + insets.top, 
                     size.width, size.height);
    	// ���� ����� ����� ������
    	packagePage.add(portSocet);
    	size = portSocet.getPreferredSize(); 
    	portSocet.setBounds(130 + insets.left, 15 + insets.top, 
                     size.width, size.height);
    	// ������ �������� ������
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
	    // ������ ����
    	packagePage.add(btnTest);
	    size = btnTest.getPreferredSize(); 
	    btnTest.setBounds(560+ insets.left, 10 + insets.top, 
                     	 size.width, size.height); 
	    btnTest.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				client.test();
			}	    	
	    });
	    
	    // ������ �������� ������
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
	    // ������ ������� ����� �����
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
	    // ������ ������� ����� �����
	    packagePage.add(btnInitInputs);
	    size = btnInitInputs.getPreferredSize(); 
	    btnInitInputs.setBounds(520 + insets.left, 40 + insets.top, 
                     	 size.width, size.height); 
	    btnInitInputs.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i=0;i<45;i++){
					inputs[i].setText(i+"");
				}
			}	    	
	    });
	    
	    // IP �����
		try {
			InetAddress IP = InetAddress.getLocalHost();
			ipLabel.setText(IP.getHostAddress());
			packagePage.add(ipLabel);
		    size = ipLabel.getPreferredSize(); 
		    ipLabel.setBounds(300 + insets.left, 10 + insets.top, 
	                     	 size.width, size.height); 
		} catch (UnknownHostException e) {
			e.printStackTrace();
			Custom.showMessage("�� ���������� ���������� IP");	
		}
		// ������ �������� ������
		packagePage.add(btnCloseSocket);
	    size = btnCloseSocket.getPreferredSize(); 
	    btnCloseSocket.setBounds(15 + insets.left, 45 + insets.top, 
                     	 size.width, size.height);
    }
    public void createJoindPage(){    
    	Container joindPage = new JPanel();
    	joindPage.setLayout(null);
    	Insets insets = joindPage.getInsets();
    	tabbedPane.addTab("�������" ,joindPage);
    	 // ������ �������� ����� �� ����������
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
	    // ������ �������� ���� �����
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
	    // ������ ���������� ����� ��������
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
	  
	    // ��������
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
    
    public void createPositionPage(){    
    	Container positionPage = new JPanel();
    	positionPage.setLayout(null);
    	Insets insets = positionPage.getInsets();
    	tabbedPane.addTab("�������" ,positionPage);
    	 // ������ �������� ����� �� ����������
    	positionPage.add(btnSendPosition);
    	Dimension size = btnSendPosition.getPreferredSize(); 
    	btnSendPosition.setBounds(300 + insets.left, 50 + insets.top, 
	    					     100, size.height); 
    	btnSendPosition.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				client.sendPositionPoints();
			}	    	
	    });
	
    	sledersPosLables[0] = new JLabel("x");
    	sledersPosLables[1] = new JLabel("y");
    	sledersPosLables[2] = new JLabel("z");
	    // ��������
	    for (int i=0;i<3;i++){
	    	slidersPos[i] = new JSlider(-360, 360, 0);
	    	positionPage.add(sliders[i]);
	    	slidersPos[i].setMajorTickSpacing(120);
	    	slidersPos[i].setMinorTickSpacing(30);
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
	    	positionPage.add(slederLables[i]);
	    }
	    
	  
    	
    }
    
	public ClientGui() {
	    super("Simple Example");
	    this.setBounds(100,100,660,480);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    // ���������        
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