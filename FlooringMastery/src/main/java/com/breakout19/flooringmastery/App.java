
package com.breakout19.flooringmastery;
import com.breakout19.flooringmastery.service.InvalidCustomerNameException;
import com.breakout19.flooringmastery.service.InvalidProductException;
import com.breakout19.flooringmastery.service.validateAreaMinException;
import com.breakout19.flooringmastery.controller.FlooringMasteryController;
import com.breakout19.flooringmastery.service.DateValidationException;
import com.breakout19.flooringmastery.service.FlooringMasteryServiceLayer;
import com.breakout19.flooringmastery.service.FlooringMasteryServiceLayerImpl;
import com.breakout19.flooringmastery.service.InvalidStateException;
import com.breakout19.flooringmastery.service.NoOrdersExistException;
import com.breakout19.flooringmastery.ui.FlooringMasteryView;
import com.breakout19.flooringmastery.ui.UserIO;
import com.breakout19.flooringmastery.ui.UserIOConsoleImpl;
import java.io.IOException;
import com.breakout19.flooringmastery.dao.FlooringMasteryAuditDao;
import com.breakout19.flooringmastery.dao.FlooringMasteryAuditDaoFileImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 *
 * @author breakout19
 */
public class App {
    public static void main(String[] args) throws NoOrdersExistException, DateValidationException, InvalidStateException, IOException, validateAreaMinException, InvalidProductException,InvalidCustomerNameException {
      /* UserIO myIo = new UserIOConsoleImpl();
        FlooringMasteryView myView = new FlooringMasteryView(myIo);
    //    FlooringMasteryDao myDao = new FlooringMasteryDaoFileImpl();
        FlooringMasteryServiceLayer myService = new FlooringMasteryServiceLayerImpl();
        FlooringMasteryController controller = new FlooringMasteryController(myView, myService);
        controller.run();
    }*/
       
       ApplicationContext appContext
                = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        FlooringMasteryController controller =appContext.getBean("controller", FlooringMasteryController.class);
        controller.run();

    
    }
}