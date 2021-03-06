package hottub.repository;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Entity(name = "running_time")
public class RunningTimeDAO {

    @Id
    private long id = 1;

    @Digits(integer=4, fraction=20)
    @Column
    private BigDecimal runningTimeHeaterHours = new BigDecimal(0);

    @Digits(integer=4, fraction=20)
    @Column
    private BigDecimal runningTimeCirculationHours = new BigDecimal(0);

    @Digits(integer=4, fraction=20)
    @Column
    private BigDecimal bathTotalTimeHours = new BigDecimal(0);

    public RunningTimeDAO() {
    }

    public RunningTimeDAO(BigDecimal runningTimeHeaterHours, BigDecimal runningTimeCirculationHours, BigDecimal bathTotalTimeHours) {
        this.runningTimeHeaterHours = runningTimeHeaterHours;
        this.runningTimeCirculationHours = runningTimeCirculationHours;
        this.bathTotalTimeHours = bathTotalTimeHours;
    }

    public long getId() {
        return id;
    }


    public BigDecimal getRunningTimeHeaterHours() {
        return runningTimeHeaterHours;
    }

    public void setRunningTimeHeaterHours(BigDecimal runningTimeHeaterHours) {
        this.runningTimeHeaterHours = runningTimeHeaterHours;
    }

    public BigDecimal getRunningTimeCirculationHours() {
        return runningTimeCirculationHours;
    }

    public void setRunningTimeCirculationHours(BigDecimal runningTimeCirculationHours) {
        this.runningTimeCirculationHours = runningTimeCirculationHours;
    }

    public BigDecimal getBathTotalTimeHours() {
        return bathTotalTimeHours;
    }

    public void setBathTotalTimeHours(BigDecimal bathTotalTimeHours) {
        this.bathTotalTimeHours = bathTotalTimeHours;
    }
}
