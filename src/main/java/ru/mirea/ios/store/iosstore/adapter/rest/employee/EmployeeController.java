package ru.mirea.ios.store.iosstore.adapter.rest.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.ios.store.iosstore.adapter.rest.employee.model.EmployeeRequest;
import ru.mirea.ios.store.iosstore.app.inbound.employee.PostEmployeeInbound;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/employees")
public class EmployeeController {
    private final PostEmployeeInbound postEmployeeInbound;

    @PostMapping
    public ResponseEntity<String> postNewEmployee(@RequestBody EmployeeRequest body,
                                                  @RequestParam String adminId) {
        var employee = postEmployeeInbound.handleRequest(body, adminId);
        return new ResponseEntity<>("employee with id = " + employee.getId() +  " was created", HttpStatus.OK);
    }
}
