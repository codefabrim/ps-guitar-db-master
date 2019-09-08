package com.guitar.db.repository;

import com.guitar.db.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationJpaRepository extends JpaRepository <Location, Long> {

//    List<Location> findByStateStartingWith(String name);
    List<Location> findByStateIgnoreCaseStartingWith(String name);


    List<Location> findFirstByStateIgnoreCaseStartingWith(String name);
//    List<Location> findByStateNotLike(String name);
    List<Location> findByStateNotLikeOrderByStateAsc(String name);






    List<Location> findByStateIsOrCountryEquals(String value, String value2);

    List<Location> findByStateNot(String state);





}
