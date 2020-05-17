package university.domain;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class SchoolEnrolment {
	
	private UUID uuid;
	private String referenceCode;
    private Student student;
	private Course course;
	private CourseClass courseClass;
	private String status; //TODO ENUM
	
}
