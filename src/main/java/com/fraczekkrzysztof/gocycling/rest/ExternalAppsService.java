package com.fraczekkrzysztof.gocycling.rest;

import com.fraczekkrzysztof.gocycling.entity.ExternalApps;
import com.fraczekkrzysztof.gocycling.service.ExternalAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/custom/externalapps")
@RequiredArgsConstructor
public class ExternalAppsService {

    private final ExternalAppService externalAppService;

    @GetMapping("/list")
    public ResponseEntity<List<ExternalApps>> getListOfUserExternalApps(@RequestParam("userUid") String userUid){
        List<ExternalApps> list = externalAppService.getUserExternalApps(userUid);
        if (!CollectionUtils.isEmpty(list)){
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
}
