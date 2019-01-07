package util.datamanagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import game.GlobalData;
import main.DLogger;

public class GenericDatabase<K extends Object,T extends Object> {
	private Map<K, T> data;
	private String save;
	public GenericDatabase(String location, Class<K> clask, Class<T> clasy) {
		this(location);
		GlobalData.kryo.register(clask);
		GlobalData.kryo.register(clasy);
	}
	public GenericDatabase(String location) {
		save = location;
		data = new HashMap<>();
		File f = new File(save);
		if(!f.exists()) try {f.createNewFile(); write();} catch (IOException e) {DLogger.error(e.getMessage());}
		else
			read();
	}
	
	public void put(K key, T value) {
		data.put(key, value);
	}
	public T get(K key) {
		return data.get(key);
	}
	
	public void write() {
		try {
			File f = new File(save);
			Files.delete(f.toPath());
			f.createNewFile();
			Output out = new Output(new FileOutputStream(save));
			GlobalData.kryo.writeObject(out, data);
			out.flush();
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public void read() {
		try {
			data = (HashMap<K, T>)GlobalData.kryo.readClassAndObject(new Input(new FileInputStream(save)));
		} catch(Exception e) {e.printStackTrace();}
	}
}
