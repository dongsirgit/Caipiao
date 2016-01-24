package com.ldm;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.ldm.util.CP_bj11x5;


public class caipiaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
			List<Integer> ONElist = CP_bj11x5.getONE(list);
			System.out.println(ONElist.toString());
			request.setAttribute("numONE", ONElist.toString());
		}
		request.setAttribute("list", list);
		request.getRequestDispatcher("WEB-INF/CPresult.jsp").forward(request, response);
	}

}
