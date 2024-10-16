package spaceLab.service.Imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spaceLab.entity.Category;
import spaceLab.mapper.CategoryMapper;
import spaceLab.model.category.CategoryResponse;
import spaceLab.repository.CategoryRepository;
import spaceLab.service.CategoryService;
import spaceLab.specification.CategorySpecification;

import java.util.List;


/**
 * CategoryServiceImp
 * - categoryRepository: CategoryRepository
 * - categoryMapper: CategoryMapper
 * --
 * + getAllCategoryResponse(): List<CategoryResponse>
 * + getAllCategory(): List<Category>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCategory() {
        log.info("Fetching all categories");
        try {
            List<Category> categories = categoryRepository.findAll(CategorySpecification.byNotDeleted()
                    .and(CategorySpecification.byStatus(Category.Status.ACTIVE)));
            log.info("Successfully fetched {} categories", categories.size());
            return categories;
        } catch (Exception e) {
            log.error("Error while fetching categories", e);
            throw e;
        }
    }

    @Override
    public List<CategoryResponse> getAllCategoryResponse() {
        log.info("Fetching all category responses");
        List<Category> categories = getAllCategory();
        List<CategoryResponse> categoryResponses = categoryMapper.toCategoryResponseList(categories);
        log.info("Mapped {} categories to DTOs", categoryResponses.size());
        return categoryResponses;
    }
}
