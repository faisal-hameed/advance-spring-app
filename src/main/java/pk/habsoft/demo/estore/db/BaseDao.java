package pk.habsoft.demo.estore.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The Class AbstractDao.
 *
 * @param <T>
 *            the generic type
 */
public abstract class BaseDao<T> {

    /** The session factory. */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Gets the session.
     *
     * @return the session
     */
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Persist.
     *
     * @param entity
     *            the entity
     * @return the serializable
     */
    public Long save(T entity) {
        return (Long) getSession().save(entity);
    }

    /**
     * Delete.
     *
     * @param entity
     *            the entity
     */
    public boolean delete(T entity) {
        getSession().delete(entity);

        return true;
    }

    /**
     * Delete Entity.
     *
     * @param clazz
     *            the clazz
     * @param id
     *            the id
     * @return true, if successful
     */
    @SuppressWarnings("unchecked")
    public boolean delete(Class<T> clazz, long id) {

        final T obj = (T) getSession().get(clazz, id);
        if (obj != null) {
            getSession().delete(obj);
            return true;
        }

        return false;
    }

    /**
     * Gets the all records.
     *
     * @param clazz
     *            the clazz
     * @return the all records
     */
    @SuppressWarnings("unchecked")
    public List<T> getAllRecords(Class<T> clazz) {

        return getSession().createCriteria(clazz).list();

    }

    /**
     * Gets the all records.
     *
     * @param clazz
     *            the clazz
     * @param columnToSort
     *            the column to sort
     * @return the all records
     */
    @SuppressWarnings("unchecked")
    public List<T> getAllRecordsWithDescOrder(Class<T> clazz, String columnToSort) {

        return getSession().createCriteria(clazz).addOrder(Order.desc(columnToSort)).list();

    }

    /**
     * Gets the object by id.
     *
     * @param clazz
     *            the clazz
     * @param id
     *            the id
     * @return the object by id
     */
    @SuppressWarnings("unchecked")
    protected T getObjectById(Class<T> clazz, long id) {

        return (T) getSession().get(clazz, id);
    }

    protected T getObjectByAttribute(Class clazz, String attribute, String attributeValue, boolean ignoreCase) {
        Criteria cr = getSession().createCriteria(clazz);
        if (ignoreCase) {
            cr.add(Restrictions.eq(attribute, attributeValue.toLowerCase()).ignoreCase());
        } else {
            cr.add(Restrictions.eq(attribute, attributeValue));
        }

        return (T) cr.uniqueResult();
    }

    /**
     * Save or update.
     *
     * @param entities
     *            the entities
     * @return true, if successful
     */
    public boolean saveOrUpdate(List<T> entities) {

        for (final T entity : entities) {
            getSession().saveOrUpdate(entity);
        }

        return true;

    }

    /**
     * Update.
     *
     * @param entity
     *            the entity
     * @return the t
     */
    public T update(T entity) {

        getSession().update(entity);

        return entity;

    }

    /**
     * Save or update.
     *
     * @param entity
     *            the entity
     * @return the object
     */
    public T saveOrUpdate(T entity) {

        getSession().saveOrUpdate(entity);

        return entity;

    }

}