package lingvo.movie.core.security.utils;

import lingvo.movie.core.dao.UserRepository;
import lingvo.movie.core.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by yaroslav on 03.05.15.
 */
@Controller
public class SignUpController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/signUp", method = POST)
    public void signUp(User user, HttpServletResponse response) {
        System.out.println(user);
        User savedUser = userRepository.save(user);
        if(savedUser!=null && savedUser.getId()!=null)
            response.setStatus(HttpServletResponse.SC_CREATED);
        else
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
