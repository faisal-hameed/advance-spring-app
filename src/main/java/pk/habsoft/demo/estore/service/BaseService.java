package pk.habsoft.demo.estore.service;

import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import pk.habsoft.demo.estore.db.entity.UserEntity;
import pk.habsoft.demo.estore.exceptions.ECoreException;
import pk.habsoft.demo.estore.security.AuthenticationWithToken;

public class BaseService {

    private static final Logger LOGGER = LogManager.getLogger(BaseService.class);

    /*
     * @Autowired private AuditLogDao auditDao;
     */

    public Optional<UserEntity> getLoggedinUser() {
        AuthenticationWithToken authentication = (AuthenticationWithToken) SecurityContextHolder.getContext()
                .getAuthentication();

        UserEntity userDto = null;
        if (authentication != null) {
            LOGGER.info("Get loggedin user details : " + authentication.getJwtUser());
            userDto = (UserEntity) authentication.getJwtUser();
        }

        return Optional.ofNullable(userDto);

    }

    protected String recordNotFound(String object, long id) {
        return String.format("%s not found with id : %d", object, id);
    }

    protected String recordUpdatedSuccessfully(String object) {
        return String.format("%s updated successfully.", object);
    }

    protected String recordDeletedSuccessfully(String object) {
        return String.format("%s deleted successfully.", object);
    }

    public long getLoggedinUserId() {
        Optional<UserEntity> currentUser = getLoggedinUser();

        return currentUser.map(UserEntity::getId)
                .orElseThrow(() -> new ECoreException("Current user details not found"));
    }

    public String getLoggedinUserName() {
        Optional<UserEntity> currentUser = getLoggedinUser();

        return currentUser.map(UserEntity::getName).orElse("Unknown");
    }

    // public boolean isAdmin() {
    // Optional<UserEntity> currentUser = getLoggedinUser();
    //
    // return isAdmin(currentUser);
    // }
    //
    // public boolean isAdmin(Optional<UserEntity> user) {
    // if (!user.isPresent())
    // return false;
    // return user.get().hasRole(RoleType.ADMIN);
    // }
    //
    // public boolean isSupAdmin(Optional<UserEntity> user) {
    // if (!user.isPresent())
    // return false;
    // return user.get().hasRole(RoleType.SUPER_ADMIN);
    // }

    protected void logMessage(String message) {
        /*
         * AuditLogEntity log = new AuditLogEntity(0, getLoggedinUserName(),
         * DateUtil.getCurrentDate(), message); auditDao.save(log);
         */
    }

}
