package org.springframework.samples.petclinic.featureflag;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flags")
@Tag(name = "Feature Flags", description = "APIs for managing feature flags")
public class FeatureFlagController {

	private final FeatureFlagService service;

	public FeatureFlagController(FeatureFlagService service) {
		this.service = service;
	}

	// CREATE
	@Operation(summary = "Create a feature flag",
			description = "Creates a feature flag with global enable, whitelist, blacklist and percentage rollout support")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Feature flag created"),
			@ApiResponse(responseCode = "400", description = "Invalid input") })
	@PostMapping
	public ResponseEntity<FeatureFlag> create(@RequestBody FeatureFlag flag) {

		FeatureFlag saved = service.save(flag);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}

	// GET ALL
	@Operation(summary = "Get all feature flags")
	@ApiResponse(responseCode = "200", description = "List of feature flags")
	@GetMapping
	public ResponseEntity<List<FeatureFlag>> getAll() {

		return ResponseEntity.ok(service.findAll());
	}

	// GET BY KEY
	@Operation(summary = "Get feature flag by key")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Feature flag found"),
			@ApiResponse(responseCode = "404", description = "Feature flag not found") })
	@GetMapping("/{key}")
	public ResponseEntity<FeatureFlag> getByKey(@PathVariable String key) {

		FeatureFlag flag = service.findByKey(key);

		if (flag == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(flag);
	}

	// DELETE
	@Operation(summary = "Delete feature flag by key")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Feature flag deleted"),
			@ApiResponse(responseCode = "404", description = "Feature flag not found") })
	@DeleteMapping("/{key}")
	public ResponseEntity<Void> delete(@PathVariable String key) {

		service.delete(key);
		return ResponseEntity.noContent().build();
	}

}
