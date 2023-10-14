package com.nisum.apirest.service;

import com.nisum.apirest.model.dto.request.PhoneRequestDTO;
import com.nisum.apirest.model.entity.Phone;
import com.nisum.apirest.model.dto.response.PhoneResponseDTO;
import com.nisum.apirest.model.entity.User;
import com.nisum.apirest.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhoneService {
    @Autowired
    private PhoneRepository phoneRepository;

    public List<Phone> getAllPhones() {
        return phoneRepository.findAll();
    }

    public Phone savePhone(Phone phone) {
        return phoneRepository.save(phone);
    }
    public List<PhoneResponseDTO> findPhonesByUserId(long userId) {
        Optional<List<Phone>> phonesOptional = phoneRepository.findByUserId(userId);

        if (phonesOptional.isPresent()) {
            List<Phone> phones = phonesOptional.get();

            // Utiliza map y stream para convertir Phone en PhoneResponseDTO
            List<PhoneResponseDTO> phoneResponseDTOs = phones.stream()
                    .map(this::convertToPhoneResponseDTO)
                    .collect(Collectors.toList());

            return phoneResponseDTOs;
        }

        return Collections.emptyList();
    }


    private PhoneResponseDTO convertToPhoneResponseDTO(Phone phone) {
        PhoneResponseDTO phoneResponseDTO = new PhoneResponseDTO();
        phoneResponseDTO.setNumber(phone.getNumber());
        phoneResponseDTO.setCityCode(phone.getCityCode());
        phoneResponseDTO.setContryCode(phone.getContryCode());
        return phoneResponseDTO;
    }

    public void savePhonesForUser(List<PhoneRequestDTO> phonesRequest, User user) {
        List<Phone> phones = new ArrayList<>();

        for (PhoneRequestDTO phoneRequest : phonesRequest) {
            Phone phone = new Phone();
            phone.setNumber(phoneRequest.getNumber());
            phone.setCityCode(phoneRequest.getCityCode());
            phone.setContryCode(phoneRequest.getCountryCode());
            phone.setUser(user);
            phones.add(phone);
        }

        List<Phone> savedPhones = phoneRepository.saveAll(phones);

        List<PhoneResponseDTO> phoneResponseDTOs = savedPhones.stream()
                .map(this::convertToPhoneResponseDTO)
                .toList();

    }

}
