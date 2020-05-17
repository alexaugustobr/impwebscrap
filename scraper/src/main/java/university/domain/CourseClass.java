package university.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CourseClass {
	
	private UUID uuid;
	private String letter;
	private String period;
	private Integer semester;
	private Course course;
	
}
