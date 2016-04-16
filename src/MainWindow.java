import java.awt.Color;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.ejml.simple.SimpleMatrix;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Slider;

public class MainWindow {
	
	Runnable timer;
	Display display;
	ClientSocket client; 	
	boolean flgFirstRotGet = true;
	boolean flgFirstPosGet = true;
	boolean flgUseMatrix = true;
	CamSocket camSocket;
	public void onTime(){
		setSensorProgress();
		setSensorText();		
		setAngleText();
		setRMatrix();
		setRotations();
		setDekart();
		setMatrix4x4();
		setCam();
	}
	
	public void init(){
		 
		 final int time = 100;
		 timer = new Runnable() {
			 public void run() {
				 onTime();
		         display.timerExec(time, this);
		         
		     }
		 };
		 display.timerExec(time, timer);
		 client = new ClientSocket();
		 camSocket = new CamSocket();
		 client.openSocket("192.168.1.0","40000");
		 //camSocket.openSocket("192.168.1.101",5005);
		 int pos [] = client.getPositions();
		 
		 sliderDX.setSelection(pos[0]+500);
		 sliderDY.setSelection(pos[1]+500);
		 sliderDZ.setSelection(pos[2]+500);
		 sliderDO.setSelection(pos[3]+500);
		 sliderDA.setSelection(pos[4]+500);
		 sliderDT.setSelection(pos[5]+500);
		 
		 
		 
		 sliderDX.addListener(SWT.Selection, new Listener() {	
			@Override
			public void handleEvent(Event arg0) {
				// TODO Auto-generated method stub
				textDSx.setText((sliderDX.getSelection()-500)+"");
			}
		 });
		 sliderDY.addListener(SWT.Selection, new Listener() {	
				@Override
				public void handleEvent(Event arg0) {
					// TODO Auto-generated method stub
					textDSy.setText((sliderDY.getSelection()-500)+"");
				}
			 });
		 sliderDZ.addListener(SWT.Selection, new Listener() {	
				@Override
				public void handleEvent(Event arg0) {
					// TODO Auto-generated method stub
					textDSz.setText((sliderDZ.getSelection()-500)+"");
				}
			 });
		 sliderDO.addListener(SWT.Selection, new Listener() {	
				@Override
				public void handleEvent(Event arg0) {
					// TODO Auto-generated method stub
					textDSo.setText((sliderDO.getSelection()-500)+"");
				}
			 });
		 sliderDA.addListener(SWT.Selection, new Listener() {	
				@Override
				public void handleEvent(Event arg0) {
					// TODO Auto-generated method stub
					textDSa.setText((sliderDA.getSelection()-500)+"");
				}
			 });
		 sliderDT.addListener(SWT.Selection, new Listener() {	
				@Override
				public void handleEvent(Event arg0) {
					// TODO Auto-generated method stub
					textDSt.setText((sliderDT.getSelection()-500)+"");
				}
			 });
		 sliderJx.addListener(SWT.Selection, new Listener() {	
				@Override
				public void handleEvent(Event arg0) {
					// TODO Auto-generated method stub
					textJSx.setText((sliderJx.getSelection()-160)+"");
				}
			 });
		 sliderJy.addListener(SWT.Selection, new Listener() {	
				@Override
				public void handleEvent(Event arg0) {
					// TODO Auto-generated method stub
					textJSy.setText((sliderJy.getSelection()-105)+"");
				}
			 });
		 sliderJz.addListener(SWT.Selection, new Listener() {	
				@Override
				public void handleEvent(Event arg0) {
					// TODO Auto-generated method stub
					textJSz.setText((sliderJz.getSelection()-155)+"");
				}
			 });
		 sliderJo.addListener(SWT.Selection, new Listener() {	
				@Override
				public void handleEvent(Event arg0) {
					// TODO Auto-generated method stub
					textJSo.setText((sliderJo.getSelection()-270)+"");
				}
			 });
		 sliderJa.addListener(SWT.Selection, new Listener() {	
				@Override
				public void handleEvent(Event arg0) {
					// TODO Auto-generated method stub
					textJSa.setText((sliderJa.getSelection()-145)+"");
				}
			 });
		 sliderJt.addListener(SWT.Selection, new Listener() {	
				@Override
				public void handleEvent(Event arg0) {
					// TODO Auto-generated method stub
					textJSt.setText((sliderJt.getSelection()-360)+"");
				}
			 }); 
		 setSliders();
		 setRotations();
		 setDekart();
	}
	public void setSliders(){
		int rot [] = client.getRotations();
		sliderJx.setSelection(rot[0]+160);
		sliderJy.setSelection(rot[1]+105);
		sliderJz.setSelection(rot[2]+155);
		sliderJo.setSelection(rot[3]+270);
		sliderJa.setSelection(rot[4]+145);
		sliderJt.setSelection(rot[5]+360);
		textJSx.setText(rot[0]+"");
		textJSy.setText(rot[1]+"");
		textJSz.setText(rot[2]+"");
		textJSo.setText(rot[3]+"");
	 	textJSa.setText(rot[4]+"");
	 	textJSt.setText(rot[5]+"");
		
		int pos [] = client.getPositions();
		sliderDX.setSelection(pos[0]+500);
		sliderDY.setSelection(pos[1]+500);
		sliderDZ.setSelection(pos[2]+500);
		sliderDO.setSelection(pos[3]+500);
		sliderDA.setSelection(pos[4]+500);
		sliderDT.setSelection(pos[5]+500);
		textDSx.setText(pos[0]+"");
		textDSy.setText(pos[1]+"");
		textDSz.setText(pos[2]+"");
		textDSo.setText(pos[3]+"");
	 	textDSa.setText(pos[4]+"");
	 	textDSt.setText(pos[5]+"");
	}
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	
	
	public void open() {
		display = Display.getDefault();
		createContents();
		textJo.open();
		textJo.layout();		
		init();
		
		while (!textJo.isDisposed()) {
			if (!display.readAndDispatch()) {	
				display.sleep();
			}
		}
		onDestroy();
	}
	public void onDestroy(){
		client.closeSocket();
		client.close();
		camSocket.closeSocket();
		System.out.println("Destroyed");
	}	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		textJo = new Shell();
		textJo.setSize(984, 638);
		textJo.setText("SWT Application");
		
		progressBarX = new ProgressBar(textJo, SWT.NONE);
		progressBarX.setMaximum(200000);
		progressBarX.setBounds(37, 14, 182, 17);
		
		progressBarY = new ProgressBar(textJo, SWT.NONE);
		progressBarY.setMaximum(200000);
		progressBarY.setBounds(37, 37, 182, 17);
		
		progressBarZ = new ProgressBar(textJo, SWT.NONE);
		progressBarZ.setMaximum(200000);
		progressBarZ.setBounds(37, 60, 182, 17);
		
		progressBarMx = new ProgressBar(textJo, SWT.NONE);
		progressBarMx.setMaximum(200000);
		progressBarMx.setBounds(37, 83, 182, 17);
		
		progressBarMy = new ProgressBar(textJo, SWT.NONE);
		progressBarMy.setMaximum(200000);
		progressBarMy.setBounds(37, 106, 182, 17);
		
		progressBarMz = new ProgressBar(textJo, SWT.NONE);
		progressBarMz.setMaximum(200000);
		progressBarMz.setBounds(37, 129, 182, 17);
		
		progressBarRx = new ProgressBar(textJo, SWT.NONE);
		progressBarRx.setMaximum(200000);
		progressBarRx.setBounds(370, 14, 182, 17);
		
		progressBarRy = new ProgressBar(textJo, SWT.NONE);
		progressBarRy.setMaximum(200000);
		progressBarRy.setBounds(370, 37, 182, 17);
		
		progressBarRz = new ProgressBar(textJo, SWT.NONE);
		progressBarRz.setMaximum(200000);
		progressBarRz.setBounds(370, 60, 182, 17);
		
		progressBarRMx = new ProgressBar(textJo, SWT.NONE);
		progressBarRMx.setMaximum(200000);
		progressBarRMx.setBounds(370, 83, 182, 17);
		
		progressBarRMy = new ProgressBar(textJo, SWT.NONE);
		progressBarRMy.setMaximum(200000);
		progressBarRMy.setBounds(370, 106, 182, 17);
		
		progressBarRMz = new ProgressBar(textJo, SWT.NONE);
		progressBarRMz.setMaximum(200000);
		progressBarRMz.setBounds(370, 129, 182, 17);
		
		textX = new Text(textJo, SWT.BORDER);
		textX.setBounds(225, 14, 76, 17);
		
		textY = new Text(textJo, SWT.BORDER);
		textY.setBounds(225, 37, 76, 17);
		
		textZ = new Text(textJo, SWT.BORDER);
		textZ.setBounds(225, 60, 76, 17);
		
		textMx = new Text(textJo, SWT.BORDER);
		textMx.setBounds(225, 83, 76, 17);
		
		textMy = new Text(textJo, SWT.BORDER);
		textMy.setBounds(225, 106, 76, 17);
		
		textMz = new Text(textJo, SWT.BORDER);
		textMz.setBounds(225, 129, 76, 17);
		
		textRx = new Text(textJo, SWT.BORDER);
		textRx.setBounds(558, 14, 76, 17);
		
		textRy = new Text(textJo, SWT.BORDER);
		textRy.setBounds(558, 37, 76, 17);
		
		textRz = new Text(textJo, SWT.BORDER);
		textRz.setBounds(558, 60, 76, 17);
		
		textRMx = new Text(textJo, SWT.BORDER);
		textRMx.setBounds(558, 83, 76, 17);
		
		textRMy = new Text(textJo, SWT.BORDER);
		textRMy.setBounds(558, 106, 76, 17);
		
		textRMz = new Text(textJo, SWT.BORDER);
		textRMz.setBounds(558, 129, 76, 17);
		
		Label lblRx = new Label(textJo, SWT.NONE);
		lblRx.setBounds(344, 14, 20, 15);
		lblRx.setText("Rx");
		
		Label lblRy = new Label(textJo, SWT.NONE);
		lblRy.setBounds(344, 37, 20, 15);
		lblRy.setText("Ry");
		
		Label lblRz = new Label(textJo, SWT.NONE);
		lblRz.setBounds(344, 60, 20, 15);
		lblRz.setText("Rz");
		
		Label lblRmx = new Label(textJo, SWT.NONE);
		lblRmx.setBounds(331, 83, 28, 15);
		lblRmx.setText("RMx");
		
		Label lblRmy = new Label(textJo, SWT.NONE);
		lblRmy.setBounds(331, 106, 28, 15);
		lblRmy.setText("RMy");
		
		Menu menu = new Menu(textJo, SWT.BAR);
		textJo.setMenuBar(menu);
		
		MenuItem mntmFirst = new MenuItem(menu, SWT.NONE);
		mntmFirst.setText("First");
		
		MenuItem mntmSecond = new MenuItem(menu, SWT.NONE);
		mntmSecond.setText("Second");
		
		Label lblRmz = new Label(textJo, SWT.NONE);
		lblRmz.setBounds(331, 131, 28, 15);
		lblRmz.setText("RMz");
		
		Label lblX = new Label(textJo, SWT.NONE);
		lblX.setBounds(26, 16, 5, 15);
		lblX.setText("x");
		
		Label lblY = new Label(textJo, SWT.NONE);
		lblY.setBounds(26, 37, 5, 15);
		lblY.setText("y");
		
		Label lblMx = new Label(textJo, SWT.NONE);
		lblMx.setBounds(11, 83, 20, 15);
		lblMx.setText("Mx");
		
		Label lblMy = new Label(textJo, SWT.NONE);
		lblMy.setBounds(11, 106, 20, 15);
		lblMy.setText("My");
		
		Label lblMz = new Label(textJo, SWT.NONE);
		lblMz.setBounds(11, 129, 20, 15);
		lblMz.setText("Mz");
		
		Label lblZ = new Label(textJo, SWT.NONE);
		lblZ.setBounds(26, 60, 5, 15);
		lblZ.setText("z");
		
		textO = new Text(textJo, SWT.BORDER);
		textO.setBounds(42, 161, 34, 18);
		
		textA = new Text(textJo, SWT.BORDER);
		textA.setBounds(42, 185, 34, 17);
		
		textT = new Text(textJo, SWT.BORDER);
		textT.setBounds(42, 208, 34, 17);
		
		Label lblO = new Label(textJo, SWT.NONE);
		lblO.setBounds(16, 164, 9, 15);
		lblO.setText("O");
		
		lblA = new Label(textJo, SWT.NONE);
		lblA.setText("A");
		lblA.setBounds(16, 185, 20, 15);
		
		lblT = new Label(textJo, SWT.NONE);
		lblT.setText("T");
		lblT.setBounds(16, 210, 20, 15);
		
		textR11 = new Text(textJo, SWT.BORDER);
		textR11.setBounds(130, 162, 53, 17);
		
		textR12 = new Text(textJo, SWT.BORDER);
		textR12.setBounds(189, 161, 53, 17);
		
		textR13 = new Text(textJo, SWT.BORDER);
		textR13.setBounds(248, 161, 53, 17);
		
		textR21 = new Text(textJo, SWT.BORDER);
		textR21.setBounds(130, 185, 53, 17);
		
		textR22 = new Text(textJo, SWT.BORDER);
		textR22.setBounds(189, 185, 53, 17);
		
		textR23 = new Text(textJo, SWT.BORDER);
		textR23.setBounds(248, 185, 53, 17);
		
		textR31 = new Text(textJo, SWT.BORDER);
		textR31.setBounds(130, 208, 53, 17);
		
		textR32 = new Text(textJo, SWT.BORDER);
		textR32.setBounds(189, 208, 53, 17);
		
		textR33 = new Text(textJo, SWT.BORDER);
		textR33.setBounds(248, 208, 53, 17);
		
		sliderDX = new Slider(textJo, SWT.NONE);
		sliderDX.setMaximum(1000);
		sliderDX.setBounds(370, 185, 170, 17);
		
		sliderDY = new Slider(textJo, SWT.NONE);
		sliderDY.setMaximum(1000);
		sliderDY.setBounds(370, 208, 170, 17);
		
		sliderDZ = new Slider(textJo, SWT.NONE);
		sliderDZ.setMaximum(500);
		sliderDZ.setBounds(370, 231, 170, 17);
		
		sliderDO = new Slider(textJo, SWT.NONE);
		sliderDO.setMaximum(1000);
		sliderDO.setBounds(370, 254, 170, 17);
		
		sliderDA = new Slider(textJo, SWT.NONE);
		sliderDA.setMaximum(1000);
		sliderDA.setBounds(370, 277, 170, 17);
		
		sliderDT = new Slider(textJo, SWT.NONE);
		sliderDT.setMaximum(1000);
		sliderDT.setBounds(370, 300, 170, 17);
		
		textDSx = new Text(textJo, SWT.BORDER);
		textDSx.setBounds(558, 185, 47, 17);
		
		textDSy = new Text(textJo, SWT.BORDER);
		textDSy.setBounds(558, 208, 47, 17);
		
		textDSz = new Text(textJo, SWT.BORDER);
		textDSz.setBounds(558, 231, 47, 17);
		
		textDSo = new Text(textJo, SWT.BORDER);
		textDSo.setBounds(558, 254, 47, 17);
		
		textDSa = new Text(textJo, SWT.BORDER);
		textDSa.setBounds(558, 277, 47, 17);
		
		textDSt = new Text(textJo, SWT.BORDER);
		textDSt.setBounds(558, 300, 47, 17);
		
		Button btnHome = new Button(textJo, SWT.NONE);
		btnHome.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				client.home1();
			}
		});
		btnHome.setBounds(11, 231, 75, 25);
		btnHome.setText("Home");
		
		Button btnHome_1 = new Button(textJo, SWT.NONE);
		btnHome_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				client.home2();
			}
		});
		btnHome_1.setBounds(11, 269, 75, 25);
		btnHome_1.setText("Home2");
		
		textSpeed = new Text(textJo, SWT.BORDER);
		textSpeed.setBounds(153, 254, 53, 17);
		
		Button btnSetparams = new Button(textJo, SWT.NONE);
		btnSetparams.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				client.setParams(0, 0, Integer.parseInt(textSpeed.getText()), 0, 0, 0);
			}
		});
		btnSetparams.setBounds(123, 275, 75, 25);
		btnSetparams.setText("SetParams");
		
		Label lblSpeed = new Label(textJo, SWT.NONE);
		lblSpeed.setBounds(113, 254, 34, 15);
		lblSpeed.setText("Speed");
		
		lblX_1 = new Label(textJo, SWT.NONE);
		lblX_1.setText("X");
		lblX_1.setBounds(344, 187, 20, 15);
		
		lblY_1 = new Label(textJo, SWT.NONE);
		lblY_1.setText("Y");
		lblY_1.setBounds(344, 208, 20, 15);
		
		lblZ_1 = new Label(textJo, SWT.NONE);
		lblZ_1.setText("Z");
		lblZ_1.setBounds(344, 231, 20, 15);
		
		lblO_1 = new Label(textJo, SWT.NONE);
		lblO_1.setText("O");
		lblO_1.setBounds(344, 254, 20, 15);
		
		lblA_1 = new Label(textJo, SWT.NONE);
		lblA_1.setText("A");
		lblA_1.setBounds(344, 277, 20, 15);
		
		lblT_1 = new Label(textJo, SWT.NONE);
		lblT_1.setText("T");
		lblT_1.setBounds(344, 300, 20, 15);
		
		textDx = new Label(textJo, SWT.NONE);
		textDx.setText("0");
		textDx.setBounds(611, 187, 28, 15);
		
		textDy = new Label(textJo, SWT.NONE);
		textDy.setText("0");
		textDy.setBounds(611, 210, 28, 15);
		
		textDz = new Label(textJo, SWT.NONE);
		textDz.setText("0");
		textDz.setBounds(611, 233, 28, 15);
		
		textDo = new Label(textJo, SWT.NONE);
		textDo.setText("0");
		textDo.setBounds(611, 254, 28, 15);
		
		textDa = new Label(textJo, SWT.NONE);
		textDa.setText("0");
		textDa.setBounds(611, 279, 28, 15);
		
		textDt = new Label(textJo, SWT.NONE);
		textDt.setText("0");
		textDt.setBounds(611, 300, 28, 15);
		
		lblJ = new Label(textJo, SWT.NONE);
		lblJ.setText("J1");
		lblJ.setBounds(662, 187, 20, 15);
		
		lblJ_1 = new Label(textJo, SWT.NONE);
		lblJ_1.setText("J2");
		lblJ_1.setBounds(662, 210, 20, 15);
		
		lblJ_2 = new Label(textJo, SWT.NONE);
		lblJ_2.setText("J3");
		lblJ_2.setBounds(662, 233, 20, 15);
		
		lblJ_3 = new Label(textJo, SWT.NONE);
		lblJ_3.setText("J4");
		lblJ_3.setBounds(662, 256, 20, 15);
		
		lblJ_4 = new Label(textJo, SWT.NONE);
		lblJ_4.setText("J5");
		lblJ_4.setBounds(662, 279, 20, 15);
		
		lblJ_5 = new Label(textJo, SWT.NONE);
		lblJ_5.setText("J6");
		lblJ_5.setBounds(662, 302, 20, 15);
		
		sliderJx = new Slider(textJo, SWT.NONE);
		sliderJx.setMaximum(320);
		sliderJx.setSelection(0);
		sliderJx.setBounds(688, 185, 170, 17);
		
		sliderJy = new Slider(textJo, SWT.NONE);
		sliderJy.setMaximum(245);
		sliderJy.setSelection(0);
		sliderJy.setBounds(688, 208, 170, 17);
		
		sliderJz = new Slider(textJo, SWT.NONE);
		sliderJz.setMaximum(275);
		sliderJz.setSelection(0);
		sliderJz.setBounds(688, 231, 170, 17);
		
		sliderJo = new Slider(textJo, SWT.NONE);
		sliderJo.setMaximum(540);
		sliderJo.setSelection(0);
		sliderJo.setBounds(688, 254, 170, 17);
		
		sliderJa = new Slider(textJo, SWT.NONE);
		sliderJa.setMaximum(290);
		sliderJa.setSelection(0);
		sliderJa.setBounds(688, 277, 170, 17);
		
		sliderJt = new Slider(textJo, SWT.NONE);
		sliderJt.setMaximum(720);
		sliderJt.setSelection(0);
		sliderJt.setBounds(688, 300, 170, 17);
		
		textJSx = new Text(textJo, SWT.BORDER);
		textJSx.setText("0");
		textJSx.setBounds(864, 185, 47, 17);
		
		textJSy = new Text(textJo, SWT.BORDER);
		textJSy.setText("0");
		textJSy.setBounds(864, 208, 47, 17);
		
		textJSz = new Text(textJo, SWT.BORDER);
		textJSz.setText("0");
		textJSz.setBounds(864, 231, 47, 17);
		
		textJSo = new Text(textJo, SWT.BORDER);
		textJSo.setText("0");
		textJSo.setBounds(864, 254, 47, 17);
		
		textJSa = new Text(textJo, SWT.BORDER);
		textJSa.setText("0");
		textJSa.setBounds(864, 277, 47, 17);
		
		textJSt = new Text(textJo, SWT.BORDER);
		textJSt.setText("0");
		textJSt.setBounds(864, 300, 47, 17);
		
		textJx = new Label(textJo, SWT.NONE);
		textJx.setText("0");
		textJx.setBounds(917, 187, 20, 15);
		
		textJy = new Label(textJo, SWT.NONE);
		textJy.setText("0");
		textJy.setBounds(917, 210, 20, 15);
		
		textJz = new Label(textJo, SWT.NONE);
		textJz.setText("0");
		textJz.setBounds(917, 231, 20, 15);
		
		label_3 = new Label(textJo, SWT.NONE);
		label_3.setText("0");
		label_3.setBounds(917, 254, 20, 15);
		
		textJa = new Label(textJo, SWT.NONE);
		textJa.setText("0");
		textJa.setBounds(917, 279, 20, 15);
		
		textJt = new Label(textJo, SWT.NONE);
		textJt.setText("0");
		textJt.setBounds(917, 300, 20, 15);
		
		Button btnSendD = new Button(textJo, SWT.NONE);
		btnSendD.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				client.runInPointD(sliderDX.getSelection(),
								   sliderDY.getSelection(),
								   sliderDZ.getSelection(),
								   sliderDO.getSelection(),
								   sliderDA.getSelection(),
								   sliderDT.getSelection(),10);	
			}
		});
		btnSendD.setBounds(447, 322, 75, 25);
		btnSendD.setText("Send");
		
		Button btnSendJ = new Button(textJo, SWT.NONE);
		btnSendJ.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				client.runInPointA(sliderJx.getSelection()-160,
						           sliderJy.getSelection()-105,
						           sliderJz.getSelection()-155,
						           sliderJo.getSelection()-270,
						           sliderJa.getSelection()-145,
						           sliderJt.getSelection()-360,10);	
			}
		});
		btnSendJ.setBounds(770, 322, 75, 25);
		btnSendJ.setText("Send");
		
		textM11 = new Text(textJo, SWT.BORDER);
		textM11.setBounds(688, 14, 53, 17);
		
		textM21 = new Text(textJo, SWT.BORDER);
		textM21.setBounds(688, 37, 53, 17);
		
		textM31 = new Text(textJo, SWT.BORDER);
		textM31.setBounds(688, 60, 53, 17);
		
		textM41 = new Text(textJo, SWT.BORDER);
		textM41.setBounds(688, 83, 53, 17);
		
		textM12 = new Text(textJo, SWT.BORDER);
		textM12.setBounds(747, 14, 53, 17);
		
		textM22 = new Text(textJo, SWT.BORDER);
		textM22.setBounds(747, 37, 53, 17);
		
		textM32 = new Text(textJo, SWT.BORDER);
		textM32.setBounds(747, 60, 53, 17);
		
		textM42 = new Text(textJo, SWT.BORDER);
		textM42.setBounds(747, 83, 53, 17);
		
		textM13 = new Text(textJo, SWT.BORDER);
		textM13.setBounds(805, 14, 53, 17);
		
		textM23 = new Text(textJo, SWT.BORDER);
		textM23.setBounds(805, 37, 53, 17);
		
		textM33 = new Text(textJo, SWT.BORDER);
		textM33.setBounds(805, 60, 53, 17);
		
		textM43 = new Text(textJo, SWT.BORDER);
		textM43.setBounds(806, 83, 53, 17);
		
		textM14 = new Text(textJo, SWT.BORDER);
		textM14.setBounds(864, 14, 53, 17);
		
		textM24 = new Text(textJo, SWT.BORDER);
		textM24.setBounds(864, 37, 53, 17);
		
		textM34 = new Text(textJo, SWT.BORDER);
		textM34.setBounds(864, 60, 53, 17);
		
		textM44 = new Text(textJo, SWT.BORDER);
		textM44.setBounds(864, 83, 53, 17);
		
		Button btnUpdate = new Button(textJo, SWT.NONE);
		btnUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setSliders();
			}
		});
		btnUpdate.setBounds(530, 323, 75, 25);
		btnUpdate.setText("Update");
		
		Button btnNewButton = new Button(textJo, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int [] p = client.getPositions();
				//KawasakiMatrix.getMatrix3x3ZYZm(true,Integer.parseInt(cO.getText()), Integer.parseInt(cA.getText()), Integer.parseInt(cT.getText()));
				KawasakiMatrix.getMatrix3x3ZYZm(true,p[3],p[4],p[5]);
			};
		});
		btnNewButton.setBounds(123, 322, 75, 25);
		btnNewButton.setText("Test");
		
		cO = new Text(textJo, SWT.BORDER);
		cO.setText("0");
		cO.setBounds(10, 330, 28, 17);
		
		cA = new Text(textJo, SWT.BORDER);
		cA.setText("0");
		cA.setBounds(48, 330, 28, 17);
		
		cT = new Text(textJo, SWT.BORDER);
		cT.setText("0");
		cT.setBounds(82, 330, 28, 17);
		
		Button btnChange = new Button(textJo, SWT.NONE);
		btnChange.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				flgUseMatrix = !flgUseMatrix;
			}
		});
		btnChange.setBounds(666, 129, 53, 25);
		btnChange.setText("Change");
		
		Label lblCam = new Label(textJo, SWT.NONE);
		lblCam.setBounds(21, 381, 55, 15);
		lblCam.setText("Cam:");
		
		text = new Text(textJo, SWT.BORDER);
		text.setBounds(11, 414, 28, -1);
		
		textCam1 = new Text(textJo, SWT.BORDER);
		textCam1.setBounds(11, 402, 65, 21);
		
		textCam2 = new Text(textJo, SWT.BORDER);
		textCam2.setBounds(11, 430, 65, 21);
		
		textCam3 = new Text(textJo, SWT.BORDER);
		textCam3.setBounds(11, 457, 65, 21);
		
		textCam4 = new Text(textJo, SWT.BORDER);
		textCam4.setBounds(11, 485, 65, 21);
		
		textCam5 = new Text(textJo, SWT.BORDER);
		textCam5.setBounds(11, 512, 65, 21);
		
		textCam6 = new Text(textJo, SWT.BORDER);
		textCam6.setBounds(11, 539, 65, 21);
		
		text_1 = new Text(textJo, SWT.BORDER);
		text_1.setBounds(166, 393, 76, 21);
		
		text_2 = new Text(textJo, SWT.BORDER);
		text_2.setBounds(166, 417, 76, 21);
		
		text_3 = new Text(textJo, SWT.BORDER);
		text_3.setBounds(166, 442, 76, 21);
		
		text_4 = new Text(textJo, SWT.BORDER);
		text_4.setBounds(272, 393, 76, 21);
		
		text_5 = new Text(textJo, SWT.BORDER);
		text_5.setBounds(272, 417, 76, 21);
		
		text_6 = new Text(textJo, SWT.BORDER);
		text_6.setBounds(272, 442, 76, 21);
		
		text_7 = new Text(textJo, SWT.BORDER);
		text_7.setBounds(379, 393, 76, 21);
		
		text_8 = new Text(textJo, SWT.BORDER);
		text_8.setBounds(379, 442, 76, 21);
		
		text_9 = new Text(textJo, SWT.BORDER);
		text_9.setBounds(379, 417, 76, 21);
		
		text_10 = new Text(textJo, SWT.BORDER);
		text_10.setBounds(482, 393, 76, 21);
		
		text_11 = new Text(textJo, SWT.BORDER);
		text_11.setBounds(482, 417, 76, 21);
		
		text_12 = new Text(textJo, SWT.BORDER);
		text_12.setBounds(482, 442, 76, 21);
		
		Label lblSensor = new Label(textJo, SWT.NONE);
		lblSensor.setBounds(189, 372, 55, 15);
		lblSensor.setText("Sensor");
		
		Label label = new Label(textJo, SWT.NONE);
		label.setBounds(285, 372, 55, 15);
		label.setText("\u0424\u043B\u0430\u043D\u0435\u0446");
		
		Label label_1 = new Label(textJo, SWT.NONE);
		label_1.setBounds(400, 372, 28, 15);
		label_1.setText("\u0411\u0430\u0437\u0430");
		
		label_2 = new Label(textJo, SWT.NONE);
		label_2.setBounds(497, 372, 55, 15);
		label_2.setText("\u041C\u0438\u0440");
		
		Button btnNewButton_1 = new Button(textJo, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				client.setParams(100,
						 		 100,
						         20,
						 		 300,
						 		 5,
						 		 5);
				client.home1();
				int pos1[] = {-6,156,837,93,125,156};
				client.runInPointD(pos1,"",100);
				client.runInDeltaPointA(0, 0, -15, 0, 0, 0, 10);
				client.runInDeltaPointA(-60, 0, 0, 0, 0, 0, 10);
				int pos2[] = {-861,372,279,159,166,98};
				client.runInPointD(pos2,"",60);
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				client.setParams(100,
				 		 100,
				         20,
				 		 100,
				 		 5,
				 		 5);
				client.home2();
			}
		});
		btnNewButton_1.setBounds(273, 508, 75, 25);
		btnNewButton_1.setText("Test");

	}
	public void setRotations(){
		int rot [] = client.getRotations();
		textJx.setText(rot[0]+"");
		textJy.setText(rot[1]+"");
		textJz.setText(rot[2]+"");
		textJo.setText(rot[3]+"");
		textJa.setText(rot[4]+"");
		textJt.setText(rot[5]+"");		
	}
	public void setDekart(){
		int pos [] = client.getPositions();
		textDx.setText(pos[0]+"");
		textDy.setText(pos[1]+"");
		textDz.setText(pos[2]+"");
		textDo.setText(pos[3]+"");
		textDa.setText(pos[4]+"");
		textDt.setText(pos[5]+"");		
	}
	
	public void setCam(){
		int vals[] = camSocket.getVals();
		textCam1.setText(vals[0]+"");
		textCam2.setText(vals[1]+"");
		textCam3.setText(vals[2]+"");
		textCam4.setText(vals[3]+"");
		textCam5.setText(vals[4]+"");
		textCam6.setText(vals[5]+"");
	}
	public void setSensorText(){
		int vals[] = client.getSensorVals();
		
		int p[] = client.getPositions();
		SimpleMatrix V = KawasakiMatrix.modifyVector3m(false,flgUseMatrix,
														p[3],p[4],p[5],
														vals[0],vals[1],vals[2]);
		textO.setText(p[3]+"");
		textA.setText(p[4]+"");
		textT.setText(p[5]+"");
		
	
    	int rVals[] = client.sensor.getRVals(p[3],p[4],p[5]);	
    	
		textX.setText(vals[0]+"");
		textY.setText(vals[1]+"");
		textZ.setText(vals[2]+"");
		textMx.setText(vals[3]+"");
		textMy.setText(vals[4]+"");
		textMz.setText(vals[5]+"");
		
		textRx.setText(rVals[0]+"");
		textRy.setText(rVals[1]+"");
		textRz.setText(rVals[2]+"");
		textRMx.setText(rVals[3]+"");
		textRMy.setText(rVals[4]+"");
		textRMz.setText(rVals[5]+"");
		
		
	}
	public void setSensorProgress(){
		//int rVals[] = client.getSensorRVals();
		int vals[] = client.getSensorVals();	
		/*double arrayV[][] = {{vals[0],vals[1],vals[2]}}; 
    	SimpleMatrix V = new SimpleMatrix(arrayV);
    	SimpleMatrix S = client.getMatrix4x4_2M().extractMatrix(0, 3, 0, 3).invert();
    	V = V.mult(S);*/
		int p[] = client.getPositions();
    	int rVals[] = client.sensor.getRVals(p[3],p[4],p[5]);	
    	/*rVals[0] = (int) V.get(0);
    	rVals[1] = (int) V.get(1);
    	rVals[2] = (int) V.get(2);    	*/
    	
    	double v[][] = {{vals[0]},{vals[1]},{vals[2]}};
		SimpleMatrix tV = new SimpleMatrix(v);
		SimpleMatrix nV = KawasakiMatrix.getMw().mult(tV);
		//System.out.println(nV);
		//System.out.println(tV);
		//vals[0] = nV.getIndex(0, 0);
		//vals[1] = nV.getIndex(1, 0);
		//vals[2] = nV.getIndex(2, 0);
		
		//SimpleMatrix V = KawasakiMatrix.modifyVector3m(false,flgUseMatrix,
		//											   p[3],p[4],p[5],
		//											   vals[0],vals[1],vals[2]);
    	
		if ( vals[0] < 0)
			progressBarX.setState(SWT.PAUSED); 
		else
        	progressBarX.setState(SWT.NORMAL); 
		if ( vals[1] < 0)
			progressBarY.setState(SWT.PAUSED); 
		else
        	progressBarY.setState(SWT.NORMAL); 
		if ( vals[2] < 0)
			progressBarZ.setState(SWT.PAUSED); 
		else
        	progressBarZ.setState(SWT.NORMAL); 
		if ( vals[3] < 0)
			progressBarMx.setState(SWT.PAUSED); 
		else
        	progressBarMx.setState(SWT.NORMAL); 
		if ( vals[4] < 0)
			progressBarMy.setState(SWT.PAUSED); 
		else
			progressBarMy.setState(SWT.NORMAL); 
		if ( vals[5] < 0)
			progressBarMz.setState(SWT.PAUSED); 
		else
        	progressBarMz.setState(SWT.NORMAL); 
		
		if ( rVals[0] < 0)
			progressBarRx.setState(SWT.PAUSED); 
		else
        	progressBarRx.setState(SWT.NORMAL); 
		if ( rVals[1]< 0)
			progressBarRy.setState(SWT.PAUSED); 
		else
        	progressBarRy.setState(SWT.NORMAL); 
		if ( rVals[2] < 0)
			progressBarRz.setState(SWT.PAUSED); 
		else
        	progressBarRz.setState(SWT.NORMAL); 
		if ( rVals[3] < 0)
			progressBarRMx.setState(SWT.PAUSED); 
		else
        	progressBarRMx.setState(SWT.NORMAL); 
		if ( rVals[4] < 0)
			progressBarRMy.setState(SWT.PAUSED); 
		else
			progressBarRMy.setState(SWT.NORMAL); 
		if ( rVals[5] < 0)
			progressBarRMz.setState(SWT.PAUSED); 
		else
        	progressBarRMz.setState(SWT.NORMAL); 
		
		progressBarX.setSelection(Math.abs(vals[0]));
		progressBarY.setSelection(Math.abs(vals[1]));
		progressBarZ.setSelection(Math.abs(vals[2]));
		progressBarMx.setSelection(Math.abs(vals[3]));
		progressBarMy.setSelection(Math.abs(vals[4]));
		progressBarMz.setSelection(Math.abs(vals[5]));
		
		progressBarRx.setSelection(Math.abs(rVals[0]));
		progressBarRy.setSelection(Math.abs(rVals[1]));
		progressBarRz.setSelection(Math.abs(rVals[2]));
		progressBarRMx.setSelection(Math.abs(rVals[3]));
		progressBarRMy.setSelection(Math.abs(rVals[4]));
		progressBarRMz.setSelection(Math.abs(rVals[5]));
		
	}
	public void setAngleText(){
		int pos [] = client.getPositions();
		
	}
	public void setRMatrix(){
		int [] p = client.getPositions();
		//SimpleMatrix R3x3 = KawasakiMatrix.getMatrix3x3ZYZm(false,p[3],p[4],p[5]);		
		SimpleMatrix R3x3 = KawasakiMatrix.getMw();
		textR11.setText(R3x3.get(0,0)+"");
		textR12.setText(R3x3.get(0,1)+"");
		textR13.setText(R3x3.get(0,2)+"");
		textR21.setText(R3x3.get(1,0)+"");
		textR22.setText(R3x3.get(1,1)+"");
		textR23.setText(R3x3.get(1,2)+"");
		textR31.setText(R3x3.get(2,0)+"");
		textR32.setText(R3x3.get(2,1)+"");
		textR33.setText(R3x3.get(2,2)+"");
		
	}
	public void setVectorVals(){
		
	}
	public void setMatrix4x4(){
	/*	SimpleMatrix M  = client.getMatrix4x4_3M(false);
		System.out.println(M);
		
		textM11.setText(M.get(0,0)+"");
		textM12.setText(M.get(0,1)+"");
		textM13.setText(M.get(0,2)+"");
		textM14.setText(M.get(0,3)+"");
		
		textM21.setText(M.get(1,0)+"");
		textM22.setText(M.get(1,1)+"");
		textM23.setText(M.get(1,2)+"");
		textM24.setText(M.get(1,3)+"");
		
		textM31.setText(M.get(2,0)+"");
		textM32.setText(M.get(2,1)+"");
		textM33.setText(M.get(2,2)+"");
		textM34.setText(M.get(2,3)+"");
		
		textM41.setText(M.get(3,0)+"");
		textM42.setText(M.get(3,1)+"");
		textM43.setText(M.get(3,2)+"");
		textM44.setText(M.get(3,3)+"");*/
	}
	
	
	protected Shell textJo;
	private Text textX;
	private Text textY;
	private Text textZ;
	private Text textMx;
	private Text textMy;
	private Text textMz;
	private Text textRx;
	private Text textRy;
	private Text textRz;
	private Text textRMx;
	private Text textRMy;
	private Text textRMz;
	ProgressBar progressBarX;	
	ProgressBar progressBarY;	
	ProgressBar progressBarZ;	
	ProgressBar progressBarMx;	
	ProgressBar progressBarMy;	
	ProgressBar progressBarMz;	
	ProgressBar progressBarRx;	
	ProgressBar progressBarRy;	
	ProgressBar progressBarRz;	
	ProgressBar progressBarRMx;
	ProgressBar progressBarRMy;	
	ProgressBar progressBarRMz;
	private Text textO;
	private Text textA;
	private Text textT;
	private Label lblA;
	private Label lblT;
	private Text textR11;
	private Text textR12;
	private Text textR13;
	private Text textR21;
	private Text textR22;
	private Text textR23;
	private Text textR31;
	private Text textR32;
	private Text textR33;
	private Text textDSx;
	private Text textDSy;
	private Text textDSz;
	private Text textDSo;
	private Text textDSa;
	private Text textDSt;
	private Text textSpeed;
	private Label lblX_1;
	private Label lblY_1;
	private Label lblZ_1;
	private Label lblO_1;
	private Label lblA_1;
	private Label lblT_1;
	Slider sliderDX;	
	Slider sliderDY;	
	Slider sliderDZ;	
	Slider sliderDO;	
	Slider sliderDA;	
	Slider sliderDT;
	
	private Label textDx;
	private Label textDy;
	private Label textDz;
	private Label textDo;
	private Label textDa;
	private Label textDt;
	private Label lblJ;
	private Label lblJ_1;
	private Label lblJ_2;
	private Label lblJ_3;
	private Label lblJ_4;
	private Label lblJ_5;
	private Slider sliderJx;
	private Slider sliderJy;
	private Slider sliderJz;
	private Slider sliderJo;
	private Slider sliderJa;
	private Slider sliderJt;
	private Text textJSx;
	private Text textJSy;
	private Text textJSz;
	private Text textJSo;
	private Text textJSa;
	private Text textJSt;
	private Label textJx;
	private Label textJy;
	private Label textJz;
	private Label label_3;
	private Label textJa;
	private Label textJt;
	private Text textM11;
	private Text textM21;
	private Text textM31;
	private Text textM41;
	private Text textM12;
	private Text textM22;
	private Text textM32;
	private Text textM42;
	private Text textM13;
	private Text textM23;
	private Text textM33;
	private Text textM43;
	private Text textM14;
	private Text textM24;
	private Text textM34;
	private Text textM44;
	private Text cO;
	private Text cA;
	private Text cT;
	private Text text;
	private Text textCam1;
	private Text textCam2;
	private Text textCam3;
	private Text textCam4;
	private Text textCam5;
	private Text textCam6;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_7;
	private Text text_8;
	private Text text_9;
	private Text text_10;
	private Text text_11;
	private Text text_12;
	private Label label_2;
}
