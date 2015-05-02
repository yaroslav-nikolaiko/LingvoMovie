package lingvo.movie.core.security.utils;

import javax.validation.constraints.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.security.access.AccessDecisionVoter.ACCESS_DENIED;
import static org.springframework.security.access.AccessDecisionVoter.ACCESS_GRANTED;

/**
 * Created by yaroslav on 02.05.15.
 */
public class Voters {
    static final String USERS = "users";

    public static int voteByUserID(@NotNull UserPrincipalWithId user, @NotNull String url) {
        Pattern pattern = Pattern.compile(".*/"+USERS+"/(\\d).*");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            Long requestId = Long.valueOf(matcher.group(1));
            return requestId.equals(user.getId()) ? ACCESS_GRANTED : ACCESS_DENIED;
        }
        else return 0;
    }
}
