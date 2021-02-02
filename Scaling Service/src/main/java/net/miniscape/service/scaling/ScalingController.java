package net.miniscape.service.scaling;

import net.miniscape.server.ServerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/scaling")
public class ScalingController {
    @Autowired
    private ScalingService service;

    @PostMapping(value = "/server")
    public ResponseEntity<String> requestServer(@RequestBody ServerRequest request) {
        return new ResponseEntity(service.requestServer(request), HttpStatus.OK);
    }
}