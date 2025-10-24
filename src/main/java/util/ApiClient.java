package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ApiClient {
    private static final String API_KEY = "435a5a7a49726c6139364554524a63";

    public static String getArrivalInfo(String stationName) {
        StringBuilder result = new StringBuilder();
        try {
            // 한글 역명을 URL-safe하게 변환
            String encodedStation = URLEncoder.encode(stationName, StandardCharsets.UTF_8);
            String apiUrl = "http://swopenAPI.seoul.go.kr/api/subway/" 
                            + API_KEY + "/json/realtimeStationArrival/0/5/" 
                            + encodedStation;

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000); // 연결 시간 제한 5초
            conn.setReadTimeout(5000);    // 읽기 시간 제한 5초

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                rd.close();
            } else {
                System.out.println("HTTP Error Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String result = getArrivalInfo("서울역");
        System.out.println(result);  // JSON 데이터 출력
    }
}
