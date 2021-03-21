package io.untypedjay.unbuggable.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import io.untypedjay.unbuggable.dao.EmployeeDao;
import io.untypedjay.unbuggable.dao.EmployeeRepository;
import io.untypedjay.unbuggable.domain.Employee;
import io.untypedjay.unbuggable.util.DbScriptRunner;
import io.untypedjay.unbuggable.util.JpaUtil;

import static io.untypedjay.unbuggable.util.PrintUtil.printSeparator;
import static io.untypedjay.unbuggable.util.PrintUtil.printTitle;

public class DbTest {

  private static void createSchema(DataSource ds, String ddlScript) {
    try {
      DbScriptRunner scriptRunner = new DbScriptRunner(ds.getConnection());
      InputStream is = DbTest.class.getClassLoader().getResourceAsStream(ddlScript);
      if (is == null)
        throw new IllegalArgumentException(String.format("File %s not found in classpath.", ddlScript));
      scriptRunner.runScript(new InputStreamReader(is));
    }
    catch (SQLException | IOException e) {
      e.printStackTrace();
      return;
    }
  }

  private static void testJdbc() {

		try (AbstractApplicationContext factory =
			new ClassPathXmlApplicationContext(
        "io/untypedjay/unbuggable/test/applicationContext-jdbc.xml")) {

      printTitle("create schema", 60, '-');
			createSchema(factory.getBean("dataSource", DataSource.class),
        "io/untypedjay/unbuggable/test/CreateWorklogDbSchema.sql");

			//
			// get reference to implementation of EmployeeDao
			//

      EmployeeDao emplDao = factory.getBean("employeeDaoJdbc", EmployeeDao.class);

      printTitle("insert employee", 60, '-');
      Employee empl1 = new Employee("Josefine", "Feichtlbauer", LocalDate.of(1970, 10, 26));
      emplDao.insert(empl1);
      System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));

      Employee empl2 = new Employee("Franz", "Heinzelmayr", LocalDate.of(1975, 9, 20));
      emplDao.insert(empl2);
      System.out.println("empl2 = " + (empl2 == null ? (null) : empl2.toString()));

      printTitle("update employee", 60, '-');
      empl1.setFirstName("Jaquira");
      empl1 = emplDao.merge(empl1);
      System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));

      printTitle("find employee", 60, '-');
    	Employee empl = emplDao.findById(1L);
    	System.out.println("empl=" + (empl == null ? (null) : empl.toString()));
    	empl = emplDao.findById(100L);
    	System.out.println("empl=" + (empl == null ? (null) : empl.toString()));

      printTitle("find all employees", 60, '-');
      emplDao.findAll().forEach(System.out::println);
		}
  }

  @SuppressWarnings("unused")
  private static void testJpa() {
    try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
      "io/untypedjay/unbuggable/test/applicationContext-jpa1.xml")) {
      EntityManagerFactory emf = factory.getBean(EntityManagerFactory.class);
      EmployeeDao emplDao = factory.getBean("employeeDaoJpa", EmployeeDao.class);

      //JpaUtil.beginTransaction(emf);



      JpaUtil.executeInTransaction(emf, () -> {
        Employee empl1 = new Employee("Josefine", "Feichtlbauer", LocalDate.of(1970, 10, 26));
        printTitle("insert employee", 60, '-');
        emplDao.insert(empl1);
        System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));

        Employee empl2 = new Employee("Franz", "Heinzelmayr", LocalDate.of(1975, 9, 20));
        emplDao.insert(empl2);
        System.out.println("empl2 = " + (empl2 == null ? (null) : empl2.toString()));

        printTitle("update employee", 60, '-');
        empl1.setFirstName("Jaquira");
        empl1 = emplDao.merge(empl1);
        System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));
      });

      //JpaUtil.commitTransaction(emf);

      JpaUtil.executeInTransaction(emf, () -> {
        printTitle("find employee", 60, '-');
        Employee empl = emplDao.findById(1L);
        System.out.println("empl=" + (empl == null ? (null) : empl.toString()));
        empl = emplDao.findById(100L);
        System.out.println("empl=" + (empl == null ? (null) : empl.toString()));

        printTitle("find all employees", 60, '-');
        emplDao.findAll().forEach(System.out::println);
      });
    }
  }

  @SuppressWarnings("unused")
  private static void testSpringData() {
    try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
      "io/untypedjay/unbuggable/test/applicationContext-jpa1.xml")) {
      EntityManagerFactory emf = factory.getBean(EntityManagerFactory.class);
      JpaUtil.executeInTransaction(emf, () -> {
        EmployeeRepository emplRepo = JpaUtil.getJpaRepository(emf, EmployeeRepository.class);

        printTitle("insert/update", 60, '-');
        Employee empl1 = new Employee("Josefine", "Feichtlbauer", LocalDate.of(1970, 10, 26));
        Employee empl2 = new Employee("Franz", "Heinzelmayr", LocalDate.of(1975, 9, 20));
        empl1 = emplRepo.save(empl1);
        empl2 = emplRepo.save(empl2);
        emplRepo.flush();

        empl2.setFirstName("Hugo");
        empl2 = emplRepo.save(empl2);
      });

      JpaUtil.executeInTransaction(emf, () -> {
        EmployeeRepository emplRepo = JpaUtil.getJpaRepository(emf, EmployeeRepository.class);

        printTitle("find employee  by id", 60, '-');
        Optional<Employee> empl1 = emplRepo.findById(1L);
        System.out.println("empl1=" + (empl1.isEmpty() ? (null) : empl1.get().toString()));

        printTitle("find all employees", 60, '-');
        emplRepo.findAll().forEach(System.out::println);

        printTitle("find by last name", 60, '-');
        Optional<Employee> empl3 = emplRepo.findByLastName("Heinzelmayr");
        System.out.println("empl3=" + (empl3.isEmpty() ? (null) : empl3.get().toString()));

        printTitle("find older than", 60, '-');
        emplRepo.findOlderThan(LocalDate.of(1975, 1, 1)).forEach(System.out::println);
      });
		}
  }

  public static void main(String[] args) {
  	printSeparator(60); printTitle("testJDBC", 60); printSeparator(60);
    testJdbc();

  	printSeparator(60); printTitle("testJpa", 60); printSeparator(60);
    testJpa();

  	printSeparator(60); printTitle("testSpringData", 60); printSeparator(60);
    testSpringData();
  }
}
