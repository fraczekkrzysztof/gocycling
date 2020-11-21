package com.fraczekkrzysztof.gocycling.rest;

import com.fraczekkrzysztof.gocycling.dto.route.RouteDto;
import com.fraczekkrzysztof.gocycling.external.AllExternalRoutesRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/custom/externalroutes")
@RequiredArgsConstructor
public class ExternalRoutesControler {

    private final AllExternalRoutesRetriever routesRetriever;
    @GetMapping("/list")
    public ResponseEntity<List<RouteDto>> getAllExternalUserRoutes(@RequestParam("userUid") String userUid){
        List<RouteDto> listOfRoutes = routesRetriever.getExternalRoutes(userUid);
        return ResponseEntity.status(HttpStatus.OK).body(listOfRoutes);
    }
}
