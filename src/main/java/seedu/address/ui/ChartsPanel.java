package seedu.address.ui;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.Region;

/**
 * The charts panel used to display graphs
 */
public class ChartsPanel extends UiPart<Region> {

    public static final String FXML = "ChartsPanel.fxml";

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private LineChart<Double, Double> priceChart;


    public ChartsPanel() {
        super(FXML);
    }

    /**
     * @param
     */
    private void addPlot(ArrayList<Date> xAxis, ArrayList<Double> yAxis) {
        Series dataSeries = new Series();
        dataSeries.setName("Price History Series");
        populateData(dataSeries, xAxis, yAxis);

        priceChart.getData().add(dataSeries);
        priceChart.setCreateSymbols(false);
    }

    /**
     * @param dataSeries
     * @param xAxis
     * @param yAxis
     */
    private void populateData(Series dataSeries, ArrayList<Date> xAxis, ArrayList<Double> yAxis) {
        assert (xAxis.size() == yAxis.size());
        for (int i = 0; i < xAxis.size(); i++) {
            final String date = DateFormat.getInstance().format(xAxis.get(i));
            dataSeries.getData().add(new Data(date, yAxis.get(i)));
        }
    }
}
