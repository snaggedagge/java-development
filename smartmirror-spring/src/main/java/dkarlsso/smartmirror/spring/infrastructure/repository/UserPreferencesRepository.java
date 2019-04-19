package dkarlsso.smartmirror.spring.infrastructure.repository;

import dkarlsso.commons.oauth2.Oauth2Scope;
import dkarlsso.smartmirror.spring.domain.model.UserPreference;
import dkarlsso.smartmirror.spring.domain.model.UserPreferenceId;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface UserPreferencesRepository extends CrudRepository<UserPreference, UserPreferenceId> {

    String KEY_DEFAULT_GOOGLE_CALENDARS = "DEFAULT_GOOGLE_CALENDARS";


    default List<String> getDefaultGoogleCalendarIds(final String userEmail) {
        final Optional<UserPreference> userPreference = this.findById(UserPreferenceId.builder()
                                                                        .email(userEmail)
                                                                        .preferenceKey(KEY_DEFAULT_GOOGLE_CALENDARS).build());
        return userPreference.map(userPreference1 -> Arrays.asList(userPreference1.getPreferenceValue().split(",")))
                .orElseGet(ArrayList::new);
    }

    default void saveDefaultGoogleCalendarIds(final String userEmail, List<String> calendarIds) {
        final UserPreference userPreference = UserPreference.builder()
                .userPreferenceId(
                        UserPreferenceId.builder()
                                .email(userEmail)
                                .preferenceKey(KEY_DEFAULT_GOOGLE_CALENDARS).build())
                .preferenceValue(calendarIds.stream().collect(Collectors.joining(",")))
                .build();
        this.save(userPreference);
    }

}
