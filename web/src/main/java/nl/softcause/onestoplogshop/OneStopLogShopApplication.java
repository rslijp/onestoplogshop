package nl.softcause.onestoplogshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OneStopLogShopApplication {
        private static final Logger logger = LoggerFactory.getLogger(OneStopLogShopApplication.class);

        public static void main(String[] args) {
            logger.info("Booting application");
//            for (int i = 0; i < args.length; i++) {
//                if (args[i].equals("--enable-dev-captcha")) {
//                    logger.warn("Development captcha enabled");
//                    Captcha.DEV_CAPTCHA = true;
//                }
//                if (args[i].equals("--print-security-tokens")) {
//                    logger.warn("Printing security tokens");
//                    TwoFactorToken.showTokens();
//                    ResetPasswordToken.showTokens();
//                }
//                if (args[i].equals("--no-uuid-caching")) {
//                    logger.warn("Disabled UUID cache");
//                    HibernateUUIDResolver.CACHE_ENABLED = false;
//                    HibernateIDResolver.CACHE_ENABLED = false;
//                }
//            }
            SpringApplication.run(OneStopLogShopApplication.class, args);
        }

}
