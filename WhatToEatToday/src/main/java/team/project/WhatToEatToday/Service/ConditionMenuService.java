package team.project.WhatToEatToday.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.project.WhatToEatToday.domain.ConditionCategory;
import team.project.WhatToEatToday.domain.ConditionMenu;
import team.project.WhatToEatToday.domain.Menu;
import team.project.WhatToEatToday.repository.ConditionCategoryRepository;
import team.project.WhatToEatToday.repository.ConditionMenuRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConditionMenuService {

	private final ConditionMenuRepository conditionMenuRepository;

	@Transactional
	public void save(ConditionMenu conditionMenu){
		conditionMenuRepository.save(conditionMenu);
	}

	@Transactional
	public Long delete(ConditionMenu conditionMenu) {
		Long deletedMenuId = conditionMenu.getId();
		conditionMenuRepository.delete(conditionMenu);
		return deletedMenuId;
	}
	
	public ConditionMenu findOne(Long id) {
	        return conditionMenuRepository.findOne(id);
	    }

	public List<ConditionMenu> findByConditionId(Long id) { return conditionMenuRepository.findOneByConditionId(id); }

	public ConditionMenu findByName(String name) { return conditionMenuRepository.findByName(name); }
	
	
	public List<ConditionMenu> findAll(){
		return conditionMenuRepository.findAll();
	}

	public List<ConditionMenu> findOneList(Long id) { return conditionMenuRepository.findOneList(id); }
	
	
}
