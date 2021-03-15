package io.untypedjay.jpa;

import io.untypedjay.domain.*;
import io.untypedjay.util.JpaUtil;
import org.apache.log4j.BasicConfigurator;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class WorkLogManager {
  private static void insertEmployee1(Employee employee) {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("UnbuggablePU");
    EntityManager em = factory.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    em.persist(employee); // insert

    tx.commit();
    em.close();
    factory.close();
  }

  private static <T> void insertEntity(T entity) {
    try {
      EntityManager em = JpaUtil.getTransactedEntityManager();
      em.persist(entity); // insert
      JpaUtil.commit();
    } catch (Exception e) {
      JpaUtil.rollback();
      throw e;
    }
  }

  private static Employee addLogbookEntries(Employee empl, LogbookEntry... entries) {
    try {
      EntityManager em = JpaUtil.getTransactedEntityManager();
      for (var entry : entries) {
        entry = em.merge(entry); // check if there is already a persisted element
        empl.addLogbookEntry(entry);
      }

      empl = em.merge(empl);

      JpaUtil.commit(); // closes entity manager as well
    } catch (Exception e) {
      JpaUtil.rollback();
      throw e;
    }

    return empl;
  }

  private static <T> T saveEntity(T entity) {
    try {
      EntityManager em = JpaUtil.getTransactedEntityManager();
      entity = em.merge(entity);
      JpaUtil.commit();
    } catch (Exception e) {
      JpaUtil.rollback();
      throw e;
    }

    return entity;
  }

  private static void listEmployees() {
    try {
      EntityManager em = JpaUtil.getTransactedEntityManager();

      var emplList = em.createQuery("select e from Employee e", Employee.class).getResultList();

      emplList.forEach(empl -> {
        System.out.println(empl);

        if (empl.getLogbookEntries().size() > 0) {
          System.out.println("  Logbook Entries:");
          empl.getLogbookEntries().forEach(entry -> {
            System.out.printf("       %s (%s - %s)\n", entry.getActivity(), entry.getStartTime(), entry.getEndTime());
          });
        }
      });

      JpaUtil.commit();
    } catch (Exception e) {
      JpaUtil.rollback();
      throw e;
    }
  }

  private static void testFetchingStrategies() {

    Long entryId = null;
    Long emplId = null;

    // preparation work
    try {
      EntityManager em = JpaUtil.getTransactedEntityManager();

      Optional<LogbookEntry> entry =
        em.createQuery("select le from LogbookEntry le", LogbookEntry.class)
          .setMaxResults(1)
          .getResultList().stream().findAny();
      if (entry.isEmpty()) return;
      entryId = entry.get().getId();

      Optional<Employee> employee =
        em.createQuery("select em from Employee em", Employee.class)
          .setMaxResults(1)
          .getResultList().stream().findAny();
      if (employee.isEmpty()) return;
      emplId = employee.get().getId();

      JpaUtil.commit();
    } catch (Exception e) {
      JpaUtil.rollback();
      throw e;
    }

    System.out.println("#######################################");

    try {
      EntityManager em = JpaUtil.getTransactedEntityManager();

      System.out.println("###> Fetching LogbookEntry...");
      LogbookEntry entry = em.find(LogbookEntry.class, entryId);
      System.out.println("###> Fetched LogbookEntry");

      Employee empl1 = entry.getEmployee();
      System.out.println("###> Fetched associated Employee");

      System.out.println(empl1); // call .toString() and load attributes
      System.out.println("###> Accessed associated Employee");

      JpaUtil.commit();
    } catch (Exception e) {
      JpaUtil.rollback();
      throw e;
    }

    System.out.println("#######################################");

    try {
      EntityManager em = JpaUtil.getTransactedEntityManager();

      System.out.println("###> Fetching Employee...");
      Employee empl2 = em.find(Employee.class, emplId);
      System.out.println("###> Fetched Employee");

      var entries = empl2.getLogbookEntries();
      System.out.println("###> Fetched associated LogbookEntries");

      for (var entry : entries) {
        System.out.println(entry);
      }
      System.out.println("###> Accessed associated LogbookEntries");

      JpaUtil.commit();
    } catch (Exception e) {
      JpaUtil.rollback();
      throw e;
    }

    System.out.println("#######################################");
  }

  private static void listEntriesOfEmployee(Employee employee) {
    try {
      EntityManager em = JpaUtil.getTransactedEntityManager();

      TypedQuery<LogbookEntry> entriesQry =
        em.createQuery("select le from LogbookEntry le where le.employee = :empl",
        LogbookEntry.class);
      entriesQry.setParameter("empl", employee);

      entriesQry.getResultList().forEach(System.out::println);

      JpaUtil.commit();
    } catch (Exception e) {
      JpaUtil.rollback();
      throw e;
    }
  }

  private static void listEntriesOfEmployeeCQ(Employee employee) {
    try {
      EntityManager em = JpaUtil.getTransactedEntityManager();

      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<LogbookEntry> entriesCQ = cb.createQuery(LogbookEntry.class);
      ParameterExpression<Employee> p = cb.parameter(Employee.class);
      Root<LogbookEntry> entry = entriesCQ.from(LogbookEntry.class);

      entriesCQ.where(cb.equal(entry.get(LogbookEntry_.EMPLOYEE), p)).select(entry);

      TypedQuery<LogbookEntry> entriesQry = em.createQuery(entriesCQ);
      entriesQry.setParameter(p, employee);
      entriesQry.getResultList().forEach(System.out::println);

      JpaUtil.commit();
    } catch (Exception e) {
      JpaUtil.rollback();
      throw e;
    }
  }

  private static void loadEmployeesWithEntries() {
    try {
      EntityManager em = JpaUtil.getTransactedEntityManager();

      TypedQuery<Employee> query = em.createQuery(
        "select e from Employee e join fetch e.logbookEntries", Employee.class);

      query.getResultList().forEach(System.out::println);

      JpaUtil.commit();
    } catch (Exception e) {
      JpaUtil.rollback();
      throw e;
    }
  }

  public static void main(String[] args) {
    PermanentEmployee pe1 = new PermanentEmployee("Jeremy", "Miller", LocalDate.of(2000, 12, 2));
    pe1.setSalary(3000);
    pe1.setAddress(new Address("4020", "Linz", "Wiener Straße 1"));
    Employee empl1 = pe1;

    TemporaryEmployee te1 = new TemporaryEmployee("Cale", "Bee", LocalDate.of(1985, 2, 20));
    te1.setAddress(new Address("4232", "Hagenberg", "Softwarepark 1"));
    te1.setHourlyRate(100.0);
    te1.setRenter("ÖSV");
    te1.setStartDate(LocalDate.of(2020, 1, 1));
    te1.setEndDate(LocalDate.of(2020, 1, 1));
    Employee empl2 = te1;

    LogbookEntry entry1 = new LogbookEntry("Implementieren",
      LocalDateTime.of(2021, 03, 02, 10, 0),
      LocalDateTime.of(2021, 03, 02, 14, 0));

    LogbookEntry entry2 = new LogbookEntry("Testen",
      LocalDateTime.of(2021, 03, 02, 14, 0),
      LocalDateTime.of(2021, 03, 02, 15, 0));

    LogbookEntry entry3 = new LogbookEntry("Dokumentieren",
      LocalDateTime.of(2021, 03, 02, 15, 0),
      LocalDateTime.of(2021, 03, 02, 15, 30));

    System.out.println("===== creating DB schema =====");
    JpaUtil.getEntityManager();

    try {
      System.out.println("===== inserting entities =====");
      insertEntity(empl1);
      insertEntity(empl2);

      empl2.setLastName("Restructure");
      empl2 = saveEntity(empl2);

      System.out.println("===== listing employees =====");
      listEmployees();

      System.out.println("===== inserting logbook entries =====");
      addLogbookEntries(empl1, entry1, entry2);
      addLogbookEntries(empl2, entry3);

      System.out.println("===== listing employees =====");
      listEmployees();

      System.out.println("===== test fetching strategies =====");
      testFetchingStrategies();

      System.out.println("===== listing entries of employee =====");
      listEntriesOfEmployee(empl1);

      System.out.println("===== listing entries of employee (criteria query) =====");
      listEntriesOfEmployeeCQ(empl1);

      System.out.println("===== loading employees with entries =====");
      loadEmployeesWithEntries();

    } finally {
      System.out.println("===== closing EntityManagerFactory =====");
      JpaUtil.closeEntityManagerFactory();
    }
  }
}
