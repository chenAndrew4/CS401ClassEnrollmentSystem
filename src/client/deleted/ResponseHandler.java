package client.deleted;

import shared.models.responses.BaseResponse;

// after remove client class, no more usage
public interface ResponseHandler<T> {
    T handleResponse(BaseResponse response);
}
