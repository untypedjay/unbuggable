package io.untypedjay.unbuggable.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class JpaUtil {
  
  private static final Logger logger = LoggerFactory.getLogger(JpaUtil.class);

  public static void beginTransaction(EntityManagerFactory entityManagerFactory) {
    EntityManager em = getEntityManager(entityManagerFactory);
    if (em == null)
      em = openEntityManager(entityManagerFactory);
    if (!em.getTransaction().isActive()) {
      em.getTransaction().begin();
      logger.trace("Begin Transaction");
    }
  }

  public static void commitTransaction(EntityManagerFactory entityManagerFactory) {
    EntityManager em = getEntityManager(entityManagerFactory);
    if (em == null)
      throw new DataAccessResourceFailureException("Invalid commit: No open transaction");
    if (em.getTransaction().isActive()) {
      em.getTransaction().commit();
      logger.trace("Committed Transaction");
    }
    closeEntityManager(entityManagerFactory);
  }

  public static void rollbackTransaction(EntityManagerFactory entityManagerFactory) {
    EntityManager em = getEntityManager(entityManagerFactory);
    if (em == null)
      throw new DataAccessResourceFailureException("Invalid rollback: No open transaction");
    if (em.getTransaction().isActive()) {
      em.getTransaction().rollback();
      logger.trace("Rolled back Transaction");
    }
    closeEntityManager(entityManagerFactory);
  }

  public static void executeInOpenEntityManager(EntityManagerFactory entityManagerFactory,
      ActionCallback callback) {

    boolean useOpenEntityManager = false;
    EntityManager em = getEntityManager(entityManagerFactory);
    if (em == null)
      em = openEntityManager(entityManagerFactory);
    else
      useOpenEntityManager = true;

    try {
      callback.execute();
    }
    finally {
      if (!useOpenEntityManager) closeEntityManager(entityManagerFactory);
    }
  }

  public static void executeInTransaction(EntityManagerFactory entityManagerFactory,
      ActionCallback callback) {

    boolean useOpenEntityManager = false;
    
    EntityManager em = getEntityManager(entityManagerFactory);
    if (em == null)
      em = openEntityManager(entityManagerFactory);
    else
      useOpenEntityManager = true;

    boolean useActiveTx = false;
    if (!em.getTransaction().isActive()) {
      em.getTransaction().begin();
      logger.trace("Begin Transaction");
    }
    else
      useActiveTx = true;

    try {
      callback.execute();
    }
    finally {
      if (!useActiveTx) {
        em.getTransaction().commit();
        logger.trace("Committed Transaction");
      }
      if (!useOpenEntityManager) closeEntityManager(entityManagerFactory);
    }
  }

  public static EntityManager openEntityManager(EntityManagerFactory entityManagerFactory) {
    try {
      EntityManager em = getEntityManager(entityManagerFactory);
      if (em == null) {
        logger.trace("Opening JPA EntityManager");
        em = entityManagerFactory.createEntityManager();
      }

      TransactionSynchronizationManager.bindResource(entityManagerFactory,
          new EntityManagerHolder(em));
      return em;
    }
    catch (IllegalStateException ex) {
      throw new DataAccessResourceFailureException("Could not open JPA EntityManager", ex);
    }
  }

  public static void closeEntityManager(EntityManagerFactory entityManagerFactory) {
    try {
      EntityManager em = getEntityManager(entityManagerFactory);
      if (em != null) {
        TransactionSynchronizationManager.unbindResource(entityManagerFactory);
        em.close();
        logger.trace("Closed JPA EntityManager");
      }
    }
    catch (IllegalStateException ex) {
      throw new DataAccessResourceFailureException("Could not close JPA EntityManager", ex);
    }
  }
  
  public static EntityManager getEntityManager(EntityManagerFactory entityManagerFactory) {
    if (entityManagerFactory == null) return null;
    return EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory);
  }

  public static <T> T getJpaRepository(EntityManagerFactory entityManagerFactory, 
  		                                 Class<T> entityType) {
    EntityManager em = getEntityManager(entityManagerFactory);
    JpaRepositoryFactory repoFactory = new JpaRepositoryFactory(em);
    return repoFactory.getRepository(entityType);
  }

}
