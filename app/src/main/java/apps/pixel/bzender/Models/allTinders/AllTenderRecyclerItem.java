package apps.pixel.bzender.Models.allTinders;

import org.jetbrains.annotations.NotNull;

public class AllTenderRecyclerItem {

    private String startDate;
    private String endDate;
    private String sponserCount;
    private String name;
    private String count ;


    public AllTenderRecyclerItem(String startDate, String endDate, String sponserCount, String name , String count) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.sponserCount = sponserCount;
        this.name = name;
        this.count = count ;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSponserCount() {
        return sponserCount;
    }

    public void setSponserCount(String sponserCount) {
        this.sponserCount = sponserCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Override
    public String toString() {
        return "AllTenderRecyclerItem{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", sponserCount='" + sponserCount + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
