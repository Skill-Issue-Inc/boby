import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
//import java.util.ArrayList;
//import java.util.List;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Main {

	public static void main(String[] args) throws Exception {
		try {//import bot token
			String file = "token.txt";
			File textFile = new File(System.getProperty("user.dir") + "/bot/" + file);
			BufferedReader abc = new BufferedReader(new FileReader(textFile));
			//create bot
			JDA api = JDABuilder.createDefault(abc.readLine()).build();
			abc.close();
			//start command detection
			MyEventListener ls = new MyEventListener(api);
			api.addEventListener(ls);
			ls.thread.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
