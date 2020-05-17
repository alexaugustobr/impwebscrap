package university.scraper;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
class UserSession {

    private final String ra;
    private final String senha;
    private final Map<String, String> authCookies;
    private final String nome;
    private final String cursoNome;
    
}
