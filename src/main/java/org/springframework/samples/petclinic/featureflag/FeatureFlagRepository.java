package org.springframework.samples.petclinic.featureflag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FeatureFlagRepository extends JpaRepository<FeatureFlag, Long> {

	@Query("""
			    SELECT f FROM FeatureFlag f
			    LEFT JOIN FETCH f.blacklistUsers
			    LEFT JOIN FETCH f.whitelistUsers
			    WHERE f.flagKey = :flagKey
			""")
	Optional<FeatureFlag> findByFlagKey(String flagKey);

}
