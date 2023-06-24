package cl.sebastian.indicator.data.manager;

import cl.sebastian.indicator.data.model.User;
import cl.sebastian.indicator.data.repository.UserRepository;
import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author seba
 */
@Service
public class UserManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private transient UserRepository userRepository;

    @Transactional
    public User getUser(final String username, final String password) {
        User user = null;
        if (!StringUtils.isAnyBlank(username, password)) {
            user = userRepository.findByUsernameIgnoreCaseAndPassword(username, password);
        }
        return user;
    }
}
