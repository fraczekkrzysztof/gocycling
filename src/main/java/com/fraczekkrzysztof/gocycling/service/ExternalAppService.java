package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.entity.ExternalApps;

import java.util.List;

public interface ExternalAppService {

    List<ExternalApps> getUserExternalApps(String userUid);
}
