package shared.models.requests;

import shared.enums.Institutes;

public class SetSyllabusRequest extends BaseRequest {
    private String sectionId;
    private String syllabus;

    public SetSyllabusRequest(Institutes instituteID, String sessionToken, String sectionId, String syllabus) {
        super(instituteID, sessionToken);
        this.sectionId = sectionId;
        this.syllabus = syllabus;
    }

    public String getSectionId() {
        return sectionId;
    }

    public String getSyllabus() {
        return syllabus;
    }
}
