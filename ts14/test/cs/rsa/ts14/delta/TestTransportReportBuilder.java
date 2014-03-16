package cs.rsa.ts14.delta;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestTransportReportBuilder {
    private TransportReportBuilder transportReportBuilder;

    @Before
    public void setTransportReportBuilder() {
        transportReportBuilder = new TransportReportBuilder();
    }

    @Test
    public void shouldReturnEmptyReport() {
        transportReportBuilder.buildBegin();
        transportReportBuilder.buildEnd();
        String result = transportReportBuilder.getResult();
        assertEquals("-------------------------------------------------------------------------" + System.getProperty("line.separator") +
                "| Car:     0     Bicycle:    0     Public:     0    Traveling:    0     |" + System.getProperty("line.separator") +
                "| Home:    0    NonWDays:    0     Unknown:    0                        |" + System.getProperty("line.separator") +
                "-------------------------------------------------------------------------" + System.getProperty("line.separator"), result);
    }

    @Test
    public void shouldReturnReportWithCorrectFormatAnd1DayOfEveryCategory() {
        transportReportBuilder.buildBegin();
        transportReportBuilder.buildWeekDaySpecification("Mon", "Ca");
        transportReportBuilder.buildWeekDaySpecification("Mon", "Bi");
        transportReportBuilder.buildWeekDaySpecification("Mon", "Pu");
        transportReportBuilder.buildWeekDaySpecification("Mon", "Tr");
        transportReportBuilder.buildWeekDaySpecification("Mon", "Ho");
        transportReportBuilder.buildWeekDaySpecification("Mon", "No");
        transportReportBuilder.buildEnd();
        String result = transportReportBuilder.getResult();
        assertEquals("-------------------------------------------------------------------------" + System.getProperty("line.separator") +
                "| Car:     1     Bicycle:    1     Public:     1    Traveling:    1     |" + System.getProperty("line.separator") +
                "| Home:    1    NonWDays:    1     Unknown:    0                        |" + System.getProperty("line.separator") +
                "-------------------------------------------------------------------------" + System.getProperty("line.separator"), result);
    }

    @Test
    public void shouldReturnReportWithCorrectFormatAnd10DaysOfEveryCategory() {
        transportReportBuilder.buildBegin();
        add10Days("Ca");
        add10Days("Bi");
        add10Days("Pu");
        add10Days("Tr");
        add10Days("Ho");
        add10Days("No");
        transportReportBuilder.buildEnd();
        String result = transportReportBuilder.getResult();
        assertEquals("-------------------------------------------------------------------------" + System.getProperty("line.separator") +
                "| Car:    10     Bicycle:   10     Public:    10    Traveling:   10     |" + System.getProperty("line.separator") +
                "| Home:   10    NonWDays:   10     Unknown:    0                        |" + System.getProperty("line.separator") +
                "-------------------------------------------------------------------------" + System.getProperty("line.separator"), result);
    }

    @Test
    public void shouldReturnReportWithCorrectFormatAnd100DaysOfEveryCategory() {
        transportReportBuilder.buildBegin();
        add100Days("Ca");
        add100Days("Bi");
        add100Days("Pu");
        add100Days("Tr");
        add100Days("Ho");
        add100Days("No");
        transportReportBuilder.buildEnd();
        String result = transportReportBuilder.getResult();
        assertEquals("-------------------------------------------------------------------------" + System.getProperty("line.separator") +
                "| Car:   100     Bicycle:  100     Public:   100    Traveling:  100     |" + System.getProperty("line.separator") +
                "| Home:  100    NonWDays:  100     Unknown:    0                        |" + System.getProperty("line.separator") +
                "-------------------------------------------------------------------------" + System.getProperty("line.separator"), result);
    }

    @Test
    public void shouldReturnReportWithCorrectFormatAndDifferentTransportFigures() {
        transportReportBuilder.buildBegin();
        transportReportBuilder.buildWeekDaySpecification("Mon", "Ca");
        transportReportBuilder.buildWeekDaySpecification("Mon", "Bi");
        transportReportBuilder.buildWeekDaySpecification("Mon", "Bi");
        transportReportBuilder.buildWeekDaySpecification("Mon", "Pu");
        transportReportBuilder.buildWeekDaySpecification("Mon", "Pu");
        transportReportBuilder.buildWeekDaySpecification("Mon", "Pu");
        add10Days("Tr");
        add10Days("Ho");
        add10Days("Ho");
        add100Days("No");
        transportReportBuilder.buildEnd();
        String result = transportReportBuilder.getResult();
        assertEquals("-------------------------------------------------------------------------" + System.getProperty("line.separator") +
                "| Car:     1     Bicycle:    2     Public:     3    Traveling:   10     |" + System.getProperty("line.separator") +
                "| Home:   20    NonWDays:  100     Unknown:    0                        |" + System.getProperty("line.separator") +
                "-------------------------------------------------------------------------" + System.getProperty("line.separator"), result);
    }

    private void add10Days(String transportMode) {
        transportReportBuilder.buildWeekDaySpecification("Mon", transportMode);
        transportReportBuilder.buildWeekDaySpecification("Mon", transportMode);
        transportReportBuilder.buildWeekDaySpecification("Mon", transportMode);
        transportReportBuilder.buildWeekDaySpecification("Mon", transportMode);
        transportReportBuilder.buildWeekDaySpecification("Mon", transportMode);
        transportReportBuilder.buildWeekDaySpecification("Mon", transportMode);
        transportReportBuilder.buildWeekDaySpecification("Mon", transportMode);
        transportReportBuilder.buildWeekDaySpecification("Mon", transportMode);
        transportReportBuilder.buildWeekDaySpecification("Mon", transportMode);
        transportReportBuilder.buildWeekDaySpecification("Mon", transportMode);
    }
    private void add100Days(String transportMode) {
    	add10Days(transportMode);
    	add10Days(transportMode);
    	add10Days(transportMode);
    	add10Days(transportMode);
    	add10Days(transportMode);
    	add10Days(transportMode);
    	add10Days(transportMode);
    	add10Days(transportMode);
    	add10Days(transportMode);
    	add10Days(transportMode);
    }}
