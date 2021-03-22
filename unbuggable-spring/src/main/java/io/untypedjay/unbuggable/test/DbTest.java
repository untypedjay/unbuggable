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

  @SuppressWarnings("unused")
  private static void testSpringData() {
    try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext (
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
//  	printSeparator(60); printTitle("testJDBC", 60); printSeparator(60);
//    testJdbc();
//
//  	printSeparator(60); printTitle("testJpa", 60); printSeparator(60);
//    testJpa();

  	printSeparator(60); printTitle("testSpringData", 60); printSeparator(60);
    testSpringData();
  }
}
