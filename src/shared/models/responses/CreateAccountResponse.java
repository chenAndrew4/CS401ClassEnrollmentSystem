package shared.models.responses;

public class CreateAccountResponse extends BaseResponse {
    private String userId;

    public CreateAccountResponse(String message, String userId) {
        super(message);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
