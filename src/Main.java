import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Main {

	public static void main(String[] args) throws Exception {
		try {
		JDA api = JDABuilder.createDefault(
				"NTU4MzIxMzY0ODkzOTU4MTY0.XJO3Sg.VBC6gFnlYV2FdSWnTl9lacu-pVw").build();
		MyEventListener ls = new MyEventListener(api);
		api.addEventListener(ls);
		ls.thread.start();
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
