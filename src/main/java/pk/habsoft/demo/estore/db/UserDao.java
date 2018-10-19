package pk.habsoft.demo.estore.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import pk.habsoft.demo.estore.db.entity.UserEntity;

@Repository
public class UserDao extends BaseDao<UserEntity> {

	public UserEntity findByEmail(String email) {
		Criteria criteria = getSession().createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("email", email));
		return (UserEntity) criteria.uniqueResult();
	}

	public UserEntity authenticate(String email, String passwordHash) {
		Criteria criteria = getSession().createCriteria(UserEntity.class);
		Criterion emailCR = Restrictions.eq("email", email);
		Criterion passwordCR = Restrictions.eq("password", passwordHash);
		criteria.add(Restrictions.and(emailCR, passwordCR));
		return (UserEntity) criteria.uniqueResult();
	}

	public UserEntity updateUser(UserEntity user) {
		getSession().update(user);

		return user;
	}

	@SuppressWarnings("unchecked")
	public List<UserEntity> getAllUsers() {
		Criteria criteria = getSession().createCriteria(UserEntity.class);
		return (List<UserEntity>) criteria.list();
	}

	public UserEntity getUser(long id) {
		return getObjectById(UserEntity.class, id);
	}

	public Long createUser(UserEntity user) {
		return save(user);
	}

	public boolean deleteUser(long id) {
		return delete(UserEntity.class, id);
	}

}