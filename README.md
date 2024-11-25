# CS401ClassEnrollmentSystem
##### 1.
all the file path of dbs, images goes to clientConfig and serverManager class which access to whole client or server 
##### 2.
if you got casting problem check corresponding datamanager file, it returns a copy of the instance, may have problem there
##### 3.
username and password is in userdatamanagerTest file
##### 4.
student -> enroll course -> send enroll course request
student -> drop course -> send drop course request
student -> view enrolled courses -> student.enrolledcourse list get info
student -> view waitlist courses -> student.waitlistcourse list get info
student -> view schedule -> student.enrolledcourse list -> term + scheduleID list -> getscheduleList
student -> view grade -> student.finishedcoursesGrade list get grade info
faculty -> view assigned courses -> faculty.assignedcoures list get info
faculty -> view class roster -> faculty.assignedcourse list get info
faculty -> view waitlist -> faculty.assignedcourse list -> term + scheduleID list -> getscheduleList;

In a course enrollment system dashboard, students, faculty, and administrators have distinct roles and permissions based on their responsibilities. Here's a breakdown of typical functionalities available to each user type:

---

### **For Students**
Students primarily interact with the system to manage their courses and academic progress.

1. **Course Enrollment and Management**
    - Enroll in courses.(Manage schedule conflicts or resolve enrollment holds)
    - Drop courses.
    - View waitlisted courses and positions.

2. **Schedule and Academic Details**
    - View their weekly or semester course schedule.
    - Access details for each course, including syllabus, instructor contact, and room location.

3. **Grades and Academic Records**
    - View grades for completed courses.
    - Access academic transcripts.

4. **Waitlist Management**
    - Join a waitlist for full courses.
    - Check waitlist status and position.
    - Receive notifications when a seat becomes available.

6. **Administrative Features**
    - Update personal details (e.g., address, phone number).
    - Receive announcements and notifications from the university.
---

### **For Faculty**
Faculty members use the system to manage the courses they teach and interact with their students.

1. **Course Management**
    - modify course sections (if allowed by the institution).
    - Set course capacity limits and waitlist thresholds.
    - Upload and update the course syllabus.

2. **Class Roster and Attendance**
    - View enrolled students and waitlists.

3. **Grading and Assessments**
    - Enter grades and comments for assignments, exams, and final grades.
    - Manage grading scales and weightage.
    - Publish grades and notify students.

4. **Schedule and Availability**
    - View teaching schedule.
    - Manage office hours and meeting availability.


** dont do for now**
5. **Notifications and Feedback**
    - Send announcements to students enrolled in their courses.
    - Receive student feedback or course evaluations.

6. **Advisory Roles**
    - Advise assigned students (if acting as an advisor).
    - Approve or reject course overrides or special enrollment requests.

---

### **For Administrators**
Administrators manage the overall functioning of the enrollment system and maintain academic records.

1. **Course and Program Management**
    - Add, update, or delete courses and academic programs.
    - Manage course prerequisites, co-requisites, and dependencies.
    - Set semester schedules and registration deadlines.

2. **User Management**
    - Add or update user accounts (students, faculty, and administrator).
    - Assign roles and permissions to users.
    - Reset passwords and resolve login issues.

3. **Enrollment and Waitlist Oversight**
    - Override enrollment limits for specific students or courses.
    - Monitor waitlist statuses and send notifications.
    - Resolve schedule conflicts or disputes.

4. **Reports and Analytics**
    - Generate reports on course enrollments, waitlists, and academic performance.
    - Analyze trends in enrollment data (e.g., course popularity).
    - Monitor system usage and detect issues.

5. **Notifications and Announcements**
    - Broadcast messages to specific user groups (e.g., students, faculty).
    - Post system updates, academic calendars, or policy changes.

6. **System Configuration**
    - Update grading policies, and academic regulations.
    - Monitor and resolve system errors or performance issues.

---

### **Summary Table**
| **Feature**                 | **Student** | **Faculty** | **Administrator** |
|-----------------------------|-------------|-------------|--------------------|
| Enroll/Drop Courses         | ✅           | ❌           | ✅                 |
| Manage Waitlist             | ✅           | ❌           | ✅                 |
| View/Modify Schedule        | ✅           | ✅           | ✅                 |
| Grade Students              | ❌           | ✅           | ❌                 |
| View Grades                 | ✅           | ✅           | ✅                 |
| Course Management           | ❌           | ✅           | ✅                 |
| Manage User Accounts        | ❌           | ❌           | ✅                 |
| Notifications/Announcements | ✅           | ✅           | ✅                 |
| Generate Reports            | ❌           | ❌           | ✅                 |
| System Configuration        | ❌           | ❌           | ✅                 |

--- 

This structure ensures that each user group has access to functionalities necessary for their roles while maintaining system security and organization.
