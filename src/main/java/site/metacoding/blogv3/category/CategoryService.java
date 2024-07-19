package site.metacoding.blogv3.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    //카테고리 등록
    @Transactional
    public void save(Category category){  // isPresent 써서 있으면 못 쓰게하고,  없으면 사용할 수 있도록!
        Optional<Category> category1 = categoryRepository.findCategoriesBy(category.getUser(), category.getCategoryName());
        if (category1.isPresent()){
            throw new IllegalStateException("카테고리가 중복되었어요");
        }else {
            categoryRepository.save(category);
        }
    }

}
