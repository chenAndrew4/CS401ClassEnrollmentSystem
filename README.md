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
facult -> view class roster -> faculty.assignedcourse list get info
facult -> view waitlist -> 