package app.service.impl;

import app.service.App2Service;
import app.service.AppService;
import mf.annotation.Autowired;
import mf.annotation.Bean;

@Bean
public class App2ServiceImpl implements App2Service {

    @Autowired
    private AppService appService;

    @Override
    public String getServiceName() {
        return appService.getServiceName();
    }
}
