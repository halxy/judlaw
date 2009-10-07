package util.persistencia;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;


/**
 * 
 * @author Halley Freitas
 *
 */
public class DBManager {
	
	/**
	 * Default constructor.
	 */
	public DBManager(){
		
	}

	/**
	 * Save the object in the database.
	 * @param object The object to be saved.
	 */	
	public void save(Object object) {
		HibernateUtil.beginTransaction();
		HibernateUtil.getSession().save(object);
		HibernateUtil.commitTransaction();
		HibernateUtil.closeSession();

	}

	/**
	 * Retrieve the persisted object.
	 * @param object The object to persist.
	 * @return The persisted object.
	 */
	public Object persistObject(Object object) {
		HibernateUtil.beginTransaction();
		HibernateUtil.getSession().save(object);
		Object obj = HibernateUtil.persistObject(object);
		HibernateUtil.commitTransaction();
		HibernateUtil.closeSession();
		return obj;
	}
	
	/**
	 * Remove the object from the database.
	 * @param object The object to be removed.
	 */	
	public void remove(Object object) {		
		HibernateUtil.beginTransaction();
		HibernateUtil.getSession().delete(object);
		HibernateUtil.commitTransaction();
		HibernateUtil.clearSession();
		HibernateUtil.closeSession();
	}
	
	/**
	 * Remove all the objects from the database.
	 * @param object The objects to be removed.
	 */	
	@SuppressWarnings("unchecked")
	public void removeAll(Object object) {
		List results;
		HibernateUtil.beginTransaction();
		results = HibernateUtil.getSession().createCriteria(	object.getClass()).list();
		
		for (Object result : results) {
			remove(result);
		}
		HibernateUtil.closeSession();
	}	
	
	/**
	 * Select all the objects instance of the parameter class.
	 * @param object The object to be selected.
	 */	
	@SuppressWarnings("unchecked")
	public List selectAll(Object object) {
		Criteria criteria = HibernateUtil.getSession().createCriteria(
				object.getClass());
		List list = criteria.list();
		HibernateUtil.closeSession();
		return list;
	}
	
	/**
	 * Select the object with the id parameter.
	 * @param object The object to be selected.
	 * @param id The object to be selected id.
	 */	
	public Object selectObjectById(Object object, Serializable id) {
		Object obj = HibernateUtil.getSession().get(object.getClass(), id);
		HibernateUtil.closeSession();
		return obj;
	}
	
	/**
	 * Updates the object in the database.
	 * @param object The object to be updated.
	 */	
	public void update(Object object) {
		HibernateUtil.beginTransaction();
		HibernateUtil.getSession().update(object);
		HibernateUtil.commitTransaction();
		HibernateUtil.closeSession();
	}
	
	/**
	 * Retrieve the last generated id.
	 * @return The last generated id.
	 */
	@SuppressWarnings("unchecked")
	public Object selectLastGeneratedId(){
		List results;
		Query sqlQuery = HibernateUtil.getSession().createSQLQuery("SELECT currval ('blmsmatchsequence')");
		results = sqlQuery.list();
		Object object = results.get(0);
		HibernateUtil.closeSession();
		return object;
	}
}
