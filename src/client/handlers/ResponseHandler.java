package client.handlers;

import shared.models.responses.BaseResponse;

public interface ResponseHandler<T> {
    T handleResponse(BaseResponse response);
}
