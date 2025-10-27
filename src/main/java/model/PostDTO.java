package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PostDTO {

	private int POST_ID;
	private String TITLE;
	private String  CONTENT;
	private String  WRITER_ID;
	private int  HIT;
	private String  REG_DT;
	private String  MOD_DT;

	
}
