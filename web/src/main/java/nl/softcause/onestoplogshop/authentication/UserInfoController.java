package nl.softcause.onestoplogshop.authentication;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import nl.softcause.onestoplogshop.api.DiscoverLogController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UserInfoController {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @GetMapping("/api/oidc-user")
    public UserData user(@AuthenticationPrincipal OidcUser oidcUser) {
        if(oidcUser==null){
            return null;
        }
        return new UserData(oidcUser.getClaim("name"));
    }

    public static class UserData {

        private final String name;

        public UserData(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

}
