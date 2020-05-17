package university.scraper;

import lombok.AllArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import university.domain.MuralMessage;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
class DashboardWorker {
    
    private static final String MURAL_ELEMENT_ID = "mural-recados";
    
    // ID > li< > <h6> titulor
    // ID > li > <div class="inf_mural">
    
    public List<MuralMessage> messages(UserSession user) {
    
    
        try {
            List<MuralMessage> muralMessageList = new ArrayList<MuralMessage>();
        
            Connection connection = Jsoup.connect(UniversityUrls.STUDENT_DASHBOARD)
                    .cookies(user.getAuthCookies())
                    .method(Connection.Method.GET);
            Document document = connection.execute().parse();
        
            Element ul = document.getElementById(MURAL_ELEMENT_ID);
        
            Elements lis = ul.getElementsByTag("li");
        
            for (Element li : lis) {
            
                Element h6 = li.getElementsByTag("h6").get(0);
            
                Element div = li.getElementsByTag("div").get(0);
            
                muralMessageList.add(new MuralMessage(
                        h6.text(),
                        div.text()
                ));
            
            }
        
            return muralMessageList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

