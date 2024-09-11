package spaceLab.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spaceLab.model.location.LocationResponse;
import spaceLab.service.LocationService;

import java.util.List;

/**
LocationController
- locationService: LocationService
--
+ getLocation(@PathVariable("id") Long id): ResponseEntity<LocationDto>
+ getPageableLocationAddresses(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size): ResponseEntity<Page<LocationDto>>
+ getListLocationDto(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size): ResponseEntity<Page<LocationDto>>
 */
@Tag(name = "Location")
@RestController
@RequestMapping( "/api/v1")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }


    @Operation(summary = "Get location by id",description = "Get location by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = LocationResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Customer unauthorized",content = {@Content(mediaType = "application/json",schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Location not found",content = {@Content(mediaType = "application/json",schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad request",content = {@Content(mediaType = "application/json",schema = @Schema())})})
    @GetMapping("/location/{id}")
    public ResponseEntity<LocationResponse> getLocation(@PathVariable("id")
                                                 @Parameter(name = "id", description = "Location id", example = "1")
                                                 Long id){
        LocationResponse locationDto = locationService.getLocationResponse(id);
        if(locationDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(locationDto,HttpStatus.OK);
    }



    @Operation(summary = "Get locations list",description = "Get all locations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = LocationResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Customer unauthorized",content = {@Content(mediaType = "application/json",schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad request",content = {@Content(mediaType = "application/json",schema = @Schema())})})
    @GetMapping("/locationsList")
    public ResponseEntity<List<LocationResponse>> getListLocationDto(@RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "5") Integer size){
        List<LocationResponse> locationAddressDTOS = locationService.findAllLocationResponses();
        return new ResponseEntity<>(locationAddressDTOS, HttpStatus.OK);
    }

}
