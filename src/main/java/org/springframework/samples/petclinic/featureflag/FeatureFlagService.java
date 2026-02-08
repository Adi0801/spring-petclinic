package org.springframework.samples.petclinic.featureflag;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FeatureFlagService {
	private final FeatureFlagRepository repository;

	public FeatureFlagService(FeatureFlagRepository repository) {
		this.repository = repository;
	}

	public boolean isFeatureEnabled(String flagKey, String userId) {

		FeatureFlag flag = repository.findByFlagKey(flagKey)
			.orElseThrow(() ->
				new IllegalStateException(
					"Feature flag not found: " + flagKey
				));


		if (!flag.isEnabled()) {
			return false;
		}


		if (flag.getBlacklistUsers().contains(userId)) {
			return false;
		}


		if (flag.getWhitelistUsers().contains(userId)) {
			return true;
		}


		Integer rollout = flag.getRolloutPercentage();
		if (rollout == null || rollout >= 100) {
			return true;
		}

		if (rollout <= 0) {
			return false;
		}

		int hash = Math.abs(userId.hashCode() % 100);
		return hash < rollout;
	}

	// CRUD helpers
	public FeatureFlag save(FeatureFlag flag) {
		return repository.save(flag);
	}

	public List<FeatureFlag> findAll() {
		return repository.findAll();
	}

	public FeatureFlag findByKey(String key) {
		return repository.findByFlagKey(key)
			.orElseThrow(() -> new RuntimeException("Flag not found"));
	}

	public void delete(String key) {
		repository.delete(findByKey(key));
	}
}
