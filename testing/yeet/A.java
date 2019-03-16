package yeet;

import play.ai.devtech.core.api.interfaces.Assembable;
import play.ai.devtech.core.api.interfaces.Packetable;
import play.ai.devtech.core.api.io.ByteReader;
import play.ai.devtech.core.api.io.Packer;

public class A implements Packetable, Assembable {

	int field = 34;
	public A (int a) {
		this.field = a;
	}
	
	public A() {
		
	}
	

	@Override
	public void pack(Packer p) {
		p.packInt(field);
	}

	@Override
	public void from(ByteReader reader) {
		field = reader.readInt();
	}
	
	@Override
	public String toString() {
		return field+"";
	}
}
