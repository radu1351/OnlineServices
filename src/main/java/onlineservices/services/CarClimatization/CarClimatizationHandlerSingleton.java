package onlineservices.services.CarClimatization;

public class CarClimatizationHandlerSingleton {
    private static volatile CarClimatizationHandler singletonInstance;

    private CarClimatizationHandlerSingleton() {
    }

    public static CarClimatizationHandler getInstance() {
        if (singletonInstance == null) {
            synchronized (CarClimatizationHandler.class) {
                if (singletonInstance == null) {
                    singletonInstance = new CarClimatizationHandler();
                }
            }
        }
        return singletonInstance;
    }
}
