package university.scraper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
class LoginRequest {

    private final String ra;
    private final String password;
}
