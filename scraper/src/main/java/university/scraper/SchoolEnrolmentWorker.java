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
	private static final int TDS_PERIOD_INDEX = 1;
	private static final int TDS_COURSE_NAME_INDEX = 2;
	private static final int TDS_COURSE_CLASS_INDEX = 3;
	private static final int TDS_COURSE_CLASS_SEMESTER_INDEX = 4;
	
	private static final String REFERENCE_CODE_JSON_OBJECT_ATTRIBUTE_NAME = "codigo";
	
	public List<SchoolEnrolment> extractAllSchoolEnrollments(UserSession userSession) {
		
		try {
			Document document = new UniversityRequestWorker().withUrl(URL).withUser(userSession).doGet().parse();
			
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
		
		Elements rowList = table.getElementsByClass("tr");
		
		Iterator<Element> iterator = rowList.iterator();
		
		while (iterator.hasNext()) {
			Element row = iterator.next();
			SchoolEnrolment enrolment = extractFromRow(row);
		}
		
		return schoolEnrolmentList;
	}
	
	private SchoolEnrolment extractFromRow(Element row) {
		
		Elements tdList = row.getElementsByTag("td");
		
		Iterator<Element> iterator = tdList.iterator();
		
		String referenceCode = extractReferenceCodeFromTd(tdList.get(TDS_ENROLMENT_CODE_INDEX));
		
		return SchoolEnrolment.builder()
				.referenceCode(referenceCode)
				.build();
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
	
	public static void main(String[] args) {
		String html = new String("<a class=\"btn btn-boletim\" data-obj='{\"page\":\"boletim-graduacao-ac\",\"codigo\":\"MDE2TVRVNE9UYzBNRFk1TUE9PU5UUTROREU9\"}' rel=\"internal\"><span class=\"visualization\">Visualizar</span><img src=\"../res/img/aluno/search.png\" /></a>");
		
		Element tag = Jsoup.parse(html, "", Parser.xmlParser());
		
		
		String s = new SchoolEnrolmentWorker().extractReferenceCodeFromLink(tag.child(0));
	}
	
	
	
}
