package play.ai.devtech.core.api.io;

import java.io.IOException;
import java.io.OutputStream;

import play.ai.devtech.core.api.bytes.Packer;
import play.ai.devtech.core.api.bytes.Packetable;
import play.ai.devtech.core.util.DLogger;

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
