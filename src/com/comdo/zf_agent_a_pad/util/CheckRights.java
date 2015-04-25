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

	public static Boolean IS_ERJI = false;
	public static Boolean IS_YIJI = false;
	
	public static void setUserTrue() {

		List<Machtigingen> list = MyApplication.NewUser.getMachtigingen();
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

	public static void setYijiTrue() {
		IS_YIJI = true;
		
	}
	public static void setErjiTrue() {
		IS_ERJI = true;
		
	}
	public static void setAllFalse() {
		RIGHT_1 = false;
		RIGHT_2 = false;
		RIGHT_3 = false;
		RIGHT_4 = false;
		RIGHT_5 = false;
		RIGHT_6 = false;
		RIGHT_7 = false;
		RIGHT_8 = false;
		RIGHT_9 = false;
	}
}
