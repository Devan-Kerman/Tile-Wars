package yeet;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import play.ai.devtech.core.api.ArrayBuilder;
import play.ai.devtech.core.api.bytes.ByteReader;
import play.ai.devtech.core.api.bytes.Packer;
import play.ai.devtech.core.world.chunk.Chunk;
import play.ai.devtech.core.world.chunk.ChunkManager;

public class Testing {
	public static void main(String[] args) {
		Chunk chunk = ChunkManager.safeChunk(10, 12);
		long current = System.currentTimeMillis();
		Packer packer = new Packer();
		packer.pack((byte)1);
		packer.pack((byte)2);
		packer.pack((byte)3);
		packer.pack((byte)4);
		packer.pack((byte)5);
		packer.pack((byte)6);
		packer.packFloat(10);
		packer.packDouble(10.5);
		packer.packInt(11);
		packer.packPoint(new Point(12, 13));
		packer.packShort((short)14);
		packer.packList(Arrays.<A>asList(new A(15), new A(16), new A(17), new A(18), new A(19), new A(20)));
		Map<A, A> aa = new HashMap<>();
		aa.put(new A(21), new A(22));
		aa.put(new A(23), new A(24));
		aa.put(new A(25), new A(26));
		aa.put(new A(27), new A(28));
		packer.packMap(aa);
		ArrayBuilder test = new ArrayBuilder(Integer.class);
		test.append(29);
		test.append(30);
		test.append(31);
		test.append(32);
		test.append(33);
		test.append(34);
		test.append(35);
		test.append(36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50);
		test.append(51);
		test.append(52);
		packer.packObj(test);
		packer.autoPack(chunk);
		long t = (System.currentTimeMillis()-current);
		
		
		ByteReader reader = new ByteReader(packer.unpack());
		System.out.println("Bytes: " + Arrays.toString(reader.read(6)));
		System.out.println("Float: " + reader.readFloat());
		System.out.println("Double: " + reader.readDouble());
		System.out.println("Int: " + reader.readInt());
		System.out.println("Point: " + reader.readPoint());
		System.out.println("Short: " + reader.readShort());
		System.out.println("List: " + reader.readList(A.class));
		System.out.println("Map: " + reader.readMap(A.class, A.class));
		System.out.println("Object: " + Arrays.toString((Integer[])reader.<ArrayBuilder>readObject().build()));
		//System.out.println("Chunk: " + Arrays.deepToString(reader.read(Chunk.class).tiles));
		System.out.println("\nTotal (b): "+reader.len());
		System.out.println("Time: " + t +"ms \n");
	}
	
	static float not(float x) {
		return x*2.0f;
	}
}