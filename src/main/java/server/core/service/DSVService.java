package server.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.core.entity.EmployeeEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DSVService {

    @Autowired
    private EncryptionService encryptionService;

    public List<EmployeeEntity>[] dsvToEmployeeList(MultipartFile file, char delimiter) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            List<EmployeeEntity> employeeEntityList = new ArrayList<>();
            List<EmployeeEntity> unprocessed = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(String.valueOf(delimiter));
                EmployeeEntity employeeEntity = mapFieldsToEmployee(fields);
                if (employeeEntity.getName() == null || employeeEntity.getUsername() == null) {
                    unprocessed.add(employeeEntity);
                } else {
                    employeeEntityList.add(employeeEntity);
                }
            }
            for (EmployeeEntity employeeEntity: employeeEntityList){
                System.out.println(employeeEntity.toString());
            }
            for (EmployeeEntity employeeEntity: unprocessed){
                System.out.println(employeeEntity.toString());
            }
            return new List[]{employeeEntityList, unprocessed};
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private EmployeeEntity mapFieldsToEmployee(String[] fields) {
        EmployeeEntity employee = new EmployeeEntity();
        if (fields.length == 0) return employee;
        if (fields[0] != null) employee.setUsername(fields[0]);
        if (fields[1] != null) employee.setName(fields[1]);
        if (fields.length == 3 && fields[2] != null) employee.setPassword_hash(encryptionService.hash(fields[2]));
        else employee.setPassword_hash(encryptionService.hash("Password01"));
        return employee;
    }

}
