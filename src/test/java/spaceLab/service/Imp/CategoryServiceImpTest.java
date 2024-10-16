package spaceLab.service.Imp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import spaceLab.entity.Category;
import spaceLab.mapper.CategoryMapper;
import spaceLab.model.category.CategoryResponse;
import spaceLab.repository.CategoryRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImpTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImp categoryService;

    @Test
    void testGetAllCategory() {
        List<Category> categories = Arrays.asList(new Category(), new Category());
        Mockito.when(categoryRepository.findAll(any(Specification.class))).thenReturn(categories);

        List<Category> result = categoryService.getAllCategory();

        Assertions.assertEquals(categories.size(), result.size());
        Mockito.verify(categoryRepository, Mockito.times(1)).findAll(any(Specification.class));
    }

    @Test
    void testGetAllCategory_WithException() {

        Mockito.when(categoryRepository.findAll(any(Specification.class)))
                .thenThrow(new RuntimeException("Database error"));

        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            categoryService.getAllCategory();
        });
        Assertions.assertEquals("Database error", exception.getMessage());
        Mockito.verify(categoryRepository, Mockito.times(1)).findAll(any(Specification.class));
    }

    @Test
    void testGetAllCategoryResponse() {

        List<Category> categories = Arrays.asList(new Category(), new Category());
        List<CategoryResponse> categoryResponses = Arrays.asList(new CategoryResponse(), new CategoryResponse());
        Mockito.when(categoryRepository.findAll(any(Specification.class))).thenReturn(categories);
        Mockito.when(categoryMapper.toCategoryResponseList(categories)).thenReturn(categoryResponses);

        List<CategoryResponse> result = categoryService.getAllCategoryResponse();

        Assertions.assertEquals(categoryResponses.size(), result.size());
        Mockito.verify(categoryRepository, Mockito.times(1)).findAll(any(Specification.class));
        Mockito.verify(categoryMapper, Mockito.times(1)).toCategoryResponseList(categories);
    }
}
