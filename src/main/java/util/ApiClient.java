package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

import model.ArrivalDTO;

public class ApiClient {

    private static final String API_KEY = "77735a454963677236314f634c7968";
    private static final String BASE_URL =
            "http://swopenapi.seoul.go.kr/api/subway/" + API_KEY + "/json/realtimeStationArrival/0/5/";

    public static List<ArrivalDTO> getArrivalInfo(String stationName) {
        List<ArrivalDTO> list = new ArrayList<>();

        try {
            String apiUrl = BASE_URL + URLEncoder.encode(stationName, "UTF-8");
            URL url = new URL(apiUrl);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            br.close();

            JSONObject json = new JSONObject(sb.toString());
            if (!json.has("realtimeArrivalList")) return list;

            JSONArray rows = json.getJSONArray("realtimeArrivalList");
            for (int i = 0; i < rows.length(); i++) {
                JSONObject obj = rows.getJSONObject(i);
                ArrivalDTO dto = new ArrivalDTO();
                dto.setStation(obj.optString("statnNm"));
                dto.setTrainLine(obj.optString("trainLineNm"));
                dto.setDirection(obj.optString("updnLine"));
                dto.setArrivalMsg(obj.optString("arvlMsg2"));
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}