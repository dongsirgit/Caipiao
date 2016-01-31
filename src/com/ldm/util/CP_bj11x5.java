package com.ldm.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ldm.CaipiaoBean;
import com.ldm.MethodBean;

public class CP_bj11x5 {

	private static final Integer[] NUMS_11_All = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };

	// 预测将出号码
	public static Map<String, Object> getONE(List<CaipiaoBean> list) {

		List<Integer[]> history20 = new ArrayList<Integer[]>();

		MethodBean mb = new MethodBean();

		for (CaipiaoBean cb : list) {
			String[] tmp = cb.getOpenCode().split(",");
			Integer[] num = new Integer[5];
			for (int i = 0; i < tmp.length; i++) {
				num[i] = Integer.valueOf(tmp[i]);
			}
			history20.add(num);
		}
		List<Integer> result = new ArrayList<>(Arrays.asList(NUMS_11_All));
		// 1、上期开奖数字 首位末位相减 排除
		result.remove((Integer) Math.abs(history20.get(0)[4] - history20.get(0)[0]));
		System.out.println("方法一排除：" + (Integer) Math.abs(history20.get(0)[4] - history20.get(0)[0]));
		mb.setMeth1((Integer) Math.abs(history20.get(0)[4] - history20.get(0)[0]));
		// 2、上期首位、第二位 相减 排除
		result.remove((Integer) Math.abs(history20.get(0)[1] - history20.get(0)[0]));
		System.out.println("方法二排除：" + (Integer) Math.abs(history20.get(0)[1] - history20.get(0)[0]));
		mb.setMeth2((Integer) Math.abs(history20.get(0)[1] - history20.get(0)[0]));
		// 3、上期 上上期 首位数相减 排除
		if (history20.size() > 1) {
			if ((history20.get(0)[0] - history20.get(1)[0]) == 0) {
				result.remove((Integer) 10);
				System.out.println("方法三排除：" + 10);
				mb.setMeth3(10);
			} else {
				result.remove((Integer) Math.abs(history20.get(0)[0] - history20.get(1)[0]));
				System.out.println("方法三排除：" + (Integer) Math.abs(history20.get(0)[0] - history20.get(1)[0]));
				mb.setMeth3((Integer) Math.abs(history20.get(0)[0] - history20.get(1)[0]));
			}
		}
		// 4、上期首位 找到历史记录中最近首位相同的一期开奖号码 它的下一期的首位数字 排除
		int cruONE = history20.get(0)[0];
		for (int j = 1; j < history20.size(); j++) {
			if (history20.get(j)[0] == cruONE) {
				result.remove(history20.get(j - 1)[0]);
				System.out.println("方法四排除：" + history20.get(j - 1)[0]);
				mb.setMeth4(history20.get(j - 1)[0]);
				break;
			}
		}
		// 5、向上追10期，那一期的首位 排除(如上期为第14期，就排除第4期的首位数字)
		if (history20.size() > 10) {
			result.remove(history20.get(10)[0]);
			System.out.println("方法五排除：" + history20.get(10)[0]);
			mb.setMeth5(history20.get(10)[0]);
		}
		// 6、最近5期首位数字权重值 求和 除以5取余数 排除(权重值 ：号码1、2、11
		// 权重值为1，号码3、4权重值为2，号码5、6权重值为3，号码7、8权重值为4，号码9、10权重值为5) 注：余数为0 排除数字5
		if (history20.size() > 4) {
			int count = 0;
			for (int k = 0; k < 5; k++) {
				switch (history20.get(k)[0]) {
				case 1:
					count += 1;
					break;
				case 2:
					count += 1;
					break;
				case 11:
					count += 1;
					break;
				case 3:
					count += 2;
					break;
				case 4:
					count += 2;
					break;
				case 5:
					count += 3;
					break;
				case 6:
					count += 3;
					break;
				case 7:
					count += 4;
					break;
				case 8:
					count += 4;
					break;
				case 9:
					count += 5;
					break;
				case 10:
					count += 5;
					break;
				default:
					break;
				}
			}
			Integer mod = count % 5;
			if (mod == 0)
				mod = 5;
			result.remove(mod);
			System.out.println("方法六排除：" + mod);
			mb.setMeth6(mod);
		}
		// 7、首位减5 排除
		if ((5 - history20.get(0)[0]) == 0) {
			result.remove((Integer) 10);
			System.out.println("方法七排除：" + 10);
			mb.setMeth7(10);
		} else {
			result.remove((Integer) Math.abs(5 - history20.get(0)[0]));
			System.out.println("方法七排除：" + Math.abs(5 - history20.get(0)[0]));
			mb.setMeth7(Math.abs(5 - history20.get(0)[0]));
		}

		// 8、上一期 第2个号码减第3个号码 排除
		result.remove((Integer) Math.abs(history20.get(0)[1] - history20.get(0)[2]));
		System.out.println("方法八排除：" + Math.abs(history20.get(0)[1] - history20.get(0)[2]));
		mb.setMeth8(Math.abs(history20.get(0)[1] - history20.get(0)[2]));
		// 9、上一期首位：0、5当做1, 1、6、11当作2,2、7当做3,3、8当作4,4、9当作5。
		// 最近5期平均值取余（即首位的代表值相加除5取余） 排除
		if (history20.size() > 4) {
			int count1 = 0;
			for (int k = 0; k < 5; k++) {
				switch (history20.get(k)[0]) {
				case 1:
					count1 += 2;
					break;
				case 2:
					count1 += 3;
					break;
				case 11:
					count1 += 2;
					break;
				case 3:
					count1 += 4;
					break;
				case 4:
					count1 += 5;
					break;
				case 5:
					count1 += 1;
					break;
				case 6:
					count1 += 2;
					break;
				case 7:
					count1 += 3;
					break;
				case 8:
					count1 += 4;
					break;
				case 9:
					count1 += 5;
					break;
				case 10:
					count1 += 1;
					break;
				default:
					break;
				}
			}
			Integer mod1 = count1 % 5;
			if (mod1 == 0)
				mod1 = 5;
			result.remove(mod1);
			System.out.println("方法九排除：" + mod1);
			mb.setMeth9(mod1);
		}
		// 10、上期前3个数 最大数减最小数 排除
		Integer[] temp = { history20.get(0)[0], history20.get(0)[1], history20.get(0)[2] };
		Arrays.sort(temp);
		result.remove((Integer) (temp[2] - temp[0]));
		System.out.println("方法十排除： " + (temp[2] - temp[0]));
		mb.setMeth10((temp[2] - temp[0]));

		Map<String, Object> map = new HashMap<>();
		map.put("list", result);
		map.put("bean", mb);
		return map;
	}
}
