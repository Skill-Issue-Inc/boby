import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {
	public static JDA boby;

	public static void main(String[] args) throws Exception {
		try {//import bot token
			String file = "token.txt";
			File textFile = new File(System.getProperty("user.dir") + "/bot/" + file);
			BufferedReader abc = new BufferedReader(new FileReader(textFile));
			//create bot
			boby = JDABuilder.createDefault(abc.readLine())
					.enableIntents(GatewayIntent.MESSAGE_CONTENT).build();
			abc.close();
			//start command detection
			MyEventListener ls = new MyEventListener(boby);
			boby.addEventListener(ls);
			ls.thread.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
