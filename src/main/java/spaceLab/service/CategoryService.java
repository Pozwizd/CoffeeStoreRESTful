package spaceLab.service;

import org.springframework.stereotype.Service;
import spaceLab.entity.Category;
import spaceLab.model.category.CategoryResponse;

import java.util.List;

@Service
public interface CategoryService {

    List<Category> getAllCategory();
    List<CategoryResponse> getAllCategoryResponse();
}
