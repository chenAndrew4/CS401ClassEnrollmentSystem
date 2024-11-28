package client;

import java.io.File;

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
    public static final String DEFAULT_ADMIN_ICON = ICON_FILE_PATH_PREFIX + "administrator1.png";
    public static final String DEFAULT_DASH_BACKGROUND_PATH = ICON_FILE_PATH_PREFIX + "dashboard_background.png";
    public static final String INVALID_CREDS_FILE_PATH = ICON_FILE_PATH_PREFIX + "invalid_creds.png";
    public static final String ENROLL_ICON = ICON_FILE_PATH_PREFIX + "add.png";
    public static final String DROP_ICON = ICON_FILE_PATH_PREFIX + "remove.png";
    public static final String WAITLIST_ICON = ICON_FILE_PATH_PREFIX + "waitlist.png";
    public static final String GRADES_ICON = ICON_FILE_PATH_PREFIX + "grade1.png";
    public static final String SCHEDULE_ICON = ICON_FILE_PATH_PREFIX + "schedule.png";
    public static final String LOGOUT_ICON = ICON_FILE_PATH_PREFIX + "logout.png";
    public static final String MANAGE_USERS_ICON = ICON_FILE_PATH_PREFIX + "user.png";
    public static final String MANAGE_COURSES_ICON = ICON_FILE_PATH_PREFIX + "course.png";
    public static final String REPORTS_ICON = ICON_FILE_PATH_PREFIX + "report.png";
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

    // frame size
    // initial frame
    public static final int INITIAL_FRAME_WIDTH = 1600;
    public static final int INITIAL_FRAME_HEIGHT = 1200;
}
