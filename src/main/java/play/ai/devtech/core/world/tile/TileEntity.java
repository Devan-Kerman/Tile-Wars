package play.ai.devtech.core.world.tile;

import java.awt.Point;

import play.ai.devtech.core.api.interfaces.Assembable;
import play.ai.devtech.core.api.interfaces.Locatable;
import play.ai.devtech.core.api.interfaces.Packetable;
import play.ai.devtech.core.api.io.ByteReader;
import play.ai.devtech.core.api.io.Packer;
import play.ai.devtech.core.errors.NotEnoughResourcesException;
import play.ai.devtech.core.nation.Nation;
import play.ai.devtech.runtime.Game;

public abstract class TileEntity implements Locatable, Assembable, Packetable {

	Point chunk;
	LocalPoint location;

	public TileEntity() {
		location = new LocalPoint((byte) 0, (byte) 0);
		chunk = new Point();
	}
	
	/**
	 * This method is called when a tile entity is created
	 * @param n
	 */
	public abstract void build(Nation n) throws NotEnoughResourcesException;

	public TileEntity(Point chunk, LocalPoint location) {
		this.chunk = chunk;
		this.location = location;
	}

	public abstract int getId();

	public abstract <T extends Assembable & Packetable> T data();

	public abstract void run(Game g, Nation n);

	@Override
	public float getX() {
		return location.x;
	}

	@Override
	public float getY() {
		return location.y;
	}

	@Override
	public void pack(Packer p) {
		p.packPoint(chunk);
		p.autoPack(location);
		p.autoPack(data());
	}

	@Override
	public void from(ByteReader reader) {
		chunk = reader.readPoint();
		location = reader.read(LocalPoint.class);
		data().from(reader);
	}

}
