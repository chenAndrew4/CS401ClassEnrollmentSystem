package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Student;


public class EnrollCourseRequest extends BaseRequest {
        private String courseId;
        private String sectionId;
        private Student student;

        public EnrollCourseRequest(MessageType messageType, MessageStatus messageStatus, String sessionToken, String courseId, String sectionId, Student student) {
            super(messageType, messageStatus,student.getInstitutionID() ,student.getSessionToken(), student.isAuthenticated());
            this.courseId = courseId;
            this.sectionId = sectionId;
            this.student = student;
        }

        public String getCourseId() {
            return courseId;
        }

        public String getSectionId() {
            return sectionId;
        }

        public Student getStudent() {
            return student;
        }
}
