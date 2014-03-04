package cs.rsa.ts14.delta;

import cs.rsa.ts14.framework.ReportBuilder;

import java.util.HashMap;

public class TransportReportBuilder implements ReportBuilder {
    private HashMap<String, Integer> transport = new HashMap<>();
    private final String[] TYPES = new String[]{"Bi", "Ca", "Pu", "Tr", "No", "Ho", "Un"};

    @Override
    public void buildBegin() {
        transport.clear();
        for (String type : TYPES) {
            transport.put(type, 0);
        }
    }

    @Override
    public void buildWeekSpecification(int weekNo, int countWorkdays, int countUsedVacationdays) {
    }

    @Override
    public void buildWorkSpecification(String category, String subCategory, double hours) {
    }

    @Override
    public void buildWeekDaySpecification(String weekDay, String transportMode) {
        int noOfDays = transport.get(transportMode);
        transport.put(transportMode, noOfDays + 1);
    }

    @Override
    public void buildAssignment(String variable, double value) {

    }

    @Override
    public void buildEnd() {

    }

    @Override
    public String getResult() {
        String line = String.format("%73s", "").replace(' ', '-');
        return String.format(line + "\n" +
                "| Car:%6s%5s" +
                "Bicycle:%5s%5s" +
                "Public:%6s%4s" +
                "Traveling:%5s%5s|\n" +
                "| Home:%5s%4s" +
                "NonWDays:%5s%5s" +
                "Unknown:%5s%24s|\n" +
                line,
                transport.get("Ca"), "",
                transport.get("Bi"), "",
                transport.get("Pu"), "",
                transport.get("Tr"), "",
                transport.get("Ho"), "",
                transport.get("No"), "",
                transport.get("Un"), "");
    }
}
