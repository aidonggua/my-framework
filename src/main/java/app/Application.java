package app;

import app.config.Config;
import app.service.App2Service;
import mf.MFApplication;

public class Application {

    public static void main(String[] args) {
        MFApplication mfApplication = new MFApplication().run(Config.class);
        App2Service app2Service = (App2Service) mfApplication.getMfContext()
                                                             .getInstance(App2Service.class);

        System.out.println(app2Service.getServiceName());
    }
}
