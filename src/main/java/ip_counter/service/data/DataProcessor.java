package ip_counter.service.data;

public interface DataProcessor<T> {

    void process(T t);

    long getResult();
}
