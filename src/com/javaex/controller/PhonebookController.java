package com.javaex.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;

@WebServlet("/pbc") // PhonebookController
public class PhonebookController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("PhonebookController 시작");

		String act = request.getParameter("action");

//		System.out.println(act);

//		case 로 쓰면 좋지만 변수 안먹는 경우가 있어서 if문으로 만든다

		if ("list".equals(act)) {
			System.out.println("act=list");

			PhoneDao phoneDao = new PhoneDao();
			List<PersonVo> personList = phoneDao.getList();

//			html과 list를 섞어서 표현해야한다.
//			servlet 으로는 표현이 복잡하다 -> 해결법 jsp를 이용.

//			attribute란 Servlet간 공유하는 객체
			request.setAttribute("pList", personList);
			PrintWriter out = response.getWriter(); out.println("<head>");

//			포워드
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/list.jsp");
			rd.forward(request, response);

			System.out.println(personList);

		} else if ("writeForm".equals(act)) {
			System.out.println("act=writeForm");

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/writeForm.jsp");
			rd.forward(request, response);

		} else if ("write".equals(act)) {
			System.out.println("action=write");

//			파라미터 3개를 꺼내온다
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");

//			vo로 만든다
			PersonVo personVo = new PersonVo(name, hp, company);
			System.out.println(personVo);

//			dao 메모리 올린다
			PhoneDao phoneDao = new PhoneDao();

//			dao.insert(vo);
			phoneDao.ContactsInput(personVo);

//			리다이렉트 (지금 상황에서 포워드보다 낫다)
//			리다이렉트-> 서버-클라 사이클 돌때 
//			포워드 -> 서버 내 사이클 돌때
			response.sendRedirect("/phonebook2/pbc?action=list");
			
			
			
		} else if ("updateForm".equals(act)) {
			System.out.println("action=updateForm");
			//list 부터 id 식별자를 받아 form에 출력 
//			id 형변환
			int id = Integer.parseInt(request.getParameter("id"));
			
//			숫자로 변경한 id로 대상 식별
			PersonVo personVo = new PhoneDao().getPerson(id);
			System.out.println("personVo 출력: "+personVo);
			
//			Action으로 넘어온 값을 변경시킨후 JSP 페이지로 넘겨주기
			request.setAttribute("psnVo", personVo);
			
//			포워드 
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/updateForm.jsp");
			rd.forward(request, response);
			
			
		} else if ("update".equals(act)) {
			System.out.println("update");
			
//			파라미터 4개를 꺼내온다
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");

//			형변환
			int personId = Integer.parseInt(request.getParameter("id"));
			System.out.println("수정할번호 "+personId);
//			vo로 만든다
			PersonVo personVo = new PersonVo(personId, name, hp, company);
			System.out.println(personVo);

//			dao 메모리 올린다
			PhoneDao phoneDao = new PhoneDao();

//			쿼리 처리
			phoneDao.ContactsUpdate(personVo);
			
//			리다이렉트 - 포워드 방식 쓰면 에러남
			response.sendRedirect("/phonebook2/pbc?action=list");
			
			
		} else if ("delete".equals(act)) {
			System.out.println("delete 시작");
			
//			연락처 id 가져오기 
			String id = request.getParameter("id");

//			형변환
			int personId = Integer.parseInt(request.getParameter("id"));

//			dao 메모리 올린다
			PhoneDao phoneDao = new PhoneDao();

//			쿼리 처리
			phoneDao.ContactsRemove(personId);
			
			
//			리다이렉트 쓸것 - 포워드 방식하면 에러 
			response.sendRedirect("/phonebook2/pbc?action=list");
			
		} else {
			System.out.println("파라미터 값 없음");
			
		} // if 문 종료

	} // doGet 종료

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

} // PhonebookController 종료
