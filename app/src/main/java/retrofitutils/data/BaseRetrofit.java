package retrofitutils.data;

public class BaseRetrofit<T> {

    int code;
    String message;
    T data;

    @Override
    public String toString() {
        return "BaseRetrofit{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
