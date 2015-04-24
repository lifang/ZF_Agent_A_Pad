package com.comdo.zf_agent_a_pad.util;

import java.util.List;

import com.comdo.zf_agent_a_pad.entity.Machtigingen;
import com.comdo.zf_agent_a_pad.entity.UserEntity;

public class CheckRights {

	public static Boolean RIGHT_1 = false;
	public static Boolean RIGHT_2 = false;
	public static Boolean RIGHT_3 = false;
	public static Boolean RIGHT_4 = false;
	public static Boolean RIGHT_5 = false;
	public static Boolean RIGHT_6 = false;
	public static Boolean RIGHT_7 = false;
	public static Boolean RIGHT_8 = false;
	public static Boolean RIGHT_9 = false;

	public static void check(UserEntity entity) {

		List<Machtigingen> list = entity.getMachtigingen();
		if (list.size() > 0) {
			for (Machtigingen machtigingen : list) {
				if ("1".equals(machtigingen.getRole_id())) {
					RIGHT_1 = true;
				} else if ("2".equals(machtigingen.getRole_id())) {

					RIGHT_2 = true;
				} else if ("3".equals(machtigingen.getRole_id())) {

					RIGHT_3 = true;
				} else if ("4".equals(machtigingen.getRole_id())) {

					RIGHT_4 = true;
				} else if ("5".equals(machtigingen.getRole_id())) {

					RIGHT_5 = true;
				} else if ("6".equals(machtigingen.getRole_id())) {

					RIGHT_6 = true;
				} else if ("7".equals(machtigingen.getRole_id())) {

					RIGHT_7 = true;
				} else if ("8".equals(machtigingen.getRole_id())) {

					RIGHT_8 = true;
				} else if ("9".equals(machtigingen.getRole_id())) {

					RIGHT_9 = true;
				}

			}
		}
	}

	public static void setAllTrue() {
		RIGHT_1 = true;
		RIGHT_2 = true;
		RIGHT_3 = true;
		RIGHT_4 = true;
		RIGHT_5 = true;
		RIGHT_6 = true;
		RIGHT_7 = true;
		RIGHT_8 = true;
		RIGHT_9 = true;
	}

}
