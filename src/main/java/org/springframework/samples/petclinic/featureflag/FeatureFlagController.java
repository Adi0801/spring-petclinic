package org.springframework.samples.petclinic.featureflag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

	@Operation(
		summary = "Create a feature flag",
		description = "Creates a feature flag with global, whitelist, blacklist and rollout support"
	)
	@PostMapping
	public FeatureFlag create(@RequestBody FeatureFlag flag) {
		return service.save(flag);
	}

	@Operation(summary = "Get all feature flags")
	@GetMapping
	public List<FeatureFlag> getAll() {
		return service.findAll();
	}

	@Operation(summary = "Get feature flag by key")
	@GetMapping("/{key}")
	public FeatureFlag getByKey(@PathVariable String key) {
		return service.findByKey(key);
	}

	@DeleteMapping("/{key}")
	public void delete(@PathVariable String key) {
		service.delete(key);
	}
}
