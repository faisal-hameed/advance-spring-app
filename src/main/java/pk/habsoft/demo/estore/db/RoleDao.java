package pk.habsoft.demo.estore.db;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pk.habsoft.demo.estore.db.entity.RoleEntity;

@Repository
public interface RoleDao extends CrudRepository<RoleEntity, Long> {

	List<RoleEntity> findAll();

	List<RoleEntity> findRolesByIsInternal(boolean isInternal);

	RoleEntity findByCode(String roleCode);

}