package pk.habsoft.demo.estore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import pk.habsoft.demo.estore.dto.ProductDTO;
import pk.habsoft.demo.estore.service.ProductService;

@Api("products")
@RestController
@RequestMapping(Endpoints.ProductEndpoint.BASE_URL)
public class ProductEndPoint {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = Endpoints.ProductEndpoint.GET_BY_ID, method = RequestMethod.GET)
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")
    @ApiOperation("Get product by Id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved product"),
            @ApiResponse(code = 401, message = "You are not authorized to get product") })
    @ResponseHeader(name = "CUSTOM-HEADER", description = "Custorm header in response")
    public ProductDTO getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @RequestMapping(value = Endpoints.ProductEndpoint.GET_ALL_PRODUCTS, method = RequestMethod.GET)
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

}
