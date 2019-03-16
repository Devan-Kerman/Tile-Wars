package server.network;

import java.util.function.BiFunction;

public interface Opcode extends BiFunction<byte[], Client, byte[]> {

}
