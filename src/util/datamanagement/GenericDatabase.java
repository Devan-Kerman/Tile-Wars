package util.datamanagement;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class GenericDatabase<T> {
	public HashMap<String, T> data;
	public File save;
	public GenericDatabase(String location) {
		save = new File(location);
		data = new HashMap<String, T>();
	}
	
	public void write(T c) {
		try {
			File f = new File(save.getAbsolutePath()+".temp");
			f.delete();
			f.createNewFile();
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f), 300000));
			oos.writeObject(c);
			oos.close();
			f.renameTo(save);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public T read(int x, int y) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(save), 300000));
		T c = (T) ois.readObject();
		ois.close();
		return c;
	}
}
