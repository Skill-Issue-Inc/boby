import java.io.Serializable;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;

public class FunProfile extends Object implements Serializable, Comparable<FunProfile> {
		/**
		 * FunData
		 */
		private static final long serialVersionUID = 1L;
		/*public FunProfile(User parent) {
			funbucks = 25;
			ownerid = parent.getIdLong();
		}
		
		@Override
	    public String toString() {
	        return new StringBuffer("FunBuckSave: ")
	                .append(this.ownerid)
	                .append(this.funbucks)
	                .append(this.inboundt)
	                .append(this.outboundt)
	                .append(this.adminedit)
	                .append(this.adminedit).toString();
	    }*/
		/*public void setFunbucks(float funbucks) {
			this.funbucks = funbucks;
		}
		public float getFunbucks() {
			return this.funbucks;
		}
		public void setOwnerid(Long funbucks) {
			this.ownerid = funbucks;
		}
		public long getOwnerid() {
			return this.ownerid;
		}
		
		public void writeObject(ObjectOutputStream oos) 
	      throws IOException {
	        oos.defaultWriteObject();
	        oos.writeObject(address.getHouseNumber());
	    }*/
		public void SetUserID() {
			ownerid = owner.getIdLong();
		}
		public void GetUser(JDA api) {
			owner = api.retrieveUserById(ownerid).complete();
			System.out.println(owner + " " + ownerid);
		}
		
		public float funbucks = 25;
		public transient User owner;
		public long ownerid;
		String funname = "";
		public int inboundt = 0;
		public int outboundt = 0;
		public int adminedit = 0;
		public boolean banned = false;
		@Override
		public int compareTo(FunProfile o) {
			if(ownerid == 410529944624693279l)
				return 999;
			
			return (int)(funbucks - o.funbucks) * -1;
		}
	}
