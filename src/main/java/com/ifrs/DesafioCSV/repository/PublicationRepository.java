package com.ifrs.DesafioCSV.repository;

import com.ifrs.DesafioCSV.domain.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublicationRepository extends JpaRepository<Publication , Long> {

    @Query(value = "SELECT * FROM publication p WHERE p.doi = :doi", nativeQuery = true)
    List<Publication> searchByDoi(@Param("doi") String doi);

    @Query(value = "SELECT * FROM Publication p WHERE p.p_year = :year", nativeQuery = true)
    List<Publication> findByP_year(@Param("year") int year);

}
