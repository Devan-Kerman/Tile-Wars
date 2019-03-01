package play.ai.devtech.network.players;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import play.ai.devtech.core.api.bytes.Packer;
import play.ai.devtech.core.api.io.Output;
import play.ai.devtech.core.api.io.Wrapper;

public class Players {
	public static Map<String, String> logins;
	public static Map<Login, Integer> nations;
	
	private static Supplier<Wrapper<Set<String>>> userwrappers;
	static {
		logins = new HashMap<>();
		nations = new HashMap<>();
		userwrappers = () -> new Wrapper<>(obj -> {
			Packer packer = new Packer();
			packer.packInt(logins.size());
			logins.forEach((s1, s2) -> {
				packer.packString(s1, StandardCharsets.US_ASCII);
				packer.packString(s2, StandardCharsets.US_ASCII);
				packer.packInt(nations.get(new Login(s1, s2)));
			});
			return packer.unpack();
		}, bar -> {
			int len = bar.readInt();
			for (int x = 0; x < len; x++) {
				Login log = new Login(bar.readString(StandardCharsets.US_ASCII), bar.readString(StandardCharsets.US_ASCII));
				logins.put(log.username, log.password);
				nations.put(log, bar.readInt());
			}
			return null;
		});
	}

	public static void save() throws FileNotFoundException {
		new Output(new BufferedOutputStream(new FileOutputStream("players.list"))).write(userwrappers.get());
	}
}
