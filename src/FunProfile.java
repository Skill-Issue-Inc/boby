import java.io.Serializable;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;

public class FunProfile extends Object implements Serializable, Comparable<FunProfile> {
		/**
		 * FunData
		 */
		private static final long serialVersionUID = 1L;
		public static int defaultvalue = 25;
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
		public void SetUserID() {
			ownerid = owner.getIdLong();
		}
		public void GetUser(JDA api) {
			owner = api.retrieveUserById(ownerid).complete();
			System.out.println(owner + " " + ownerid);
		}
		public String GetName() {
			if(notif) {
				return owner.getAsMention();
			}
			else {
				return owner.getAsTag();
			}
		}
		public String GetAddr() {
			if(ExtNanoAddr == "N/A") {
				return "internal-address";
			}
			else {
				return ExtNanoAddr;
			}
		}
		
		public float funbucks = 0;
		public transient User owner;
		public long ownerid;
		String funname = "";
		public int inboundt = 0;
		public int outboundt = 0;
		public int adminedit = 0;
		public int blanketedit = 0;
		public boolean notif = true;
		public boolean banned = false;
		public String ExtNanoAddr = "N/A";
		public boolean UseExt = false;
		@Override
		public int compareTo(FunProfile o) {
			if(ownerid == 410529944624693279l)
				return 999;
			
			return (int)(funbucks - o.funbucks) * -1;
		}
	}
