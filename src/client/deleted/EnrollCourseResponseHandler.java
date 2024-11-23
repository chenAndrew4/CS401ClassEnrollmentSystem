package client.deleted;

import shared.models.responses.BaseResponse;

// after remove client class, no more usage
public class EnrollCourseResponseHandler implements ResponseHandler {
    @Override
    public Object handleResponse(BaseResponse response) {
        return null;
    }
}
