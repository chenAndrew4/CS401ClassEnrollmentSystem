package shared.models.requests;

import shared.enums.Institutions;

public class SetSyllabusRequest extends BaseRequest {
    private String sectionId;
    private String syllabus;

    public SetSyllabusRequest(Institutions institutionID, String sessionToken, String sectionId, String syllabus) {
        super(institutionID, sessionToken);
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
