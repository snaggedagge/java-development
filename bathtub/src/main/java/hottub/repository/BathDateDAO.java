package hottub.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;


@Entity(name = "bath_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BathDateDAO {

    @Id
    private LocalDate date;
}
