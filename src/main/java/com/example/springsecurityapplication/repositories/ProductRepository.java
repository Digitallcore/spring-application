package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByTitle(String title);
    List<Product> findByTitleContainingIgnoreCase(String name);
    @Query(value = "SELECT * FROM product WHERE ((lower(title) LIKE %?1%) OR (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) AND (price>=?2 AND price<=?3)", nativeQuery = true)
    List<Product> findByTitleAndPriceGreaterThanEqualAndPriceLessThan(String title, float ot, float Do);
    @Query(value = "SELECT * FROM product WHERE ((lower(title) LIKE %?1%) OR (lower(title) LIKE '?1%' ) OR (lower(title) LIKE '%?1')) AND (price>=?2 AND price<=?3) ORDER BY price ASC", nativeQuery = true)
    List<Product> findByTitleOrderByPriceAsc(String title, float ot, float Do);
    @Query(value = "SELECT * FROM product WHERE ((lower(title) LIKE %?1%) OR (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) AND (price>=?2 AND price<=?3) ORDER BY price DESC", nativeQuery = true)
    List<Product> findByTitleOrderByPriceDesc(String title, float ot, float Do);
    @Query(value = "SELECT * FROM product WHERE ((lower(title) LIKE %?1% ) OR (lower(title) LIKE '?1%' ) OR (lower(title) LIKE '%?1' )) AND (price >= ?2 AND price <= ?3) AND category_id=?4 ORDER BY price", nativeQuery = true)
    List<Product> findByTitleAndCategoryOrderByPriceAsc(String title, float ot, float Do, int category);
    @Query(value = "SELECT* FROM product WHERE category_id=?4 AND ((lower(title) LIKE %?1%) OR (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) AND (price >= ?2 AND price <= ?3) ORDER BY price DESC", nativeQuery = true)
    List<Product> findByTitleAndCategoryOrderByPriceDesc(String title, float ot, float Do, int category);
    @Query(value = "SELECT * FROM product WHERE ((lower(title) LIKE %?1% ) OR (lower(title) LIKE '?1%' ) OR (lower(title) LIKE '%?1' )) AND (price >= ?2 AND price <= ?3) AND category_id=?4", nativeQuery = true)
    List<Product> findByTitlePriceCategory(String title, float ot, float Do, int category);
    @Query(value = "SELECT* FROM product WHERE category_id=?2 AND ((lower(title) LIKE %?1%) OR (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) ORDER BY price ASC", nativeQuery = true)
    List<Product> findByTitleCategoryOrderByPriceAsc(String title, int category);
    @Query(value = "SELECT* FROM product WHERE ((lower(title) LIKE %?1%) OR (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) ORDER BY price ASC", nativeQuery = true)
    List<Product> findByTitleOrderByPriceAsc(String title);
    @Query(value = "SELECT* FROM product WHERE category_id=?2 AND ((lower(title) LIKE %?1%) OR (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) ORDER BY price DESC", nativeQuery = true)
    List<Product> findByTitleCategoryOrderByPriceDesc(String title, int category);
    @Query(value = "SELECT* FROM product WHERE ((lower(title) LIKE %?1%) OR (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) ORDER BY price DESC", nativeQuery = true)
    List<Product> findByTitleOrderByPriceDesc(String title);
    @Query(value = "SELECT* FROM product WHERE category_id=?2 AND ((lower(title) LIKE %?1%) OR (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1'))", nativeQuery = true)
    List<Product> findByTitleCategory(String title, int category);


}
