package university.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SchoolEnrolment {
	
	private UUID uuid;
	private String referenceCode;
    private Student student;
	private Course course;
	private CourseClass courseClass;
	private String status; //TODO ENUM
	
}
