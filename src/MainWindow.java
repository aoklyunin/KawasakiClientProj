import java.awt.Color;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class MainWindow {
	
	Runnable timer;
	Display display;
	ClientSocket client; 
	
	protected Shell shell;
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
	

	public void onTime(){
		setSensorProgress();
		setSensorText();		
		setAngleText();
		setRMatrix();
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
		 client.openSocket("192.168.1.0","40000");
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
		init();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				
				display.sleep();
			}
		}
		onDestroy();
	}
	public void onDestroy(){
		client.closeSocket();
		client.close();
		System.out.println("Destroyed");
	}	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(672, 301);
		shell.setText("SWT Application");
		
		progressBarX = new ProgressBar(shell, SWT.NONE);
		progressBarX.setMaximum(200000);
		progressBarX.setBounds(37, 14, 182, 17);
		
		progressBarY = new ProgressBar(shell, SWT.NONE);
		progressBarY.setMaximum(200000);
		progressBarY.setBounds(37, 37, 182, 17);
		
		progressBarZ = new ProgressBar(shell, SWT.NONE);
		progressBarZ.setMaximum(200000);
		progressBarZ.setBounds(37, 60, 182, 17);
		
		progressBarMx = new ProgressBar(shell, SWT.NONE);
		progressBarMx.setMaximum(200000);
		progressBarMx.setBounds(37, 83, 182, 17);
		
		progressBarMy = new ProgressBar(shell, SWT.NONE);
		progressBarMy.setMaximum(200000);
		progressBarMy.setBounds(37, 106, 182, 17);
		
		progressBarMz = new ProgressBar(shell, SWT.NONE);
		progressBarMz.setMaximum(200000);
		progressBarMz.setBounds(37, 129, 182, 17);
		
		progressBarRx = new ProgressBar(shell, SWT.NONE);
		progressBarRx.setMaximum(200000);
		progressBarRx.setBounds(370, 14, 182, 17);
		
		progressBarRy = new ProgressBar(shell, SWT.NONE);
		progressBarRy.setMaximum(200000);
		progressBarRy.setBounds(370, 37, 182, 17);
		
		progressBarRz = new ProgressBar(shell, SWT.NONE);
		progressBarRz.setMaximum(200000);
		progressBarRz.setBounds(370, 60, 182, 17);
		
		progressBarRMx = new ProgressBar(shell, SWT.NONE);
		progressBarRMx.setMaximum(200000);
		progressBarRMx.setBounds(370, 83, 182, 17);
		
		progressBarRMy = new ProgressBar(shell, SWT.NONE);
		progressBarRMy.setMaximum(200000);
		progressBarRMy.setBounds(370, 106, 182, 17);
		
		progressBarRMz = new ProgressBar(shell, SWT.NONE);
		progressBarRMz.setMaximum(200000);
		progressBarRMz.setBounds(370, 129, 182, 17);
		
		textX = new Text(shell, SWT.BORDER);
		textX.setBounds(225, 14, 76, 17);
		
		textY = new Text(shell, SWT.BORDER);
		textY.setBounds(225, 37, 76, 17);
		
		textZ = new Text(shell, SWT.BORDER);
		textZ.setBounds(225, 60, 76, 17);
		
		textMx = new Text(shell, SWT.BORDER);
		textMx.setBounds(225, 83, 76, 17);
		
		textMy = new Text(shell, SWT.BORDER);
		textMy.setBounds(225, 106, 76, 17);
		
		textMz = new Text(shell, SWT.BORDER);
		textMz.setBounds(225, 129, 76, 17);
		
		textRx = new Text(shell, SWT.BORDER);
		textRx.setBounds(558, 14, 76, 17);
		
		textRy = new Text(shell, SWT.BORDER);
		textRy.setBounds(558, 37, 76, 17);
		
		textRz = new Text(shell, SWT.BORDER);
		textRz.setBounds(558, 60, 76, 17);
		
		textRMx = new Text(shell, SWT.BORDER);
		textRMx.setBounds(558, 83, 76, 17);
		
		textRMy = new Text(shell, SWT.BORDER);
		textRMy.setBounds(558, 106, 76, 17);
		
		textRMz = new Text(shell, SWT.BORDER);
		textRMz.setBounds(558, 129, 76, 17);
		
		Label lblRx = new Label(shell, SWT.NONE);
		lblRx.setBounds(344, 14, 20, 15);
		lblRx.setText("Rx");
		
		Label lblRy = new Label(shell, SWT.NONE);
		lblRy.setBounds(344, 37, 20, 15);
		lblRy.setText("Ry");
		
		Label lblRz = new Label(shell, SWT.NONE);
		lblRz.setBounds(344, 60, 20, 15);
		lblRz.setText("Rz");
		
		Label lblRmx = new Label(shell, SWT.NONE);
		lblRmx.setBounds(331, 83, 28, 15);
		lblRmx.setText("RMx");
		
		Label lblRmy = new Label(shell, SWT.NONE);
		lblRmy.setBounds(331, 106, 28, 15);
		lblRmy.setText("RMy");
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmFirst = new MenuItem(menu, SWT.NONE);
		mntmFirst.setText("First");
		
		MenuItem mntmSecond = new MenuItem(menu, SWT.NONE);
		mntmSecond.setText("Second");
		
		Label lblRmz = new Label(shell, SWT.NONE);
		lblRmz.setBounds(331, 131, 28, 15);
		lblRmz.setText("RMz");
		
		Label lblX = new Label(shell, SWT.NONE);
		lblX.setBounds(26, 16, 5, 15);
		lblX.setText("x");
		
		Label lblY = new Label(shell, SWT.NONE);
		lblY.setBounds(26, 37, 5, 15);
		lblY.setText("y");
		
		Label lblMx = new Label(shell, SWT.NONE);
		lblMx.setBounds(11, 83, 20, 15);
		lblMx.setText("Mx");
		
		Label lblMy = new Label(shell, SWT.NONE);
		lblMy.setBounds(11, 106, 20, 15);
		lblMy.setText("My");
		
		Label lblMz = new Label(shell, SWT.NONE);
		lblMz.setBounds(11, 129, 20, 15);
		lblMz.setText("Mz");
		
		Label lblZ = new Label(shell, SWT.NONE);
		lblZ.setBounds(26, 60, 5, 15);
		lblZ.setText("z");
		
		textO = new Text(shell, SWT.BORDER);
		textO.setBounds(42, 161, 34, 18);
		
		textA = new Text(shell, SWT.BORDER);
		textA.setBounds(42, 185, 34, 17);
		
		textT = new Text(shell, SWT.BORDER);
		textT.setBounds(42, 208, 34, 17);
		
		Label lblO = new Label(shell, SWT.NONE);
		lblO.setBounds(16, 164, 9, 15);
		lblO.setText("O");
		
		lblA = new Label(shell, SWT.NONE);
		lblA.setText("A");
		lblA.setBounds(16, 185, 20, 15);
		
		lblT = new Label(shell, SWT.NONE);
		lblT.setText("T");
		lblT.setBounds(16, 210, 20, 15);
		
		textR11 = new Text(shell, SWT.BORDER);
		textR11.setBounds(130, 162, 53, 17);
		
		textR12 = new Text(shell, SWT.BORDER);
		textR12.setBounds(189, 161, 53, 17);
		
		textR13 = new Text(shell, SWT.BORDER);
		textR13.setBounds(248, 161, 53, 17);
		
		textR21 = new Text(shell, SWT.BORDER);
		textR21.setBounds(130, 185, 53, 17);
		
		textR22 = new Text(shell, SWT.BORDER);
		textR22.setBounds(189, 185, 53, 17);
		
		textR23 = new Text(shell, SWT.BORDER);
		textR23.setBounds(248, 185, 53, 17);
		
		textR31 = new Text(shell, SWT.BORDER);
		textR31.setBounds(130, 208, 53, 17);
		
		textR32 = new Text(shell, SWT.BORDER);
		textR32.setBounds(189, 208, 53, 17);
		
		textR33 = new Text(shell, SWT.BORDER);
		textR33.setBounds(248, 208, 53, 17);

	}
	
	public void setSensorText(){
		int rVals[] = client.getSensorRVals();
		int vals[] = client.getSensorVals();
		
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
		int rVals[] = client.getSensorRVals();
		int vals[] = client.getSensorVals();	
		
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
		if ( rVals[1] < 0)
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
		textO.setText(pos[3]+"");
		textA.setText(pos[4]+"");
		textT.setText(pos[5]+"");
	}
	public void setRMatrix(){
		double [][] rM = client.getRMatrix();
		textR11.setText(rM[0][0]+"");
		textR12.setText(rM[0][1]+"");
		textR13.setText(rM[0][2]+"");
		textR21.setText(rM[1][0]+"");
		textR22.setText(rM[1][1]+"");
		textR23.setText(rM[1][2]+"");
		textR31.setText(rM[2][0]+"");
		textR32.setText(rM[2][1]+"");
		textR33.setText(rM[2][2]+"");
		
	}
}
