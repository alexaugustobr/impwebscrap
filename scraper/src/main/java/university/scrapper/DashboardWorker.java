package university.scrapper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DashboardWorker {
    
    private static final String MURAL_ELEMENT_ID = "mural-recados";
    
    // ID > li< > <h6> titulor
    // ID > li > <div class="inf_mural">
    
    public List<Recado> recados(AuthenticatedUser user) {
    
    
        try {
            List<Recado> recadoList = new ArrayList<Recado>();
        
            Connection connection = Jsoup.connect(UniversityUrl.STUDENT_DASHBOARD)
                    .cookies(user.getAuthCookies())
                    .method(Connection.Method.GET);
            Document document = connection.execute().parse();
        
            Element ul = document.getElementById(MURAL_ELEMENT_ID);
        
            Elements lis = ul.getElementsByTag("li");
        
            for (Element li : lis) {
            
                Element h6 = li.getElementsByTag("h6").get(0);
            
                Element div = li.getElementsByTag("div").get(0);
            
                recadoList.add(new Recado(
                        h6.text(),
                        div.text()
                ));
            
            }
        
            return recadoList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

