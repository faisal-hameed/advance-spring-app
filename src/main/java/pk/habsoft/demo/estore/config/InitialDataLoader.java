package pk.habsoft.demo.estore.config;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import pk.habsoft.demo.estore.db.RoleDao;
import pk.habsoft.demo.estore.db.UserDao;
import pk.habsoft.demo.estore.db.entity.RoleEntity;
import pk.habsoft.demo.estore.db.entity.UserEntity;
import pk.habsoft.demo.estore.enums.RoleType;

@Component
@Transactional
public class InitialDataLoader implements ApplicationRunner {

	private static final Logger LOGGER = Logger.getLogger(InitialDataLoader.class);

	private RoleEntity roleAdmin = new RoleEntity(0L, RoleType.ADMIN.getCode(), RoleType.ADMIN.getLabel(),
			RoleType.ADMIN.isInternal(), null);
	// private UserRoleEntity userRoleAdmin = new UserRoleEntity(0L, null, null,
	// null, new Date(), null, 10, 10, 1, 1,
	// true, 1);
	// private UserRoleEntity userRoleSuperAdmin = new UserRoleEntity(0L, null,
	// null, null, new Date(), null, 10, 10, 1, 1,
	// true, 1);

	// @Autowired
	// PasswordEncoder passEncoder;
	@Autowired
	private ModelMapper mapper;

	@Autowired
	UserDao userDao;
	@Autowired
	RoleDao roleDao;
	// @Autowired
	// UserRoleDao userRoleDao;

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		LOGGER.info("Initializing DB with startup data.");

		initDefaultUserRoles();

		initDefaultUsers();

		LOGGER.info("DB initialization completed....");

		System.err.println(userDao.getUser(1));

		// System.out.println("Team members : " +
		// teamMemberService.getTeamMembers(1).size());
		//
		// System.out.println(teamMemberService.getTeamMembers(1));
	}

	private void initDefaultUsers() {
		LOGGER.info("Initializing default users...");
		UserEntity tmpUser;
		Long id;
		// if (userDao.findByEmail("admin@gmail.com") == null) {
		// tmpUser = new UserEntity(0L, "admin@gmail.com",
		// passEncoder.encode("admin123"), "Admin User", "UTC",
		// UserStatus.ACTIVE, null);
		// id = userDao.save(tmpUser);
		// tmpUser.setId(id);
		// // Create User role
		// userRoleEmp.setUser(userDao.getUser(id));
		// userRoleEmp.setTeam(teamDao.findOne(1L));
		// userRoleEmp.setRole(roleDao.findByCode(RoleType.EMPLOYEE.getCode()));
		// userRoleAdmin.setUser(userDao.getUser(id));
		// userRoleAdmin.setTeam(teamDao.findOne(1L));
		// userRoleAdmin.setRole(roleDao.findByCode(RoleType.ADMIN.getCode()));
		// userRoleDao.save(userRoleEmp);
		// userRoleDao.save(userRoleAdmin);
		// }

	}

	private void initDefaultUserRoles() {
		LOGGER.info("Initializing default user roles...");
		// Roles to load
		List<RoleEntity> roles = new ArrayList<>();
		for (RoleType roleType : RoleType.values()) {
			roles.add(mapper.map(roleType, RoleEntity.class));
		}

		// Create Role if not exists
		for (RoleEntity roleEntity : roles) {
			if (roleDao.findByCode(roleEntity.getCode()) == null) {
				LOGGER.info(roleEntity.getLabel() + " : created : " + roleDao.save(roleEntity));
			} else {
				// If ID already exists, update role. Maybe code/label is
				// updated
				LOGGER.info("Updated : " + roleDao.save(roleEntity));
			}

		}
	}

}
