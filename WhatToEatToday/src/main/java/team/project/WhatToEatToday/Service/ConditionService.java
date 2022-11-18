package team.project.WhatToEatToday.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team.project.WhatToEatToday.domain.Condition;
import team.project.WhatToEatToday.repository.ConditionRepository;

@Service
@RequiredArgsConstructor
public class ConditionService {

	private final ConditionRepository conditionRepository;
	
	
	 	@Transactional
	    public Long join(Condition condition) {
		 conditionRepository.save(condition);
	        return condition.getId();
	    }

	    public List<Condition> findAll() {
	        return conditionRepository.findAll();
	    }

	    public Condition findOne(Long conditionId) {
	        return conditionRepository.findOne(conditionId);
	    }
	
	    
	    public List<Condition> findCate1(Long id){
	    	return conditionRepository.findCate1(id);
	    }
	
}
