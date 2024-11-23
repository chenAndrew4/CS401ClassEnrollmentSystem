package client;

public interface ResponseCallback<T, R> {
    R onSuccess(T result);
    void onFailure(String reason);
}
