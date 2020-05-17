package university.scraper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import university.domain.Course;
import university.domain.CourseClass;
import university.domain.SchoolEnrolment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@AllArgsConstructor
class SchoolEnrolmentWorker {
	
	private static final String TABLE_BODY_ID = "grid-cursos-notas-faltas";
	
	private static final String URL = UniversityUrls.SCHOOL_ENROLMENT;
	
	private static final int TDS_ENROLMENT_CODE_INDEX = 0;
	private static final int TDS_YEAR_INDEX = 1;
	private static final int TDS_COURSE_NAME_INDEX = 2;
	private static final int TDS_COURSE_CLASS_INDEX = 3;
	private static final int TDS_COURSE_CLASS_SEMESTER_INDEX = 4;
	private static final int TDS_STATUS_INDEX = 5;
	
	private static final String REFERENCE_CODE_JSON_OBJECT_ATTRIBUTE_NAME = "codigo";
	
	public List<SchoolEnrolment> extractAllSchoolEnrollments(UserSession userSession) {
		
		try {
			Document document = new UniversityRequestWorker()
					.withUrl(URL)
					.withUser(userSession)
					.doGet()
					.parse();
			
			return extractFromDocument(document);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private List<SchoolEnrolment> extractFromDocument(Document document) {
		
		Element tableBody = document.getElementById(TABLE_BODY_ID);
		
		List<SchoolEnrolment> enrolments = extractFromTableBody(tableBody);
		
		return enrolments;
	}
	
	private List<SchoolEnrolment> extractFromTableBody(Element table) {
		
		List<SchoolEnrolment> schoolEnrolmentList = new ArrayList<>();
		
		Elements rowList = table.getElementsByTag("tr");
		
		Iterator<Element> iterator = rowList.iterator();
		
		while (iterator.hasNext()) {
			Element row = iterator.next();
			SchoolEnrolment enrolment = extractFromRow(row);
			schoolEnrolmentList.add(enrolment);
		}
		
		return schoolEnrolmentList;
	}
	
	private SchoolEnrolment extractFromRow(Element row) {
		
		Elements tdList = row.getElementsByTag("td");
		
		Iterator<Element> iterator = tdList.iterator();
		
		String referenceCode = extractReferenceCodeFromTd(tdList.get(TDS_ENROLMENT_CODE_INDEX));
		
		Integer year = extractYearFromTd(tdList.get(TDS_YEAR_INDEX));
		
		String courseName = extractCourseNameFromTd(tdList.get(TDS_COURSE_NAME_INDEX));
		
		String letter = extractLetterFromTd(tdList.get(TDS_COURSE_CLASS_INDEX));
		
		String period = extractPeriodFromTd(tdList.get(TDS_COURSE_CLASS_INDEX));
		
		Integer semester = extractSemesterFromTd(tdList.get(TDS_COURSE_CLASS_SEMESTER_INDEX));
		
		String status = extractStatusFromTd(tdList.get(TDS_STATUS_INDEX));
		
		Course course = Course.builder()
				.name(courseName)
				.build();
		
		CourseClass courseClass = CourseClass.builder()
				.year(year)
				.semester(semester)
				.letter(letter)
				.period(period)
				.build();
		
		return SchoolEnrolment.builder()
				.referenceCode(referenceCode)
				.course(course)
				.courseClass(courseClass)
				.status(status)
				.build();
	}
	
	private String extractStatusFromTd(Element element) {
		return element.text();
	}
	
	private String extractPeriodFromTd(Element element) {
		return element.text().split(" ")[2];
	}
	
	private String extractLetterFromTd(Element element) {
		return element.text().split(" ")[1].substring(1);
	}
	
	private String extractCourseNameFromTd(Element element) {
		return element.text();
	}
	
	private String extractReferenceCodeFromTd(Element td) {
		
		Element a = td.getElementsByTag("a").iterator().next();
		
		return extractReferenceCodeFromLink(a);
	}
	
	private String extractReferenceCodeFromLink(Element a) {
		String attrJson = a.attr("data-obj");
		
		JsonObject jsonObject = new Gson().fromJson(attrJson, JsonObject.class);
		
		JsonPrimitive primitive = jsonObject.getAsJsonPrimitive(REFERENCE_CODE_JSON_OBJECT_ATTRIBUTE_NAME);
		
		return primitive.getAsString();
	}
	
	private Integer extractYearFromTd(Element element) {
		String text = element.text();
		return Integer.valueOf(text.split("/")[0]);
	}
	
	private Integer extractSemesterFromTd(Element element) {
		return Integer.valueOf(element.text().split("º ")[0]);
	}
	
	public static void main(String[] args) {
		
		
		System.out.println("1A".substring(1));
		
		String html = new String("<a class=\"btn btn-boletim\" data-obj='{\"page\":\"boletim-graduacao-ac\",\"codigo\":\"MDE2TVRVNE9UYzBNRFk1TUE9PU5UUTROREU9\"}' rel=\"internal\"><span class=\"visualization\">Visualizar</span><img src=\"../res/img/aluno/search.png\" /></a>");
		
		Element tag = Jsoup.parse(html, "", Parser.xmlParser());
		
		String s = new SchoolEnrolmentWorker().extractReferenceCodeFromLink(tag.child(0));
	}
	
	
	
}
