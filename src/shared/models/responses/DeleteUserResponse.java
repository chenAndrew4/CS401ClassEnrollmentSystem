package shared.models.responses;

public class DeleteUserResponse extends BaseResponse {
    private String deletedUsername; // The username of the deleted user

    public DeleteUserResponse(String message, String deletedUsername) {
        super(message);
        this.deletedUsername = deletedUsername;
    }

    public String getDeletedUsername() {
        return deletedUsername;
    }

    public void setDeletedUsername(String deletedUsername) {
        this.deletedUsername = deletedUsername;
    }
}

