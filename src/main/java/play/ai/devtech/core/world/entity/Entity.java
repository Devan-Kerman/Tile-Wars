package play.ai.devtech.core.world.entity;

import java.awt.geom.Point2D;

import play.ai.devtech.core.api.interfaces.Assembable;
import play.ai.devtech.core.api.interfaces.Locatable;
import play.ai.devtech.core.api.interfaces.Packetable;
import play.ai.devtech.core.api.io.ByteReader;
import play.ai.devtech.core.api.io.Packer;

public abstract class Entity implements Locatable, Packetable, Assembable {
	
	Point2D location;
	
	public Entity(Point2D loc) {
		location = loc;
	}
	
	public Entity() {
		location = new Point2D.Double(0, 0);
	}

	@Override
	public float getX() {
		return (float) location.getX();
	}

	@Override
	public float getY() {
		return (float) location.getY();
	}
	
	@Override
	public void from(ByteReader reader) {
		location.setLocation(reader.readDouble(), reader.readDouble());
	}
	
	@Override
	public void pack(Packer p) {
		p.packDouble(location.getX());
		p.packDouble(location.getY());
	}

}
