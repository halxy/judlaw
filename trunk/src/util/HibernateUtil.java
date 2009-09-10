package util;

import java.io.Serializable;


import jud.Acordao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

public class HibernateUtil {

	private static final SessionFactory sessionFactory;
	
	private static final SchemaExport schemaExport;
	
	private static final SchemaUpdate schemaUpdate;

	static {
		try {
			AnnotationConfiguration cfg = new AnnotationConfiguration();
			sessionFactory = cfg.configure().buildSessionFactory();
			// soh deve criar os exportadores/atualizadores de esquema
			// após chamar cfg.configure(), senão nao cria os mapeamentos, etc!
			schemaExport = new SchemaExport(cfg);
			schemaUpdate = new SchemaUpdate(cfg);
			
			schemaUpdate.execute(false, true);
		} catch (Throwable ex) {
			// Log exception!
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	// usado somente para testes.
	public static void cleanAll() {
		schemaExport.drop(false, true);
		schemaExport.create(false, true);
	}

	public static Session getSession() throws HibernateException {
		return sessionFactory.getCurrentSession();
	}
	
	public static Serializable save(Object obj) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			Serializable idReturned = session.save(obj);
			tx.commit();
			return idReturned;
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			throw e;
		}
	}
	
	public static void update(Object obj) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.update(obj);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Object load(Class clazz, Serializable id) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			Object gotten = session.get(clazz, id);
			return gotten;
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			throw e;
		}
	}
	
	public static void delete(Object obj) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.delete(obj);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			throw e;
		}
	}
	
	public static void main(String[] args) {
		cleanAll();
	}
}
