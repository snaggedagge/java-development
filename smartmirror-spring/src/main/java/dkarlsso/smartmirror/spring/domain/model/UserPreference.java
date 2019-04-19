package dkarlsso.smartmirror.spring.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity(name = "user_preference")
@Builder
@AllArgsConstructor
@Getter
public class UserPreference {

    @EmbeddedId
    private final UserPreferenceId userPreferenceId;

    @Column
    private String preferenceValue;
}
