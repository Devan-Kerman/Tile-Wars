
package play.ai.devtech.core.api.functions;

public interface TriConsumer<A,B,C> {
    void accept(A a, B b, C c);
}