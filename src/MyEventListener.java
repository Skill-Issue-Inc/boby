import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class MyEventListener extends ListenerAdapter {
	public String prefixString = "s!";
	public String prefixString2 = "boby ";
	public String prefixString3 = "f!";
	public MessageChannel channel;
	public List<FunProfile> lads = new ArrayList<FunProfile>();
	Random random = new Random();
	public JDA api;
	public int lyricline = 0;
	public final String funName = System.getProperty("user.dir") + "/bot/" + "fun.ser";
	public boolean sing;
	
	@SuppressWarnings("unchecked")
	MyEventListener(JDA jda){
		api = jda;
		/*System.out.println("List of attached Servers:");
        for (Guild guild : api.getGuilds()) {
        	System.out.println("   -" + guild.getName());
		}*/
		try {
			FileInputStream fis = new FileInputStream(funName);
			ObjectInputStream ois = new ObjectInputStream(fis);
	        lads = (List<FunProfile>)ois.readObject();
	        for (FunProfile funProfile : lads) {
				funProfile.GetUser(api);
			}
	        ois.close();
	        fis.close();
	        System.out.println("Deserializationin Import Success");
	        System.out.println(lads);
	        
	        
		}
		catch (Exception e) {
			System.out.println("Error in Deserializationin");
		}
	}
	
	  Thread thread = new Thread(){
		    public void run(){
		    	Scanner scanner = new Scanner(System.in);
		    	boolean contin = true;
		    	while(contin) {
		    		String text = scanner.nextLine();
		    		if(text != "" && text != null) {
		    			channel.sendMessage(text).queue();
		    			System.out.println("");
		    			if(text == "s!quit") {
			    			contin = false;
			    			System.out.println("Input Canceled");
			    		}
		    		}
		    	}
		    	scanner.close();
		    }
		  };
	
	@SuppressWarnings("deprecation")
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getAuthor().isBot() && (!event.getMessage().getContentRaw().toLowerCase().contains(prefixString) ||
		   !event.getMessage().getContentRaw().toLowerCase().contains(prefixString2))) return;
		
		Message inputMessage = event.getMessage(); //save input message
		channel = event.getChannel(); //respond in same channel
		String content = inputMessage.getContentRaw(); //get the raw content of the message
		System.out.println(event.getAuthor().getName() + ": " + content);
		
		try {
		if(content.toLowerCase().startsWith(prefixString) 
				|| content.toLowerCase().startsWith(prefixString2)
				|| content.toLowerCase().startsWith(prefixString3)) {
			String command = "";
			if(content.toLowerCase().startsWith(prefixString)){
				command = content.substring(prefixString.length());
				//System.out.println("Prefix found! cutting to: " + command);
				//System.out.println("Prefix length: " + prefixString.length());	
			}
			if(content.toLowerCase().startsWith(prefixString2)){
				command = content.substring(prefixString2.length());
				//System.out.println("Prefix found! cutting to: " + command);
				//System.out.println("Prefix length: " + prefixString.length());	
			}
			
			if(!command.startsWith(prefixString3)) {
			
			if(command.startsWith("help")) {
				if(!command.startsWith("helpme")) {
				channel.sendMessage("Version 2.4 full command list:\r\n" + 
						"prefix: `s!` and `boby `\r\n" + 
						"```\r\n" + 
						"Command (required field) [optional field] {attachment}\r\n\n" +
						"help - displays help message\r\n" + 
						"ping - approx ping\r\n" + 
						"say (string) - says the message\r\n" + 
						"msg (mention) (string) - sends a DM from the bot to the user\r\n" + 
						"msgid (UserID) (string) - sends a DM from the bot to the user\r\n" + 
						"amsgid\r\n" + 
						"motor [int] - Motor Image Repo\r\n" + 
						"jojo [int] - Jojo Image Repo\r\n" + 
						"img [int] - Public Image Repo\r\n" + 
						"bazinga - acronym funny\r\n" + 
						"addimg {image} - adds the attachment to s!img\r\n" + 
						"makebot\r\n" + 
						"die\r\n" + 
						"dead\r\n" + 
						"perished\r\n" + 
						"crash\r\n" + 
						"addjojo\r\n" + 
						"killself - kills boby\r\n" + 
						"tacoflip - heads or tails\r\n" + 
						"no - yes\r\n" + 
						"yes - no\r\n" + 
						"thanks - gratuitous gratitude\r\n" + 
						"helpme - boby totally wants to help ;)\r\n" + 
						"spamE(string) - spams contents in formatting\r\n" + 
						"spamP(string) - spams contents in plain\r\n" + 
						"spamC(string) - spams contents in plain without spaces\r\n" + 
						"clearchat - clears the current chat ;)\r\n" + 
						"decide (string) [string]...repeat - randomly chooses\r\n" + 
						"listargs - debug feature to test frick:tm: arg parser\r\n" + 
						"E [int] - E image repo\r\n" + 
						"addE {image} - Adds to E image repo\r\n" + 
						"revive - revive a dead boby\r\n" + 
						"amongus or amogus - Are you imposter? Find out with this horrible command!\r\n" +
						"join - boby joins vc for no reason\r\n" +
						"stop - boby leaves vc\r\n" +
						"????? - I just wanna tell you how I'm feeling\r\n" + 
						"face - Sends user's profile picture\r\n" + 
						"```\r\n" + 
						"Other Features:\r\n" + 
						"```\r\n" + 
						"-lyric continuation complex to it so that if you say any full lyric line in your sentence it will send the next one\r\n" + 
						"-responds when boby is mentioned\r\n" + 
						"-responds to \"boby should do\"\r\n" + 
						"-add \"list\" to the end of image repos to see the contents\r\n" + 
						"-for FUN(TM) commands, use s!funhelp or f!help\r\n" + 
						"```").queue();
				System.out.println("Helped out: " + event.getAuthor().getName());
				}
			}
			
			if(command.startsWith("revive")) {
				api.getPresence().setStatus(OnlineStatus.ONLINE);
				channel.sendMessage("thank you for reviving me").queue();
			}
			else {
				if(api.getPresence().getStatus() == OnlineStatus.DO_NOT_DISTURB) {
					return;
				}
			}
			
			if(command.startsWith("ping")) {
				channel.sendMessage("Pong! " + event.getJDA().getGatewayPing()).queue();
				System.out.println("Pinged: " + event.getAuthor().getName());
			}
			
			if(command.startsWith("say ")) {
				String arg1 = command.substring(4);
				channel.sendMessage("" + arg1 + "").queue();
				System.out.println("Messsage Copied: " + command.substring(4) + " from " + event.getAuthor().getName());
			}
			
			if(command.startsWith("msg ")) {
				String arg1 = GetNextArg(command);
				String arg2 = GetArgAt(command, 2);
				System.out.println("arg1:" + arg1);
				System.out.println("arg2:" + arg2);
				String msg = command.substring(command.indexOf(">"));
				List<Member> mentionsList = inputMessage.getMentionedMembers();
				/*if(mentionsList.size() < 1) {
					PrivateChannel pchannel = event.getGuild().getMemberById(Long.parseLong(arg2)).getUser()
							.openPrivateChannel().complete();
					pchannel.sendMessage(arg1 + "_from " + event.getAuthor().getName() + "_").queue();
					channel.sendMessage("**" + arg1 + "**").queue();
					System.out.println("Messsage Sent: " + command.substring(4) + " from " + event.getAuthor().getName());
					return;
				}*/
				for (Member member : mentionsList) {
					PrivateChannel pchannel = member.getUser().openPrivateChannel().complete();
					pchannel.sendMessage("_from " + event.getAuthor().getName() + "_\n" + msg).queue();
					channel.sendMessage("**Messsage Sent**\n" + msg).queue();
					System.out.println("Messsage Sent: " + command.substring(4) + " from " + event.getAuthor().getName());
				}
			}
			if(command.startsWith("amsg")) {
				channel.sendMessage("Jeez dude I'm workin' on it").queue();
			}
			if(command.startsWith("msgid")) {
				String arg1 = GetNextArg(command);
				String arg2 = GetArgAt(command, 2);
				System.out.println("arg1:" + arg1);
				System.out.println("arg2:" + arg2);
				String msg = command.substring(command.indexOf(arg2));
				
				PrivateChannel pchannel = api.getUserById(Long.parseLong(arg1)).openPrivateChannel().complete();
				pchannel.sendMessage("_from " + event.getAuthor().getName() + "_\n"  + msg).queue();
				channel.sendMessage("> **" + msg + "**").queue();
				System.out.println("Messsage Sent: " + command.substring(4) + " from " + event.getAuthor().getName());
				return;
				
				
			}
			if(command.startsWith("amsgid")) {
				channel.sendMessage("Jeez dude I'm workin' on it").queue();
			}
			try {
				if(command.startsWith("motor")) {				
					FileReturn ret = getFileFromDir("Motors", command);
					channel.sendMessage((ret.index + 1) + "/" + ret.length).addFile(ret.file).queue();
				}
				if(command.startsWith("jojo")) {
					FileReturn ret = getFileFromDir("JOJO", command);
					channel.sendMessage((ret.index + 1) + "/" + ret.length).addFile(ret.file).queue();
				}
				if(command.startsWith("img")) {
					FileReturn ret = getFileFromDir("img", command);
					channel.sendMessage((ret.index + 1) + "/" + ret.length + ": `" + ret.file.getName() + "`")
						.addFile(ret.file).queue();
				}
				if(command.startsWith("E")) {
					FileReturn ret = getFileFromDir("E", command);
					channel.sendMessage((ret.index + 1) + "/" + ret.length + ": `" + ret.file.getName() + "`")
					.addFile(ret.file).queue();
				}
			} catch (NullPointerException e) {}
			if(command.toLowerCase().startsWith("bazinga")) {
				File textFile = new File(System.getProperty("user.dir") + "/bot/bazinga.txt");
				List<String> lines = new ArrayList<String>();
				try {
					BufferedReader abc = new BufferedReader(new FileReader(textFile));
					String line;
					while((line = abc.readLine()) != null) {
					    lines.add(line);
					}
					abc.close();
				} catch (Exception e) {
					System.out.println("Error reading bazinga.txt!");
				}
				
				System.out.println("Read " + lines.size() + " lines.");
				int select = random.nextInt(lines.size());
				String bazingaString = lines.get(select);
				channel.sendMessage(bazingaString).queue();
				System.out.println((select + 1) + "/" + lines.size());
			}
			if(command.startsWith("addimg")) {
				try {
					Attachment attachment = inputMessage.getAttachments().get(0);
					if(attachment.isImage()) {
						channel.sendMessage("Added **" + DownloadFile(attachment, "img") + "** to s!img").queue();
					} else {
						channel.sendMessage("Attachment is not an image").queue();
					}
				} catch (Exception e) {
					channel.sendMessage("No attachment to add").queue();
				}
			}
			if(command.startsWith("addE")) {
				try {
					Attachment attachment = inputMessage.getAttachments().get(0);
					if(attachment.isImage()) {
						channel.sendMessage("Added **" + DownloadFile(attachment, "E") + "** to s!E").queue();
					} else {
						channel.sendMessage("Attachment is not an image").queue();
					}
				} catch (Exception e) {
					channel.sendMessage("No attachment to add").queue();
				}
			}
			if(command.startsWith("makebot")) {
				channel.sendMessage("<@!510621703051935744>").queue();
			}
			if(command.startsWith("die")) {
				channel.sendMessage("no").queue();
			}
			if(command.startsWith("dead")) {
				channel.sendMessage("I'm not dead yet!").queue();
			}
			if(command.startsWith("perished")) {
				channel.sendMessage("no u").queue();
			}
			if(command.startsWith("crash")) {
				channel.sendMessage("bad").queue();
			}
			if(command.startsWith("killself")) {
				api.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
				channel.sendMessage("A").queue();
			}
			if(command.startsWith("yes")) {
				channel.sendMessage("no").queue();
			}
			if(command.startsWith("no")) {
				channel.sendMessage("yes").queue();
			}
			if(command.startsWith("thanks")) {
				String E = "";
				switch (random.nextInt(8)) {
					case 0:
						E += ":]";
						break;
					case 1:
						E += ":)";
						break;
					case 2:
						E += ":D";
						break;
					case 3:
						E += "Its just my job";
						break;
					case 4:
						E += "yeah";
						break;
					case 5:
						E += "no problemo";
						break;
					case 6:
						E += "welcome";
						break;
					case 7:
						E += "I just wanna tell you how I'm feeling";
						break;
					case 8:
						E += "have a nice day";
						break;
					default:
						break;
				}
				channel.sendMessage(E).queue();
			}
			if(command.startsWith("helpme")) {
				channel.sendMessage("no").queue();
			}
			if(command.startsWith("addjojo")) {
				channel.sendMessage("I didnt know you'd actually try and use this yet").queue();
			}
			if(command.startsWith("tacoflip")) {
				FileReturn ret = getFileFromDir("tacoflip", null);
				String sendString = "Error";
				if(ret.index == 1)
					sendString = "**Heads! :+1:**";
				if(ret.index == 0)
					sendString = "**Tails! :-1:**";
				if(random.nextInt(16) == 1) {
					sendString = "Hmm, well this is awkward, it landed on its side. **You decide!**";
					ret.file = new File(System.getProperty("user.dir") + "/bot/side.png");
				}
				channel.sendMessage(sendString).addFile(ret.file).queue();
			}
			if(command.startsWith("flip")) {
				FileReturn ret = getFileFromDir("flip", null);
				String sendString = "Error";
				if(ret.index == 1)
					sendString = "**Heads! :+1:**";
				if(ret.index == 0)
					sendString = "**Tails! :-1:**";
				channel.sendMessage(sendString).addFile(ret.file).queue();
			}
			if(command.startsWith("spamE")) {
				String l = command.substring(6);
				String E = "";
				int amount = random.nextInt(256);
				for (int i = 0; i < amount; i++) {
					switch (random.nextInt(8)) {
						case 0:
							E += l + " ";
							break;
						case 1:
							E += "_" + l + "_ ";
							break;
						case 2:
							E += "**" + l + "** ";
							break;
						case 3:
							E += "***" + l + "*** ";
							break;
						case 4:
							E += "__" + l + "__ ";
							break;
						case 5:
							E += "__*" + l + "*__ ";
							break;
						case 6:
							E += "__**" + l + "**__ ";
							break;
						case 7:
							E += "__***" + l + "***__ ";
							break;
						case 8:
							E += "`" + l + "` ";
							break;
						default:
							break;
					}
					
				}
				if(E.length() > 2000) {
					E = E.substring(0, 2000);
				}
				channel.sendMessage(E).queue();
			}
			if(command.startsWith("spamP")) {
				String l = command.substring(6);
				String E = "";
				int amount = random.nextInt(256);
				for (int i = 0; i < amount; i++) {
					E += l + " ";
				}
				if(E.length() > 2000) {
					E = E.substring(0, 2000);
				}
				channel.sendMessage(E).queue();
			}
			if(command.startsWith("spamC")) {
				String l = command.substring(6);
				String E = "";
				int amount = random.nextInt(256);
				for (int i = 0; i < amount; i++) {
					E += l;
				}
				if(E.length() > 2000) {
					E = E.substring(0, 2000);
				}
				channel.sendMessage(E).queue();
			}
			if(command.startsWith("clearchat")) {
				String E = "*";
				for (int i = 0; i < 32; i++) {
					E += "\n";
				}
				E += "*";
				channel.sendMessage(E).queue();
			}
			if(command.startsWith("yeet")) {
				List<Emote> emotes = event.getGuild().getEmotes();
				String msg = "";
				for (Emote emote : emotes) {
					msg += emote.getAsMention();
				}
				if(emotes.size() == 0) {
					msg += "No Custom Emotes";
				}
				channel.sendMessage(msg).queue();
			}
			if(command.startsWith("decide")) {
				String msg = "";
				List<String> args = GetArgs(command);
				msg = args.get(random.nextInt(args.size()));
				channel.sendMessage("I choose " + msg).queue();
			}
			if(command.startsWith("listargs")) {
				for (String arg : GetArgs(command)) {
					channel.sendMessage(arg).queue();
				}
			}
			if(command.startsWith("should do")) {
				if(random.nextBoolean())
					channel.sendMessage("Do _you_ do " + command.substring(9) + "?").queue();
				else
					channel.sendMessage(command.substring(9)).queue();
			}
			if(command.startsWith("amogus") || command.startsWith("amongus")) {
				if(random.nextInt(19) == 0){
					channel.sendMessage("sTOP POSTING ABOUT AMONGUS").queue();
					channel.sendMessage("I'm TIRED OF SEEING IT").queue();
				}
				else if(random.nextBoolean())
					channel.sendMessage("youre imposter").queue();
				else
					channel.sendMessage("youre crewmate").queue();
			}
			if(command.startsWith("stop")) {
				// Gets the channel in which the bot is currently connected.
	            VoiceChannel connectedChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();
	            // Checks if the bot is connected to a voice channel.
	            if(connectedChannel == null) {
	                // Get slightly fed up at the user.
	                channel.sendMessage("I'm not doing anything").queue();
	                return;
	            }
	            // Disconnect from the channel.
	            event.getGuild().getAudioManager().closeAudioConnection();
	            // Notify the user.
	            channel.sendMessage("Disconnected").queue();
			}
			if(command.startsWith("join")) {
	            // Checks if the bot has permissions.
	            /*if(!event.getGuild().getSelfMember().hasPermission(channel, Permission.VOICE_CONNECT)) {
	                // The bot does not have permission to join any voice channel. Don't forget the .queue()!
	                channel.sendMessage("I do not have permissions to join a voice channel!").queue();
	                return;
	            }*/
	            // Creates a variable equal to the channel that the user is in.
	            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
	            // Checks if they are in a channel -- not being in a channel means that the variable = null.
	            if(connectedChannel == null) {
	                // Don't forget to .queue()!
	                channel.sendMessage("You are not connected to a voice channel").queue();
	                return;
	            }
	            // Gets the audio manager.
	            AudioManager audioManager = event.getGuild().getAudioManager();
	            // When somebody really needs to chill.
	            if(audioManager.isAttemptingToConnect()) {
	                channel.sendMessage("I'm workin on it").queue();
	                return;
	            }
	            // Connects to the channel.
	            audioManager.openAudioConnection(connectedChannel);
	            // Obviously people do not notice someone/something connecting.
	            channel.sendMessage("Connected").queue();
			}
			if(command.toLowerCase().startsWith("how are you") 
					|| command.toLowerCase().startsWith("are you ok") 
					|| command.toLowerCase().startsWith("do you need help")) {
				channel.sendMessage("I just wanna tell you how I'm feeling").queue();
			}
			if(command.startsWith("sing")) {
				sing = true;
			}
			if(command.startsWith("face")) {
				User funguy;
				List<Member> mentionsList = inputMessage.getMentionedMembers();
				if(mentionsList.size() > 1) {
					channel.sendMessage("crig only 1 mentn plz").queue();
				}
				if(mentionsList.size() < 1) {
					funguy = event.getAuthor();
				}
				try {
					funguy = mentionsList.get(0).getUser();
				} catch (Exception e) {
					channel.sendMessage("Error in getting mention").queue();
					return;
				}
				channel.sendMessage(funguy.getAvatarUrl()).queue();
			}
			if(command.startsWith("shrine")) {
				channel.sendMessage("```" + 
						"       .\r\n" + 
						"       |\r\n" + 
						"  .   ]#[   .\r\n" + 
						"   \\_______/\r\n" + 
						".    ]###[    .\r\n" + 
						" \\__]#.-.#[__/\r\n" + 
						"  |___| |___|\r\n" + 
						"  |___|_|___|\r\n" + 
						"  ####/_\\####\r\n" + 
						"     |___|\r\n" + 
						"    /_____\\```").queue();
			}
			
			
			
			
			}//FUNNY HAHA MONEY CURRENCY MAX OWNED TM (R) INC. (c) TKOD DING DING DING DINM GISNG
			if(command.startsWith("fun") || content.toLowerCase().startsWith(prefixString3)) { 
				String pre = "fun";
				if(content.toLowerCase().startsWith(prefixString3)) {
					command = content.substring(prefixString3.length());
					pre = "";
				}
				
				//RESTRICTED CHANNEL ONLY ON THIS CHANNEL TM TM (R) FUNBUCKS 2021
				if(event.getChannel().getName().contains("funbucks")) {
					FunProfile funfrom = GetProfile(event.getAuthor(), channel);
					if(funfrom.banned) {
						channel.sendMessage("You have been banned from using Fun:tm: commands. Sorry").queue();
						return;
					}
					
					
					//GIB ALL DAT FFUUNN INFO
					if(command.startsWith(pre + "help"))
						channel.sendMessage("FUNBUCK:tm: UTILITY (c) 2021 \n"
					+ "**type \"fun\" at the beginning of evey command** or use f! prefix \n"
					+ "help - displays this fun message\n"
					+ "pay (mention) (amount) - Pays (mention) funbucks in (amount) from _your fun bank_\n"
					+ "pr{ofile} [mention] - Displays Funny:tm: Profile\n"
					+ "notif{ications} - Turns on/off ping notifications\n"
					+ "> MAX ADMIN STUFFS:\n"
					+ "fine (mention) (amount) - sucks (amount) funbucks from (mention) into the back void \n"
					+ "grant (mention) (amount) - grants (amount) funbucks to (mention) from the eternal stash \n"
					+ "ban (mention) - prevents (mention) from using any fun:tm: commands \n"
					+ "pardon (mention) - un-bans (mention)\n"
					+ "tax (amount) - deflates the entire economy over the starting funbucks\n"
					+ "salary (amount) - inflates the entire economy\n").queue();
					
					//pAY THE MANS THE MONEYZ (c) (R) (k)
					if(command.startsWith(pre + "pay"))
					{
						List<Member> mentionsList = inputMessage.getMentionedMembers();
						FunProfile funto;
						
						try {
							funto = GetProfile(mentionsList.get(0).getUser(), channel);
						} catch (Exception e) {
							channel.sendMessage("Yo, who am I payin' here?").queue();
							return;
						}
						
						String arg2;
						try {
							arg2 = GetArgAt(command, 2);
						} catch (Exception e) {
							channel.sendMessage("Yo, how much am I payin' here?").queue();
							return;
						}
						
						float amount;
						try {
							amount = Float.parseFloat(arg2);
						} catch (Exception e) {
							channel.sendMessage("Yo, thats crig?").queue();
							return;
						}
						if(amount < 0) {
							channel.sendMessage("oi, you are the one payin' here. value below 0 not allowed").queue();
							return;
						}
						if(amount > funfrom.funbucks) {
							channel.sendMessage("oi, you dont have enough money").queue();
							return;
						}
						funfrom.funbucks -= amount;
						funfrom.outboundt++;
						funto.funbucks += amount;
						funto.inboundt++;
						
						channel.sendMessage("TRANSACTION OF **$" + amount 
								+ "** FROM " + funfrom.GetName() 
								+ " TO " + funto.GetName()).queue();
					}
					//Sow off dat profil
					if(command.startsWith(pre + "pr"))
					{
						List<Member> mentionsList = inputMessage.getMentionedMembers();
						if(mentionsList.size() > 1) {
							channel.sendMessage("crig only 1 mentn plz").queue();
						}
						
						FunProfile funguy;
						try {
							funguy = GetProfile(mentionsList.get(0).getUser(), channel);
						} catch (Exception e) {
							funguy = GetProfile(event.getAuthor(), channel);
						}
						
						
						channel.sendMessage("FUN BANK USER " + funguy.GetName() + "\n```\n"
								+ "Total User Funbucks: " + funguy.funbucks + "\n"
								+ "Outbound Transactions: " + funguy.outboundt + "\n"
								+ "Inbound Transactions: " + funguy.inboundt + "\n"
								+ "Administrative Edits: " + funguy.adminedit + "\n"
								+ "Blanket Edits: " + funguy.blanketedit + "\n"
								+ "User Ban Status: " + funguy.banned + "\n"
								+ "User Notification Status: " + funguy.notif + "\n"
								+ "User Discord ID: " + funguy.owner.getId() + "\n"
								+ "```").queue();
					}
					if(command.startsWith(pre + "notif"))
					{
						FunProfile funto = funfrom;
						
						funto.notif = !funto.notif;
						
						if(funto.notif) {
							channel.sendMessage("Ping notifications are now turned **on**").queue();
						}
						else {
							channel.sendMessage("Ping notifications are now turned **off**").queue();
						}
					}
					
					
					
					
					
					
					//ADMIN STUFF TKOD ONLY DO BAD CRING YE HAHAHA FUN
					if(event.getAuthor().getIdLong() == 410529944624693279l)
					{
						//channel.sendMessage("Admin User Detected").queue();
						
						if(command.startsWith(pre + "fine"))
						{
							List<Member> mentionsList = inputMessage.getMentionedMembers();
							
							FunProfile funto;
							try {
								funto = GetProfile(mentionsList.get(0).getUser(), channel);
							} catch (Exception e) {
								channel.sendMessage("USER NOT SPECIFIED").queue();
								return;
							}
							
							String arg2;
							try {
								arg2 = GetArgAt(command, 2);
							} catch (Exception e) {
								channel.sendMessage("FINE AMOUNT NOT SPECIFIED").queue();
								return;
							}
							
							float amount;
							try {
								amount = Float.parseFloat(arg2);
							} catch (Exception e) {
								channel.sendMessage("FINE AMOUNT NOT UNDERSTOOD").queue();
								return;
							}
							if(amount < 0) {
								channel.sendMessage("THE FINE COMMAND CANNOT BE USED TO PAY USERS").queue();
								return;
							}
							if(amount > funto.funbucks) {
								channel.sendMessage("Warning: User will go into debt").queue();
							}
							funto.funbucks -= amount;
							funto.adminedit++;
							
							channel.sendMessage("USER " + funto.GetName() + " FINED **$" + amount 
									+ "** FUNBUCKS:tm:").queue();
						}
						
						if(command.startsWith(pre + "grant"))
						{
							List<Member> mentionsList = inputMessage.getMentionedMembers();
							
							FunProfile funto;
							try {
								funto = GetProfile(mentionsList.get(0).getUser(), channel);
							} catch (Exception e) {
								channel.sendMessage("USER NOT SPECIFIED").queue();
								return;
							}
							
							String arg2;
							try {
								arg2 = GetArgAt(command, 2);
							} catch (Exception e) {
								channel.sendMessage("GRANT AMOUNT NOT SPECIFIED").queue();
								return;
							}
							
							float amount;
							try {
								amount = Float.parseFloat(arg2);
							} catch (Exception e) {
								channel.sendMessage("GRANT AMOUNT NOT UNDERSTOOD").queue();
								return;
							}
							if(amount < 0) {
								channel.sendMessage("THE GRANT COMMAND CANNOT BE USED TO FINE USERS").queue();
								return;
							}
							funto.funbucks += amount;
							funto.adminedit++;
							
							channel.sendMessage("USER " + funto.GetName() + " GRANTED **$" + amount 
									+ "** FUNBUCKS:tm:").queue();
						}
						
						if(command.startsWith(pre + "ban"))
						{
							List<Member> mentionsList = inputMessage.getMentionedMembers();
							
							FunProfile funto;
							try {
								funto = GetProfile(mentionsList.get(0).getUser(), channel);
							} catch (Exception e) {
								channel.sendMessage("USER NOT SPECIFIED").queue();
								return;
							}
							if(funto.banned) {
								channel.sendMessage("USER ALREADY BANNED").queue();
								return;
							}
							funto.banned = true;
							channel.sendMessage("USER " + funto.GetName() 
							+ " HAS BEEN BANNED FROM USING FUNBUCKS:tm:").queue();
						}
						
						if(command.startsWith(pre + "pardon"))
						{
							List<Member> mentionsList = inputMessage.getMentionedMembers();
							
							FunProfile funto;
							try {
								funto = GetProfile(mentionsList.get(0).getUser(), channel);
							} catch (Exception e) {
								channel.sendMessage("USER NOT SPECIFIED").queue();
								return;
							}
							if(!funto.banned) {
								channel.sendMessage("USER NOT BANNED").queue();
								return;
							}
							funto.banned = false;
							channel.sendMessage("USER " + funto.GetName() 
							+ " IS NOW ALLOWED TO USE FUNBUCKS:tm:").queue();
						}
						if(command.startsWith(pre + "tax"))
						{							
							String arg2;
							try {
								arg2 = GetArgAt(command, 1);
							} catch (Exception e) {
								channel.sendMessage("TAX AMOUNT NOT SPECIFIED").queue();
								return;
							}
							
							float amount;
							try {
								amount = Float.parseFloat(arg2);
							} catch (Exception e) {
								channel.sendMessage("TAX AMOUNT NOT UNDERSTOOD").queue();
								return;
							}
							if(amount < 0) {
								channel.sendMessage("THE TAX COMMAND CANNOT BE USED TO INFLATE THE EKROMERMEY").queue();
								return;
							}
							for (FunProfile funto : lads) {
								if(amount > funto.funbucks - FunProfile.defaultvalue) {
									channel.sendMessage("Information: User " + funto.GetName() + " at or under default amount. Skipping").queue();
								}
								else {
									funto.funbucks -= amount;
									funto.blanketedit++;
									channel.sendMessage("USER " + funto.GetName() 
									+ " TAXED **$" + amount + "** FUNBUCKS:tm:").queue();
								}
							}
							channel.sendMessage("TAX COMPLETE").queue();
						}
						if(command.startsWith(pre + "salary"))
						{							
							String arg2;
							try {
								arg2 = GetArgAt(command, 1);
							} catch (Exception e) {
								channel.sendMessage("SALARY AMOUNT NOT SPECIFIED").queue();
								return;
							}
							
							float amount;
							try {
								amount = Float.parseFloat(arg2);
							} catch (Exception e) {
								channel.sendMessage("SALARY AMOUNT NOT UNDERSTOOD").queue();
								return;
							}
							if(amount < 0) {
								channel.sendMessage("THE SALARY COMMAND CANNOT BE USED TO TAX THE EKROMERMEY").queue();
								return;
							}
							for (FunProfile funto : lads) {
								funto.funbucks += amount;
								funto.blanketedit++;
								channel.sendMessage("USER " + funto.GetName() 
								+ " SALARYIED **$" + amount + "** FUNBUCKS:tm:").queue();
							}
							FunProfile.defaultvalue += amount;
							channel.sendMessage("SALARY COMPLETE").queue();
						}
					}
					
					//SAVE DEM FUN(TM) DATABASE STUFFS
					try {
						for (FunProfile funProfile : lads) {
							funProfile.SetUserID();
						}
					    FileOutputStream fos = new FileOutputStream(funName);
					    ObjectOutputStream oos = new ObjectOutputStream(fos);
					    oos.writeObject(lads);
					    oos.flush();
					    oos.close();
					    System.out.println("Saved Fun(TM) state.");
					} catch (Exception e) {
						channel.sendMessage("WARNING: UNABLE TO SAVE TO FUN:tm: DATABASE!").queue();
						e.printStackTrace();
					}
					
					
					//EDIT THE EPIC FUN ECONOMY (R) LEADERBOARD OF FUN (TM)
					for (Guild guild : api.getGuilds()) {
						//System.out.println("Getting Guilds");
						for (TextChannel channel : guild.getTextChannels()) {
							//System.out.println("Getting Channels");
							//System.out.println(channel.getName());
							if(channel.getName().contains("fun-economy")) {
								System.out.println("Found #fun-economy in " + guild.getName());
								
								((MessageChannel) channel).getHistory().retrievePast(1).queue(messages -> {
									String board = "Current registered members of the Fun:tm: economy:\n";
									Collections.sort(lads);
									for (FunProfile funProfile : lads) {
										if(funProfile.owner.getIdLong() == 410529944624693279l)
										{
											board += "$" + "∞" + " <:funbuckplus:886723353199656960> " 
													+ funProfile.owner.getAsMention() + " \"I own the economy\" \n";
										}
										else if(funProfile.banned)
										{
											board += "$" + funProfile.funbucks + " <:funbuckplus:886723353199656960> " 
													+ funProfile.owner.getAsMention() + " (banned) \n";
										}
										else
											board += "$" + funProfile.funbucks + " <:funbuckplus:886723353199656960> " 
													+ funProfile.owner.getAsMention() + "\n";
									}
									
								    // messages (list) contains all received messages
								    // Access them in here
								    // Use for example messages.get(0) to get the received message
								    // (messages is of type List)
									if(messages.size() == 0) {
										System.out.println("not found message");
										channel.sendMessage(board).queue();
									}
									else {
										System.out.println("found message");
										messages.get(0).editMessage(board).queue();
									}
								});
							}
						}
					}//(886795516984328223l).editMessageById(886795577097076746l, board).queue();
					
				}
			}
			
			

		}
		if((!content.toLowerCase().startsWith(prefixString) 
				&& !content.toLowerCase().startsWith(prefixString2))
				|| (content.toLowerCase() == prefixString + "sing"
			    || content.toLowerCase() == prefixString2 + "sing")){
			if(event.getAuthor().isBot())
				return;
			
			List<String> exclude = ReadTextFile("exclude.txt");
			for (String string : exclude) {
				if(event.getGuild().getId().contains(string))
					return;
			}
			
			
			if(random.nextBoolean())
				if(content.toLowerCase().contains("boby")) {
					switch (random.nextInt(5)) {
					case 0:
						channel.sendMessage("yes?").queue();
						break;
					case 1:
						channel.sendMessage("that's my name").queue();
						break;
					case 2:
						channel.sendMessage(
				"human you have mentioned the name of a superior being. I, boby, shall now say hello.").queue();
						channel.sendMessage("\"Hello\"").queue();
						break;
					case 3:
						channel.sendMessage("hi").queue();
						break;
					case 4:
						channel.sendMessage("you mentioned me?").queue();
						break;
					default:
						channel.sendMessage("hmm?").queue();
						break;
					}
				}
		}
		
		//Lyric guy
		List<String> lyrics = ReadTextFile("lyric.txt");
		//System.out.println("Read " + lyrics.size() + " lines.");
		for (int i = 0; i < lyrics.size(); i++) {
			int currentline;
			if(i + lyricline > lyrics.size()) {
				currentline = i + lyricline - lyrics.size();
			}
			else {
				currentline = i + lyricline;
			}
			if(currentline >= lyrics.size()) {
				currentline -= lyrics.size();
			}
			
			//System.out.println("currentline " + currentline + "  \"" + lyrics.get(currentline) + "\"");
			//System.out.println((content.toLowerCase()).contains(lyrics.get(currentline).toLowerCase()));
			if(!"".contains(lyrics.get(currentline)) && 
					((content.toLowerCase()).contains(lyrics.get(currentline).toLowerCase())
					|| (content.toLowerCase()).contains(RemovePunct(lyrics.get(currentline).toLowerCase()))
					//|| (content.toLowerCase()).contains(lyrics.get(currentline).toLowerCase().replace("", ""))
					) || sing){
				if(sing)
				{
					currentline = lyricline;
					sing = false;
				}
				
				int next = 1;
				if("".contains(lyrics.get(currentline + 1)))
					next = 2;
				System.out.println("sending: " + lyrics.get(currentline + next));
				channel.sendMessage(lyrics.get(currentline + next)).queue();;
				lyricline = currentline + next;
				if(lyricline >= lyrics.size())
					lyricline = 0;
				break;
			}
		}
		
		super.onMessageReceived(event);
		}catch (Exception e) {
			channel.sendMessage("Sorry, my brain is spaghetti code :( \nError in command: " + e.toString()).queue();
			e.printStackTrace();
		}
	}
	
	private FunProfile GetProfile(User user, MessageChannel channel) {
		for (FunProfile funProfile : lads) {
			if(funProfile.owner.getIdLong() == user.getIdLong())
				return funProfile;
		}
		channel.sendMessage("Adding new user " + user.getAsMention()).queue();
		FunProfile newprofile = new FunProfile();
		newprofile.owner = user;//.getIdLong();
		newprofile.funbucks = 25;
		lads.add(newprofile);
		return newprofile;
	}
	
	private String RemovePunct(String input) {
		/*if(input.contains("(")) {
			input = input.substring(0, input.indexOf('('));
		}*/
		return input.replace("\'", "").replace("-", " ").replace(".", "")
				.replace("?", "").replace(",", "").replace("\"", "");
	}
	
	private String DownloadFile(Attachment attachment, String dir) {
		String dirString = (System.getProperty("user.dir") + "/bot/" + dir + "/");
		File toDownload = new File(dirString + attachment.getFileName());
		while (toDownload.exists()) {
			toDownload.getName().lastIndexOf('.');
			toDownload = new File(dirString 
					+ toDownload.getName().substring(0, toDownload.getName().lastIndexOf('.'))
							+ "0." + attachment.getFileExtension());
		}
		System.out.println(dirString + toDownload.getName());
		attachment.downloadToFile(toDownload);
		return toDownload.getName();
	}
	
	private List<String> ReadTextFile(String file) {
		File textFile = new File(System.getProperty("user.dir") + "/bot/" + file);
		List<String> lines = new ArrayList<String>();
		try {
			BufferedReader abc = new BufferedReader(new FileReader(textFile));
			String line;
			while((line = abc.readLine()) != null) {
			    lines.add(line);
			}
			abc.close();
		} catch (Exception e) {
			System.out.println("Error reading " + file);
		}
		return lines;
	}
	
	class FileReturn {
		File file;
		int index;
		int length;
	}
	
	private FileReturn getFileFromDir(String dirname, String command) {
		File MotorDir = new File(System.getProperty("user.dir") + "/bot/" + dirname);
		String contents[] = MotorDir.list();
		System.out.println(System.getProperty("user.dir") + "/bot/" + dirname);
		int select = random.nextInt(contents.length);
		if(command != null) {
			try {
				select = Integer.parseInt(GetNextArg(command)) - 1;
			}catch (Exception e){
				if(command.contains("list")) {
					String ls = "";
					for (int i = 0; i < contents.length; i++) {
						ls += "`[" + (i + 1) + "] " + contents[i] + "`\n";
					}
					if(ls.length() > 2000) {
						channel.sendMessage(ls.substring(0, 2000)).queue();
						ls = ls.substring(2000);
						if(!ls.startsWith("`")) {
							ls = "`" + ls;
						}
					}
					channel.sendMessage(ls).queue();
					return null;
				}
			}
		}
		String motorString = contents[select];		
		FileReturn ret = new FileReturn();
		ret.file = new File(MotorDir, motorString);
		ret.index = select;
		ret.length = contents.length;
		return ret;
	}
	
	private String GetNextArg(String command) {
		return GetArgAt(command, 1);
	}
	private String GetArgAt(String command ,int arglevel) {
		return GetArgs(command).get(arglevel - 1);
	}
	private List<String> GetArgs(String command) {//othello7 frick(TM) arg parser
		List<String> Args = new ArrayList<String>();//Set up list for the args
		//arglevel //the arg number
		int index = 0; //how many loop points
		int start = 0; //the location of the arg
		String temp1 = "";//we will do a lot with this
		while(command.chars().filter(ch -> ch == ' ').count() > index) {//lets go on a trip across the entire command!
			start = command.indexOf(" ", start);//find the next " " based from the previous one
			start++;//add 1 to remove the " " at the beginning
			try {//try to get start and end of command
				temp1 = command.substring(start, command.indexOf(" ", start));
			} catch (java.lang.StringIndexOutOfBoundsException e) {//ok we reached the end
				try {//try to get start and follow to end
					temp1 = command.substring(start);
				} catch (Exception ex) {
					System.out.println("No argument");//aah somethin' bad
				}
			}
			Args.add(temp1);//add that arg man lad
			index++;//continue
		}
		return Args;//command.substring(start, temp1.indexOf(" "));
	}
}
