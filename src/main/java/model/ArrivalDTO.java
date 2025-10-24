package model;

import lombok.Getter;
import lombok.Setter;

public class ArrivalDTO {
	
    private String subwayLine;   // 호선
    private String trainLineNm;  // 행선지
    private String arvlMsg2;     // 도착 예정 메시지 (ex: 서울역 진입)
    private String arvlMsg3;     // 도착역
    private String updnLine;     // 상행/하행
    private String btrainNo;     // 열차 번호
    
    
    public ArrivalDTO() {
		// TODO Auto-generated constructor stub
	}
    
    
    
    
}
