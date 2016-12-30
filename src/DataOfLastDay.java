
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bichoymessiha
 */
public class DataOfLastDay implements Serializable {

    private HashMap<Long, Float> chartTimedB = new HashMap<>();
    private ArrayList<String[]> mapLocationdB = new ArrayList<>();
    private float minimumdB;
    private float maximumdB;

    public DataOfLastDay() {
    }

    public HashMap<Long, Float> getChartTimedB() {
        return chartTimedB;
    }

    public ArrayList<String[]> getMapLocationdB() {
        return mapLocationdB;
    }

    public float getMinimumdB() {
        return minimumdB;
    }

    public float getMaximumdB() {
        return maximumdB;
    }

    public void setChartTimedB(HashMap<Long, Float> chartTimedB) {
        this.chartTimedB = chartTimedB;
    }

    public void setMapLocationdB(ArrayList<String[]> mapLocationdB) {
        this.mapLocationdB = mapLocationdB;
    }

    public void setMinimumdB(float minimumdB) {
        this.minimumdB = minimumdB;
    }

    public void setMaximumdB(float maximumdB) {
        this.maximumdB = maximumdB;
    }

    @Override
    public String toString() {
        String str = "chartTimedB=" + chartTimedB
                + ", minimumdB=" + minimumdB
                + ", maximumdB=" + maximumdB
                + ", mapLocationdB={";
        for (String[] row : mapLocationdB) {
            str += row[0] + " , ";
            str += row[1] + " , ";
            str += row[2] + " / ";
        }
        str += '}';
        return str;
    }
}
