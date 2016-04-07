import java.io.FileWriter;
import java.io.IOException;

import org.ejml.simple.SimpleMatrix;

public class KawasakiMatrix {
	
		public static SimpleMatrix getMassMatrix(){
			double dx=-2;
			double dy=-12;
			double dz=-50;
			double m = Math.sqrt(dx*dx+dy*dy+dz*dz);
			
			double c1 = dx/m;
			double s1 = Math.sqrt(1-c1*c1);
			
			double c2 = dy/m;
			double s2 = Math.sqrt(1-c2*c2);
			
			double c3 = dz/m;
			double s3 = Math.sqrt(1-c2*c2);
			
			return new SimpleMatrix();
			
		}
		public static SimpleMatrix getBaseModification(){
			double [][] d = {{0,-1,0},{1,0,0},{0,0,1}};
			return new SimpleMatrix(d);
			
		}
		public static double [][] getMatrix3x3ZYZd( boolean flgLog,
													double o, double a, double t){
			return mToD(getMatrix3x3ZYZm(flgLog,
					 o,  a,  t));			
		}
		public static SimpleMatrix modifyVector3m(boolean flgLog,
											double o, double a, double t,
											double x, double y, double z ){
			double v[][] = {{x},{y},{z}};
			SimpleMatrix R = getMatrix3x3ZYZm(false,o,a,t);
			SimpleMatrix R1 = R.invert();
			SimpleMatrix Rn = R.mult(getBaseModification());
			SimpleMatrix V = new SimpleMatrix(v);
			SimpleMatrix Vn = Rn.mult(V);
			if (flgLog){
				try(FileWriter writer = new FileWriter("c:\\Programming\\debug.txt", false))
	    		{					
	    			writer.write("     o="+x+" a="+y+" t="+z+'\n');	
	    			writer.write("new: o="+Vn.get(0,0)+" a="+Vn.get(1,0)+" t="+Vn.get(2,0)+'\n');	
					writer.write("---------------------------------------------------------"+'\n');	 
					writer.write(R.toString());
	    			writer.write("---------------------------------------------------------"+'\n');
	    			writer.write(R1.toString());
	    			writer.write("---------------------------------------------------------"+'\n');
	    			writer.flush();
	    		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
			return Vn;			
		}
	
		public static SimpleMatrix getMatrix3x3ZYZm(boolean flgLog,
													double o, double a, double t){
			double cO = Math.cos(Math.toRadians(o));
			double sO = Math.sin(Math.toRadians(o));
			double cA = Math.cos(Math.toRadians(a));
			double sA = Math.sin(Math.toRadians(a));
			double cT = Math.cos(Math.toRadians(t));
			double sT = Math.sin(Math.toRadians(t));								
			
			double [][] m = {
					{ cO*cA*cT-sO*sT, -cO*cA*sT-sO*cT, cO*sA},
					{ sO*cA*cT+cO*sT, -sO*cA*sT+cO*cT, sO*sA},
					{ -sA*cT,     sA*sT,cA}
			};
			SimpleMatrix M = new SimpleMatrix(m);			
			if (flgLog){
				try(FileWriter writer = new FileWriter("c:\\Programming\\debug.txt", false))
	    		{
					double d1[][] =  {
							{cO,-sO,0},
							{sO,cO,0},
							{0,0,1}					
					};			
					SimpleMatrix R1 = new SimpleMatrix(d1);
					double d2[][] =  {
							{cA,0,sA},
							{0,1,0},
							{-sA,0,cA}					
					};			
					SimpleMatrix R2 = new SimpleMatrix(d2);
					double d3[][] =  {
							{cT,-sT,0},
							{sT,cT,0},
							{0,0,1}					
					};			
					SimpleMatrix R3 = new SimpleMatrix(d3);
					SimpleMatrix R = R1.mult(R2).mult(R3);
	    			writer.write("o="+o+" a="+a+" t="+t+'\n');					
					writer.write("---------------------------------------------------------"+'\n');	    		
	    			writer.write(R1.toString()+'\n');
	    			writer.write(R2.toString()+'\n');
	    			writer.write(R3.toString()+'\n');
	    			writer.write("---------------------------------------------------------"+'\n');
	    			writer.write(R.toString()+'\n');
	    			writer.write("---------------------------------------------------------"+'\n');
	    			writer.write(M.toString()+'\n');
	    			writer.flush();
	    		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
			return M;
		}
    
		public static double[][] mToD(SimpleMatrix M){
			double [][] a = new double[4][4];
	    	for (int i =0;i<4;i++) 
	    		for (int j =0; j<4;j++)
	    			a[i][j] = M.get(i,j);
	    	return a;
		}

		public static SimpleMatrix buildCurM4x4(double t, double alpha,double a, double d){
			double arrayM[][] = {
				{Math.cos(t), -Math.cos(alpha)*Math.sin(t), Math.cos(alpha)*Math.sin(t) , a*Math.cos(t)},
				{Math.sin(t), Math.cos(alpha)*Math.cos(t) , -Math.sin(alpha)*Math.cos(t), a*Math.sin(t)},
				{0          , Math.sin(alpha)             , Math.cos(alpha)             , d            },
				{0          , 0                           , 0                           , 1            }	
			}; 			
			return new SimpleMatrix(arrayM);	
		}
		
		public static double[][] getMatrix3x3(double o1, double a1, double t1){
	    	double o = Math.toRadians(o1);
	    	double a = Math.toRadians(a1);
	    	double t = Math.toRadians(t1);
	    	
	    	double c1 = Math.cos(o);
	    	double s1 = Math.sin(o);
	    	double c2 = Math.cos(a);
	    	double s2 = Math.sin(a);
	    	double c3 = Math.cos(t);
	    	double s3 = Math.sin(t);
	    	
	    	double [][]M = {
	    			{c1*c2*c3-s1*s3, -c3*s1-c1*c2*s3, c1*s2},
	    			{c1*s3+ c2*c3*s1,c1*c3-c2*s1*s3,s1*s2},
	    			{-c3*s2,s2*s3,c2}   	  					
	    	};
	    	return M;
	    	
	    }	
	
		public static SimpleMatrix getMatrix3x3M(double o1, double a1, double t1){
	    		return new SimpleMatrix(getMatrix3x3(o1,a1,t1));
	    	
	    }
	    public static SimpleMatrix getMatrix4x4CJm(double r1,double r2,double r3,double r4,double r5,double r6,
	    										   double d1,double d2,double d3,double d4,double d5){
	    	// первое колено
	    	double c1 = Math.cos(Math.toRadians(r1));
	    	double s1 = Math.sin(Math.toRadians(r1));
	    	double a1 [][]={{c1 ,-s1, 0 , 0 },
	    					{s1 , c1, 0 , 0 },
	    					{0  ,  0, 1 , 0},
	    					{0  ,  0, 0 , 1 }};
	    	SimpleMatrix A1 = new SimpleMatrix(a1);
	    	// второе колено
	    	double c2 = Math.cos(Math.toRadians(r2));
	    	double s2 = Math.sin(Math.toRadians(r2));
	    	double a2 [][]={{c2 , 0  , -s2 , 0 },
	    					{0  , 1  ,  0  , 0 },
	    					{-s2, 0  , c2  , d1},
	    					{0  , 0  ,   0 , 1 }};
	    	SimpleMatrix A2 = new SimpleMatrix(a2);
	    	// третье колено
	    	double c3 = Math.cos(Math.toRadians(r3));
	    	double s3 = Math.sin(Math.toRadians(r3));
	    	double a3 [][]={{c3 ,0 , s3 , 0 },
	    					{0 , 1 , 0 , 0 },
	    					{-s3 ,  0 , c3 , d2},
	    					{ 0 ,  0 , 0 , 1 }};
	    	SimpleMatrix A3 = new SimpleMatrix(a3);
	    	// четвёртое колено
	    	double c4 = Math.cos(Math.toRadians(r4));
	    	double s4 = Math.sin(Math.toRadians(r4));
	    	double a4 [][]={{c4, -s4  ,0  , 0},
	    					{s4, c4  ,0 , 0},
	    					{0 , 0 , 1   , d3},
	    					{0 , 0  , 0   , 1}};
	    	SimpleMatrix A4 = new SimpleMatrix(a4);
	    	// пятое колено
	    	double c5 = Math.cos(Math.toRadians(r5));
	    	double s5 = Math.sin(Math.toRadians(r5));
	    	double a5 [][]={{c5 , 0  , s5 , 0},
	    					{0 , 1  , 0  , 0},
	    					{-s5  , 0 , c5   , d4},
	    					{0  , 0  , 0   , 1}};
	    	SimpleMatrix A5 = new SimpleMatrix(a5);
	    	// шестое колено
	    	double c6 = Math.cos(Math.toRadians(r6));
	    	double s6 = Math.sin(Math.toRadians(r6));
	    	double a6 [][]={{c6 , -s6  , 0  , 0},
	    					{s6 , c6  , 0 , 0},
	    					{0  , 0 , 1   , d5},
	    					{0  , 0  , 0   , 1}};
	    	SimpleMatrix A6 = new SimpleMatrix(a6);
	    	SimpleMatrix M = A1.mult(A2).mult(A3).mult(A4).mult(A5).mult(A6);	
	    	return M;
	    }
	    
	    public static double [][] getMatrix4x4Jd(boolean flgLog,
													double r1, double r2, double r3, double r4, double r5, double r6,
													double d1, double d2, double d3, double d4, double d5, double d6){
	    	
	    	return mToD(getMatrix4x4Jm(flgLog,
					 					r1,  r2,  r3,  r4,  r5,  r6,
					 					d1,  d2,  d3,  d4,  d5,  d6));
	    }
	    
	    public static SimpleMatrix getMatrix4x4Jm(boolean flgLog,
	    											double r1, double r2, double r3, double r4, double r5, double r6,
	    											double d1, double d2, double d3, double d4, double d5, double d6){ 	
	    	double [] t = { 90+r1,0,90,r4,0,r6};
	    	double [] d = {430,450,450,100,70,10};
	    	double [] alpha = {90, r2,-90+r3,90,-90+r5,0};
	    	double [] a = {0,0,0,0,0,0};
	    	SimpleMatrix R = SimpleMatrix.identity(4);
	    	if (flgLog){
	    		try(FileWriter writer = new FileWriter("c:\\Programming\\debug.txt", false))
	    		{
	    			writer.write("I="+R.toString());		
	    			for (int i=0;i<6;i++){
	    				writer.write("---------------------------------------------------------"+'\n');
	    				SimpleMatrix M = buildCurM4x4(Math.toRadians(t[i]),Math.toRadians(alpha[i]),a[i],d[i]);
	    				writer.write("t="+t[i]+" a="+a[i]+" alpha="+alpha[i]+" d="+d[i]+'\n');
	    				writer.write("A"+(i+1)+"="+M.toString()+'\n');	
	    				R = R.mult(M);
	    			}
	    			writer.write("R="+R.toString()+'\n');
	    			writer.flush();
	    		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}else{
	    		for (int i=0;i<6;i++){
	    			SimpleMatrix M = buildCurM4x4(Math.toRadians(t[i]),Math.toRadians(alpha[i]),a[i],d[i]);
	    			R = R.mult(M);
	    		}
	    	}
	    	return R;
	    } 
	    
	    
	    
}
