package university.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Course {
	
	private UUID uuid;
	private String name;
	private List<Discipline> disciplines;
	
}
