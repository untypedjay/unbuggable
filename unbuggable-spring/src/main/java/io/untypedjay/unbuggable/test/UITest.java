package io.untypedjay.unbuggable.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import io.untypedjay.unbuggable.domain.Employee;
import io.untypedjay.unbuggable.domain.LogbookEntry;
import io.untypedjay.unbuggable.ui.UIProcessFacade;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static io.untypedjay.unbuggable.util.PrintUtil.printSeparator;
import static io.untypedjay.unbuggable.util.PrintUtil.printTitle;

@SuppressWarnings("Duplicates")
public class UITest {

  private static void uiTest() {

    // create domain objects

    Employee empl1 = new Employee("Sepp", "Forcher", LocalDate.of(1935, 12, 12));
    Employee empl2 = new Employee("Alfred", "Kunz", LocalDate.of(1944, 8, 10));
    Employee empl3 = new Employee("Sigfried", "Hinz", LocalDate.of(1954, 5, 3));

    LogbookEntry entry1 = new LogbookEntry("Analyse", 
        LocalDateTime.of(2018, 3, 1, 10, 0), LocalDateTime.of(2018, 3, 1, 11, 30));
    LogbookEntry entry2 = new LogbookEntry("Implementierung", 
        LocalDateTime.of(2018, 3, 1, 11, 30), LocalDateTime.of(2018, 3, 1, 16, 30));
    LogbookEntry entry3 = new LogbookEntry("Testen", 
        LocalDateTime.of(2018, 3, 1, 10, 15), LocalDateTime.of(2018, 3, 1, 14, 30));

    try (AbstractApplicationContext factory =
           new ClassPathXmlApplicationContext("io/untypedjay/unbuggable/test/applicationContext-jpa1.xml")) {

      UIProcessFacade ui = factory.getBean("uiComponent", UIProcessFacade.class);

			printTitle("saveEmployees", 60, '-');
			ui.saveEmployees(empl1, empl2, empl3);
			
			printTitle("findAll", 60, '-');
			ui.findAll();
    }
  }

  public static void main(String[] args) {
  	printSeparator(60); printTitle("UITest (JPA)", 60); printSeparator(60);
    uiTest();
  }
}
