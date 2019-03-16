package play.ai.devtech.core.api.objects.primitives;

import play.ai.devtech.core.api.interfaces.Assembable;
import play.ai.devtech.core.api.interfaces.Packetable;
import play.ai.devtech.core.api.io.ByteReader;
import play.ai.devtech.core.api.io.Packer;

public class Int implements Packetable, Assembable {
	volatile int val;
	public Int(int val) {
		this.val = val;
	}
	
	public Int() {}
	
	public int add(Int val) {
		return this.val + val.val;
	}
	
	public int add(int val) {
		return this.val + val;
	}
	
	public int sub(Int val) {
		return this.val - val.val;
	}
	
	public int sub(int val) {
		return this.val - val;
	}
	
	public int preInc(int val, Runnable r) {
		this.val+=val;
		r.run();
		return val;
	}
	
	public int postInc(int val, Runnable r) {
		r.run();
		this.val+=val;
		return val;
	}
	
	public int preInc(Runnable r) {
		return preInc(1, r);
	}
	
	public int postInc(Runnable r) {
		return postInc(1, r);
	}
	
	public int preDec(int val, Runnable r) {
		this.val-=val;
		r.run();
		return val;
	}
	
	public int postDec(int val, Runnable r) {
		r.run();
		this.val-=val;
		return val;
	}
	
	public int preDec(Runnable r) {
		return preDec(1, r);
	}
	
	public int postDec(Runnable r) {
		return postDec(1, r);
	}
	
	public int scale(float scale) {
		this.val*=scale;
		return val;
	}

	@Override
	public void from(ByteReader reader) {
		val = reader.readInt();
	}

	@Override
	public void pack(Packer p) {
		p.packInt(val);
	}
	
}
