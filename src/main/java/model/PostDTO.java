package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PostDTO {

	private int num;		//글의순서
	private String writer;	//저자
	private String email;	//이메일
	private String subject;	//제목
	private String password;//비밀번호
	private String reg_date;//작성일자
	private int ref;		//원글번호
	private int re_step;	//글 계층
	private int re_level;	//글 순서
	private int readcount;	//조회수
	private String content;
	
}
