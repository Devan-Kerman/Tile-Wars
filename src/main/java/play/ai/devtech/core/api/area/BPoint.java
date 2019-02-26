package play.ai.devtech.core.api.area;

import play.ai.devtech.core.api.bytes.Assembable;
import play.ai.devtech.core.api.bytes.ByteReader;
import play.ai.devtech.core.api.bytes.Packer;
import play.ai.devtech.core.api.bytes.Packetable;

/**
 * A point, but only uses 2 bytes
 * @author devan
 *
 */
public class BPoint implements Assembable, Packetable{
	public byte x, y;
	public BPoint(byte x, byte y) {
		this.x = x;
		this.y = y;
	}
	
	public BPoint() {
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
