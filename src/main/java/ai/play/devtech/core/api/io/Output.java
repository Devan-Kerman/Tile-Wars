package ai.play.devtech.core.api.io;

import ai.play.devtech.core.api.interfaces.Packetable;
import ai.play.devtech.tilewars.DLogger;
import java.io.IOException;
import java.io.OutputStream;

public class Output {

	OutputStream os;
	Packer packer = new Packer();
	public Output(OutputStream os) {
		this.os = os;
	}
	
	public <T extends Packetable> void write(T t) {
		packer.autoPack(t);
	}
	
	public void push() {
		try {
			os.write(packer.unpack());
			os.flush();
			os.close();
		} catch (IOException e) {
			DLogger.error("ERROR ON PUSH");
			e.printStackTrace();
		}
	}
}
