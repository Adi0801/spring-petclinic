package org.springframework.samples.petclinic.featureflag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/flags")

public class FeatureFlagController {
	private final FeatureFlagService service;

	public FeatureFlagController(FeatureFlagService service) {
		this.service = service;
	}

	@PostMapping
	public FeatureFlag create(@RequestBody FeatureFlag flag) {
		return service.save(flag);
	}

	@GetMapping
	public List<FeatureFlag> getAll() {
		return service.findAll();
	}

	@GetMapping("/{key}")
	public FeatureFlag getByKey(@PathVariable String key) {
		return service.findByKey(key);
	}

	@DeleteMapping("/{key}")
	public void delete(@PathVariable String key) {
		service.delete(key);
	}
}
