package spaceLab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spaceLab.entity.Category;
import spaceLab.model.category.CategoryResponse;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "itemsInCategory", expression = "java(category.getProducts() != null ? category.getProducts().size() : 0)")
    @Mapping(source = "status", target = "status")
    CategoryResponse categoryToCategoryResponse(Category category);

    List<CategoryResponse> toCategoryResponseList(List<Category> categoryList);
}
