package ai.play.devtech.core.api.io;

import ai.play.devtech.core.api.interfaces.Assembable;
import ai.play.devtech.core.api.testing.Bencher;
import ai.play.devtech.core.api.testing.Benchmark;
import ai.play.devtech.tilewars.DLogger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Only for single time operations like file reading (no networking)
 * 
 * @author devan
 *
 */
public class Input {

	InputStream is;

	public Input(InputStream is) {
		this.is = is;
	}

	public static Bencher deSerializationBencher = new Bencher("benchmarks/Deserialization.txt");
	public <T extends Assembable> T read(Class<T> clas) throws IOException {
		try {
			T t = clas.newInstance();
			byte[] read = readAll();
			Benchmark benchmark = deSerializationBencher.getBenchmarker();
			benchmark.start();
			t.from(new ByteReader(read));
			benchmark.stop();
			benchmark.submit();
			return t;
		} catch (InstantiationException | IllegalAccessException e) {
			DLogger.error("No. " + clas);
			e.printStackTrace();
		}
		return null;
	}

	public byte[] readAll() throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[1024];
		while ((nRead = is.read(data, 0, data.length)) != -1)
			buffer.write(data, 0, nRead);
		buffer.flush();
		return buffer.toByteArray();
	}

}
