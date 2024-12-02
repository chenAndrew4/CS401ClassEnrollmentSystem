package client;

import java.io.File;

import javax.swing.JOptionPane;

public class ClientConfig {
	public static String SERVER_ADDRESS = "localhost";
    public static int SERVER_PORT = 25800;

    // File path
    public static final String ICON_FILE_PATH_PREFIX = "src" + File.separator + "client" + File.separator + "assets" + File.separator + "icons" + File.separator;
    public static final String CLIENT_LOGO_FILE_PATH = "src" + File.separator + "client" + File.separator + "assets" + File.separator + "icons" + File.separator + "app_32.png";

    // SJSU Paths
    public static final String SJSU_FILE_PATH_PREFIX = ICON_FILE_PATH_PREFIX + "SJSU" + File.separator;
    public static final String SJSU_LOGO_FILE_PATH = SJSU_FILE_PATH_PREFIX + "logo.png";
    public static final String SJSU_DASH_BACKGROUND_FILE_PATH = SJSU_FILE_PATH_PREFIX + "dashboard_background.png";

    // CSUEB Paths
    public static final String CSUEB_FILE_PATH_PREFIX = ICON_FILE_PATH_PREFIX + "CSUEB" + File.separator;
    public static final String CSUEB_LOGO_FILE_PATH = CSUEB_FILE_PATH_PREFIX + "logo.png";
    public static final String CSUEB_DASH_BACKGROUND_FILE_PATH = CSUEB_FILE_PATH_PREFIX + "dashboard_background.png";

    // CSUF Paths
    public static final String CSUF_FILE_PATH_PREFIX = ICON_FILE_PATH_PREFIX + "CSUF" + File.separator;
    public static final String CSUF_LOGO_FILE_PATH = CSUF_FILE_PATH_PREFIX + "logo.png";
    public static final String CSUF_DASH_BACKGROUND_FILE_PATH = CSUF_FILE_PATH_PREFIX + "dashboard_background.png";

    // Common Icons
    public static final String DEFAULT_STUDENT_ICON = ICON_FILE_PATH_PREFIX + "student1.png";
    public static final String DEFAULT_FACULTY_ICON = ICON_FILE_PATH_PREFIX + "faculty1.png";
    public static final String DEFAULT_ADMIN_ICON = ICON_FILE_PATH_PREFIX + "admin.png";
    public static final String DEFAULT_DASH_BACKGROUND_PATH = ICON_FILE_PATH_PREFIX + "dashboard_background.png";
    public static final String INVALID_CREDS_FILE_PATH = ICON_FILE_PATH_PREFIX + "invalid_creds.png";
    public static final String ENROLL_ICON = ICON_FILE_PATH_PREFIX + "add.png";
    public static final String DROP_ICON = ICON_FILE_PATH_PREFIX + "remove.png";
    public static final String WAITLIST_ICON = ICON_FILE_PATH_PREFIX + "waitlist.png";
    public static final String GRADES_ICON = ICON_FILE_PATH_PREFIX + "grade1.png";
    public static final String SCHEDULE_ICON = ICON_FILE_PATH_PREFIX + "schedule.png";
    public static final String LOGOUT_ICON = ICON_FILE_PATH_PREFIX + "logout.png";
    public static final String MANAGE_USERS_ICON = ICON_FILE_PATH_PREFIX + "users.png";
    public static final String MANAGE_COURSES_ICON = ICON_FILE_PATH_PREFIX + "manage_course.png";
    public static final String REPORTS_ICON = ICON_FILE_PATH_PREFIX + "report.png";
    public static final String MANAGE_REPORTS_ICON = ICON_FILE_PATH_PREFIX + "reports.png";
    public static final String SETTING_ICON = ICON_FILE_PATH_PREFIX + "setting.png";
    public static final String MANAGE_CLASSES_ICON = ICON_FILE_PATH_PREFIX + "course.png";
    public static final String GRADE_SUBMISSIONS_ICON = ICON_FILE_PATH_PREFIX + "grade.png";
    public static final String COMMUNICATION_ICON = ICON_FILE_PATH_PREFIX + "notices.png";
    public static final String COURSE_ICON = ICON_FILE_PATH_PREFIX + "course.png";
    public static final String MANAGE_WAITLIST_ICON = ICON_FILE_PATH_PREFIX + "waitlist.png";
    public static final String MANAGE_SCHEDULE_ICON = ICON_FILE_PATH_PREFIX + "schedule.png";
    public static final String MANAGE_NOTICE_ICON = ICON_FILE_PATH_PREFIX + "notification.png";
    public static final String BACK_ARROW_ICON = ICON_FILE_PATH_PREFIX + "backArrow.png";
    public static final String NEXT_TERM_ICON = ICON_FILE_PATH_PREFIX + "nextTerm.png";
    public static final String SEARCH_ICON = ICON_FILE_PATH_PREFIX + "search.png";
    public static final String LEFT_ARROW_ICON = ICON_FILE_PATH_PREFIX + "left_arrow.png";
    public static final String RIGHT_ARROW_ICON = ICON_FILE_PATH_PREFIX + "right_arrow.png";
    public static final String ERROR_ICON = ICON_FILE_PATH_PREFIX + "error_32.png";
    public static final String SUCCESS_ICON = ICON_FILE_PATH_PREFIX + "success_32.png";
    public static final String SAVE_ICON = ICON_FILE_PATH_PREFIX + "save_24.png";
    
    // Administrator *GUI
    // Administrator - Manage Users
    public static final String ADD_USER = ICON_FILE_PATH_PREFIX + "add_user.png";
    public static final String DELETE_USER = ICON_FILE_PATH_PREFIX + "remove_user.png";
    public static final String EDIT_USER = ICON_FILE_PATH_PREFIX + "edit_user.png";
    // Administrator - Manage Courses
    public static final String ADD_COURSE = ICON_FILE_PATH_PREFIX + "add_course.png";
    public static final String DELETE_COURSE = ICON_FILE_PATH_PREFIX + "remove_course.png";
    public static final String EDIT_COURSE = ICON_FILE_PATH_PREFIX + "edit_course.png";
    // Administrator - Reports
    public static final String COURSE_DETAILS_REPORT = ICON_FILE_PATH_PREFIX + "course_details.png";
    public static final String GRADE_REPORT = ICON_FILE_PATH_PREFIX + "grades_report.png";
    public static final String WAITLISTS_REPORT = ICON_FILE_PATH_PREFIX + "waitlist_report.png";
    public static final String SCHEDULE_REPORT = ICON_FILE_PATH_PREFIX + "manage_schedule.png";
    public static final String ENROLLMENT_REPORT = ICON_FILE_PATH_PREFIX + "enrollment_report.png";
    // Administrator - System Configuration
    public static final String SERVER_SETTINGS = ICON_FILE_PATH_PREFIX + "server_settings.png";
    public static final String PROFILE_SETTINGS = ICON_FILE_PATH_PREFIX + "edit_profile.png";
    public static final String SYSTEM_CONFIG = ICON_FILE_PATH_PREFIX + "system_config.png";
    // Administrator - Manage Enrollment
    public static final String MANAGE_ENROLLMENT_ICON = ICON_FILE_PATH_PREFIX + "manage_enrollment.png";
    // Administrator - Manage Notifications
    public static final String MANAGE_NOTIFICATIONS = ICON_FILE_PATH_PREFIX + "manage_notifications.png";
    // Administrator - Manage Waitlist
    public static final String MANAGE_WAITLIST = ICON_FILE_PATH_PREFIX + "manage_waitlist.png";
    public static final String ADD_WAITLIST = ICON_FILE_PATH_PREFIX + "add_waitlist.png";
    public static final String DELETE_WAITLIST = ICON_FILE_PATH_PREFIX + "delete_waitlist.png";
    public static final String EDIT_WAITLIST = ICON_FILE_PATH_PREFIX + "edit_waitlist.png";
    // Administrator - Manage Schedules
    public static final String MANAGE_SCHEDULES = ICON_FILE_PATH_PREFIX + "manage_schedule.png";
    public static final String ADD_SCHEDULE = ICON_FILE_PATH_PREFIX + "add_schedule.png";
    public static final String DELETE_SCHEDULE = ICON_FILE_PATH_PREFIX + "delete_schedule.png";
    public static final String EDIT_SCHEDULE = ICON_FILE_PATH_PREFIX + "edit_schedule.png";
    public static final String ASSIGNED_COURSES = ICON_FILE_PATH_PREFIX + "course.png";
    public static final String ASSIGNED_SYLLABUS = ICON_FILE_PATH_PREFIX + "syllabus.png";

    
    // Administrator - JOptionPane.showMessageDialog()
    // Administrator - Add User
    public static final String ADD_USER_32 = ICON_FILE_PATH_PREFIX + "add_user_32.png";
    // Administrator - Edit User
    public static final String EDIT_USER_32 = ICON_FILE_PATH_PREFIX + "edit_user_32.png";
    
    
    
    // frame size
    // initial frame
    public static final int INITIAL_FRAME_WIDTH = 1600;
    public static final int INITIAL_FRAME_HEIGHT = 1200;
}
