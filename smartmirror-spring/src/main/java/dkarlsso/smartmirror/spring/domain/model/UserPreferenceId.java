package dkarlsso.smartmirror.spring.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class UserPreferenceId {

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String preferenceKey;

}
