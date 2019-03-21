package ai.play.devtech.core.world.tile;

import ai.play.devtech.core.errors.NotEnoughResourcesException;
import ai.play.devtech.core.nation.Nation;
import ai.play.devtech.runtime.Game;
import ai.play.devtech.core.api.interfaces.Assembable;
import ai.play.devtech.core.api.interfaces.Locatable;
import ai.play.devtech.core.api.interfaces.Packetable;
import ai.play.devtech.core.api.io.ByteReader;
import ai.play.devtech.core.api.io.Packer;
import java.awt.Point;

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
