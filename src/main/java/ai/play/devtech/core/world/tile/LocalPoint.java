package ai.play.devtech.core.world.tile;

import ai.play.devtech.core.api.interfaces.Assembable;
import ai.play.devtech.core.api.interfaces.Packetable;
import ai.play.devtech.core.api.io.ByteReader;
import ai.play.devtech.core.api.io.Packer;

/**
 * A point, but only uses 2 bytes
 * @author devan
 *
 */
public class LocalPoint implements Assembable, Packetable{
	public byte x, y;
	public LocalPoint(byte x, byte y) {
		this.x = x;
		this.y = y;
	}
	
	public LocalPoint() {
		/*For serialization*/
	}
	
	@Override
	public void pack(Packer p) {
		p.packV(x, y);
	}
	
	@Override
	public void from(ByteReader reader) {
		x = reader.read();
		y = reader.read();
	}
}
