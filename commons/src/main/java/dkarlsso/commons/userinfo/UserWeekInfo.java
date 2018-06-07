package dkarlsso.commons.userinfo;

public class UserWeekInfo {

    private DayInfo[] dayInfos;

    private String name;

    public UserWeekInfo() {
        dayInfos = new DayInfo[7];

        for(int i=0;i<7;i++) {
            dayInfos[i] = new DayInfo();
        }

    }

    public UserWeekInfo(DayInfo[] dayInfos, String name) {
        this();
        this.dayInfos = dayInfos;
        this.name = name;
    }

    public DayInfo[] getDayInfos() {
        return dayInfos;
    }

    public DayInfo getDayInfo(int day) {
        return dayInfos[day];
    }

    public String getName() {
        return name;
    }

    public void setDayInfos(DayInfo[] dayInfos) {
        this.dayInfos = dayInfos;
    }

    public void setName(String name) {
        this.name = name;
    }
}
