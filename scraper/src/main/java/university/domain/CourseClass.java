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
public class CourseClass {
	
	private UUID uuid;
	private String letter;
	private String period; //TODO ENUM
	private Integer year;
	private Integer semester;
	private Course course;
	
}
