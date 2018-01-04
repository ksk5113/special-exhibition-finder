package ksk.finder.exhibition.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ksk.finder.exhibition.model.Museum;

public interface MuseumRepository extends JpaRepository<Museum, String> {

	// List<Museum> findByPublicd(boolean publicd);

}