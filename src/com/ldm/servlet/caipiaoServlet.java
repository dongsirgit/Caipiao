package com.ldm.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.ldm.bean.CaipiaoBean;
import com.ldm.bean.CaipiaoResultBean;
import com.ldm.bean.MethodBean;
import com.ldm.bean.RecordBean;
import com.ldm.service.CaipiaoResultService;
import com.ldm.util.CP_bj11x5;


public class caipiaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Map<String, RecordBean> records = new HashMap<String, RecordBean>();
    public caipiaoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String httpUrl = "http://apis.baidu.com/apistore/lottery/lotteryquery";
		String httpArg = "lotterycode=bj11x5&recordcnt=20";
		String jsonResult = CaipiaoResultService.request(httpUrl, httpArg);
		CaipiaoResultBean crb = JSON.parseObject(jsonResult,CaipiaoResultBean.class);
		List<CaipiaoBean> list=  crb.getRetData().getData();
		if(list!=null && list.size()>0){
			Map<String, Object> map =  CP_bj11x5.getONE(list);
			@SuppressWarnings("unchecked")
			List<Integer> ONElist = (List<Integer>) map.get("list");
			MethodBean mb = (MethodBean) map.get("bean");
			System.out.println(ONElist.toString());
			RecordBean rb = new RecordBean();
			rb.setONElist(ONElist.toString());
			rb.setMethodBean(mb);
			records.put(String.valueOf(Integer.valueOf(list.get(0).getExpect())+1), rb);
			if(records.size()>20){
				Set<Integer> set = new TreeSet<Integer>();
				 for(String a:map.keySet()){
					 set.add(Integer.valueOf(a));
				 }
				records.remove(set.iterator().next().toString());
			}
			System.out.println(records);
			System.out.println("list长度："+list.size());
			request.setAttribute("numONE", ONElist.toString());
			request.setAttribute("bean", mb);
			for(CaipiaoBean cb:list){
				cb.setRecordBean(records.get(cb.getExpect()));
			}
		}
		request.setAttribute("list", list);
		request.getRequestDispatcher("WEB-INF/CPresult.jsp").forward(request, response);
	}

}
