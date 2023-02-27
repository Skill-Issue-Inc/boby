import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServerSettings extends Object implements Serializable {

	private static final long serialVersionUID = 1L;

	public ServerSettings(int id) {
		ID = id;
	}

	public boolean rolepairExists(long roleID, String tag) {
		for (rolepair rolepair : rolepairs) {
			if(rolepair.role == roleID && rolepair.tag.equals(tag))
				return true;
		}
		return false;
	}

	public boolean CreateRolepair(long roleID, String tag) {
		for (rolepair rolepair : rolepairs) {
			if(rolepair.role == roleID && rolepair.tag.equals(tag))
				return true;
		}
		rolepairs.add(new rolepair(roleID, tag.toLowerCase()));
		return false;
	}
	
	public boolean DeleteRolepairs(long roleID) {
		boolean retrue = false;
		for (int i = 0; i < rolepairs.size(); i++) {
			if(rolepairs.get(i).role == roleID)
			{
				rolepairs.remove(i);
				i--;
				retrue = true;
			}
		}
		if(retrue)
			return true;
		return false;
	}
	
	public boolean DeleteTag(String tag) {
		for (int i = 0; i < rolepairs.size(); i++) {
			if(rolepairs.get(i).tag.toLowerCase().equals(tag.toLowerCase()))
			{
				rolepairs.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public long getRoleIDFromTag(String tag) {
		for (rolepair rolepair : rolepairs) {
			if(rolepair.tag.toLowerCase().equals(tag.toLowerCase()))
				return rolepair.role;
		}
		return -1;
	}
	
	public String debugfrick() {
		//List<String> squidgaes = new ArrayList<String>();
		String e = ""; 
		for (rolepair rolepair : rolepairs) {
			e += "\n" + rolepair.role + " " + rolepair.tag;
		}
		return e;
	}
	
	public int ID;
	public boolean ambient = true;
	public boolean lyric = true;
	public boolean autocrop = true;
	public List<rolepair> rolepairs = new ArrayList<rolepair>();
	
	
	private class rolepair implements Serializable {
		private static final long serialVersionUID = 1L;
		public rolepair(long role, String tag) {
			this.role = role;
			this.tag = tag;
		}
		
		public long role;
		public String tag;
	}
}