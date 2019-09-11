# Spring Query DSL

https://github.com/codefabrim/ps-guitar-db-master.git

## Advantage 
use the definition of JPA

check query at compile time 


## Query Methods
- find...By
- query...By
- read...By
- count..By
- get...By


Multiple Criteria can ber combine with AND , OR

![Screenshot 2019-09-08 at 12.01.30.png](:storage/7df87abb-094d-4b77-ada0-f585bc980215/a06764db.png)

#### add in LocationJPaRepository query findByStateLike  and adapt test cole in LocationPersisitenceTest  [LocationPersistence fixed]

```java
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationJpaRepository extends JpaRepository <Location, Long> {

    List<Location> findByStateLike(String name);
}


//------

	@Test
	public void testFindWithLike() throws Exception {
	//	List<Location> locs = locationRepository.getLocationByStateName("New");
		List<Location> locs = locationJpaRepository.findByStateLike("New%");
		assertEquals(4, locs.size());
	}


```


![Screenshot 2019-09-08 at 12.05.56.png](:storage/7df87abb-094d-4b77-ada0-f585bc980215/95124a02.png)

#### Query DSL: use of "Or" and "AND"

```java

public interface LocationJpaRepository extends JpaRepository <Location, Long> {

    List<Location> findByStateLike(String name);


    List<Location> findByStateOrCountryLike(String value, String value2);

    List<Location> findByStateAndCountryLike(String state, String country);



}

//----

	@Test
	public void testJpaOr() throws Exception {
		List<Location> locs = locationJpaRepository.findByStateOrCountryLike("Maryland", "");
        assertEquals("Maryland", locs.get(0).getState());
	}




```
## Is , Equals
![Screenshot 2019-09-08 at 13.19.41.png](:storage/7df87abb-094d-4b77-ada0-f585bc980215/8b55f5bb.png)

```java

    List<Location> findByStateIsOrCountryEquals(String value, String value2);

  
    List<Location> findByStateNot(String state);
 
 //----
 
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

 
 ```
 
 
 ## Like and Not Like
![Screenshot 2019-09-08 at 13.27.58.png](:storage/7df87abb-094d-4b77-ada0-f585bc980215/fb230071.png)

```java
public interface LocationJpaRepository extends JpaRepository <Location, Long> {

    List<Location> findByStateLike(String name);
    List<Location> findByStateNotLike(String name);
/////------

	@Test
	public void testFindWithLike() throws Exception {
		List<Location> locs = locationJpaRepository.findByStateLike("New%");
		assertEquals(4, locs.size());

		 locs = locationJpaRepository.findByStateNotLike("New%");
		assertEquals(46, locs.size());
	}
```

## StartingWith, EndingWith, Containing

![Screenshot 2019-09-08 at 13.36.25.png](:storage/7df87abb-094d-4b77-ada0-f585bc980215/4be06994.png)
```java

public interface LocationJpaRepository extends JpaRepository <Location, Long> {

    List<Location> findByStateLike(String name);
    List<Location> findByStateStartingWith(String name);
    List<Location> findByStateNotLike(String name);

//----
	@Test
	public void testFindWithLike() throws Exception {
		List<Location> locs = locationJpaRepository.findByStateStartingWith("New");
		assertEquals(4, locs.size());

		 locs = locationJpaRepository.findByStateNotLike("New%");
		assertEquals(46, locs.size());

```

## Less than(Equal) , GreaterThan(Equal)
![Screenshot 2019-09-08 at 13.38.57.png](:storage/7df87abb-094d-4b77-ada0-f585bc980215/9dd29489.png)

```java
@Repository
public interface ModelJpaRepository  extends JpaRepository<Model, Long> {

    List<Model> findByPriceGreaterThanEqualAndPriceLessThanEqual(BigDecimal low,BigDecimal high);


-----
//ModelRepository.java
	public List<Model> getModelsInPriceRange(BigDecimal lowest, BigDecimal highest) {
	/**	@SuppressWarnings("unchecked")
		List<Model> mods = entityManager
				.createQuery("select m from Model m where m.price >= :lowest and m.price <= :highest")
				.setParameter("lowest", lowest)
				.setParameter("highest", highest).getResultList();
		*/
		List<Model> mods = modelJpaRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual(lowest, highest);
		return mods;
	}
----
	@Test
	public void testGetModelsInPriceRange() throws Exception {
//		List<Model> mods = modelRepository.getModelsInPriceRange(BigDecimal.valueOf(1000L), BigDecimal.valueOf(2000L));
//		List<Model> mods = modelJpaRepository.getModelsInPriceRange(BigDecimal.valueOf(1000L), BigDecimal.valueOf(2000L));
		List<Model> mods = modelJpaRepository.getModelsInPriceRange(BigDecimal.valueOf(1000L), BigDecimal.valueOf(2000L));
		assertEquals(4, mods.size());
	}

```
##Beforer, After, Between
![Screenshot 2019-09-08 at 13.49.04.png](:storage/7df87abb-094d-4b77-ada0-f585bc980215/fc457a08.png)

```java
@Repository
public interface ManufacturerJpaRepository  extends JpaRepository <Manufacturer, Long>  {

    List<Manufacturer> findByFoundedDateBefore(Date d);

------
ManufacturerRepository.java
	/**
	 * Custom finder
	 */
	public List<Manufacturer> getManufacturersFoundedBeforeDate(Date date) {
//		@SuppressWarnings("unchecked")
//		List<Manufacturer> mans = entityManager
//				.createQuery("select m from Manufacturer m where m.foundedDate < :date")
//				.setParameter("date", date).getResultList();
		List<Manufacturer> mans = manufacturerJpaRepository.findByFoundedDateBefore(date);
		return mans;
	}
```

## True and False
![Screenshot 2019-09-08 at 15.09.59.png](:storage/7df87abb-094d-4b77-ada0-f585bc980215/bb816f07.png)
```java
public interface ManufacturerJpaRepository  extends JpaRepository <Manufacturer, Long>  {

    List<Manufacturer> findByFoundedDateBefore(Date d);

    List<Manufacturer> findByActiveTrue();
    List<Manufacturer> findByActiveFalse();

-----


	/**
	 * Native Query finder
	 */
	public List<Manufacturer> getManufacturersThatSellModelsOfType(String modelType) {
//		@SuppressWarnings("unchecked")
//		List<Manufacturer> mans = entityManager
//				.createNamedQuery("Manufacturer.getAllThatSellAcoustics")
//				.setParameter(1, modelType).getResultList();
//		return mans;
		List<Manufacturer> mans = manufacturerJpaRepository.findByActiveFalse();
		return mans;
	}




```

![Screenshot 2019-09-08 at 15.42.28.png](:storage/7df87abb-094d-4b77-ada0-f585bc980215/90eb7e23.png)

## In , NotIn
![Screenshot 2019-09-08 at 15.43.10.png](:storage/7df87abb-094d-4b77-ada0-f585bc980215/428b06fa.png)
```java
public interface ModelJpaRepository  extends JpaRepository<Model, Long> {

    List<Model> findByPriceGreaterThanEqualAndPriceLessThanEqual(BigDecimal low,BigDecimal high);

    List<Model> findByModelTypeNameIn(List<String> types);


}
///----
@Test
	public void testGetModelsByTypes() throws Exception {

		List<String > types = new ArrayList<String>();
		types.add("Electric");
		types.add("Acoustic");
		types.add("Bass");
		List<Model> mods = modelJpaRepository.findByModelTypeNameIn(types);

		mods.forEach((model) ->  {
			assertTrue(
					(model.getModelType().getName().equals("Electric")) ||
							(model.getModelType().getName().equals("Acoustic"))
			);
		});
		assertEquals(4, mods.size());
	}
```
## IgnoreCase
![Screenshot 2019-09-08 at 16.08.00.png](:storage/7df87abb-094d-4b77-ada0-f585bc980215/cb3f696d.png)

```java
public interface LocationJpaRepository extends JpaRepository <Location, Long> {
//    List<Location> findByStateStartingWith(String name);
    List<Location> findByStateIgnoreCaseStartingWith(String name);
    List<Location> findByStateNotLike(String name);
    
    ------
    
    @Test
    	public void testFindWithLike() throws Exception {
    //		List<Location> locs = locationJpaRepository.findByStateStartingWith("New");
    		List<Location> locs = locationJpaRepository.findByStateIgnoreCaseStartingWith("New");
    		assertEquals(4, locs.size());
    
    		 locs = locationJpaRepository.findByStateNotLike("New%");
    		assertEquals(46, locs.size());
    
```
## OrderBy
![Screenshot 2019-09-08 at 16.13.04.png](:storage/7df87abb-094d-4b77-ada0-f585bc980215/4a83c1c9.png)
```java
public interface LocationJpaRepository extends JpaRepository <Location, Long> {

//    List<Location> findByStateStartingWith(String name);
    List<Location> findByStateIgnoreCaseStartingWith(String name);

    List<Location> findByStateNotLikeOrderByStateAsc(String name);

//-----
public void testFindWithLike() throws Exception {
		List<Location> locs = locationJpaRepository.findByStateIgnoreCaseStartingWith("New");
		assertEquals(4, locs.size());

	   locs = locationJpaRepository.findByStateNotLikeOrderByStateAsc("New%");
		assertEquals(46, locs.size());

		locs = locationJpaRepository.findByStateNotLikeOrderByStateAsc("New%");
		assertEquals("Alabama", locs.get(0).getState());

		locs.forEach(
				(item) -> {
					System.out.println(item.getState());
				});
	}


```
## First, Top, Distinct
![Screenshot 2019-09-08 at 16.42.09.png](:storage/7df87abb-094d-4b77-ada0-f585bc980215/8298069e.png)

```java
public interface LocationJpaRepository extends JpaRepository <Location, Long> {

//    List<Location> findByStateStartingWith(String name);
    List<Location> findByStateIgnoreCaseStartingWith(String name);


    List<Location> findFirstByStateIgnoreCaseStartingWith(String name);
    ///----test
    	locs  = locationJpaRepository.findFirstByStateIgnoreCaseStartingWith("a");
    		assertEquals("Alabama", locs.get(0).getState());

    
```
