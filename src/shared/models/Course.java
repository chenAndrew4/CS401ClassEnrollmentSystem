package shared.models;

import shared.enums.*;
import shared.utils.IDGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
	private String courseID;
	private String name;
	private String description;
	private Institutions institutionID;
	private String notes;
	private LevelType level;
	private AcademicProgramType academicProgram;
	private Float units;
	private Department department;
	private List<String> prerequisites;
	private List<CourseSection> sections;

	public Course() {}

	public Course(String courseID, String name, String description, Institutions institutionID, String notes, LevelType level, AcademicProgramType academicProgram, Float units, Department department) {
		this.courseID = courseID;
		this.name = name;
		this.description = description;
		this.institutionID = institutionID;
		this.notes = notes;
		this.level = level;
		this.academicProgram = academicProgram;
		this.units = units;
		this.department = department;
	}

	// Copy constructor
	public Course(Course other) {
		this.courseID = other.courseID;
		this.name = other.name;
		this.description = other.description;
		this.institutionID = other.institutionID;
		this.notes = other.notes;
		this.level = other.level;
		this.academicProgram = other.academicProgram;
		this.units = other.units;
		this.department = other.department;

		// Deep copy of prerequisites
		this.prerequisites = new ArrayList<>(other.prerequisites);

		// Deep copy of sections
		this.sections = new ArrayList<>();
		for (CourseSection section : other.sections) {
			this.sections.add(new CourseSection(section)); // Assuming CourseSection has a copy constructor
		}
	}

	public String getCourseID() {
		return courseID;
	}

	public Course setCourseID(String courseID) {
//		if (courseID.startsWith(department.name())) {
			this.courseID = courseID;
			return this;
//		}
//		return null;
	}

	public String getName() {
		return name;
	}

	public Course setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Course setDescription(String description) {
		this.description = description;
		return this;
	}

	public Institutions getInstitutionID() {
		return institutionID;
	}

	public Course setInstitutionID(Institutions institutionID) {
		this.institutionID = institutionID;
		return this;
	}

	public String getNotes() {
		return notes;
	}

	public Course setNotes(String notes) {
		this.notes = notes;
		return this;
	}

	public LevelType getLevel() {
		return level;
	}

	public Course setLevel(LevelType level) {
		this.level = level;
		return this;
	}

	public AcademicProgramType getAcademicProgram() {
		return academicProgram;
	}

	public Course setAcademicProgram(AcademicProgramType academicProgram) {
		this.academicProgram = academicProgram;
		return this;
	}

	public Float getUnits() {
		return units;
	}

	public Course setUnits(Float units) {
		this.units = units;
		return this;
	}

	public Department getDepartment() {
		return department;
	}

	public Course setDepartment(Department department) {
		this.department = department;
		return this;
	}

	public List<CourseSection> getSections() {
		return sections;
	}

	public void setSections(List<CourseSection> sections) {
		this.sections = sections;
	}

	public List<String> getPrerequisites() {
		return prerequisites;
	}

	public Course setPrerequisites(List<String> prerequisites) {
		this.prerequisites = prerequisites;
		return this;
	}

	@Override
	public String toString() {
		return "Course{" +
				"courseID='" + courseID + '\'' +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", institutionID=" + institutionID +
				", notes='" + notes + '\'' +
				", level=" + level +
				", academicProgram=" + academicProgram +
				", units=" + units +
				", department=" + department +
				", prerequisites=" + prerequisites +
				'}';
	}
}