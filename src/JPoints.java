import java.util.ArrayList;

public class JPoints {
	final static String ABSOLUTE="abs";
	private ArrayList <JPoint> pointList = new ArrayList<JPoint>();
	
	public class JPoint{
		double j1;
		double j2;
		double j3;
		double j4;
		double j5;
		double j6;
		String type;
		public JPoint() {
				this.j1 = 0;
				this.j2 = 0;
				this.j3 = 0;
				this.j4 = 0;
				this.j5 = 0;
				this.j6 = 0;
				this.type = ABSOLUTE;
		}
		public JPoint(double j1,double j2,double j3,double j4,double j5,double j6,String type){
			this.j1 = j1;
			this.j2 = j2;
			this.j3 = j3;
			this.j4 = j4;
			this.j5 = j5;
			this.j6 = j6;
			this.type = type;
		}
	}

	public void add(double j1,double j2,double j3,double j4,double j5,double j6,String type){
		pointList.add(new JPoint(j1,j2,j3,j4,j5,j6,type));
	}
	public ArrayList <String> getRequests(){
		if (pointList.size()!=0){
			ArrayList <String> lst = new ArrayList<String>();
			int i=0;
			for (JPoint point:pointList){
				String s = Custom.getVali(10000+i,6)+" 101 0000";
				i++;
				s = s+" "+Custom.getVald(point.j1);
				s = s+" "+Custom.getVald(point.j2);
				s = s+" "+Custom.getVald(point.j3);
				s = s+" "+Custom.getVald(point.j4);
				s = s+" "+Custom.getVald(point.j5);
				s = s+" "+Custom.getVald(point.j6);
				lst.add(s);
			}
			String s = Custom.getVali(10000+i,6)+" 300 0000";
			for (int j=0;j<6;j++){
				s = s+" "+Custom.getVald(0);
			}	
			lst.add(s);
			return lst;
		}else{
			return null; 
		}		
	}
	public void clear() {
		pointList.clear();		
	}
}
