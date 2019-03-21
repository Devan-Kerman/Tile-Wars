package ai.play.devtech.core.api.interfaces;

import ai.play.devtech.core.api.io.Packer;

public interface Packetable {
	
	/**
	 * Serialize this objects data into the array
	 * @param bb
	 * 		the array
	 * @param start
	 * 		starting index
	 * @return
	 * 		number of bytes serialized to the array
	 */
	void pack(Packer p);
}
