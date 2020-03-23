package dkarlsso.smartmirror.spring.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class UserPreferenceId implements Serializable {

    private static final long serialVersionUID = 2900842758287553148L;


    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String preferenceKey;

}
