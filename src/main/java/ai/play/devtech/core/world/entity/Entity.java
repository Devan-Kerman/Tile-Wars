package ai.play.devtech.core.world.entity;

import ai.play.devtech.core.api.interfaces.Assembable;
import ai.play.devtech.core.api.interfaces.Locatable;
import ai.play.devtech.core.api.interfaces.Packetable;
import ai.play.devtech.core.api.io.ByteReader;
import ai.play.devtech.core.api.io.Packer;
import java.awt.geom.Point2D;

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
