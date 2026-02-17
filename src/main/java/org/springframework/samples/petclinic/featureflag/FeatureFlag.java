package org.springframework.samples.petclinic.featureflag;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "feature_flags")
public class FeatureFlag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "flag_key", unique = true, nullable = false)
	private String flagKey;

	private boolean enabled;

	private Integer rolloutPercentage;

	private String description;

	@ElementCollection
	@CollectionTable(name = "feature_flag_whitelist", joinColumns = @JoinColumn(name = "feature_flag_id"))
	@Column(name = "user_id")
	private Set<String> whitelistUsers = new HashSet<>();

	@ElementCollection
	@CollectionTable(name = "feature_flag_blacklist", joinColumns = @JoinColumn(name = "feature_flag_id"))
	@Column(name = "user_id")
	private Set<String> blacklistUsers = new HashSet<>();

	// getters and setters
	public Long getId() {
		return id;
	}

	public String getFlagKey() {
		return flagKey;
	}

	public void setFlagKey(String flagKey) {
		this.flagKey = flagKey;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getRolloutPercentage() {
		return rolloutPercentage;
	}

	public void setRolloutPercentage(Integer rolloutPercentage) {
		this.rolloutPercentage = rolloutPercentage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(mappedBy = "featureFlag", fetch = FetchType.LAZY)
	public Set<String> getWhitelistUsers() {
		return whitelistUsers;
	}

	@OneToMany(mappedBy = "featureFlag", fetch = FetchType.LAZY)
	public Set<String> getBlacklistUsers() {
		return blacklistUsers;
	}

}
