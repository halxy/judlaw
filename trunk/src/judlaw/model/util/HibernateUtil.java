package judlaw.model.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * 
 * @author Halley Freitas
 *
 */
public class HibernateUtil {
	
	private static final SessionFactory factory;
	
	@SuppressWarnings("unchecked")
	private static final ThreadLocal sessionThread = new ThreadLocal();
	
	@SuppressWarnings("unchecked")
	private static final ThreadLocal transactionThread = new ThreadLocal();
	
	static {
		//static block that initializes the hibernate
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	/**
	 * Private HibernateUtil class constructor.
	 */
	private HibernateUtil(){
	}

	/**
	 * Get the session.
	 * @return Returns the session.
	 */
	@SuppressWarnings("unchecked")
	public static Session getSession() {
		Session session = (Session) sessionThread.get();
		if ( session  == null  || !session.isOpen()  ) {
			session = factory.openSession();
			sessionThread.set( session );
		}
		return (Session) sessionThread.get();
	}
	
	/**
	 * Close the session.
	 */
	@SuppressWarnings("unchecked")
	public static void closeSession() {
		Session session = (Session) sessionThread.get();
		if ( session != null && session.isOpen() ) {
			sessionThread.set(null);
			session.close();
		}
	}
	
	/**
	 * Retrieve the persisted object.
	 * @param object The object to persist.
	 * @return The persisted object.
	 */
	public static Object persistObject(Object object) {
		Session session = (Session) sessionThread.get();
		if ( session != null && session.isOpen() ) {
			return session.merge(object);
		}
		return null;
	}
	
	/**
	 * Clear the session.
	 */
	public static void clearSession() {
		Session session = (Session) sessionThread.get();
		if ( session != null && session.isOpen() ) {
			session.clear();
		}
	}
	
	/**
	 * Starts a session transaction.
	 */
	@SuppressWarnings("unchecked")
	public static void beginTransaction() {
		Transaction transaction = getSession().beginTransaction();
		transactionThread.set(transaction);
	}
	
	/**
	 * Commits the transaction.
	 */
	@SuppressWarnings("unchecked")
	public static void commitTransaction() {
		Transaction transaction = (Transaction) transactionThread.get();
		if ( transaction != null &&  !transaction.wasCommitted() && !transaction.wasRolledBack() ) {
			transaction.commit();
			transactionThread.set(null);
		}
	}
	
	/**
	 * Rollback the transaction to a previous state.
	 */
	@SuppressWarnings("unchecked")
	public static void rollbackTransaction() {
		Transaction transaction = (Transaction) transactionThread.get();
		if ( transaction != null &&  !transaction.wasCommitted() && !transaction.wasRolledBack() ) {
			transaction.rollback();
			transactionThread.set(null);
		}
	}
}
