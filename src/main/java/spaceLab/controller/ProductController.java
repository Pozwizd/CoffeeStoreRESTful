package spaceLab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spaceLab.model.category.CategoryResponse;
import spaceLab.model.product.ProductResponse;
import spaceLab.service.CategoryService;
import spaceLab.service.ProductService;


/**
ProductController
- productService: ProductService
- categoryService: CategoryService
--
+ getCategories(): ResponseEntity<?>
+ getProduct(@PathVariable Long id): ResponseEntity<?>
+ getProductsByCategory(@PathVariable Long id): ResponseEntity<?>
 */
@Tag(name = "Product")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Operation(summary = "Get categories",description = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = CategoryResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Customer unauthorized",content = {@Content(mediaType = "application/json",schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad request",content = {@Content(mediaType = "application/json",schema = @Schema())})
    })
    @GetMapping("/api/v1/categories")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategoryResponse());
    }


    @Operation(summary = "Get product with attributeProducts and attributeValues",description = "Get product with additive types by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ProductResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Customer unauthorized",content = {@Content(mediaType = "application/json",schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Product not found",content = {@Content(mediaType = "application/json",schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad request",content = {@Content(mediaType = "application/json",schema = @Schema())})})
    @GetMapping("/api/v1/product/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        ProductResponse productDto = productService.getProductResponse(id);
        if (productDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(productDto);
    }

    @Operation(summary = "Get products for category",description = "Get products by category id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ProductResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Customer unauthorized",content = {@Content(mediaType = "application/json",schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad request",content = {@Content(mediaType = "application/json",schema = @Schema())})})
    @GetMapping("/api/v1/product/getProductsByCategory/{id}")
    public ResponseEntity<Page<ProductResponse>> getProductsByCategory(@PathVariable Long id,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "5") Integer size) {
        return ResponseEntity.ok(productService.findAllProductResponseByCategoryId(id, page, size));
    }
}
