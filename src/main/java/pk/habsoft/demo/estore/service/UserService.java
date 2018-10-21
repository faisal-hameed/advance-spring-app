package pk.habsoft.demo.estore.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fz.epms.controller.form.ChangePasswordForm;
import com.fz.epms.controller.form.CreateUserForm;

import lombok.Getter;
import pk.habsoft.demo.estore.db.UserDao;
import pk.habsoft.demo.estore.db.entity.RoleEntity;
import pk.habsoft.demo.estore.db.entity.UserEntity;
import pk.habsoft.demo.estore.exceptions.ECoreException;

@Service
@Transactional
public class UserService extends BaseService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    @Autowired
    @Getter
    PasswordEncoder passEncoder;
    // @Autowired
    // EmailServer mailServer;
    @Autowired(required = true)
    UserDao userDao;
    @Autowired
    ModelMapper mapper;

    public Optional<UserEntity> getUser(String email, String rawPassword) {
        UserEntity usr = userDao.authenticate(email, passEncoder.encode(rawPassword));
        return Optional.ofNullable(usr);
    }

    public UserEntity getUserById(long id) {
        UserEntity usr = userDao.getUser(id);
        if (usr == null) {
            throw new ECoreException(String.format("User not found with id : %d", id));
        }
        return usr;
    }

    /**
     * This method will be called against invitation
     * 
     * @param form
     * @return
     */
    public UserEntity createUser(CreateUserForm form) {

        UserEntity oldUser = findByEmail(form.getEmail());
        if (oldUser != null) {
            // User exists with email address.
            throw new ECoreException("User already exists with same email address.");

        } else {

            UserEntity user = new UserEntity();
            user.setEmail(form.getEmail());
            user.setPassword(passEncoder.encode(form.getPassword()));
            user.setName(form.getName());
            user.setRole(mapper.map(form.getRoleType(), RoleEntity.class));

            Long id = userDao.save(user);
            user.setId(id);

            // AccountActivatedTemplate accountActivatedEmail = new
            // AccountActivatedTemplate(user.getEmail(),
            // user.getName());
            // mailServer.sendEmail(accountActivatedEmail, false);

            return user;
        }

    }

    public boolean deleteUser(int id) {

        UserEntity oldUser = userDao.getUser(id);
        if (oldUser == null) {
            throw new ECoreException(String.format("User not exists for deletion with id = %d", id));
        }
        userDao.deleteUser(id);

        return true;
    }

    public String changePassword(ChangePasswordForm req) {
        UserEntity usr = getUser(getLoggedinUserId());

        if (!passEncoder.matches(req.getOldPassword(), usr.getPassword())) {
            throw new ECoreException("Old password does not match.");
        }
        usr.setPassword(passEncoder.encode(req.getNewPassword()));

        userDao.updateUser(usr);

        return "Password changed successfully.";
    }

    public UserEntity findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public UserEntity getUser(long id) {
        UserEntity usr = userDao.getUser(id);
        if (usr == null) {
            throw new ECoreException(String.format("User not found with id : %d", id));
        }
        return usr;
    }

}