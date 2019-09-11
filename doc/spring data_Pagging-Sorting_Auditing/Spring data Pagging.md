# Spring data: Pagging, Sorting, Custom Repositories, Auditing, Locking


## Pageable and Sort


![Screenshot 2019-09-09 at 13.04.48.png](:storage/339596a4-5b74-4046-9520-4077932d34dc/8a224c29.png)
![Screenshot 2019-09-09 at 13.04.56.png](:storage/339596a4-5b74-4046-9520-4077932d34dc/7ec5be63.png)
they can uberall sein,

- add in the parameter liste a Pageable object
- return type Page of the query insteadof List for example
- update the client code 
-  
```java
Page  <Model> queryByPriceRangeAndWoodType(@Param("lowest") BigDecimal low,
                                             @Param ("highest") BigDecimal high,
                                             @Param("wood") String wood,
                                             Pageable page);
```

```java

@Repository
public interface ModelJpaRepository  extends JpaRepository<Model, Long> {

    List<Model> findByPriceGreaterThanEqualAndPriceLessThanEqual(BigDecimal low,BigDecimal high);

    List<Model> findByModelTypeNameIn(List<String> types);

    @Query("select m from Model m where m.price >= :lowest and m.price <= :highest and m.woodType like :wood")
    Page  <Model> queryByPriceRangeAndWoodType(@Param("lowest") BigDecimal low,
                                             @Param ("highest") BigDecimal high,
                                             @Param("wood") String wood,
                                             Pageable page);

```
![Screenshot 2019-09-09 at 15.36.18.png](:storage/339596a4-5b74-4046-9520-4077932d34dc/4a866221.png)
![Screenshot 2019-09-09 at 15.37.16.png](:storage/339596a4-5b74-4046-9520-4077932d34dc/1319e791.png)

Preuve: on voit cela dans ler code sql genere avec order by... et limit  ..


## Custom repository

![Screenshot 2019-09-09 at 15.39.56.png](:storage/339596a4-5b74-4046-9520-4077932d34dc/e076230b.png)
![Screenshot 2019-09-09 at 15.40.23.png](:storage/339596a4-5b74-4046-9520-4077932d34dc/92e1e7ae.png)
![Screenshot 2019-09-09 at 15.40.56.png](:storage/339596a4-5b74-4046-9520-4077932d34dc/98f34618.png)
![Screenshot 2019-09-09 at 15.41.17.png](:storage/339596a4-5b74-4046-9520-4077932d34dc/0543d790.png)

# Auditing support

need of entreprises: 
- tracking Data access layer  when writing data
  - who created the data?
  - when was it created
  -  who modified the record?
  -  when was lthe last modified? 



![Screenshot 2019-09-09 at 15.55.38.png](:storage/339596a4-5b74-4046-9520-4077932d34dc/d135c997.png)

![Screenshot 2019-09-09 at 15.55.49.png](:storage/339596a4-5b74-4046-9520-4077932d34dc/2a90353c.png)
![Screenshot 2019-09-09 at 15.56.13.png](:storage/339596a4-5b74-4046-9520-4077932d34dc/1e6607d1.png)
![Screenshot 2019-09-09 at 15.57.20.png](:storage/339596a4-5b74-4046-9520-4077932d34dc/72d3e2e8.png)
![Screenshot 2019-09-09 at 15.58.13.png](:storage/339596a4-5b74-4046-9520-4077932d34dc/53d9f692.png)
![Screenshot 2019-09-09 at 15.58.57.png](:storage/339596a4-5b74-4046-9520-4077932d34dc/1d994632.png)


[3. Auditing](https://docs.spring.io/spring-data/jpa/docs/1.7.0.DATAJPA-580-SNAPSHOT/reference/html/auditing.html)

## Locking

for concurrent access data


![Screenshot 2019-09-09 at 16.00.24.png](:storage/339596a4-5b74-4046-9520-4077932d34dc/7f3b0ae6.png)
![Screenshot 2019-09-09 at 16.01.21.png](:storage/339596a4-5b74-4046-9520-4077932d34dc/89255055.png)
![Screenshot 2019-09-09 at 16.01.38.png](:storage/339596a4-5b74-4046-9520-4077932d34dc/6fd4dd9a.png)
![Screenshot 2019-09-09 at 16.02.14.png](:storage/339596a4-5b74-4046-9520-4077932d34dc/e555afb9.png)






