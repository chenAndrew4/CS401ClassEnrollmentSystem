package shared.models.requests;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.enums.Institutions;
import shared.models.Student;


public class SearchWaitlistRequest extends BaseRequest 
{
    private Student student;

    public SearchWaitlistRequest(MessageType messageType, MessageStatus messageStatus, String sessionToken, Student student) 
    {
        super(messageType, messageStatus, student.getInstitutionID(), student.getSessionToken(), student.isAuthenticated());
        this.student = student;
    }

    public Institutions getInstitution()
    {
    	return this.student.getInstitutionID();
    }

    public Student getStudent() 
    {
        return this.student;
    }
}
