package onlineservices.services.CarGps;

import onlineservices.services.CarClimatization.CarClimatizationHandler;

public class CarGpsHandlerSingleton {
        private static volatile CarGpsHandler singletonInstance;

        private CarGpsHandlerSingleton() {
        }

        public static CarGpsHandler getInstance() {
            if (singletonInstance == null) {
                synchronized (CarClimatizationHandler.class) {
                    if (singletonInstance == null) {
                        singletonInstance = new CarGpsHandler();
                    }
                }
            }
            return singletonInstance;
        }
}
