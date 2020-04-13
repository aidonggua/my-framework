package app.service.impl;

import app.service.AppService;
import mf.annotation.Bean;

@Bean
public class AppServiceImpl implements AppService {

    @Override
    public String getServiceName() {
        return "my framework";
    }
}
