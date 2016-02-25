package com.ldm.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ldm.bean.CaipiaoBean;
import com.ldm.bean.MethodBean;

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
		// 5、向上追10期，那一期的首位次位相减 排除(如要预测的期次为第14期，就排除第4期的首位次位相减的数字)
		if (history20.size() > 9) {
			result.remove((Integer) Math.abs(history20.get(9)[0]-history20.get(9)[1]));
			System.out.println("方法五排除：" + (Integer) Math.abs(history20.get(9)[0]-history20.get(9)[1]));
			mb.setMeth5((Integer) Math.abs(history20.get(9)[0]-history20.get(9)[1]));
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
			mb.setMeth6(mod+"");
/*			
			String meth6 = "";
			switch(mod){
			case 0:
				result.remove(Integer.valueOf(9));
				result.remove(Integer.valueOf(10));
				meth6=9+","+10;
				break;
			case 1:
				result.remove(Integer.valueOf(1));
				result.remove(Integer.valueOf(11));
				result.remove(Integer.valueOf(2));
				meth6=1+","+2+","+11;
				break;
			case 2:
				result.remove(Integer.valueOf(3));
				result.remove(Integer.valueOf(4));
				meth6=3+","+4;
				break;
			case 3:
				result.remove(Integer.valueOf(5));
				result.remove(Integer.valueOf(6));
				meth6=5+","+6;
				break;
			case 4:
				result.remove(Integer.valueOf(7));
				result.remove(Integer.valueOf(8));
				meth6=7+","+8;
				break;
			}
			System.out.println("方法六排除：" + meth6);
			mb.setMeth6(meth6);
*/
		}
		// 7、上一期首位对码号：10、5当做1 , 2、7当做2 , 3、8当作3 , 4、9当作4 , 1、6、11当作5。 
		String meth7 = "";
		switch(history20.get(0)[0]){
		case 1:
			result.remove(Integer.valueOf(6));
			meth7=""+6;
			break;
		case 2:
			result.remove(Integer.valueOf(7));
			meth7=""+7;
			break;
		case 3:
			result.remove(Integer.valueOf(8));
			meth7=""+8;
			break;
		case 4:
			result.remove(Integer.valueOf(9));
			meth7=""+9;
			break;
		case 5:
			result.remove(Integer.valueOf(10));
			meth7=""+10;
			break;
		case 6:
			result.remove(Integer.valueOf(1));
			result.remove(Integer.valueOf(11));
			meth7=1+","+11;
			break;
		case 7:
			result.remove(Integer.valueOf(2));
			meth7=""+2;
			break;
		case 8:
			result.remove(Integer.valueOf(3));
			meth7=""+3;
			break;
		case 9:
			result.remove(Integer.valueOf(4));
			meth7=""+4;
			break;
		case 10:
			result.remove(Integer.valueOf(5));
			meth7=""+5;
			break;
		case 11:
			result.remove(Integer.valueOf(6));
			meth7=""+6;
			break;
		}
		System.out.println("方法七排除：" + meth7);
		mb.setMeth7(meth7);

		// 8、上一期 第2个号码减第3个号码 排除
		result.remove((Integer) Math.abs(history20.get(0)[1] - history20.get(0)[2]));
		System.out.println("方法八排除：" + Math.abs(history20.get(0)[1] - history20.get(0)[2]));
		mb.setMeth8(Math.abs(history20.get(0)[1] - history20.get(0)[2]));
		// 9、上一期首位对码号：10、5当做1 , 2、7当做2 , 3、8当作3 , 4、9当作4 , 1、6、11当作5。
		// 最近5期平均值取余（即首位的代表值相加除5取余） 排除
		if (history20.size() > 4) {
			int count1 = 0;
			for (int k = 0; k < 5; k++) {
				switch (history20.get(k)[0]) {
				case 1:
					count1 += 5;
					break;
				case 2:
					count1 += 2;
					break;
				case 3:
					count1 += 3;
					break;
				case 4:
					count1 += 4;
					break;
				case 5:
					count1 += 1;
					break;
				case 6:
					count1 += 5;
					break;
				case 7:
					count1 += 2;
					break;
				case 8:
					count1 += 3;
					break;
				case 9:
					count1 += 4;
					break;
				case 10:
					count1 += 1;
					break;
				case 11:
					count1 += 5;
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
			mb.setMeth9(mod1+"");
/*
			String meth9 = "";
			switch(mod1){
			case 0:
				result.remove(Integer.valueOf(9));
				result.remove(Integer.valueOf(4));
				meth9=4+","+9;
				break;
			case 1:
				result.remove(Integer.valueOf(5));
				result.remove(Integer.valueOf(10));
				meth9=5+","+10;
				break;
			case 2:
				result.remove(Integer.valueOf(1));
				result.remove(Integer.valueOf(6));
				result.remove(Integer.valueOf(11));
				meth9=1+","+6+","+11;
				break;
			case 3:
				result.remove(Integer.valueOf(2));
				result.remove(Integer.valueOf(7));
				meth9=2+","+7;
				break;
			case 4:
				result.remove(Integer.valueOf(3));
				result.remove(Integer.valueOf(8));
				meth9=3+","+8;
				break;
			}
			System.out.println("方法九排除：" + meth9);
			mb.setMeth9(meth9);
*/
		}
		// 10、上期前3个数 最大数减最小数 排除
		Integer[] temp = { history20.get(0)[0], history20.get(0)[1], history20.get(0)[2] };
		Arrays.sort(temp);
		result.remove((Integer) (temp[2] - temp[0]));
		System.out.println("方法十排除： " + (temp[2] - temp[0]));
		mb.setMeth10((temp[2] - temp[0]));
		// 11、右一：上一期首位右边的号码（1右边为2，2右边为3，以此类推，11右边是1，循环的）称为右一。
		//向前查找历史数据首位号码为右一的那一期，计算上一期与该期中间间隔的期数（上一期和该期都不算），排除个位（如间隔5期，就排除5;间隔13期，就排除3;间隔0、10、20期，排除10）
		int rightNum = history20.get(0)[0] + 1;
		if(history20.get(0)[0] == 11){
			rightNum = 1;
		}
		int num4rem = 0;
		String flag = null ;
		for (int j = 1; j < history20.size(); j++) {
			if (history20.get(j)[0] != rightNum) {
				num4rem++;
				continue;
			}else{
				flag = "yes";
				break;
			}
		}
		if("yes".equals(flag)){
			if(num4rem%10 != 0){
				result.remove(Integer.valueOf(num4rem%10));
				System.out.println("方法十一排除：" + num4rem%10);
				mb.setMeth11(num4rem%10);
			}else{
				result.remove(Integer.valueOf(10));
				System.out.println("方法十一排除：" + 10);
				mb.setMeth11(10);
			}
		}else{
			System.out.println("方法十一没有排除号码,历史数据中没有右一号码");
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", result);
		map.put("bean", mb);
		return map;
	}
}
