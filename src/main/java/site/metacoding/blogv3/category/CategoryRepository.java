package site.metacoding.blogv3.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.metacoding.blogv3.user.User;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    //카테고리등록

    //동일한 유저의 카테고리가 기존에 있니? <Optional>해서 하고 있으면
    @Query("SELECT c FROM Category c WHERE c.user = :user AND c.categoryName = :categoryName")
    Optional<Category> findCategoriesBy(@Param("user") User user, @Param("categoryName") String categoryName);
}
