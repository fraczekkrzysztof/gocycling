package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.UserExternalAppsRepository;
import com.fraczekkrzysztof.gocycling.entity.ExternalApps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExternalAppServiceImpl implements  ExternalAppService {

    private final UserExternalAppsRepository userExternalAppsRepository;

    @Override
    public List<ExternalApps> getUserExternalApps(String userUid) {
        return userExternalAppsRepository.findExternalAppByUserUid(userUid).stream()
                .map(uea -> uea.getAppType()).collect(Collectors.toList());
    }
}
