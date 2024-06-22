package server.core.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.core.entity.EmployeeEntity;
import server.core.enums.EmployeeField;
import server.core.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public void save(EmployeeEntity employeeEntity){
        employeeRepository.save(employeeEntity);
    }

    @Transactional
    public void save(List<EmployeeEntity> employeeEntityList){
        employeeRepository.saveAll(employeeEntityList);
    }

    public EmployeeEntity find(Long id){
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        return employeeEntity.orElse(null);
    }
    public EmployeeEntity find(EmployeeField field, String value) throws IllegalStateException{
        EmployeeEntity employeeEntity;
        switch (field){
            case USERNAME:
                employeeEntity = employeeRepository.findByUsername(value);
                break;
            case ID:
                employeeEntity = find(Long.getLong(value));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + field.getFieldName());
        }
        return employeeEntity;
    }
    public List<EmployeeEntity> findAll(){
        return employeeRepository.findAll();
    }

    public boolean exists(EmployeeField field, String value){
        switch (field){
            case ID:
                return employeeRepository.existsById(Long.getLong(value));
            case USERNAME:
                return employeeRepository.existsByUsername(value);
            case NAME:
                return employeeRepository.existsByUsername(value);
            default:
                throw new IllegalStateException("Unexpected value:" + field.getFieldName());
        }
    }

    public void update(EmployeeField field, String value, EmployeeEntity employeeEntity){
        if (!exists(field, value)) return;
        if (field != EmployeeField.ID && field != EmployeeField.USERNAME) return;
        Long id = find(field, value).getId();
        employeeEntity.setId(id);
        save(employeeEntity);
    }

}
