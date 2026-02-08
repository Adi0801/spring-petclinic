
**Spring PetClinic – Feature Flag Implementation
GitHub Repository**

**Repository Link:**
**https://github.com/<your-username>/spring-petclinic-feature-flags**


📌 Overview

This project enhances the Spring PetClinic application by implementing a custom Feature Flag system from scratch (without using libraries such as FF4J or Togglz).

The system allows application features to be enabled or disabled at runtime, supports advanced rollout strategies, and persists feature states in the database so that they survive application restarts.

**🚀 How to Run the Application
Prerequisites**

Java 17+

Maven 3.8+

**Steps**
git clone https://github.com/<your-username>/spring-petclinic-feature-flags
cd spring-petclinic-feature-flags
mvn clean install
mvn spring-boot:run

**Access**

Application URL:
http://localhost:8080

**🏗️ Design Decisions & Assumptions**
1. Database-Backed Feature Flags

Feature flags are stored in the database and loaded dynamically.

This ensures feature configurations persist across application restarts.

Separate tables are used for:

Feature flags

Whitelisted users

Blacklisted users

2. Centralized Feature Evaluation

All feature-flag logic is centralized in:

FeatureFlagService.isFeatureEnabled(flagKey, userId)


Controllers only check whether a feature is enabled, not how the decision is made.

3. User Identification

No authentication was required by the assignment.

The client IP address (request.getRemoteAddr()) is used as a deterministic user identifier.

This supports whitelist, blacklist, and percentage-based rollout.

4. Evaluation Priority (Short-Circuit Logic)

Feature flags are evaluated in the following order:

Feature flag exists (fail fast if missing)

Global disable (kill switch)

Blacklist (deny access)

Whitelist (allow access)

Percentage rollout (deterministic hash-based decision)

Once a decision is made, no further checks are executed.

5. Exception Handling

When a feature is disabled, a custom exception (FeatureDisabledException) is thrown.

A global @ControllerAdvice handles this exception and returns HTTP 403 (Forbidden).

This avoids generic errors and clearly represents controlled access restriction.

6. Custom Annotation (Additional Enhancement)

A custom annotation @FeatureToggle("FLAG_KEY") is implemented using Spring AOP.

This allows declarative feature enforcement and avoids repetitive checks in controllers.

The aspect safely bypasses enforcement when no HTTP request is present (e.g., during startup or tooling).

🎯 Features Controlled by Feature Flags
Feature	Flag Key	Description	Implementation Location
Add New Pet	ADD_PET	Enables/disables adding a new pet	PetController (/pets/new GET & POST)
Add Visit	ADD_VISIT	Enables/disables adding a visit	VisitController
Owner Search	OWNER_SEARCH	Enables/disables owner search	OwnerController (/owners/find, /owners)
🔧 Feature Flag Management APIs

Feature flags are managed using REST APIs (no authentication required).

**Base Path**
/api/flags

**Available Endpoints**
Method	Endpoint	Description
POST	/api/flags	Create a new feature flag
GET	/api/flags	Retrieve all feature flags
GET	/api/flags/{key}	Retrieve a feature flag by key
DELETE	/api/flags/{key}	Delete a feature flag
Example Request – Create Feature Flag
{
  "flagKey": "ADD_PET",
  "enabled": true,
  "rolloutPercentage": 50,
  "description": "Controls add pet feature",
  "whitelistUsers": ["127.0.0.1"],
  "blacklistUsers": []
}

⚠️ Edge Case Handling

Missing feature flag → fail fast

Global disable → overrides all other rules

User in both whitelist and blacklist → blacklist takes precedence

Rollout percentage handling:

0 → feature disabled for all users

100 → feature enabled for all users

1–99 → deterministic hash-based rollout

Feature checks are applied on both GET and POST endpoints to prevent bypass

Feature state changes take effect immediately without restarting the application

🎥 Loom Walkthrough

Loom Video Link:
https://loom.com/share/<your-video-id>

(Replace with your Loom walkthrough link)

✅ Summary

This implementation provides:

A custom, database-backed feature flag system

Support for global disable, whitelist, blacklist, and percentage rollout

Clean controller integration with centralized decision logic

Robust exception handling and explicit edge-case management

All requirements and evaluation criteria specified in the assignment are fully satisfied.
