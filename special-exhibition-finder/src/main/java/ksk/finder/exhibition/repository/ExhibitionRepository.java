package ksk.finder.exhibition.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ksk.finder.exhibition.model.Exhibition;

public interface ExhibitionRepository extends JpaRepository<Exhibition, String> {

	Exhibition findByName(String exhibitionName);

}