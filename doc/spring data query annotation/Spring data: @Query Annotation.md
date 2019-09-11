# Spring data: @Query Annotation


![Screenshot 2019-09-09 at 10.10.57.png](:storage/3a06f0bb-85e9-437e-b326-3097764f1501/aff5f830.png)

## 1. JPA with @Query, NamedQueries
### Why?

- reuse existing JPQL
- use of complexe JPQL : join, grouping,..
- performance eager loading  with "fetch" keyword

## @Query Annotation
![Screenshot 2019-09-09 at 10.50.18.png](:storage/3a06f0bb-85e9-437e-b326-3097764f1501/e6ff8a75.png)

```java
//modelJpaRepository.java


    @Query("select m from Model m where m.price >= :lowest and m.price <= :highest and m.woodType like :wood")
    List<Model> queryByPriceRangeAndWoodType(@Param("lowest") BigDecimal low,
                                             @Param("highest") BigDecimal high,
                                             @Param("wood") String wood);


//ModelRepository.java

	/**
	 * Custom finder
	 */
	public List<Model> getModelsByPriceRangeAndWoodType(BigDecimal lowest, BigDecimal highest, String wood) {
//		@SuppressWarnings("unchecked")
//		List<Model> mods = entityManager
//				.createQuery("select m from Model m where m.price >= :lowest and m.price <= :highest and m.woodType like :wood")
//				.setParameter("lowest", lowest)
//				.setParameter("highest", highest)
//				.setParameter("wood", "%" + wood + "%").getResultList();
//		return mods;
				return modelJpaRepository.queryByPriceRangeAndWoodType(lowest,highest, "%" + wood + "%");

	}



```



![Screenshot 2019-09-09 at 10.50.40.png](:storage/3a06f0bb-85e9-437e-b326-3097764f1501/45b356fc.png)

## @Query Options
### Named Parameters
![Screenshot 2019-09-09 at 10.58.14.png](:storage/3a06f0bb-85e9-437e-b326-3097764f1501/a47d1098.png)
```java
    List<Model> findAllModelsByType(@Param("name") String name);

---
	/**
	 * NamedQuery finder
	 */
	public List<Model> getModelsByType(String modelType) {
		return  modelJpaRepository.findAllModelsByType(modelType);

	}

```

![Screenshot 2019-09-09 at 10.58.30.png](:storage/3a06f0bb-85e9-437e-b326-3097764f1501/ad4131c3.png)
![Screenshot 2019-09-09 at 10.59.09.png](:storage/3a06f0bb-85e9-437e-b326-3097764f1501/f6bacba1.png)

### Native queries
![Screenshot 2019-09-09 at 10.59.40.png](:storage/3a06f0bb-85e9-437e-b326-3097764f1501/893ac64a.png)
```java


    List<Manufacturer> getAllThatSellAcoustics(String name);

---
**
	 * Native Query finder
	 */
	public List<Manufacturer> getManufacturersThatSellModelsOfType(String modelType) {
		return manufacturerJpaRepository.getAllThatSellAcoustics(modelType);
//		@SuppressWarnings("unchecked")
//		List<Manufacturer> mans = entityManager
//				.createNamedQuery("Manufacturer.getAllThatSellAcoustics")
//				.setParameter(1, modelType).getResultList();
//		return mans;
//		List<Manufacturer> mans = manufacturerJpaRepository.findByActiveFalse();
//		return mans;

```

![Screenshot 2019-09-09 at 10.59.58.png](:storage/3a06f0bb-85e9-437e-b326-3097764f1501/65144339.png)
![Screenshot 2019-09-09 at 11.07.28.png](:storage/3a06f0bb-85e9-437e-b326-3097764f1501/149a08d7.png)
![Screenshot 2019-09-09 at 11.07.37.png](:storage/3a06f0bb-85e9-437e-b326-3097764f1501/c2262871.png)
![Screenshot 2019-09-09 at 11.09.11.png](:storage/3a06f0bb-85e9-437e-b326-3097764f1501/766cfcd0.png)
![Screenshot 2019-09-09 at 11.10.56.png](:storage/3a06f0bb-85e9-437e-b326-3097764f1501/a11d610b.png)

### define in entity ands after use the query define in Entity class


![Screenshot 2019-09-09 at 11.12.43.png](:storage/3a06f0bb-85e9-437e-b326-3097764f1501/45a9edb3.png)


## 2. Native SQL support (SQL )

![Screenshot 2019-09-09 at 11.49.40.png](:storage/3a06f0bb-85e9-437e-b326-3097764f1501/43423fe5.png)
![Screenshot 2019-09-09 at 11.50.05.png](:storage/3a06f0bb-85e9-437e-b326-3097764f1501/28afffc6.png)
![Screenshot 2019-09-09 at 11.50.12.png](:storage/3a06f0bb-85e9-437e-b326-3097764f1501/a910c4cd.png)
![Screenshot 2019-09-09 at 12.09.24.png](:storage/3a06f0bb-85e9-437e-b326-3097764f1501/faf68bb9.png)
![Screenshot 2019-09-09 at 12.12.39.png](:storage/3a06f0bb-85e9-437e-b326-3097764f1501/c07ef09d.png)
![Screenshot 2019-09-09 at 12.15.29.png](:storage/3a06f0bb-85e9-437e-b326-3097764f1501/023e4fb0.png)
