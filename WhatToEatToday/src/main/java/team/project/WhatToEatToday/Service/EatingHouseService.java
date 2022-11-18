package team.project.WhatToEatToday.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.project.WhatToEatToday.domain.EatingHouse;
import team.project.WhatToEatToday.repository.EatingHouseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EatingHouseService {

    private final EatingHouseRepository eatingHouseRepository;

    @Transactional
    public Long join(EatingHouse eatingHouse) {
        eatingHouseRepository.save(eatingHouse);
        return eatingHouse.getId();
    }

    @Transactional
    public Long delete(EatingHouse eatingHouse) {
        Long deletedId = eatingHouse.getId();
        eatingHouseRepository.delete(eatingHouse);
        return deletedId;
    }

    //회원 전체 조회
    public List<EatingHouse> findEatingHouses() {
        return eatingHouseRepository.findAll();
    }

    public EatingHouse findOne(Long eatingHouseId) {
        return eatingHouseRepository.findOne(eatingHouseId);
    }

}