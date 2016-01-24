package com.ldm.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ldm.CaipiaoBean;

public class CP_bj11x5 {
	
	private static final Integer[] NUMS_11_All = new Integer[] {1,2,3,4,5,6,7,8,9,10,11};
	//预测将出号码  
	public static List<Integer> getONE(List<CaipiaoBean> list){
		
		List<Integer[]> history20 = new ArrayList<Integer[]>(); 
		
		for(CaipiaoBean cb : list){
			String[] tmp = cb.getOpenCode().split(",");
			Integer[] num = new Integer[5];
			for(int i =0;i<tmp.length;i++){
				num[i] = Integer.valueOf(tmp[i]);
			}
			history20.add(num);
		}
		List<Integer> result = new ArrayList<>( Arrays.asList(NUMS_11_All));
		if(true){
			//1、上期开奖数字  首位末位相减 排除
			result.remove((Integer)Math.abs(history20.get(0)[4]-history20.get(0)[0]));
			//2、上期首位、第二位 相减 排除
			result.remove((Integer)Math.abs(history20.get(0)[1]-history20.get(0)[0]));
			//3、上期 上上期 首位数相减 排除
			result.remove((Integer)Math.abs(history20.get(0)[0]-history20.get(1)[0]));
			//4、上期首位 找到历史记录中最近首位相同的一期开奖号码 它的下一期的首位数字 排除
			int cruONE = history20.get(0)[0];
			for(int j=1;j<history20.size();j++){
				if(history20.get(j)[0]==cruONE){
					result.remove(history20.get(j-1)[0]);
					break;
				}
			}
			//5、向上追10期，那一期的首位 排除(如上期为第14期，就排除第4期的首位数字)
			result.remove(history20.get(10)[0]);
			//6、最近5期首位数字权重值 求和 除以5取余数 排除(权重值 ：号码1、2、11 权重值为1，号码3、4权重值为2，号码5、6权重值为3，号码7、8权重值为4，号码9、10权重值为5) 注：余数为0 排除数字5
			int count = 0;
			for(int k =0;k<5;k++){
				switch (history20.get(k)[0]) {
				case 1:
					count+=1;
					break;
				case 2:
					count+=1;
					break;
				case 11:
					count+=1;
					break;
				case 3:
					count+=2;
					break;
				case 4:
					count+=2;
					break;
				case 5:
					count+=3;
					break;
				case 6:
					count+=3;
					break;
				case 7:
					count+=4;
					break;
				case 8:
					count+=4;
					break;
				case 9:
					count+=5;
					break;
				case 10:
					count+=5;
					break;
				default:
					break;
				}
			}
			Integer mod = count%5;
			System.out.println("余数为："+mod);
			if(mod==0) mod=5;
			result.remove(mod);
		}
		return result;
	}
}
