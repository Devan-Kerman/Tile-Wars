package util.datamanagement;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class GenericDatabase<T extends Serializable> {
	public Map<String, T> data;
	public File save;
	public GenericDatabase(String location) {
		save = new File(location);
		data = new HashMap<>();
	}
	
	public void replace(String key, T value) {
		data.remove(key);
		data.put(key, value);
	}
	
	public void write() {
		try {
			File f = new File(save.getAbsolutePath()+".temp");
			Files.delete(f.toPath());
			f.createNewFile();
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f), 300000));
			oos.writeObject(data);
			oos.close();
			f.renameTo(save);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public void read() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(save), 300000));
			data = (HashMap<String, T>) ois.readObject();
			ois.close();
		} catch(Exception e) {e.printStackTrace();}
	}
}
