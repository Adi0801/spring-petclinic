package org.springframework.samples.petclinic.featureflag;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.samples.petclinic.annotation.FeatureToggle;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.samples.petclinic.exception.FeatureDisabledException;

@Aspect
@Component
public class FeatureToggleAspect {

	private final FeatureFlagService featureFlagService;

	public FeatureToggleAspect(FeatureFlagService featureFlagService) {
		this.featureFlagService = featureFlagService;
	}

	// @Around -> Intercepts annotated methods
	// ProceddingJointPoint allow to execute the request or block it
	@Around("@annotation(featureToggle)")
	public Object checkFeature(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {

		// Since AOP Run outside controller so we need to fetch request
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		if (attributes == null) {
			// Allow Swagger and non-web execution
			return joinPoint.proceed();
		}

		HttpServletRequest request = attributes.getRequest();
		String userId = request.getRemoteAddr();
		String flagKey = featureToggle.value();
		System.out.println(request.getRemoteAddr());

		if (!featureFlagService.isFeatureEnabled(flagKey, userId)) {
			throw new FeatureDisabledException("Feature " + flagKey + " is disabled");
		}

		return joinPoint.proceed();
	}

}
