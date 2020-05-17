package university.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Grade {
	
	private UUID uuid;
	private CourseClass courseClass;
	private Discipline discipline;
	private Course course;
	private Student student;
	private SchoolEnrolment schoolEnrolment;
	
}
