package com.guitar.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.guitar.db.repository.LocationJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.guitar.db.model.Location;

import static org.junit.Assert.*;


@ContextConfiguration(locations={"classpath:com/guitar/db/applicationTests-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class LocationPersistenceTests {

	@Autowired
	private LocationJpaRepository locationJpaRepository;




	@PersistenceContext
	private EntityManager entityManager;


	@Test
	public void testJpaFind(){
		List<Location> location =  locationJpaRepository.findAll();
		assertNotNull(location);
	}

	@Test
	@Transactional
	public void testSaveAndGetAndDelete() throws Exception {
		Location location = new Location();
		location.setCountry("Canada");
		location.setState("British Columbia");
//		location = locationRepository.create(location);
		location = locationJpaRepository.saveAndFlush(location);

		// clear the persistence context so we don't return the previously cached location object
		// this is a test only thing and normally doesn't need to be done in prod code
		entityManager.clear();

		Location otherLocation = locationJpaRepository.findOne(location.getId());
		assertEquals("Canada", otherLocation.getCountry());
		assertEquals("British Columbia", otherLocation.getState());
		
		//delete BC location now
		locationJpaRepository.delete(otherLocation);
	}

	@Test
	public void testFindWithLike() throws Exception {
		List<Location> locs = locationJpaRepository.findByStateIgnoreCaseStartingWith("New");
		assertEquals(4, locs.size());

		 locs = locationJpaRepository.findByStateNotLikeOrderByStateAsc("New%");
		assertEquals(46, locs.size());

		locs = locationJpaRepository.findByStateNotLikeOrderByStateAsc("New%");
		assertEquals("Alaska", locs.get(1).getState());

		locs.forEach(
				(item) -> {
					System.out.println (item.getState());
				});

		locs  = locationJpaRepository.findFirstByStateIgnoreCaseStartingWith("a");
		assertEquals("Alabama", locs.get(0).getState());

	}

	@Test
	public void testJpaAnd() throws Exception {
		List<Location> locs = locationJpaRepository.findByStateNot("Maryland");
		assertNotSame("Maryland", locs.get(0).getState());

    }
	@Test
	public void testJpaOr() throws Exception {
		List<Location> locs = locationJpaRepository.findByStateIsOrCountryEquals("Maryland", "United states");
        assertEquals("Maryland", locs.get(0).getState());
	}



	@Test
	@Transactional  //note this is needed because we will get a lazy load exception unless we are in a tx
	public void testFindWithChildren() throws Exception {
		Location arizona = locationJpaRepository.findOne(3L);
		assertEquals("United States", arizona.getCountry());
		assertEquals("Arizona", arizona.getState());
		
		assertEquals(1, arizona.getManufacturers().size());
		
		assertEquals("Fender Musical Instruments Corporation", arizona.getManufacturers().get(0).getName());
	}
}
