package team.project.WhatToEatToday.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.project.WhatToEatToday.domain.ConditionMenu;
import team.project.WhatToEatToday.domain.CrossMenu;
import team.project.WhatToEatToday.repository.ConditionMenuRepository;
import team.project.WhatToEatToday.repository.CrossMenuRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CrossMenuService {

	private final CrossMenuRepository crossMenuRepository;

	@Transactional
	public void save(CrossMenu crossMenu){
		crossMenuRepository.save(crossMenu);
	}
	
	public CrossMenu findOne(Long id) {
	        return crossMenuRepository.findOne(id);
	    }

	public List<CrossMenu> findByConditionId(Long id) { return crossMenuRepository.findOneByConditionId(id); }

	public CrossMenu findByName(String name) { return crossMenuRepository.findByName(name); }

	public List<CrossMenu> findNewByName(String name) { return crossMenuRepository.findNewByName(name); }
	
	
	public List<CrossMenu> findAll(){
		return crossMenuRepository.findAll();
	}
	
	
}
