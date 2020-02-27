import com.bizwink.dubboservice.service.Users.UsersService;
import com.bizwink.po.Users;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class testDubboService {
    public testDubboService() {

    }

    public static void main(String[] args) {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("dubbo-consumer.xml");

        if (applicationContext != null) {
            String[] names = applicationContext.getBeanDefinitionNames();
            System.out.print("Beans:");
            for (String string : names) {
                System.out.print(string);
                System.out.print(",");
            }
            System.out.println();

            UsersService ds = (UsersService) applicationContext.getBean("UsersService");
            Users us= ds.getByUserId("petersong");
            System.out.println("applicationContext is OK"  + "======" + us.getUSERPWD());
        } else {
            System.out.println("applicationContext get failed" );
        }
    }
}
