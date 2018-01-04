package ksk.finder.exhibition.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ksk.finder.exhibition.model.Exhibition;

public interface ExhibitionRepository extends JpaRepository<Exhibition, String> {

	// List<Museum> findByPublicd(boolean publicd);

}