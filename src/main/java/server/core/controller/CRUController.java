package server.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.core.entity.EmployeeEntity;
import server.core.enums.EmployeeField;
import server.core.service.DSVService;
import server.core.service.EmployeeService;
import server.core.service.EncryptionService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/core/cru")
public class CRUController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DSVService dsvService;

    @Autowired
    private EncryptionService encryptionService;

    @GetMapping
    public List<EmployeeEntity> getAll(){
        return employeeService.findAll();
    }

    @PostMapping
    public ResponseEntity create(@RequestBody EmployeeEntity entity){
        try{
            entity.setPassword_hash(encryptionService.hash(entity.getPassword_hash()));
            employeeService.save(entity);
            return new ResponseEntity<>("Employee Entry Created", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Unable to Create Employee Entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{username}")
    public EmployeeEntity  getByUsername(@PathVariable String username){
        return employeeService.find(EmployeeField.USERNAME, username);
    }

    @PutMapping("/{username}")
    public void updateByUsername(@PathVariable String username, @RequestBody EmployeeEntity entity){
        if (entity.getPassword_hash() != null) entity.setPassword_hash(encryptionService.hash(entity.getPassword_hash()));
        employeeService.update(EmployeeField.USERNAME, username, entity);
    }

//    @PostMapping("/batch/list")
//    public ResponseEntity createBatch(@RequestBody List<EmployeeEntity> employeeEntityList){
//        List<ResponseEntity> responseEntityList = new ArrayList<>();
//        for (var employeeEntity: employeeEntityList){
//            ResponseEntity response = create(employeeEntity);
//            responseEntityList.add(response);
//        }
//        return new ResponseEntity<>("Executed On Server But Check Response List For Individual Entity Info", HttpStatus.ACCEPTED);
//    }

    @PostMapping("/batch/dlf")
    public List<EmployeeEntity> processDelimitedFile(@RequestParam("file")MultipartFile dsv){
        List<EmployeeEntity> unprocessed = new ArrayList<>();
        try{
            List<EmployeeEntity>[] employeeEntityListArray= dsvService.dsvToEmployeeList(dsv, ',');
            unprocessed = employeeEntityListArray[1];
            for (var employeeEntity: employeeEntityListArray[0]){
                create(employeeEntity);
            }
        }catch (Exception e){
            System.out.println("Some error occured" + e);
        }
        return unprocessed;
    }

}
