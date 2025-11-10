package me.jwjung.csms.authorization;

import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@ToString
@Data
public class BlackList {

	@Setter
	private Long id;
	private String memberUuid;

	protected BlackList() {}

	public BlackList(String memberUuid) {
		BlackList blackList = new BlackList();
		blackList.memberUuid = memberUuid;
	}
}
